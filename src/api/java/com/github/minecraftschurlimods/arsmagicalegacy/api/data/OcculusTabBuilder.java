package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.SerializationException;
import org.jetbrains.annotations.Contract;

import java.util.function.Consumer;

public class OcculusTabBuilder {
    private final ResourceLocation id;
    private       Integer index;
    private       String  renderer;
    private       Integer startX;
    private       Integer startY;

    protected OcculusTabBuilder(ResourceLocation id) {
        this.id = id;
    }

    /**
     * Create a new {@link OcculusTabBuilder} with the given id.
     *
     * @param id         the id for the occulus tab
     * @return the new {@link OcculusTabBuilder} for the occulus tab
     */
    public static OcculusTabBuilder create(ResourceLocation id) {
        return new OcculusTabBuilder(id);
    }

    /**
     * Get the id of this occulus tab.
     *
     * @return the id of this occulus tab
     */
    public ResourceLocation getId() {
        return id;
    }

    /**
     * Set the index of this occulus tab.
     *
     * @param index the index
     * @return the {@link OcculusTabBuilder}
     */
    @Contract("_ -> this")
    public OcculusTabBuilder setIndex(int index) {
        this.index = index;
        return this;
    }

    /**
     * Set the renderer class of this occulus tab.
     *
     * @param renderer the renderer class
     * @return the {@link OcculusTabBuilder}
     */
    @Contract("_ -> this")
    public OcculusTabBuilder setRenderer(Class<? extends OcculusTabRenderer> renderer) {
        return setRenderer(renderer.getName());
    }

    /**
     * Set the renderer class name of this occulus tab.
     *
     * @param renderer the renderer class name
     * @return the {@link OcculusTabBuilder}
     */
    @Contract("_ -> this")
    public OcculusTabBuilder setRenderer(String renderer) {
        this.renderer = renderer;
        return this;
    }

    /**
     * Set the initial X coordinate of this occulus tab.
     *
     * @param startX the initial X coordinate
     * @return the {@link OcculusTabBuilder}
     */
    @Contract("_ -> this")
    public OcculusTabBuilder setStartX(int startX) {
        this.startX = startX;
        return this;
    }

    /**
     * Set the initial Y coordinate of this occulus tab.
     *
     * @param startY the initial Y coordinate
     * @return the {@link OcculusTabBuilder}
     */
    @Contract("_ -> this")
    public OcculusTabBuilder setStartY(int startY) {
        this.startY = startY;
        return this;
    }

    /**
     * Build this {@link OcculusTabBuilder}.<br>
     * This method accepts this builder to the provided consumer
     *
     * @param consumer the consumer that will consume this builder
     * @return the {@link OcculusTabBuilder}
     */
    @Contract("_ -> this")
    public OcculusTabBuilder build(Consumer<OcculusTabBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    JsonObject serialize() {
        JsonObject json = new JsonObject();
        if (index == null) throw new SerializationException("An occulus tab needs an index!");
        json.addProperty("index", index);
        json.addProperty("renderer", renderer);
        json.addProperty("start_x", startX);
        json.addProperty("start_y", startY);
        return json;
    }
}
