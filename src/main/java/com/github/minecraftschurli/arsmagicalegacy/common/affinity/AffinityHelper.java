package com.github.minecraftschurli.arsmagicalegacy.common.affinity;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class AffinityHelper implements IAffinityHelper {
    private static final Lazy<AffinityHelper> INSTANCE = Lazy.concurrentOf(AffinityHelper::new);

    public static AffinityHelper instance() {
        return INSTANCE.get();
    }

    @CapabilityInject(AffinityHolder.class)
    private static Capability<AffinityHolder> AFFINITY;

    public AffinityHolder getAffinityHolder(Player player) {
        return player.getCapability(AFFINITY).orElseThrow(() -> new RuntimeException("Could not retrieve affinity capability for player %s".formatted(player.getUUID())));
    }

    @Override
    public ItemStack getEssenceForAffinity(ResourceLocation affinity) {
        return getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), affinity);
    }

    @Override
    public ItemStack getTomeForAffinity(ResourceLocation affinity) {
        return getStackForAffinity(AMItems.AFFINITY_TOME.get(), affinity);
    }

    @Override
    public <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, ResourceLocation aff) {
        ItemStack stack = new ItemStack(item);
        Optional.ofNullable(ArsMagicaAPI.get().getAffinityRegistry().getValue(aff)).ifPresent(affinity -> item.setAffinity(stack, affinity));
        return stack;
    }

    @Override
    public IAffinity getAffinityForStack(ItemStack stack) {
        if (stack.getItem() instanceof IAffinityItem item) {
            return item.getAffinity(stack);
        }
        return Objects.requireNonNull(ArsMagicaAPI.get().getAffinityRegistry().getValue(IAffinity.NONE));
    }

    @Override
    public double getAffinityDepth(Player player, ResourceLocation affinity) {
        return getAffinityHolder(player).getAffinityDepth(affinity);
    }

    public static Capability<AffinityHolder> getCapability() {
        return AFFINITY;
    }

    public static class AffinityHolderProvider implements ICapabilitySerializable<Tag> {
        private LazyOptional<AffinityHolder> lazy = LazyOptional.of(AffinityHelper.AffinityHolder::empty);

        @Nullable
        @Override
        public Tag serializeNBT() {
            return lazy.lazyMap(affinityHolder -> AffinityHolder.CODEC.encodeStart(NbtOps.INSTANCE, affinityHolder).result())
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .orElse(null);
        }

        @Override
        public void deserializeNBT(final Tag nbt) {
            lazy = AffinityHolder.CODEC
                    .decode(NbtOps.INSTANCE, nbt)
                    .get()
                    .mapLeft(Pair::getFirst)
                    .mapLeft(affinityHolder -> (NonNullSupplier<AffinityHolder>)(() -> affinityHolder))
                    .map(LazyOptional::of, pairPartialResult -> LazyOptional.empty());
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return AffinityHelper.getCapability().orEmpty(cap, lazy);
        }
    }

    public record AffinityHolder(Map<ResourceLocation, Double> depths) {
        public static final Codec<AffinityHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.compoundList(ResourceLocation.CODEC, Codec.DOUBLE).xmap(pairs -> pairs.stream().collect(Pair.toMap()), map -> map.entrySet().stream().map(entry -> Pair.of(entry.getKey(), entry.getValue())).toList()).fieldOf("depths").forGetter(AffinityHolder::depths)
        ).apply(inst, AffinityHolder::new));

        public static AffinityHolder empty() {
            return new AffinityHolder(new HashMap<>());
        }

        /**
             * Get the depths map for this affinity holder.
             *
             * @return the unmodifiable view of the affinity depths map
             */
        public Map<ResourceLocation, Double> depths() {
            return Collections.unmodifiableMap(depths);
        }

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
            return getAffinityDepth(affinity.getRegistryName());
        }
    }
}
