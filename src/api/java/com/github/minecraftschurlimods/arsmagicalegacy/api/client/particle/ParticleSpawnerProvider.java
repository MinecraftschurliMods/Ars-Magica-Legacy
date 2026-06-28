package com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/// Data provider for [ParticleSpawner]s. Override [ParticleSpawnerProvider#generate(HolderLookup.Provider)] to generate your entries,
/// and use [ParticleSpawnerProvider#builder(Identifier, ParticleOptions, int, int)] or [ParticleSpawnerProvider#builder(Identifier, ParticleOptions, int, int, int)] to create a new [ParticleSpawnerBuilder].
public abstract class ParticleSpawnerProvider implements DataProvider {
    private static final String EXCEPTION_MESSAGE = "Failed to encode %s: %s";
    private final PackOutput.PathProvider pathProvider;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private final String modId;
    private final List<ParticleSpawnerBuilder> builders = new ArrayList<>();

    /// @param output         The [PackOutput] to use. Get this from [GatherDataEvent].
    /// @param lookupProvider The lookup [CompletableFuture] to use. Get this from [GatherDataEvent].
    /// @param modId          Your mod id.
    public ParticleSpawnerProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, ArsMagicaApi.MOD_ID + "/particle_spawners");
        this.lookupProvider = lookupProvider;
        this.modId = modId;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return lookupProvider.thenCompose(provider -> {
            generate(provider);
            DynamicOps<JsonElement> ops = provider.createSerializationContext(ConditionalOps.create(JsonOps.INSTANCE, provider));
            Set<Identifier> ids = Collections.synchronizedSet(new HashSet<>());
            return CompletableFuture.allOf(builders.stream().map(builder -> {
                if (!ids.add(builder.id)) throw new IllegalStateException("Duplicate datagenned object " + builder.id);
                Path path = pathProvider.json(builder.id);
                return CompletableFuture
                    .supplyAsync(() -> {
                        JsonObject json = ParticleSpawner.CODEC.encodeStart(ops, builder.build()).getOrThrow(message -> new RuntimeException(EXCEPTION_MESSAGE.formatted(path, message))).getAsJsonObject();
                        List<ICondition> conditions = builder.getConditions();
                        if (!conditions.isEmpty()) {
                            json.add(ConditionalOps.DEFAULT_CONDITIONS_KEY, ICondition.LIST_CODEC.encodeStart(ops, conditions).getOrThrow(message -> new RuntimeException(EXCEPTION_MESSAGE.formatted(path, message))));
                        }
                        return json;
                    })
                    .thenComposeAsync(json -> DataProvider.saveStable(output, json, path));
            }).toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public String getName() {
        return "Particle Spawners: " + modId;
    }

    /// Override this to generate your objects.
    ///
    /// @param provider The [HolderLookup.Provider] provided by the system. Use this to perform registry lookups if needed.
    public abstract void generate(HolderLookup.Provider provider);

    /// Creates and adds a new [ParticleSpawnerBuilder].
    ///
    /// @param id          The id of the [ParticleSpawner].
    /// @param particle    The spawned particles' [ParticleOptions].
    /// @param count       The spawned particle count.
    /// @param minLifetime The min lifetime of the spawned particles.
    /// @param maxLifetime The max lifetime of the spawned particles.
    /// @return The new [ParticleSpawnerBuilder].
    public ParticleSpawnerBuilder builder(Identifier id, ParticleOptions particle, int count, int minLifetime, int maxLifetime) {
        ParticleSpawnerBuilder builder = new ParticleSpawnerBuilder(id, particle, count, minLifetime, maxLifetime);
        builders.add(builder);
        return builder;
    }

    /// Creates and adds a new [ParticleSpawnerBuilder].
    ///
    /// @param id       The id of the [ParticleSpawner].
    /// @param particle The spawned particles' [ParticleOptions].
    /// @param count    The spawned particle count.
    /// @param lifetime The lifetime of the spawned particles.
    /// @return The new [ParticleSpawnerBuilder].
    public ParticleSpawnerBuilder builder(Identifier id, ParticleOptions particle, int count, int lifetime) {
        ParticleSpawnerBuilder builder = new ParticleSpawnerBuilder(id, particle, count, lifetime);
        builders.add(builder);
        return builder;
    }
}
