package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.ObeliskFuel;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class ObeliskFuelProvider extends AbstractRegistryDataProvider<ObeliskFuel, ObeliskFuelProvider.Builder> {
    protected ObeliskFuelProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(ObeliskFuel.REGISTRY_KEY, namespace, generator, existingFileHelper, registryOps);
    }

    @Override
    public String getName() {
        return "Obelisk Fuels[" + namespace + "]";
    }

    /**
     * @param id              The id of the obelisk fuel.
     * @param item            The item to use.
     * @param burnTime        The burn time of the ingredient, in ticks.
     * @param etheriumPerTick How much etherium is generated per burning tick.
     */
    protected Builder builder(String id, Item item, int burnTime, int etheriumPerTick) {
        return builder(id, Ingredient.of(item), burnTime, etheriumPerTick);
    }

    /**
     * @param id              The id of the obelisk fuel.
     * @param tag             The tag to use.
     * @param burnTime        The burn time of the ingredient, in ticks.
     * @param etheriumPerTick How much etherium is generated per burning tick.
     */
    protected Builder builder(String id, TagKey<Item> tag, int burnTime, int etheriumPerTick) {
        return builder(id, Ingredient.of(tag), burnTime, etheriumPerTick);
    }

    /**
     * @param id              The id of the obelisk fuel.
     * @param ingredient      The ingredient to use.
     * @param burnTime        How many ticks the ingredient burns.
     * @param etheriumPerTick How much etherium is generated per burning tick.
     */
    protected Builder builder(String id, Ingredient ingredient, int burnTime, int etheriumPerTick) {
        return new Builder(new ResourceLocation(namespace, id), this, ingredient, burnTime, etheriumPerTick);
    }

    public static class Builder extends AbstractRegistryDataProvider.Builder<ObeliskFuel, Builder> {
        private final Ingredient ingredient;
        private final int burnTime;
        private final int etheriumPerTick;

        public Builder(ResourceLocation id, ObeliskFuelProvider provider, Ingredient ingredient, int burnTime, int etheriumPerTick) {
            super(id, provider, ObeliskFuel.DIRECT_CODEC);
            this.ingredient = ingredient;
            this.burnTime = burnTime;
            this.etheriumPerTick = etheriumPerTick;
        }

        @Override
        protected ObeliskFuel get() {
            return new ObeliskFuel(ingredient, burnTime, etheriumPerTick);
        }
    }
}
