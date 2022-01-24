package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.client.ISpellIngredientRenderer;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

/**
 * Interface representing a spell data manager.
 */
public interface ISpellDataManager {
    /**
     * @param part The spell part to get the data for.
     * @return The spell part data for the given part, or null if it is not available.
     */
    ISpellPartData getDataForPart(ISpellPart part);

    /**
     * Registers a spell ingredient type.
     *
     * @param type     The id of the spell ingredient type.
     * @param codec    The codec to serialize/deserialize the spell ingredient.
     * @param renderer A supplier of the renderer (value gets cached).
     * @param <T>      The type of the spell ingredient.
     */
    <T extends ISpellIngredient> void registerSpellIngredientType(ResourceLocation type, Codec<T> codec, Supplier<ISpellIngredientRenderer<T>> renderer);

    /**
     * Registers a spell ingredient type.
     *
     * @param type         The id of the spell ingredient type.
     * @param codec        The codec to serialize/deserialize the spell ingredient.
     * @param networkCodec The network codec to serialize/deserialize the spell ingredient.
     * @param renderer     A supplier of the renderer (value gets cached).
     * @param <T>          The type of the spell ingredient.
     */
    <T extends ISpellIngredient> void registerSpellIngredientType(ResourceLocation type, Codec<T> codec, Codec<T> networkCodec, Supplier<ISpellIngredientRenderer<T>> renderer);

    /**
     * @param type The id of the spell ingredient type.
     * @return The codec for the spell ingredient type.
     */
    Codec<ISpellIngredient> getSpellIngredientCodec(ResourceLocation type);

    /**
     * @param type The id of the spell ingredient type.
     * @return The network codec for the spell ingredient type.
     */
    Codec<ISpellIngredient> getSpellIngredientNetworkCodec(ResourceLocation type);

    /**
     * @param type The id of the spell ingredient type.
     * @return The renderer for the spell ingredient type.
     */
    <T extends ISpellIngredient> ISpellIngredientRenderer<T> getSpellIngredientRenderer(ResourceLocation type);
}
