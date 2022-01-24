package com.github.minecraftschurlimods.arsmagicalegacy.common.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IRiftHelper;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.items.ItemStackHandler;

public final class RiftHelper implements IRiftHelper {
    private static final Lazy<RiftHelper> INSTANCE = Lazy.concurrentOf(RiftHelper::new);
    private static final Capability<RiftHolder> RIFT = CapabilityManager.get(new CapabilityToken<>() {
    });

    private RiftHelper() {
    }

    public static RiftHelper instance() {
        return INSTANCE.get();
    }

    public static Capability<RiftHolder> getRiftCapability() {
        return RIFT;
    }

    @Override
    public ItemStackHandler getRift(Player player) {
        return player.getCapability(RIFT).orElseThrow(() -> new RuntimeException("Could not retrieve rift capability for LivingEntity %s{%s}".formatted(player.getGameProfile().getName(), player.getGameProfile().getId())));
    }

    public void syncOnDeath(Player original, Player player) {
        original.getCapability(RIFT).ifPresent(rift -> player.getCapability(RIFT).ifPresent(holder -> holder.onSync(rift)));
    }

    public static final class RiftHolder extends ItemStackHandler {
        public static final Codec<RiftHolder> CODEC = CompoundTag.CODEC.xmap(RiftHolder::new, RiftHolder::serializeNBT);

        public RiftHolder() {
            super(54);
        }

        private RiftHolder(CompoundTag tag) {
            deserializeNBT(tag);
        }

        public void onSync(RiftHolder rift) {
            this.deserializeNBT(rift.serializeNBT());
        }
    }
}
