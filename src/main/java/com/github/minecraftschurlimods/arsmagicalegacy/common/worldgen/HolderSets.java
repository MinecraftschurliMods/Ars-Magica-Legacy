package com.github.minecraftschurlimods.arsmagicalegacy.common.worldgen;

import com.google.common.base.Preconditions;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.registries.holdersets.AndHolderSet;
import net.neoforged.neoforge.registries.holdersets.NotHolderSet;
import net.neoforged.neoforge.registries.holdersets.OrHolderSet;

import java.util.Arrays;

/// Helper class for working with [HolderSet]s.
public final class HolderSets {
    private HolderSets() {
    }

    /// Creates a [HolderSet] containing the looked up values for the given keys.
    ///
    /// @param bootstrap   The [BootstrapContext] used for lookups.
    /// @param registryKey The key of the registry to perform the lookups in.
    /// @param keys        The resource keys to lookup.
    /// @param <T>         The type of the registry.
    /// @return A [HolderSet].
    @SafeVarargs
    public static <T> HolderSet<T> direct(BootstrapContext<?> bootstrap, ResourceKey<? extends Registry<T>> registryKey, ResourceKey<T>... keys) {
        HolderGetter<T> lookup = bootstrap.lookup(registryKey);
        return HolderSet.direct(Arrays.stream(keys).map(lookup::getOrThrow).toList());
    }

    /// Variant of [HolderSets#direct(BootstrapContext, ResourceKey, ResourceKey\[\])], specialized for biomes.
    ///
    /// @param bootstrap The [BootstrapContext] used for lookups.
    /// @param biomes    The biome resource keys to lookup.
    /// @return A [HolderSet].
    @SafeVarargs
    public static HolderSet<Biome> biome(BootstrapContext<?> bootstrap, ResourceKey<Biome>... biomes) {
        return direct(bootstrap, Registries.BIOME, biomes);
    }

    /// Like [HolderSets#biome(BootstrapContext, ResourceKey\[\])], but looks up a biome tag instead of a list of biome resource keys.
    ///
    /// @param bootstrap The [BootstrapContext] used for lookups.
    /// @param biome     The biome tag to lookup.
    /// @return A [HolderSet].
    public static HolderSet<Biome> biomeTag(BootstrapContext<?> bootstrap, TagKey<Biome> biome) {
        return bootstrap.lookup(Registries.BIOME).getOrThrow(biome);
    }

    /// Groups the given [HolderSet]s using an AND operation.
    ///
    /// @param sets The [HolderSet]s to group.
    /// @param <T>  The type of the [HolderSet]s.
    /// @return A [HolderSet].
    @SafeVarargs
    public static <T> HolderSet<T> and(HolderSet<T>... sets) {
        Preconditions.checkArgument(sets.length > 0);
        return sets.length == 1 ? sets[0] : new AndHolderSet<>(sets);
    }

    /// Groups the given [HolderSet]s using an OR operation.
    ///
    /// @param sets The [HolderSet]s to group.
    /// @param <T>  The type of the [HolderSet]s.
    /// @return A [HolderSet].
    @SafeVarargs
    public static <T> HolderSet<T> or(HolderSet<T>... sets) {
        Preconditions.checkArgument(sets.length > 0);
        return sets.length == 1 ? sets[0] : new OrHolderSet<>(sets);
    }

    /// Inverts the given [HolderSet].
    ///
    /// @param set The [HolderSet] to invert.
    /// @param <T> The type of the [HolderSet].
    /// @return A [HolderSet].
    @SuppressWarnings("DataFlowIssue")
    public static <T> HolderSet<T> not(HolderSet<T> set) {
        return new NotHolderSet<>(null, set);
    }
}
