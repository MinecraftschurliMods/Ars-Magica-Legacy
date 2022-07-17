package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.occulus.OcculusTab;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Contract;

import java.util.function.Consumer;

public class OcculusTabBuilder {
    private final ResourceLocation id;
    private String renderer = OcculusTab.DEFAULT_RENDERER;
    private int index;
    private int startX = 0;
    private int startY = 0;
    private int width = OcculusTab.TEXTURE_WIDTH;
    private int height = OcculusTab.TEXTURE_HEIGHT;
    private ResourceLocation background;
    private ResourceLocation icon;

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
     * Sets the width of the occulus tab.
     *
     * @param width The width to set.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public OcculusTabBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    /**
     * Sets the height of the occulus tab.
     *
     * @param height The height to set.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public OcculusTabBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * Sets the background of the occulus tab.
     *
     * @param background The background to set.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public OcculusTabBuilder setBackground(ResourceLocation background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the icon of the occulus tab.
     *
     * @param icon The icon to set.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public OcculusTabBuilder setIcon(ResourceLocation icon) {
        this.icon = icon;
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
    OcculusTab build() {
        return new OcculusTab(renderer, background, icon, width, height, startX, startY, index, null);
    }
}
