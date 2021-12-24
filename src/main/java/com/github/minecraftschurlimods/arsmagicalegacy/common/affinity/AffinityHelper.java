package com.github.minecraftschurlimods.arsmagicalegacy.common.affinity;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.codeclib.CodecHelper;
import com.github.minecraftschurli.simplenetlib.CodecPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class AffinityHelper implements IAffinityHelper {
    public static final float MAX_DEPTH = 1F;
    private static final Lazy<AffinityHelper> INSTANCE = Lazy.concurrentOf(AffinityHelper::new);
    private static final Capability<AffinityHolder> AFFINITY = CapabilityManager.get(new CapabilityToken<>() {
    });
    private static final float ADJACENT_FACTOR = 0.25f;
    private static final float MINOR_OPPOSING_FACTOR = 0.5f;
    private static final float MAJOR_OPPOSING_FACTOR = 0.75f;

    public static final class SyncPacket extends CodecPacket<AffinityHolder> {
        public SyncPacket(AffinityHolder data) {
            super(data);
        }

        public SyncPacket(FriendlyByteBuf buf) {
            super(buf);
        }

        @Override
        public void handle(NetworkEvent.Context context) {
            AffinityHelper.handleSync(data, context);
        }

        @Override
        protected Codec<AffinityHolder> getCodec() {
            return AffinityHolder.CODEC;
        }
    }

    /**
     * Registers the network packet to the network handler.
     */
    public static void init() {
        ArsMagicaLegacy.NETWORK_HANDLER.register(SyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
    }

    /**
     * @return The only instance of this class.
     */
    public static AffinityHelper instance() {
        return INSTANCE.get();
    }

    /**
     * @return The default capability
     */
    public static Capability<AffinityHolder> getCapability() {
        return AFFINITY;
    }

    @Override
    public ItemStack getEssenceForAffinity(ResourceLocation affinity) {
        return getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), affinity);
    }

    @Override
    public ItemStack getEssenceForAffinity(IAffinity affinity) {
        return getEssenceForAffinity(affinity.getId());
    }

    @Override
    public ItemStack getTomeForAffinity(ResourceLocation affinity) {
        return getStackForAffinity(AMItems.AFFINITY_TOME.get(), affinity);
    }

    @Override
    public ItemStack getTomeForAffinity(IAffinity affinity) {
        return getTomeForAffinity(affinity.getId());
    }

    @Override
    public <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, ResourceLocation aff) {
        ItemStack stack = new ItemStack(item);
        Optional.ofNullable(ArsMagicaAPI.get().getAffinityRegistry().getValue(aff)).ifPresent(affinity -> item.setAffinity(stack, affinity));
        return stack;
    }

    @Override
    public <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, IAffinity affinity) {
        return getStackForAffinity(item, affinity.getId());
    }

    @Override
    public IAffinity getAffinityForStack(ItemStack stack) {
        if (stack.getItem() instanceof IAffinityItem item) return item.getAffinity(stack);
        return Objects.requireNonNull(ArsMagicaAPI.get().getAffinityRegistry().getValue(IAffinity.NONE));
    }

    @Override
    public double getAffinityDepth(Player player, ResourceLocation affinity) {
        return getAffinityHolder(player).getAffinityDepth(affinity);
    }

    /**
     * Gets the affinity capability for the given player.
     *
     * @param player The player to get the affinity capability for.
     * @return The affinity capability for the given player.
     */
    public AffinityHolder getAffinityHolder(Player player) {
        return player.getCapability(AFFINITY).orElseThrow(() -> new RuntimeException("Could not retrieve affinity capability for player %s".formatted(player.getUUID())));
    }

    @Override
    public double getAffinityDepth(Player player, IAffinity affinity) {
        return getAffinityDepth(player, affinity.getId());
    }

    @Override
    public void applyAffinityShift(Player player, ResourceLocation affinity, float shift) {
        applyAffinityShift(player, Objects.requireNonNull(ArsMagicaAPI.get().getAffinityRegistry().getValue(affinity)), shift);
    }

    @Override
    public void applyAffinityShift(Player player, IAffinity affinity, float shift) {
        if (affinity.getRegistryName() == Affinity.NONE) return;
        AffinityHolder storage = getAffinityHolder(player);
        float adjacentDecrement = shift * ADJACENT_FACTOR;
        float minorOppositeDecrement = shift * MINOR_OPPOSING_FACTOR;
        float majorOppositeDecrement = shift * MAJOR_OPPOSING_FACTOR;
        storage.addToAffinity(affinity.getId(), shift);
        if (storage.getAffinityDepth(affinity) == MAX_DEPTH) {
            storage.setLocked(true);
        }
        for (ResourceLocation adjacent : affinity.getAdjacentAffinities()) {
            storage.subtractFromAffinity(adjacent, adjacentDecrement);
        }
        for (ResourceLocation minorOpposite : affinity.getMinorOpposingAffinities()) {
            storage.subtractFromAffinity(minorOpposite, minorOppositeDecrement);
        }
        for (ResourceLocation majorOpposite : affinity.getMajorOpposingAffinities()) {
            storage.subtractFromAffinity(majorOpposite, majorOppositeDecrement);
        }
        ResourceLocation directOpposite = affinity.getDirectOpposingAffinity();
        storage.subtractFromAffinity(directOpposite, shift);
        if (player instanceof ServerPlayer sp) {
            syncToPlayer(sp);
        }
    }

    /**
     * Called upon player death, syncs the capabilites.
     *
     * @param original The old player entity from the event.
     * @param player   The new player entity from the event.
     */
    public void syncOnDeath(Player original, Player player) {
        original.getCapability(AFFINITY).ifPresent(affinityHolder -> player.getCapability(AFFINITY).ifPresent(holder -> holder.onSync(affinityHolder)));
    }

    /**
     * Called upon player join, syncs the capabilites.
     *
     * @param player The player entity from the event.
     */
    public void syncToPlayer(Player player) {
        ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new SyncPacket(getAffinityHolder(player)), player);
    }

    private static void handleSync(AffinityHolder affinityHolder, NetworkEvent.Context context) {
        context.enqueueWork(() -> Minecraft.getInstance().player.getCapability(AFFINITY).ifPresent(holder -> holder.onSync(affinityHolder)));
    }

    public static final class AffinityHolder {
        public static final Codec<AffinityHolder> CODEC =
                RecordCodecBuilder.create(inst -> inst.group(
                        CodecHelper.mapOf(ResourceLocation.CODEC, Codec.DOUBLE).fieldOf("depths").forGetter(AffinityHolder::depths),
                        Codec.BOOL.fieldOf("locked").forGetter(AffinityHolder::locked)
                ).apply(inst, AffinityHolder::new));

        private final Map<ResourceLocation, Double> depths;
        private boolean locked = false;

        public AffinityHolder(Map<ResourceLocation, Double> depths, boolean locked) {
            this.depths = depths;
        }

        /**
         * @return A new empty AffinityHolder.
         */
        public static AffinityHolder empty() {
            return new AffinityHolder(new HashMap<>(), false);
        }

        /**
         * Get the depths map for this affinity holder.
         *
         * @return the unmodifiable view of the affinity depths map
         */
        public Map<ResourceLocation, Double> depths() {
            return Collections.unmodifiableMap(depths);
        }

        public boolean locked() {
            return locked;
        }

        /**
         * Runs the synchronization logic.
         *
         * @param affinityHolder The affinity capability to sync.
         */
        public void onSync(AffinityHolder affinityHolder) {
            depths.clear();
            depths.putAll(affinityHolder.depths());
        }

        /**
         * Get the depth for a given affinity.
         *
         * @param affinity the affinity to get the depth for
         * @return the depth of the requested affinity or 0 if not available
         */
        public double getAffinityDepth(ResourceLocation affinity) {
            return depths().getOrDefault(affinity, 0d);
        }

        /**
         * Get the depth for a given affinity.
         *
         * @param affinity the affinity to get the depth for
         * @return the depth of the requested affinity or 0 if not available
         */
        public double getAffinityDepth(IAffinity affinity) {
            return getAffinityDepth(affinity.getId());
        }

        public void addToAffinity(ResourceLocation affinity, float shift) {
            depths.compute(affinity, (rl, curr) -> Mth.clamp((curr != null ? curr : 0) + shift, 0, MAX_DEPTH));
        }

        public void subtractFromAffinity(ResourceLocation affinity, float shift) {
            depths.compute(affinity, (rl, curr) -> Mth.clamp((curr != null ? curr : 0) - shift, 0, MAX_DEPTH));
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (AffinityHolder) obj;
            return Objects.equals(this.depths, that.depths) && this.locked == that.locked;
        }

        @Override
        public int hashCode() {
            return Objects.hash(depths, locked);
        }

        @Override
        public String toString() {
            return "AffinityHolder[" + "depths=" + depths + ",locked=" + locked + ']';
        }
    }
}
