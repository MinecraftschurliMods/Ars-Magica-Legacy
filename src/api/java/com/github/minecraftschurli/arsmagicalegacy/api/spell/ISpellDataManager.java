package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.client.ISpellIngredientRenderer;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Interface representing a spell data manager
 */
public interface ISpellDataManager {
    /**
     * Get the data for the given spell part.
     *
     * @param part the part to get the data for
     * @return the spell part data for the part or null if it is not available
     */
    @Nullable
    ISpellPartData getDataForPart(ISpellPart part);

    /**
     * Register a spell ingredient type.
     *
     * @param type the resource location to identify this type
     * @param codec the codec to serialize/deserialize the spell ingredient
     * @param renderer supplier of the renderer (value gets cached)
     * @param <T> the type of the spell ingredient
     */
    <T extends ISpellIngredient> void registerSpellIngredientType(ResourceLocation type, Codec<T> codec, Supplier<ISpellIngredientRenderer<T>> renderer);

    /**
     * Get the codec for the spell ingredient type.
     *
     * @param type the resource location identifying the spell ingredient type
     * @return the codec for the spell ingredient
     */
    Codec<ISpellIngredient> getSpellIngredientCodec(ResourceLocation type);

    /**
     * Get the renderer for the spell ingredient type.
     *
     * @param type the resource location identifying the spell ingredient type
     * @return the renderer for the spell ingredient
     */
    <T extends ISpellIngredient> ISpellIngredientRenderer<T> getSpellIngredientRenderer(ResourceLocation type);
}
