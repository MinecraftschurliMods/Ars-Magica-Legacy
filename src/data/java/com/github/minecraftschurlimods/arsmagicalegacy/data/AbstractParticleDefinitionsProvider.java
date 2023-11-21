package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Abstract superclass for generating particle definition JSONs. Minecraft apparently doesn't have something like this, so here's our own.
 */
abstract class AbstractParticleDefinitionsProvider implements DataProvider { //TODO: use Forge-provided ParticleDescriptionProvider from 1.19.4 onwards
    private static final String PATH = "assets/%s/particles/%s.json";
    private final String namespace;
    private final DataGenerator generator;
    private final Map<String, List<ResourceLocation>> values = new HashMap<>();

    /**
     * @param namespace The namespace of this data provider.
     * @param generator The {@link DataGenerator} to use.
     */
    protected AbstractParticleDefinitionsProvider(String namespace, DataGenerator generator) {
        this.namespace = namespace;
        this.generator = generator;
    }

    @Override
    public String getName() {
        return "Particle Definitions: " + namespace;
    }

    /**
     * Override this to add your particle definitions.
     */
    protected abstract void addParticleDefinitions();

    /**
     * Adds a particle definition. Does not check if the given textures actually exist.
     *
     * @param name     The name of this particle.
     * @param textures The textures this particle should use.
     */
    protected void add(String name, List<ResourceLocation> textures) {
        values.put(name, textures);
    }

    /**
     * Adds a particle definition. Does not check if the given textures actually exist.
     *
     * @param name     The name of this particle.
     * @param textures The textures this particle should use.
     */
    protected void add(String name, ResourceLocation... textures) {
        add(name, List.of(textures));
    }

    /**
     * Adds a single-texture particle definition. Does not check if the given texture actually exists.
     *
     * @param name    The name of this particle.
     * @param texture The texture this particle should use.
     */
    protected void add(String name, ResourceLocation texture) {
        add(name, List.of(texture));
    }

    @Override
    public void run(CachedOutput pOutput) {
        addParticleDefinitions();
        Set<String> ids = new HashSet<>();
        values.forEach((k, v) -> {
            if (!ids.add(k)) throw new IllegalStateException("Duplicate particle definition " + k);
            if (v.isEmpty()) throw new IllegalStateException("Empty texture list for particle definition " + k);
            try {
                DataProvider.saveStable(pOutput, toJson(v), generator.getOutputFolder().resolve(String.format(PATH, namespace, k)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static JsonObject toJson(List<ResourceLocation> list) {
        JsonArray textures = new JsonArray();
        for (ResourceLocation texture : list) {
            textures.add(texture.toString());
        }
        JsonObject result = new JsonObject();
        result.add("textures", textures);
        return result;
    }
}
