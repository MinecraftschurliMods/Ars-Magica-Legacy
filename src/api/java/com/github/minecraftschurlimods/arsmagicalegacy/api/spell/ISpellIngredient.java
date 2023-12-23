package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Interface representing an ingredient for spell crafting.
 */
public interface ISpellIngredient {
    Codec<ISpellIngredient> CODEC = CodecHelper.forRegistry(ArsMagicaAPI.get()::getSpellIngredientTypeRegistry).dispatch(ISpellIngredient::getType, SpellIngredientType::codec);
    Codec<ISpellIngredient> NETWORK_CODEC = CodecHelper.forRegistry(ArsMagicaAPI.get()::getSpellIngredientTypeRegistry).dispatch(ISpellIngredient::getType, SpellIngredientType::networkCodec);

    /**
     * @return The id of this type.
     */
    SpellIngredientType<? extends ISpellIngredient> getType();

    /**
     * @return The count of this type.
     */
    int getCount();

    /**
     * @return The tooltip of this type.
     */
    List<Component> getTooltip();

    /**
     * @param other The other ingredient to check.
     * @return If this ingredient can combine with the other given ingredient.
     */
    boolean canCombine(ISpellIngredient other);

    /**
     * @param other The other ingredient to combine this one with.
     * @return This ingredient, combined with the other one.
     */
    @Nullable
    ISpellIngredient combine(ISpellIngredient other);

    /**
     * Consumes this ingredient.
     *
     * @param level The level in which this ingredient is consumed.
     * @param pos   The position at which this ingredient is consumed.
     * @return The leftover of the consumption.
     */
    @Nullable
    ISpellIngredient consume(Level level, BlockPos pos);
}
