package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.OcculusTab;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractDatapackRegistryProvider;
import net.minecraft.resources.ResourceLocation;

public abstract class OcculusTabProvider extends AbstractDatapackRegistryProvider<OcculusTab> {
    protected OcculusTabProvider(String namespace) {
        super(OcculusTab.REGISTRY_KEY, namespace);
    }

    /**
     * @param index The index of the occulus tab.
     * @return A new occulus tab.
     */
    protected Builder builder(int index) {
        return new Builder(index);
    }

    public static class Builder {
        private Integer index;
        private ResourceLocation background;
        private ResourceLocation icon;
        private String renderer = OcculusTab.DEFAULT_RENDERER;
        private int startX = 0;
        private int startY = 0;
        private int width = OcculusTab.TEXTURE_WIDTH;
        private int height = OcculusTab.TEXTURE_HEIGHT;

        public Builder(int index) {
            this.index = index;
        }

        /**
         * Sets the index of the occulus tab.
         *
         * @param index The index to set.
         */
        public Builder setIndex(int index) {
            this.index = index;
            return this;
        }

        /**
         * Sets the initial X coordinate of the occulus tab.
         *
         * @param startX The initial X coordinate to set.
         */
        public Builder setStartX(int startX) {
            this.startX = startX;
            return this;
        }

        /**
         * Sets the initial Y coordinate of the occulus tab.
         *
         * @param startY The initial Y coordinate to set.
         */
        public Builder setStartY(int startY) {
            this.startY = startY;
            return this;
        }

        /**
         * Sets the renderer class of the occulus tab.
         *
         * @param renderer The renderer class to set.
         */
        public Builder setRenderer(Class<? extends OcculusTabRenderer> renderer) {
            return setRenderer(renderer.getName());
        }

        /**
         * Sets the renderer class of the occulus tab.
         *
         * @param renderer The renderer class to set.
         */
        public Builder setRenderer(String renderer) {
            this.renderer = renderer;
            return this;
        }

        /**
         * Sets the width of the occulus tab.
         *
         * @param width The width to set.
         */
        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        /**
         * Sets the height of the occulus tab.
         *
         * @param height The height to set.
         */
        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        /**
         * Sets the background of the occulus tab.
         *
         * @param background The background to set.
         */
        public Builder setBackground(ResourceLocation background) {
            this.background = background;
            return this;
        }

        /**
         * Sets the icon of the occulus tab.
         *
         * @param icon The icon to set.
         */
        public Builder setIcon(ResourceLocation icon) {
            this.icon = icon;
            return this;
        }

        public OcculusTab build() {
            return new OcculusTab(renderer, background, icon, width, height, startX, startY, index, null);
        }
    }
}
