package com.github.minecraftschurli.arsmagicalegacy.api.data;

import com.github.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Abstract base for occulus tab data generators
 */
public abstract class OcculusTabProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson   GSON   = (new GsonBuilder()).setPrettyPrinting().create();

    private final Map<ResourceLocation, JsonObject> data = new HashMap<>();
    private final DataGenerator generator;
    private final String namespace;

    /**
     * Create an occulus tab provider for the given namespace
     *
     * @param generator the data generator
     * @param namespace the namespace to use in data generation
     */
    protected OcculusTabProvider(DataGenerator generator, String namespace) {
        this.generator = generator;
        this.namespace = namespace;
    }

    @Internal
    @Override
    public void run(HashCache pCache) throws IOException {
        Path path = this.generator.getOutputFolder();
        createOcculusTabs();
        for (Map.Entry<ResourceLocation, JsonObject> entry : data.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            JsonObject jsonObject = entry.getValue();
            save(pCache, jsonObject, path.resolve("data/" + resourceLocation.getNamespace() + "/occulus_tabs/" + resourceLocation.getPath() + ".json"));
        }
    }

    /**
     * Implement to add your own occulus tabs
     */
    protected abstract void createOcculusTabs();

    /**
     * Create an occulus tab with the given name, index and the default renderer.
     *
     * @param name the name of the occulus tab
     * @param index the index to place the tab at
     */
    protected void add(String name, int index) {
        add(new ResourceLocation(this.namespace, name), index);
    }

    /**
     * Create an occulus tab with the given name, index and renderer.
     *
     * @param name the name of the occulus tab
     * @param index the index to place the tab at
     * @param renderer the class of the renderer to use
     */
    protected void add(String name, int index, Class<? extends OcculusTabRenderer> renderer) {
        add(name, index, renderer.getName());
    }

    /**
     * Create an occulus tab with the given name, index and renderer.
     *
     * @param name the name of the occulus tab
     * @param index the index to place the tab at
     * @param renderer the class of the renderer to use
     */
    protected void add(String name, int index, String renderer) {
        add(new ResourceLocation(this.namespace, name), index, renderer);
    }

    /**
     * Create an occulus tab with the given name, index and the default renderer.
     *
     * @param name the resource location of the occulus tab
     * @param index the index to place the tab at
     */
    protected void add(ResourceLocation name, int index) {
        final JsonObject obj = new JsonObject();
        obj.addProperty("index", index);
        data.put(name, obj);
    }

    /**
     * Create an occulus tab with the given name, index and renderer.
     *
     * @param name the resource location of the occulus tab
     * @param index the index to place the tab at
     * @param renderer the class of the renderer to use
     */
    protected void add(ResourceLocation name, int index, Class<? extends OcculusTabRenderer> renderer) {
        add(name, index, renderer.getName());
    }

    /**
     * Create an occulus tab with the given name, index and renderer.
     *
     * @param name the resource location of the occulus tab
     * @param index the index to place the tab at
     * @param renderer the class of the renderer to use
     */
    protected void add(ResourceLocation name, int index, String renderer) {
        final JsonObject obj = new JsonObject();
        obj.addProperty("index", index);
        obj.addProperty("renderer", renderer);
        data.put(name, obj);
    }

    private static void save(HashCache pCache, JsonObject pRecipeJson, Path pPath) {
        try {
            String s = GSON.toJson(pRecipeJson);
            String s1 = SHA1.hashUnencodedChars(s).toString();
            if (!Objects.equals(pCache.getHash(pPath), s1) || !Files.exists(pPath)) {
                Files.createDirectories(pPath.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(pPath)) {
                    bufferedwriter.write(s);
                }
            }

            pCache.putNew(pPath, s1);
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't save spell part data {}", pPath, ioexception);
        }
    }
}
