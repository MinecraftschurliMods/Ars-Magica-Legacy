package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Base class for occulus tab data generators.
 */
public abstract class OcculusTabProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGenerator generator;
    private final String namespace;

    protected OcculusTabProvider(String namespace, DataGenerator generator) {
        this.namespace = namespace;
        this.generator = generator;
    }

    protected abstract void createOcculusTabs(Consumer<OcculusTabBuilder> consumer);

    @Internal
    @Override
    public void run(CachedOutput pCache) {
        Set<ResourceLocation> ids = new HashSet<>();
        createOcculusTabs(consumer -> {
            if (!ids.add(consumer.getId()))
                throw new IllegalStateException("Duplicate occulus tab " + consumer.getId());
            else {
                try {
                    DataProvider.saveStable(pCache, consumer.serialize(), generator.getOutputFolder().resolve("data/" + consumer.getId().getNamespace() + "/occulus_tabs/" + consumer.getId().getPath() + ".json"));
                } catch (IOException e) {
                    LOGGER.error("Couldn't save occulus tab {}", consumer.getId(), e);
                }
            }
        });
    }

    @Override
    public String getName() {
        return "Occulus Tabs[" + namespace + "]";
    }

    /**
     * @param name  The occulus tab name.
     * @param index The index of the occulus tab.
     * @return A new occulus tab.
     */
    protected OcculusTabBuilder createOcculusTab(String name, int index) {
        return OcculusTabBuilder.create(new ResourceLocation(namespace, name)).setIndex(index);
    }
}
