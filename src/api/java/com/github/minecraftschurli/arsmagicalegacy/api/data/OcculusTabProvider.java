package com.github.minecraftschurli.arsmagicalegacy.api.data;

import com.google.common.collect.Sets;
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
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Abstract base for occulus tab data generators
 */
public abstract class OcculusTabProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
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
    public void run(HashCache pCache) {
        Path path = generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        createOcculusTabs(consumer -> {
            if (!set.add(consumer.getId()))
                throw new IllegalStateException("Duplicate occulus tab " + consumer.getId());
            else {
                save(pCache, consumer.serialize(), path.resolve("data/" + consumer.getId().getNamespace() + "/occulus_tabs/" + consumer.getId().getPath() + ".json"));
            }
        });
    }

    /**
     * Implement to add your own occulus tabs
     * @param consumer provided by the datagen
     */
    protected abstract void createOcculusTabs(Consumer<OcculusTabBuilder> consumer);

    /**
     * Creates a new occulus tab.
     *
     * @param name  The occulus tab name.
     * @param index The index of the occulus tab.
     * @return A new occulus tab.
     */
    protected OcculusTabBuilder createOcculusTab(String name, int index) {
        return OcculusTabBuilder.create(new ResourceLocation(namespace, name)).setIndex(index);
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
            LOGGER.error("Couldn't save occulus tab {}", pPath, ioexception);
        }
    }
}
