package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

/**
 * The abstract parent class of builder classes used by {@link AbstractDataProvider} for generating data contents.
 */
public abstract class AbstractDataBuilder {
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
}
