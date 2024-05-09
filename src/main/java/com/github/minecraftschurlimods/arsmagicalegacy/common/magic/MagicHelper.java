package com.github.minecraftschurlimods.arsmagicalegacy.common.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.PlayerLevelUpEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IMagicHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMCriteriaTriggers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ATTACHMENT_TYPES;

public final class MagicHelper implements IMagicHelper {
    private static final Lazy<MagicHelper> INSTANCE = Lazy.concurrentOf(MagicHelper::new);
    private static final Supplier<AttachmentType<MagicHolder>> MAGIC = ATTACHMENT_TYPES.register("magic", () -> AttachmentType.builder(MagicHolder::new).serialize(MagicHolder.CODEC).copyOnDeath().copyHandler(MagicHolder::copy).build());

    private MagicHelper() {
    }

    /**
     * @return The only instance of this class.
     */
    public static MagicHelper instance() {
        return INSTANCE.get();
    }

    @Override
    public int getLevel(Player player) {
        return player.getData(MAGIC).getLevel();
    }

    @Override
    public float getXp(Player player) {
        return player.getData(MAGIC).getXp();
    }

    @Override
    public float getXpForNextLevel(int level) {
        return level == 0 ? 0 : Config.SERVER.LEVELING_MULTIPLIER.get().floatValue() * (float) Math.pow(Config.SERVER.LEVELING_BASE.get().floatValue(), level);
    }

    @Override
    public void awardXp(Player player, float amount) {
        setXp(player, getXp(player) + amount);
    }

    @Override
    public void setXp(Player player, float amount) {
        MagicHolder holder = player.getData(MAGIC);
        int level = holder.getLevel();
        float xp = Math.max(0, amount);
        float xpForNextLevel = getXpForNextLevel(level);
        while (xp >= xpForNextLevel) {
            xp -= xpForNextLevel;
            level++;
            NeoForge.EVENT_BUS.post(new PlayerLevelUpEvent(player, level));
            if (player instanceof ServerPlayer serverPlayer) {
                AMCriteriaTriggers.PLAYER_LEVEL_UP.get().trigger(serverPlayer, level);
            }
            xpForNextLevel = getXpForNextLevel(level);
        }
        holder.setXp(xp);
        holder.setLevel(level);
        syncToPlayer(player);
    }

    @Override
    public void awardLevel(Player player, int level) {
        setLevel(player, getLevel(player) + level);
    }

    @Override
    public void setLevel(Player player, int level) {
        MagicHolder holder = player.getData(MAGIC);
        int oldLevel = holder.getLevel();
        holder.setLevel(level);
        if (level > oldLevel && level > 0) {
            for (int i = oldLevel + 1; i <= level; i++) {
                NeoForge.EVENT_BUS.post(new PlayerLevelUpEvent(player, i));
                if (player instanceof ServerPlayer serverPlayer) {
                    AMCriteriaTriggers.PLAYER_LEVEL_UP.get().trigger(serverPlayer, level);
                }
            }
        }
        syncToPlayer(player);
    }

    @Override
    public boolean knowsMagic(Player player) {
        return !Config.SERVER.REQUIRE_COMPENDIUM_CRAFTING.get() || player.isCreative() || player.isSpectator() || getLevel(player) > 0;
    }

    /**
     * Syncs the attachment to the client.
     *
     * @param player The player to sync to.
     */
    public void syncToPlayer(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        serverPlayer.connection.send(new MagicSyncPacket(player.getData(MAGIC)));
    }

    public static void registerSyncPacket(PayloadRegistrar registrar) {
        registrar.playToClient(MagicSyncPacket.TYPE, MagicSyncPacket.STREAM_CODEC, MagicSyncPacket::handle);
    }

    private record MagicSyncPacket(MagicHolder data) implements CustomPacketPayload {
        public static final Type<MagicSyncPacket> TYPE = new Type<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "magic_sync"));
        public static final StreamCodec<RegistryFriendlyByteBuf, MagicSyncPacket> STREAM_CODEC = MagicHolder.STREAM_CODEC.map(MagicSyncPacket::new, MagicSyncPacket::data);

        private void handle(IPayloadContext context) {
            context.player().setData(MAGIC, this.data);
        }

        @Override
        public Type<MagicSyncPacket> type() {
            return TYPE;
        }
    }

    public static class MagicHolder {
        public static final Codec<MagicHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.FLOAT.fieldOf("xp").forGetter(MagicHolder::getXp),
                Codec.INT.fieldOf("level").forGetter(MagicHolder::getLevel)
        ).apply(inst, MagicHolder::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, MagicHolder> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.FLOAT,
                MagicHolder::getXp,
                ByteBufCodecs.VAR_INT,
                MagicHolder::getLevel,
                MagicHolder::new
        );
        private float xp;
        private int level;

        private MagicHolder(float xp, int level) {
            this.xp = xp;
            this.level = level;
        }

        private MagicHolder() {}

        public float getXp() {
            return xp;
        }

        public void setXp(float xp) {
            this.xp = xp;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public static MagicHolder copy(MagicHolder magicHolder, IAttachmentHolder owner, HolderLookup.Provider provider) {
            MagicHolder newInst = new MagicHolder();
            newInst.setXp(magicHolder.getXp());
            newInst.setLevel(magicHolder.getLevel());
            return newInst;
        }
    }
}
