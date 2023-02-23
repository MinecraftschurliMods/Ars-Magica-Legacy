package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

/**
 * The abstract parent class of builder classes used by {@link AbstractDataProvider} for generating data contents.
 */
public abstract class AbstractDataBuilder<T, S extends AbstractDataBuilder<T, S>> {
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
     * {@return the built object.}
     */
    protected abstract T build();

    /**
     * Finishes this builder's building, using the provided consumer.
     *
     * @param consumer The consumer to use.
     */
    @SuppressWarnings("unchecked")
    public final void build(Consumer<S> consumer) {
        consumer.accept((S) this);
    }
}
