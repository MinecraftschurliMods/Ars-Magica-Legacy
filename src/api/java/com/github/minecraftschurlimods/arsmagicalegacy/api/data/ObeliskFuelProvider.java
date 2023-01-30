package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class ObeliskFuelProvider extends AbstractDataProvider<ObeliskFuelProvider.Builder> {
    protected ObeliskFuelProvider(String namespace, DataGenerator generator) {
        super("obelisk_fuel", namespace, generator);
    }

    @Override
    public String getName() {
        return "Obelisk Fuels[" + namespace + "]";
    }

    /**
     * @param name            The id of the obelisk fuel.
     * @param item            The item to use.
     * @param burnTime        The burn time of the ingredient, in ticks.
     * @param etheriumPerTick How much etherium is generated per burning tick.
     */
    protected Builder builder(String name, Item item, int burnTime, int etheriumPerTick) {
        return builder(name, Ingredient.of(item), burnTime, etheriumPerTick);
    }

    /**
     * @param name            The id of the obelisk fuel.
     * @param tag             The tag to use.
     * @param burnTime        The burn time of the ingredient, in ticks.
     * @param etheriumPerTick How much etherium is generated per burning tick.
     */
    protected Builder builder(String name, TagKey<Item> tag, int burnTime, int etheriumPerTick) {
        return builder(name, Ingredient.of(tag), burnTime, etheriumPerTick);
    }

    /**
     * @param name            The id of the obelisk fuel.
     * @param ingredient      The ingredient to use.
     * @param burnTime        How many ticks the ingredient burns.
     * @param etheriumPerTick How much etherium is generated per burning tick.
     */
    protected Builder builder(String name, Ingredient ingredient, int burnTime, int etheriumPerTick) {
        return new Builder(new ResourceLocation(namespace, name)).setIngredient(ingredient).setBurnTime(burnTime).setEtheriumPerTick(etheriumPerTick);
    }

    public static class Builder extends AbstractDataBuilder<Builder> {
        private Ingredient ingredient;
        private int burnTime;
        private int etheriumPerTick;

        public Builder(ResourceLocation id) {
            super(id);
        }

        /**
         * Sets the ingredient of this obelisk fuel entry.
         *
         * @param ingredient The ingredient to set.
         * @return This builder, for chaining.
         */
        public Builder setIngredient(Ingredient ingredient) {
            this.ingredient = ingredient;
            return this;
        }

        /**
         * Sets the burn time of this obelisk fuel entry.
         *
         * @param burnTime The burn time to set.
         * @return This builder, for chaining.
         */
        public Builder setBurnTime(int burnTime) {
            this.burnTime = burnTime;
            return this;
        }

        /**
         * Sets the etherium generation per tick of this obelisk fuel entry.
         *
         * @param etheriumPerTick The etherium generation per tick to set.
         * @return This builder, for chaining.
         */
        public Builder setEtheriumPerTick(int etheriumPerTick) {
            this.etheriumPerTick = etheriumPerTick;
            return this;
        }

        @Override
        protected JsonObject toJson() {
            JsonObject json = new JsonObject();
            json.add("input", ingredient.toJson());
            json.addProperty("burn_time", burnTime);
            json.addProperty("etherium_per_tick", etheriumPerTick);
            return json;
        }
    }
}
