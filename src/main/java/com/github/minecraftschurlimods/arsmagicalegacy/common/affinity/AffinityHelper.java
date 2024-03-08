package com.github.minecraftschurlimods.arsmagicalegacy.common.affinity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.codeclib.CodecPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ATTACHMENT_TYPES;

public final class AffinityHelper implements IAffinityHelper {
    public static final float MAX_DEPTH = 1F;
    private static final Lazy<AffinityHelper> INSTANCE = Lazy.concurrentOf(AffinityHelper::new);
    private static final Supplier<AttachmentType<AffinityHolder>> AFFINITY = ATTACHMENT_TYPES.register("affinity", () -> AttachmentType.builder(AffinityHolder::empty).serialize(AffinityHolder.CODEC).copyOnDeath().copyHandler(AffinityHolder::copy).build());
    private static final float ADJACENT_FACTOR = 0.25f;
    private static final float MINOR_OPPOSING_FACTOR = 0.5f;
    private static final float MAJOR_OPPOSING_FACTOR = 0.75f;

    private AffinityHelper() {
    }

    /**
     * @return The only instance of this class.
     */
    public static AffinityHelper instance() {
        return INSTANCE.get();
    }

    @Override
    public ItemStack getEssenceForAffinity(ResourceLocation affinity) {
        return getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), affinity);
    }

    @Override
    public ItemStack getEssenceForAffinity(Affinity affinity) {
        return getEssenceForAffinity(affinity.getId());
    }

    @Override
    public ItemStack getEssenceForAffinity(Holder<Affinity> affinity) {
        return getEssenceForAffinity(affinity.unwrapKey().get().location());
    }

    @Override
    public ItemStack getTomeForAffinity(ResourceLocation affinity) {
        return getStackForAffinity(AMItems.AFFINITY_TOME.get(), affinity);
    }

    @Override
    public ItemStack getTomeForAffinity(Affinity affinity) {
        return getTomeForAffinity(affinity.getId());
    }

    @Override
    public ItemStack getTomeForAffinity(Holder<Affinity> affinity) {
        return getTomeForAffinity(affinity.unwrapKey().get().location());
    }

    @Override
    public <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, ResourceLocation aff) {
        ItemStack stack = new ItemStack(item);
        Optional.ofNullable(ArsMagicaAPI.get().getAffinityRegistry().get(aff)).ifPresent(affinity -> item.setAffinity(stack, affinity));
        return stack;
    }

    @Override
    public <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, Affinity affinity) {
        return getStackForAffinity(item, affinity.getId());
    }

    @Override
    public <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, Holder<Affinity> affinity) {
        return getStackForAffinity(item, affinity.unwrapKey().get().location());
    }

    @Override
    public Affinity getAffinityForStack(ItemStack stack) {
        if (stack.getItem() instanceof IAffinityItem item) return item.getAffinity(stack);
        return Objects.requireNonNull(ArsMagicaAPI.get().getAffinityRegistry().get(Affinity.NONE));
    }

    @Override
    public double getAffinityDepth(Player player, ResourceLocation affinity) {
        return player.getData(AFFINITY).getAffinityDepth(affinity);
    }

    @Override
    public double getAffinityDepth(Player player, Affinity affinity) {
        return getAffinityDepth(player, affinity.getId());
    }

    @Override
    public double getAffinityDepthOrElse(Player player, ResourceLocation affinity, double defaultValue) {
        return player.isDeadOrDying() ? defaultValue : getAffinityDepth(player, affinity);
    }

    @Override
    public double getAffinityDepthOrElse(Player player, Affinity affinity, double defaultValue) {
        return getAffinityDepthOrElse(player, affinity.getId(), defaultValue);
    }

    @Override
    public void setAffinityDepth(Player player, ResourceLocation affinity, float amount) {
        AffinityHolder holder = player.getData(AFFINITY);
        holder.setAffinity(affinity, amount);
        syncToPlayer(player);
    }

    @Override
    public void setAffinityDepth(Player player, Affinity affinity, float amount) {
        setAffinityDepth(player, affinity.getId(), amount);
    }

    @Override
    public void increaseAffinityDepth(Player player, ResourceLocation affinity, float amount) {
        AffinityHolder holder = player.getData(AFFINITY);
        holder.addToAffinity(affinity, amount);
        syncToPlayer(player);
    }

    @Override
    public void increaseAffinityDepth(Player player, Affinity affinity, float amount) {
        increaseAffinityDepth(player, affinity.getId(), amount);
    }

    @Override
    public void decreaseAffinityDepth(Player player, ResourceLocation affinity, float amount) {
        AffinityHolder holder = player.getData(AFFINITY);
        holder.subtractFromAffinity(affinity, amount);
        syncToPlayer(player);
    }

    @Override
    public void decreaseAffinityDepth(Player player, Affinity affinity, float amount) {
        decreaseAffinityDepth(player, affinity.getId(), amount);
    }

    @Override
    public void applyAffinityShift(Player player, ResourceLocation affinity, float shift) {
        applyAffinityShift(player, Objects.requireNonNull(ArsMagicaAPI.get().getAffinityRegistry().get(affinity)), shift);
    }

    @Override
    public void applyAffinityShift(Player player, Affinity affinity, float shift) {
        if (affinity.getId() == Affinity.NONE) return;
        AffinityHolder holder = player.getData(AFFINITY);
        if (holder.locked()) return;
        float adjacentDecrement = shift * ADJACENT_FACTOR;
        float minorOppositeDecrement = shift * MINOR_OPPOSING_FACTOR;
        float majorOppositeDecrement = shift * MAJOR_OPPOSING_FACTOR;
        holder.addToAffinity(affinity.getId(), shift);
        if (holder.getAffinityDepth(affinity) == MAX_DEPTH) {
            holder.setLocked(true);
        }
        for (ResourceLocation adjacent : affinity.getAdjacentAffinities()) {
            holder.subtractFromAffinity(adjacent, adjacentDecrement);
        }
        for (ResourceLocation minorOpposite : affinity.minorOpposites()) {
            holder.subtractFromAffinity(minorOpposite, minorOppositeDecrement);
        }
        for (ResourceLocation majorOpposite : affinity.majorOpposites()) {
            holder.subtractFromAffinity(majorOpposite, majorOppositeDecrement);
        }
        ResourceLocation directOpposite = affinity.directOpposite();
        holder.subtractFromAffinity(directOpposite, shift);
        syncToPlayer(player);
    }

    @Override
    public void lock(Player player) {
        AffinityHolder holder = player.getData(AFFINITY);
        holder.setLocked(true);
    }

    @Override
    public void unlock(Player player) {
        AffinityHolder holder = player.getData(AFFINITY);
        holder.setLocked(false);
    }

    @Override
    public void updateLock(Player player) {
        AffinityHolder holder = player.getData(AFFINITY);
        for (Affinity affinity : ArsMagicaAPI.get().getAffinityRegistry()) {
            if (affinity.getId().equals(Affinity.NONE)) continue;
            if (holder.getAffinityDepth(affinity) == MAX_DEPTH) {
                lock(player);
                return;
            }
        }
        unlock(player);
        syncToPlayer(player);
    }

    /**
     * Syncs the attachment to the client.
     *
     * @param player The player to sync to.
     */
    public void syncToPlayer(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        PacketDistributor.PLAYER.with(serverPlayer).send(new AffinitySyncPacket(player.getData(AFFINITY)));
    }

    public static void registerSyncPacket(IPayloadRegistrar registrar) {
        registrar.play(AffinitySyncPacket.ID, AffinitySyncPacket::new, builder -> builder.client(AffinitySyncPacket::handle));
    }

    private static final class AffinitySyncPacket extends CodecPacket<AffinityHolder> {
        public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "affinity_sync");

        public AffinitySyncPacket(AffinityHolder data) {
            super(data);
        }

        public AffinitySyncPacket(FriendlyByteBuf buf) {
            super(buf);
        }

        @Override
        protected Codec<AffinityHolder> codec() {
            return AffinityHolder.CODEC;
        }

        @Override
        public ResourceLocation id() {
            return ID;
        }

        private void handle(PlayPayloadContext context) {
            context.workHandler().submitAsync(() -> context.player().orElseThrow().setData(AFFINITY, this.data));
        }
    }

    public static final class AffinityHolder {
        public static final Codec<AffinityHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.unboundedMap(ResourceLocation.CODEC, Codec.DOUBLE).<Map<ResourceLocation, Double>>xmap(HashMap::new, Function.identity()).fieldOf("depths").forGetter(AffinityHolder::depths),
                Codec.BOOL.fieldOf("locked").forGetter(AffinityHolder::locked)
        ).apply(inst, AffinityHolder::new));
        private final Map<ResourceLocation, Double> depths;
        private boolean locked;

        public AffinityHolder(Map<ResourceLocation, Double> depths, boolean locked) {
            this.depths = depths;
            this.locked = locked;
        }

        /**
         * @return An affinity holder.
         */
        public static AffinityHolder empty() {
            return new AffinityHolder(new HashMap<>(), false);
        }

        /**
         * @return An unmodifiable list of all affinity depths in this holder.
         */
        public Map<ResourceLocation, Double> depths() {
            return Collections.unmodifiableMap(depths);
        }

        /**
         * @return Whether this affinity holder is locked or not.
         */
        public boolean locked() {
            return locked;
        }

        /**
         * @param affinity The id of the affinity to get the depth for.
         * @return The depth for the given affinity.
         */
        public double getAffinityDepth(ResourceLocation affinity) {
            return depths().getOrDefault(affinity, 0d);
        }

        /**
         * @param affinity The affinity to get the depth for.
         * @return The depth for the given affinity.
         */
        public double getAffinityDepth(Affinity affinity) {
            return getAffinityDepth(affinity.getId());
        }

        /**
         * Adds the given shift to the given affinity.
         *
         * @param affinity The id of the affinity to add the given shift to.
         * @param shift    The shift to add.
         */
        public void setAffinity(ResourceLocation affinity, float shift) {
            depths.compute(affinity, (rl, curr) -> Mth.clamp((double) shift, 0, MAX_DEPTH));
        }

        /**
         * Adds the given shift to the given affinity.
         *
         * @param affinity The id of the affinity to add the given shift to.
         * @param shift    The shift to add.
         */
        public void addToAffinity(ResourceLocation affinity, float shift) {
            depths.compute(affinity, (rl, curr) -> Mth.clamp((curr != null ? curr : 0) + shift, 0, MAX_DEPTH));
        }

        /**
         * Subtracts the given shift from the given affinity.
         *
         * @param affinity The id of the affinity to add the given shift to.
         * @param shift    The shift to subtract.
         */
        public void subtractFromAffinity(ResourceLocation affinity, float shift) {
            depths.compute(affinity, (rl, curr) -> Mth.clamp((curr != null ? curr : 0) - shift, 0, MAX_DEPTH));
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != getClass()) return false;
            AffinityHolder that = (AffinityHolder) obj;
            return Objects.equals(depths, that.depths) && locked == that.locked;
        }

        @Override
        public int hashCode() {
            return Objects.hash(depths, locked);
        }

        @Override
        public String toString() {
            return "AffinityHolder[" + "depths=" + depths + ",locked=" + locked + ']';
        }

        public static AffinityHolder copy(IAttachmentHolder owner, AffinityHolder affinityHolder) {
            return new AffinityHolder(new HashMap<>(affinityHolder.depths()), affinityHolder.locked());
        }
    }
}
