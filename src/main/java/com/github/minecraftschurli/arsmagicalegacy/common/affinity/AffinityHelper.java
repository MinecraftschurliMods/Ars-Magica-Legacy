package com.github.minecraftschurli.arsmagicalegacy.common.affinity;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityHelper;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityHolder;
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

    @CapabilityInject(IAffinityHolder.class)
    private static Capability<IAffinityHolder> AFFINITY;

    @Override
    public IAffinityHolder getAffinityHolder(Player player) {
        return player.getCapability(AFFINITY).orElseThrow(() -> new RuntimeException("Could not retrieve affinity capability for player %s".formatted(player.getUUID())));
    }

    @Override
    public ItemStack getEssenceForAffinity(ResourceLocation aff) {
        return getStackForAffinity(AMItems.AFFINITY_ESSENCE.get(), aff);
    }

    @Override
    public ItemStack getTomeForAffinity(ResourceLocation aff) {
        return getStackForAffinity(AMItems.AFFINITY_TOME.get(), aff);
    }

    @Override
    public <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, ResourceLocation aff) {
        var stack = new ItemStack(item);
        stack.getOrCreateTag().putString(ArsMagicaAPI.get().getAffinityRegistry().getRegistryName().toString(), aff.toString());
        return stack;
    }

    @Override
    public IAffinity getAffinityForStack(ItemStack stack) {
        var affinityRegistry = ArsMagicaAPI.get().getAffinityRegistry();
        var key = ResourceLocation.tryParse(stack.getOrCreateTag().getString(affinityRegistry.getRegistryName().toString()));
        if (key == null) key = IAffinity.NONE;
        return Objects.requireNonNull(affinityRegistry.getValue(key));
    }

    @Override
    public double getAffinityDepth(Player player, ResourceLocation affinity) {
        return getAffinityHolder(player).getAffinityDepth(affinity);
    }

    public static Capability<IAffinityHolder> getCapability() {
        return AFFINITY;
    }

    public static class AffinityHolderProvider implements ICapabilitySerializable<Tag> {
        private LazyOptional<IAffinityHolder> lazy = LazyOptional.of(AffinityHelper.AffinityHolder::empty);

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
                    .mapLeft(affinityHolder -> (NonNullSupplier<IAffinityHolder>)(() -> affinityHolder))
                    .map(LazyOptional::of, pairPartialResult -> LazyOptional.empty());
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return AffinityHelper.getCapability().orEmpty(cap, lazy);
        }
    }

    public record AffinityHolder(Map<ResourceLocation, Double> depths) implements IAffinityHolder {
        public static final Codec<IAffinityHolder> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                Codec.compoundList(ResourceLocation.CODEC, Codec.DOUBLE).xmap(pairs -> pairs.stream().collect(Pair.toMap()), map -> map.entrySet().stream().map(entry -> Pair.of(entry.getKey(), entry.getValue())).toList()).fieldOf("depths").forGetter(IAffinityHolder::depths)
        ).apply(inst, AffinityHolder::new));

        public static AffinityHolder empty() {
            return new AffinityHolder(new HashMap<>());
        }

        @Override
        public Map<ResourceLocation, Double> depths() {
            return Collections.unmodifiableMap(depths);
        }

        public void onSync(IAffinityHolder affinityHolder) {
            depths.clear();
            depths.putAll(affinityHolder.depths());
        }
    }
}
