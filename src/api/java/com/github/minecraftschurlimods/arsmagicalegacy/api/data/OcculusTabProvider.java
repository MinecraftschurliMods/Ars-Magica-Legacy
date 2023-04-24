package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.SerializationException;

public abstract class OcculusTabProvider extends AbstractDataProvider<OcculusTabProvider.Builder> {
    protected OcculusTabProvider(String namespace, DataGenerator generator) {
        super("occulus_tabs", namespace, generator);
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

    public static class Builder extends AbstractDataBuilder<Builder> {
        private Integer index;
        private Integer startX;
        private Integer startY;
        private String renderer;

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

        @Override
        protected JsonObject toJson() {
            JsonObject json = new JsonObject();
            if (index == null) throw new SerializationException("An occulus tab needs an index!");
            json.addProperty("index", this.index);
            json.addProperty("renderer", this.renderer);
            json.addProperty("start_x", this.startX);
            json.addProperty("start_y", this.startY);
            return json;
        }
    }
}
