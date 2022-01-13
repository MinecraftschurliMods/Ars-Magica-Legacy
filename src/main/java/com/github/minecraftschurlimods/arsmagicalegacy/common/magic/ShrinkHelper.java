package com.github.minecraftschurlimods.arsmagicalegacy.common.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IShrinkHelper;
import com.github.minecraftschurlimods.simplenetlib.CodecPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.network.NetworkEvent;

public final class ShrinkHelper implements IShrinkHelper {
    private static final Lazy<ShrinkHelper> INSTANCE = Lazy.concurrentOf(ShrinkHelper::new);
    private static final Capability<ShrinkHolder> SHRINK = CapabilityManager.get(new CapabilityToken<>() {});

    private ShrinkHelper() {
    }

    /**
     * @return The only instance of this class.
     */
    public static ShrinkHelper instance() {
        return INSTANCE.get();
    }

    /**
     * @return The shrink capability.
     */
    public static Capability<ShrinkHelper.ShrinkHolder> getShrinkCapability() {
        return SHRINK;
    }

    @Override
    public boolean isShrunk(Player player) {
        return getShrinkHolder(player).isShrunk();
    }

    @Override
    public void setShrunk(Player player, boolean shrunk) {
        getShrinkHolder(player).setShrunk(shrunk);
        syncShrink(player);
    }

    /**
     * Called on player death, syncs the capability and the attribute.
     *
     * @param original The old player from the event.
     * @param player   The new player from the event.
     */
    public void syncOnDeath(Player original, Player player) {
        original.getCapability(SHRINK).ifPresent(shrinkHolder -> player.getCapability(SHRINK).ifPresent(holder -> holder.onSync(shrinkHolder)));
        syncShrink(player);
    }

    public void syncShrink(Player player) {
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new ShrinkHelper.ShrinkSyncPacket(getShrinkHolder(player)), player);
    }

    private ShrinkHelper.ShrinkHolder getShrinkHolder(LivingEntity livingEntity) {
        if (livingEntity instanceof Player && livingEntity.isDeadOrDying()) {
            livingEntity.reviveCaps();
        }
        ShrinkHelper.ShrinkHolder shrinkHolder = livingEntity.getCapability(SHRINK).orElseThrow(() -> new RuntimeException("Could not retrieve shrink capability for LivingEntity %s{%s}".formatted(livingEntity.getDisplayName().getString(), livingEntity.getUUID())));
        if (livingEntity instanceof Player && livingEntity.isDeadOrDying()) {
            livingEntity.invalidateCaps();
        }
        return shrinkHolder;
    }

    private static void handleShrinkSync(ShrinkHelper.ShrinkHolder data, NetworkEvent.Context context) {
        context.enqueueWork(() -> Minecraft.getInstance().player.getCapability(SHRINK).ifPresent(holder -> holder.onSync(data)));
    }

    public static final class ShrinkSyncPacket extends CodecPacket<ShrinkHelper.ShrinkHolder> {
        public ShrinkSyncPacket(ShrinkHelper.ShrinkHolder data) {
            super(data);
        }

        public ShrinkSyncPacket(FriendlyByteBuf buf) {
            super(buf);
        }

        @Override
        public void handle(NetworkEvent.Context context) {
            handleShrinkSync(data, context);
        }

        @Override
        protected Codec<ShrinkHelper.ShrinkHolder> getCodec() {
            return ShrinkHelper.ShrinkHolder.CODEC;
        }
    }

    public static final class ShrinkHolder {
        public static final Codec<ShrinkHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(Codec.BOOL.fieldOf("shrunk").forGetter(
                ShrinkHolder::isShrunk)).apply(inst, shrunk -> {
            ShrinkHolder holder = new ShrinkHolder();
            holder.setShrunk(shrunk);
            return holder;
        }));
        private boolean shrunk;

        public boolean isShrunk() {
            return shrunk;
        }

        public void setShrunk(boolean shrunk) {
            this.shrunk = shrunk;
        }

        public void onSync(ShrinkHolder data) {
            this.shrunk = data.shrunk;
        }
    }
}
