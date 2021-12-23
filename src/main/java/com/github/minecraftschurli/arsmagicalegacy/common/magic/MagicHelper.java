package com.github.minecraftschurli.arsmagicalegacy.common.magic;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.event.PlayerLevelUpEvent;
import com.github.minecraftschurli.arsmagicalegacy.api.magic.IMagicHelper;
import com.github.minecraftschurli.simplenetlib.CodecPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.network.NetworkEvent;

public final class MagicHelper implements IMagicHelper {
    private static final Lazy<MagicHelper> INSTANCE = Lazy.concurrentOf(MagicHelper::new);
    private static final Capability<MagicHolder> MAGIC = CapabilityManager.get(new CapabilityToken<>() {
    });

    private MagicHelper() {
    }

    /**
     * @return The only instance of this class.
     */
    public static MagicHelper instance() {
        return INSTANCE.get();
    }

    /**
     * @return The magic capability.
     */
    public static Capability<MagicHolder> getMagicCapability() {
        return MAGIC;
    }

    @Override
    public int getLevel(Player player) {
        return getMagicHolder(player).getLevel();
    }

    @Override
    public float getXp(Player player) {
        return getMagicHolder(player).getXp();
    }

    @Override
    public void awardXp(Player player, float amount) {
        MagicHolder magicHolder = getMagicHolder(player);
        float n = magicHolder.getXp() + amount;
        int l = magicHolder.getLevel();
        while (true) {
            float xpForNextLevel = getXpForNextLevel(l);
            if (n < xpForNextLevel) break;
            n -= xpForNextLevel;
            l++;
            MinecraftForge.EVENT_BUS.post(new PlayerLevelUpEvent(player, l));
        }
        magicHolder.setXp(n);
        magicHolder.setLevel(l);
        syncMagic(player);
    }

    @Override
    public boolean knowsMagic(Player player) {
        return player.isCreative() || player.isSpectator() || getLevel(player) > 0;
    }

    /**
     * Called on player death, syncs the capability.
     *
     * @param original The old player from the event.
     * @param player   The new player from the event.
     */
    public void syncOnDeath(Player original, Player player) {
        original.getCapability(MAGIC).ifPresent(magicHolder -> player.getCapability(MAGIC).ifPresent(holder -> holder.onSync(magicHolder)));
    }

    public void syncMagic(Player player) {
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new MagicSyncPacket(getMagicHolder(player)), player);
    }

    private MagicHolder getMagicHolder(Player player) {
        if (player.isDeadOrDying()) {
            player.reviveCaps();
        }
        MagicHolder magicHolder = player.getCapability(MAGIC).orElseThrow(() -> new RuntimeException("Could not retrieve magic capability for player %s{%s}".formatted(player.getDisplayName().getString(), player.getUUID())));
        if (player.isDeadOrDying()) {
            player.invalidateCaps();
        }
        return magicHolder;
    }

    private float getXpForNextLevel(int level) {
        if (level == 0) return 0;
        return 2.5f * (float) Math.pow(1.2, level);
    }

    private static void handleMagicSync(MagicHolder data, NetworkEvent.Context context) {
        context.enqueueWork(() -> Minecraft.getInstance().player.getCapability(MAGIC).ifPresent(holder -> holder.onSync(data)));
    }

    public static final class MagicSyncPacket extends CodecPacket<MagicHolder> {
        public MagicSyncPacket(MagicHolder data) {
            super(data);
        }

        public MagicSyncPacket(FriendlyByteBuf buf) {
            super(buf);
        }

        @Override
        public void handle(NetworkEvent.Context context) {
            MagicHelper.handleMagicSync(data, context);
        }

        @Override
        protected Codec<MagicHolder> getCodec() {
            return MagicHolder.CODEC;
        }
    }

    public static class MagicHolder {
        public static final Codec<MagicHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(Codec.FLOAT.fieldOf("xp").forGetter(MagicHolder::getXp), Codec.INT.fieldOf("level").forGetter(MagicHolder::getLevel)).apply(inst, (xp, level) -> {
            MagicHolder magicHolder = new MagicHolder();
            magicHolder.setXp(xp);
            magicHolder.setLevel(level);
            return magicHolder;
        }));
        private float xp;
        private int level;

        public float getXp() {
            return this.xp;
        }

        public void setXp(float xp) {
            this.xp = xp;
        }

        public int getLevel() {
            return this.level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void onSync(MagicHolder data) {
            this.xp = data.xp;
            this.level = data.level;
        }
    }
}
