package com.github.minecraftschurlimods.arsmagicalegacy.common.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IRiftHelper;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

public final class RiftHelper implements IRiftHelper {
    private static final Lazy<RiftHelper> INSTANCE = Lazy.concurrentOf(RiftHelper::new);
    private static final Capability<RiftHolder> RIFT = CapabilityManager.get(new CapabilityToken<>() {});

    private RiftHelper() {
    }

    /**
     * @return The only instance of this class.
     */
    public static RiftHelper instance() {
        return INSTANCE.get();
    }

    /**
     * @return The rift capability.
     */
    public static Capability<RiftHolder> getRiftCapability() {
        return RIFT;
    }

    @Override
    public LazyOptional<? extends ItemStackHandler> getRift(Player player) {
        return player.getCapability(RIFT);
    }

    /**
     * Called on player death, syncs the capability.
     *
     * @param original The now-dead player.
     * @param player   The respawning player.
     */
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

        /**
         * Syncs the values with the given data object.
         *
         * @param data The data object to sync with.
         */
        public void onSync(RiftHolder data) {
            deserializeNBT(data.serializeNBT());
        }
    }
}
