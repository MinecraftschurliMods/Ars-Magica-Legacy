package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.SerializationException;
import org.jetbrains.annotations.Contract;

import java.util.function.Consumer;

public class OcculusTabBuilder {
    private final ResourceLocation id;
    private Integer index;
    private Integer startX;
    private Integer startY;
    private String renderer;

    protected OcculusTabBuilder(ResourceLocation id) {
        this.id = id;
    }

    /**
     * @param id The id for the new occulus tab.
     * @return A builder for a new occulus tab.
     */
    public static OcculusTabBuilder create(ResourceLocation id) {
        return new OcculusTabBuilder(id);
    }

    /**
     * @return The id of the occulus tab.
     */
    public ResourceLocation getId() {
        return id;
    }

    /**
     * Sets the index of the occulus tab.
     *
     * @param index The index to set.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public OcculusTabBuilder setIndex(int index) {
        this.index = index;
        return this;
    }

    /**
     * Sets the initial X coordinate of the occulus tab.
     *
     * @param startX The initial X coordinate to set.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public OcculusTabBuilder setStartX(int startX) {
        this.startX = startX;
        return this;
    }

    /**
     * Sets the initial Y coordinate of the occulus tab.
     *
     * @param startY The initial Y coordinate to set.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public OcculusTabBuilder setStartY(int startY) {
        this.startY = startY;
        return this;
    }

    /**
     * Sets the renderer class of the occulus tab.
     *
     * @param renderer The renderer class to set.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public OcculusTabBuilder setRenderer(Class<? extends OcculusTabRenderer> renderer) {
        return setRenderer(renderer.getName());
    }

    /**
     * Sets the renderer class of the occulus tab.
     *
     * @param renderer The renderer class to set.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public OcculusTabBuilder setRenderer(String renderer) {
        this.renderer = renderer;
        return this;
    }

    /**
     * Builds the occulus tab.
     *
     * @param consumer The consumer that will consume the builder.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public OcculusTabBuilder build(Consumer<OcculusTabBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    /**
     * @return The serialized occulus tab.
     */
    JsonObject serialize() {
        JsonObject json = new JsonObject();
        if (index == null) throw new SerializationException("An occulus tab needs an index!");
        json.addProperty("index", this.index);
        json.addProperty("renderer", this.renderer);
        json.addProperty("start_x", this.startX);
        json.addProperty("start_y", this.startY);
        return json;
    }
}
