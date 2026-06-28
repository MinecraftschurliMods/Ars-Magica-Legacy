package com.github.minecraftschurlimods.arsmagicalegacy.client.particle;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ParticleSpawner;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class ParticleSpawnerManager extends SimpleJsonResourceReloadListener<ParticleSpawner> {
    public static final Identifier ID = ArsMagicaApi.id("particle_spawners");
    public static final ParticleSpawnerManager INSTANCE = new ParticleSpawnerManager();
    private final Map<Identifier, ParticleSpawner> values = new HashMap<>();

    private ParticleSpawnerManager() {
        super(ParticleSpawner.CODEC, FileToIdConverter.registry(ResourceKey.createRegistryKey(ID)));
    }

    @Override
    protected void apply(Map<Identifier, ParticleSpawner> map, ResourceManager resourceManager, ProfilerFiller profiler) {
        ParticleUtil.clearParticleSpawnerCache();
        values.clear();
        values.putAll(map);
    }

    @Nullable
    public ParticleSpawner get(Identifier id) {
        return values.get(id);
    }
}
