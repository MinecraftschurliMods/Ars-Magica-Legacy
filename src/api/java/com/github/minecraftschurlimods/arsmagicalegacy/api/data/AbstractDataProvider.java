package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.google.gson.JsonElement;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * The abstract class all of Ars Magica's data provider classes extend from.<br>
 * Contains some common functionality, e.g. saving to disk.
 *
 * @param <T> The builder class associated with this provider.
 */
public abstract class AbstractDataProvider<T, B extends AbstractDataBuilder<T, B>> implements DataProvider {
    protected static final Logger LOGGER = LogManager.getLogger();
    protected final JsonCodecProvider<T> provider;
    protected final String namespace;
    private final Map<ResourceLocation, T> data = new HashMap<>();
    protected final Map<ResourceLocation, T> dataView = Collections.unmodifiableMap(data);

    /**
     * Parent constructor that all subclasses should use.
     *
     * @param generator The data generator to use.
     * @param namespace The namespace to use.
     */
    protected AbstractDataProvider(ResourceKey<Registry<T>> registryKey, String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        this.namespace = namespace;
        this.provider = JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, namespace, registryOps, registryKey, data);
    }

    /**
     * Override this method to generate the data provider's contents.
     *
     * @param consumer The consumer to pass to the builders.
     */
    protected abstract void generate(Consumer<B> consumer);

    @Override
    public void run(CachedOutput output) throws IOException {
        this.data.clear();
        this.generate(builder -> {
            ResourceLocation id = builder.id;
            if (this.data.containsKey(id)) {
                throw new IllegalStateException("Duplicate id: " + id);
            }
            T value = builder.build();
            this.data.put(id, value);
            this.onSave(id, value);
        });
        this.provider.run(output);
    }

    /**
     * @return The name of this data provider, for use in logging.<br>
     * Should look something like this: {@code return "TheThingsBeingDatagenned[" + namespace + "]";}
     */
    @Override
    public abstract String getName();

    /**
     * Override this method if you want additional custom behavior when a generated object is saved to disk.
     *
     * @param id     The id of the object that will be saved to disk.
     * @param object The object that will be saved to disk.
     */
    protected void onSave(ResourceLocation id, T object) {
    }
}
