package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DatapackRegistryProvider implements DataProvider {
    private final RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder();
    private final DatapackBuiltinEntriesProvider datapackBuiltinEntriesProvider;
    private final CompletableFuture<HolderLookup.Provider> lookupProviderContainingGenerated;

    public DatapackRegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, Set<String> modIds, List<RegistryBuilder<?>> builders) {
        builders.forEach(this::add);
        this.datapackBuiltinEntriesProvider = new DatapackBuiltinEntriesProvider(output, lookupProvider, registrySetBuilder, modIds);
        this.lookupProviderContainingGenerated = lookupProvider.thenApply(lp -> registrySetBuilder.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), lp));
    }

    /**
     * Get the {@link HolderLookup.Provider} containing all vanilla data and all generated data.
     * @return the {@link HolderLookup.Provider} containing all vanilla data and all generated data.
     */
    public final CompletableFuture<HolderLookup.Provider> getLookupProviderContainingGenerated() {
        return lookupProviderContainingGenerated;
    }

    private <T> void add(RegistryBuilder<T> builder) {
        this.registrySetBuilder.add(builder.registryKey, builder::run);
    }

    @Override
    public final CompletableFuture<?> run(CachedOutput pOutput) {
        return this.datapackBuiltinEntriesProvider.run(pOutput);
    }

    @Override
    public final String getName() {
        return this.datapackBuiltinEntriesProvider.getName();
    }

    public static abstract class RegistryBuilder<T> {
        private final ResourceKey<? extends Registry<T>> registryKey;
        private final String modId;
        private BootstapContext<T> context;

        public RegistryBuilder(ResourceKey<? extends Registry<T>> registryKey, String modId) {
            this.registryKey = registryKey;
            this.modId = modId;
        }

        private void run(BootstapContext<T> context) {
            this.context = context;
            this.gather();
        }

        protected abstract void gather();

        /**
         * Get the {@link HolderSet} for the given {@link TagKey}.
         *
         * @param tagKey The {@link TagKey} to get the {@link HolderSet} for.
         * @return The {@link HolderSet} for the given {@link TagKey}.
         * @param <S> The type of the {@link HolderSet}.
         */
        protected final <S> HolderSet.Named<S> tag(TagKey<S> tagKey) {
            return getLookup(tagKey.registry()).getOrThrow(tagKey);
        }

        /**
         * Get the {@link HolderSet} for the given {@link TagKey} or {@link Optional#empty()}.
         *
         * @param tagKey The {@link TagKey} to get the {@link HolderSet} for.
         * @return The {@link HolderSet} for the given {@link TagKey}.
         * @param <S> The type of the {@link HolderSet}.
         */
        protected final <S> Optional<HolderSet.Named<S>> optionalTag(TagKey<S> tagKey) {
            return getLookup(tagKey.registry()).get(tagKey);
        }

        /**
         * Get the {@link Holder} for the given {@link ResourceKey}.
         *
         * @param key The {@link ResourceKey} to get the {@link Holder} for.
         * @return The {@link Holder} for the given {@link ResourceKey}.
         * @param <S> The type of the {@link Holder}.
         */
        protected final <S> Holder.Reference<S> holder(ResourceKey<S> key) {
            return getLookup(ResourceKey.<S>createRegistryKey(key.registry())).getOrThrow(key);
        }

        protected final <S> Holder.Reference<S> holder(ResourceKey<? extends Registry<S>> registryKey, ResourceLocation key) {
            return getLookup(registryKey).getOrThrow(ResourceKey.create(registryKey, key));
        }

        protected final <S> Holder.Reference<S> holder(ResourceKey<? extends Registry<S>> registryKey, String name) {
            return holder(registryKey, new ResourceLocation(modId, name));
        }

        /**
         * Get the {@link Holder} for the given {@link ResourceKey} or {@link Optional#empty()}.
         *
         * @param key The {@link ResourceKey} to get the {@link Holder} for.
         * @return The {@link Holder} for the given {@link ResourceKey}.
         * @param <S> The type of the {@link Holder}.
         */
        protected final <S> Optional<Holder.Reference<S>> optionalHolder(ResourceKey<S> key) {
            return getLookup(ResourceKey.<S>createRegistryKey(key.registry())).get(key);
        }

        /**
         * Get the {@link Holder} for the given {@link ResourceLocation} from this builders current registry.
         *
         * @param id The {@link ResourceLocation} to get the {@link Holder} for.
         * @return The {@link Holder} for the given {@link ResourceLocation}.
         */
        protected final Holder.Reference<T> ownHolder(ResourceLocation id) {
            return holder(ResourceKey.create(this.registryKey, id));
        }

        /**
         * Get the {@link Holder} for the given name from this builders current registry and modId.
         *
         * @param name The name to get the {@link Holder} for.
         * @return The {@link Holder} for the given {@link ResourceLocation}.
         */
        protected final Holder.Reference<T> ownHolder(String name) {
            return ownHolder(new ResourceLocation(this.modId, name));
        }

        /**
         * Get the {@link HolderGetter} for the registry of the given {@link ResourceKey}.
         * @param registryKey The {@link ResourceKey} of the registry to get the {@link HolderGetter} for.
         * @return The {@link HolderGetter} for the registry of the given {@link ResourceKey}.
         * @param <S> The type of the registry.
         */
        @NotNull
        protected final <S> HolderGetter<S> getLookup(ResourceKey<? extends Registry<S>> registryKey) {
            if (context == null) throw new IllegalStateException("Cannot get lookup before gather");
            return context.lookup(registryKey);
        }

        /**
         * Registers a value to the datapack registry.
         *
         * @param name  The name of the value.
         * @param value The value.
         */
        protected final void register(String name, T value) {
            register(new ResourceLocation(modId, name), value);
        }

        /**
         * Registers a value to the datapack registry.
         *
         * @param id    The id of the value.
         * @param value The value.
         */
        protected final void register(ResourceLocation id, T value) {
            if (context == null) throw new IllegalStateException("Cannot register before gather");
            context.register(ResourceKey.create(registryKey, id), value);
        }
    }
}
