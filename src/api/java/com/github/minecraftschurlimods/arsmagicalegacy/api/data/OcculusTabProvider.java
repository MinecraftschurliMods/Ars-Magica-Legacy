package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.OcculusTab;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.commons.lang3.SerializationException;

public abstract class OcculusTabProvider extends AbstractDataProvider<OcculusTab, OcculusTabProvider.Builder> {
    protected OcculusTabProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(OcculusTab.REGISTRY_KEY, namespace, generator, existingFileHelper, registryOps);
    }

    @Override
    public String getName() {
        return "Occulus Tabs[" + namespace + "]";
    }

    /**
     * @param name  The occulus tab name.
     * @param index The index of the occulus tab.
     * @return A new occulus tab.
     */
    protected Builder builder(String name, int index) {
        return new Builder(new ResourceLocation(namespace, name)).setIndex(index);
    }

    public static class Builder extends AbstractDataBuilder<OcculusTab, Builder> {
        private Integer index;
        private ResourceLocation background;
        private ResourceLocation icon;
        private String renderer = OcculusTab.DEFAULT_RENDERER;
        private int startX = 0;
        private int startY = 0;
        private int width = OcculusTab.TEXTURE_WIDTH;
        private int height = OcculusTab.TEXTURE_HEIGHT;

        public Builder(ResourceLocation id) {
            super(id);
        }

        /**
         * Sets the index of the occulus tab.
         *
         * @param index The index to set.
         * @return This builder, for chaining.
         */
        public Builder setIndex(int index) {
            this.index = index;
            return this;
        }

        /**
         * Sets the initial X coordinate of the occulus tab.
         *
         * @param startX The initial X coordinate to set.
         * @return This builder, for chaining.
         */
        public Builder setStartX(int startX) {
            this.startX = startX;
            return this;
        }

        /**
         * Sets the initial Y coordinate of the occulus tab.
         *
         * @param startY The initial Y coordinate to set.
         * @return This builder, for chaining.
         */
        public Builder setStartY(int startY) {
            this.startY = startY;
            return this;
        }

        /**
         * Sets the renderer class of the occulus tab.
         *
         * @param renderer The renderer class to set.
         * @return This builder, for chaining.
         */
        public Builder setRenderer(Class<? extends OcculusTabRenderer> renderer) {
            return setRenderer(renderer.getName());
        }

        /**
         * Sets the renderer class of the occulus tab.
         *
         * @param renderer The renderer class to set.
         * @return This builder, for chaining.
         */
        public Builder setRenderer(String renderer) {
            this.renderer = renderer;
            return this;
        }

        /**
         * Sets the width of the occulus tab.
         *
         * @param width The width to set.
         * @return This builder, for chaining.
         */
        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        /**
         * Sets the height of the occulus tab.
         *
         * @param height The height to set.
         * @return This builder, for chaining.
         */
        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        /**
         * Sets the background of the occulus tab.
         *
         * @param background The background to set.
         * @return This builder, for chaining.
         */
        public Builder setBackground(ResourceLocation background) {
            this.background = background;
            return this;
        }

        /**
         * Sets the icon of the occulus tab.
         *
         * @param icon The icon to set.
         * @return This builder, for chaining.
         */
        public Builder setIcon(ResourceLocation icon) {
            this.icon = icon;
            return this;
        }


        /**
         * @return The serialized occulus tab.
         */
        @Override
        protected OcculusTab build() {
            if (index == null) throw new SerializationException("Occulus tab index must be set.");
            return new OcculusTab(renderer, background, icon, width, height, startX, startY, index, null);
        }
    }
}
