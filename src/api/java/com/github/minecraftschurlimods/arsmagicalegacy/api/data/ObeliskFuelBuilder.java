package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.ObeliskFuel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.BiConsumer;

/**
 *
 */
public class ObeliskFuelBuilder {
    private final ResourceLocation id;
    private Ingredient ingredient;
    private int burntime;
    private int valuepertick;

    public ObeliskFuelBuilder(ResourceLocation id) {
        this.id = id;
    }

    /**
     * Sets the ingredient of this obelisk fuel entry.
     *
     * @param ingredient The ingredient to set.
     * @return This builder, for chaining.
     */
    public ObeliskFuelBuilder setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    /**
     * Sets the burn time of this obelisk fuel entry.
     *
     * @param burntime The burn time to set.
     * @return This builder, for chaining.
     */
    public ObeliskFuelBuilder setBurntime(int burntime) {
        this.burntime = burntime;
        return this;
    }

    /**
     * Sets the etherium generation per tick of this obelisk fuel entry.
     *
     * @param valuepertick The etherium generation per tick to set.
     * @return This builder, for chaining.
     */
    public ObeliskFuelBuilder setValuePerTick(int valuepertick) {
        this.valuepertick = valuepertick;
        return this;
    }

    /**
     * Builds this builder.
     */
    public void build(BiConsumer<ResourceLocation, ObeliskFuel> consumer) {
        consumer.accept(id, new ObeliskFuel(ingredient, burntime, valuepertick));
    }
}
