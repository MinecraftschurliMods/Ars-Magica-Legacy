package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The abstract class all of Ars Magica's data provider classes extend from.<br>
 * Contains some common functionality, e.g. saving to disk.
 *
 * @param <T> The builder class associated with this provider.
 */
public abstract class AbstractDataProvider<T extends AbstractDataBuilder> implements DataProvider {
    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected static final Logger LOGGER = LogManager.getLogger();
    protected final String folder;
    protected final String namespace;
    protected final DataGenerator generator;

    /**
     * Parent constructor that all subclasses should use.
     *
     * @param generator The data generator to use.
     * @param namespace The namespace to use.
     * @param folder    The data folder to output to.
     */
    protected AbstractDataProvider(String folder, String namespace, DataGenerator generator) {
        this.folder = folder;
        this.namespace = namespace;
        this.generator = generator;
    }

    /**
     * Override this method to generate the data provider's contents.
     *
     * @param consumer The consumer to pass to the builders.
     */
    protected abstract void generate(Consumer<T> consumer);

    @Override
    public void run(HashCache cache) {
        Set<ResourceLocation> ids = new HashSet<>();
        generate(consumer -> {
            if (!ids.add(consumer.id)) throw new IllegalStateException("Duplicate datagenned object " + consumer.id);
            try {
                DataProvider.save(GSON, cache, consumer.toJson(), generator.getOutputFolder().resolve("data/" + consumer.id.getNamespace() + "/" + folder + "/" + consumer.id.getPath() + ".json"));
                onSave(consumer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * @return The name of this data provider, for use in logging.<br>
     * Should look something like this: {@code return "TheThingsBeingDatagenned[" + namespace + "]";}
     */
    public abstract String getName();

    /**
     * Adds a new builder to the provider.
     *
     * @param consumer The consumer provided by {@link AbstractDataProvider#generate}.
     * @param builder  The builder to add.
     */
    public void add(Consumer<T> consumer, T builder) {
        consumer.accept(builder);
    }

    /**
     * Override this method if you want additional custom behavior when a datagenned object is saved to disk.
     *
     * @param object The object that will be saved to disk.
     */
    protected void onSave(T object) {
    }
}
