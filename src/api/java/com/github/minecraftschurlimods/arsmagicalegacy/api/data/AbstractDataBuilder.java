package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

/**
 * The abstract parent class of builder classes used by {@link AbstractDataProvider} for generating data contents.
 */
public abstract class AbstractDataBuilder<T extends AbstractDataBuilder<T>> {
    public final ResourceLocation id;

    /**
     * Creates a new builder with the given id.
     *
     * @param id The id to use. Should be unique within the same data provider and the same namespace.
     */
    public AbstractDataBuilder(ResourceLocation id) {
        this.id = id;
    }

    /**
     * @return A JSON object, built from this builder's contents.
     */
    protected abstract JsonObject toJson();

    /**
     * Finishes this builder's building, using the provided consumer.
     *
     * @param consumer The consumer to use.
     */
    @SuppressWarnings("unchecked")
    public final void build(Consumer<T> consumer) {
        consumer.accept((T) this);
    }
}
