package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractDataBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractDataProvider;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRegistryDataProvider<T, B extends AbstractRegistryDataProvider.Builder<T, B>> extends AbstractDataProvider<B> {
    protected final JsonCodecProvider<T> provider;
    protected final Map<ResourceLocation, T> data = new HashMap<>();

    /**
     * @param resourceKey         The key of the registry to use.
     * @param namespace           The namespace to use.
     * @param generator           The data generator to use. Get this from the event.
     * @param existingFileHelper  The existing file helper to use. Get this from the event.
     * @param registryOps         The registry ops to use. Get this from the event.
     * @param generateImmediately Whether to generate immediately. If false, generate() must be called manually.
     */
    protected AbstractRegistryDataProvider(ResourceKey<Registry<T>> resourceKey, String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps, boolean generateImmediately) {
        super(resourceKey.location().toString().replace(':', '/'), namespace, generator);
        this.provider = JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, namespace, registryOps, resourceKey, data);
        if (generateImmediately) {
            generate();
        }
    }

    /**
     * @param resourceKey         The key of the registry to use.
     * @param namespace           The namespace to use.
     * @param generator           The data generator to use. Get this from the event.
     * @param existingFileHelper  The existing file helper to use. Get this from the event.
     * @param registryOps         The registry ops to use. Get this from the event.
     */
    protected AbstractRegistryDataProvider(ResourceKey<Registry<T>> resourceKey, String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        this(resourceKey, namespace, generator, existingFileHelper, registryOps, true);
    }

    /**
     * Override this method to add your objects.
     */
    protected abstract void generate();

    @Override
    protected void add(B builder) {
        data.put(builder.id, builder.get());
    }

    @Override
    public void run(CachedOutput output) {
        try {
            provider.run(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static abstract class Builder<T, B extends Builder<T, B>> extends AbstractDataBuilder<B> {
        private static final Logger LOGGER = LogManager.getLogger();
        protected final Codec<T> codec;

        public Builder(ResourceLocation id, AbstractRegistryDataProvider<T, B> provider, Codec<T> codec) {
            super(id, provider);
            this.codec = codec;
        }

        protected abstract T get();

        @Override
        protected void toJson(JsonObject jsonObject) {
            for (Map.Entry<String, JsonElement> entry : codec.encodeStart(JsonOps.INSTANCE, get()).getOrThrow(false, LOGGER::error).getAsJsonObject().entrySet()) {
                jsonObject.add(entry.getKey(), entry.getValue());
            }
        }
    }
}
