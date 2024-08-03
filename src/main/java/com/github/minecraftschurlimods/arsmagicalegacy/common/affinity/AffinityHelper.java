package com.github.minecraftschurlimods.arsmagicalegacy.common.affinity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ATTACHMENT_TYPES;

public final class AffinityHelper implements IAffinityHelper {
    public static final float MAX_DEPTH = 1F;
    private static final Lazy<AffinityHelper> INSTANCE = Lazy.of(AffinityHelper::new);
    private static final Supplier<AttachmentType<AffinityHolder>> AFFINITY = ATTACHMENT_TYPES.register("affinity", () -> AttachmentType.builder(AffinityHolder::empty).serialize(AffinityHolder.CODEC).copyOnDeath().copyHandler(AffinityHolder::copy).build());
    private static final float ADJACENT_FACTOR = 0.25f;
    private static final float MINOR_OPPOSING_FACTOR = 0.5f;
    private static final float MAJOR_OPPOSING_FACTOR = 0.75f;

    private AffinityHelper() {}

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
        return getEssenceForAffinity(affinity.unwrapKey().orElseThrow());
    }

    @Override
    public ItemStack getEssenceForAffinity(ResourceKey<Affinity> affinity) {
        return getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), affinity);
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
    public ItemStack getTomeForAffinity(ResourceKey<Affinity> affinity) {
        return getStackForAffinity(AMItems.AFFINITY_TOME.get(), affinity);
    }

    @Override
    public <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, ResourceLocation aff) {
        
        return getStackForAffinity(item, ArsMagicaAPI.get().getAffinityRegistry().get(aff));
    }

    @Override
    public <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, Affinity affinity) {
        ItemStack stack = new ItemStack(item);
        stack.set(AMDataComponents.AFFINITY, affinity);
        return stack;
    }

    @Override
    public <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, Holder<Affinity> affinity) {
        return getStackForAffinity(item, affinity.unwrapKey().orElseThrow());
    }

    @Override
    public <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, ResourceKey<Affinity> affinity) {
        return getStackForAffinity(item, ArsMagicaAPI.get().getAffinityRegistry().get(affinity));
    }

    @Override
    public Affinity getAffinityForStack(ItemStack stack) {
        return stack.getOrDefault(AMDataComponents.AFFINITY, ArsMagicaAPI.get().getAffinityRegistry().get(Affinity.NONE.location()));
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
    public double getAffinityDepth(Player player, Holder<Affinity> affinity) {
        return getAffinityDepth(player, affinity.unwrapKey().get().location());
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
    public double getAffinityDepthOrElse(Player player, Holder<Affinity> affinity, double defaultValue) {
        return getAffinityDepthOrElse(player, affinity.unwrapKey().get().location(), defaultValue);
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
    public void setAffinityDepth(Player player, Holder<Affinity> affinity, float amount) {
        setAffinityDepth(player, affinity.unwrapKey().get().location(), amount);
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
    public void increaseAffinityDepth(Player player, Holder<Affinity> affinity, float amount) {
        increaseAffinityDepth(player, affinity.unwrapKey().get().location(), amount);
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
    public void decreaseAffinityDepth(Player player, Holder<Affinity> affinity, float amount) {
        decreaseAffinityDepth(player, affinity.unwrapKey().get().location(), amount);
    }

    @Override
    public void applyAffinityShift(Player player, ResourceLocation affinity, float shift) {
        applyAffinityShift(player, Objects.requireNonNull(ArsMagicaAPI.get().getAffinityRegistry().get(affinity)), shift);
    }

    @Override
    public void applyAffinityShift(Player player, Affinity affinity, float shift) {
        if (ArsMagicaAPI.get().getAffinityRegistry().getResourceKey(affinity).map(it -> it == Affinity.NONE).orElse(false)) return;
        AffinityHolder holder = player.getData(AFFINITY);
        if (holder.locked()) return;
        float adjacentDecrement = shift * ADJACENT_FACTOR;
        float minorOppositeDecrement = shift * MINOR_OPPOSING_FACTOR;
        float majorOppositeDecrement = shift * MAJOR_OPPOSING_FACTOR;
        holder.addToAffinity(affinity.getId(), shift);
        if (holder.getAffinityDepth(affinity) == MAX_DEPTH) {
            holder.setLocked(true);
        }
        for (Holder<Affinity> adjacent : affinity.getAdjacentAffinities()) {
            adjacent.unwrapKey().ifPresent(key -> holder.subtractFromAffinity(key.location(), adjacentDecrement));
        }
        for (Holder<Affinity> minorOpposite : affinity.minorOpposites()) {
            minorOpposite.unwrapKey().ifPresent(key -> holder.subtractFromAffinity(key.location(), minorOppositeDecrement));
        }
        for (Holder<Affinity> majorOpposite : affinity.majorOpposites()) {
            majorOpposite.unwrapKey().ifPresent(key -> holder.subtractFromAffinity(key.location(), majorOppositeDecrement));
        }
        Holder<Affinity> directOpposite = affinity.directOpposite();
        directOpposite.unwrapKey().ifPresent(key -> holder.subtractFromAffinity(key.location(), shift));
        syncToPlayer(player);
    }

    @Override
    public void applyAffinityShift(Player player, Holder<Affinity> affinity, float shift) {
        applyAffinityShift(player, affinity.value(), shift);
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
        serverPlayer.connection.send(new AffinitySyncPacket(player.getData(AFFINITY)));
    }

    public static void registerSyncPacket(PayloadRegistrar registrar) {
        registrar.playToClient(AffinitySyncPacket.TYPE, AffinitySyncPacket.STREAM_CODEC, AffinitySyncPacket::handle);
    }

    private record AffinitySyncPacket(AffinityHolder data) implements CustomPacketPayload {
        public static final Type<AffinitySyncPacket> TYPE = new Type<>(ArsMagicaAPI.resource("affinity_sync"));
        public static final StreamCodec<RegistryFriendlyByteBuf, AffinitySyncPacket> STREAM_CODEC = AffinityHolder.STREAM_CODEC.map(AffinitySyncPacket::new, AffinitySyncPacket::data);

        private void handle(IPayloadContext context) {
            context.player().setData(AFFINITY, this.data);
        }

        @Override
        public Type<AffinitySyncPacket> type() {
            return TYPE;
        }
    }

    public static final class AffinityHolder {
        public static final Codec<AffinityHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.unboundedMap(ResourceLocation.CODEC, Codec.DOUBLE).fieldOf("depths").forGetter(AffinityHolder::depths),
                Codec.BOOL.fieldOf("locked").forGetter(AffinityHolder::locked)
        ).apply(inst, AffinityHolder::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, AffinityHolder> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.map(HashMap::new, ResourceLocation.STREAM_CODEC, ByteBufCodecs.DOUBLE),
                AffinityHolder::depths,
                ByteBufCodecs.BOOL,
                AffinityHolder::locked,
                AffinityHolder::new
        );
        private final Map<ResourceLocation, Double> depths;
        private boolean locked;

        public AffinityHolder(Map<ResourceLocation, Double> depths, boolean locked) {
            this.depths = new HashMap<>(depths);
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
            depths.compute(affinity, (rl, curr) -> Mth.clamp(curr != null ? curr + shift : shift, 0, MAX_DEPTH));
        }

        /**
         * Subtracts the given shift from the given affinity.
         *
         * @param affinity The id of the affinity to add the given shift to.
         * @param shift    The shift to subtract.
         */
        public void subtractFromAffinity(ResourceLocation affinity, float shift) {
            depths.compute(affinity, (rl, curr) -> Mth.clamp(curr != null ? curr - shift : shift, 0, MAX_DEPTH));
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

        public static AffinityHolder copy(AffinityHolder affinityHolder, IAttachmentHolder owner, HolderLookup.Provider affinityHolder2) {
            return new AffinityHolder(new HashMap<>(affinityHolder.depths()), affinityHolder.locked());
        }
    }
}
