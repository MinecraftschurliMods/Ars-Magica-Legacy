package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * Interface representing an ingredient for spell crafting.
 */
public interface ISpellIngredient {
    /**
     * The codec used to serialize and deserialize any ingredient.
     */
    Codec<ISpellIngredient> CODEC = CompoundTag.CODEC.flatXmap(tag -> {
        String type = tag.getString("type");
        tag.remove("type");
        return ArsMagicaAPI.get()
                           .getSpellDataManager()
                           .getSpellIngredientCodec(new ResourceLocation(type))
                           .decode(NbtOps.INSTANCE, tag)
                           .map(Pair::getFirst);
    }, ingredient -> ArsMagicaAPI.get()
                                 .getSpellDataManager()
                                 .getSpellIngredientCodec(ingredient.getType())
                                 .encodeStart(NbtOps.INSTANCE, ingredient)
                                 .map(CompoundTag.class::cast)
                                 .map(tag -> {
                                     tag.putString("type", ingredient.getType().toString());
                                     return tag;
                                 }));

    /**
     * The codec used to serialize and deserialize any ingredient in a network context.
     */
    Codec<ISpellIngredient> NETWORK_CODEC = CompoundTag.CODEC.flatXmap(tag -> {
        String type = tag.getString("type");
        tag.remove("type");
        return ArsMagicaAPI.get()
                           .getSpellDataManager()
                           .getSpellIngredientNetworkCodec(new ResourceLocation(type))
                           .decode(NbtOps.INSTANCE, tag)
                           .map(Pair::getFirst);
    }, ingredient -> ArsMagicaAPI.get()
                                 .getSpellDataManager()
                                 .getSpellIngredientNetworkCodec(ingredient.getType())
                                 .encodeStart(NbtOps.INSTANCE, ingredient)
                                 .map(CompoundTag.class::cast)
                                 .map(tag -> {
                                     tag.putString("type", ingredient.getType().toString());
                                     return tag;
                                 }));

    /**
     * Get the resourcelocation of the type.
     */
    ResourceLocation getType();

    /**
     * Get the tooltip.
     */
    Component getTooltip();

    /**
     * Check if this can combine with the other.
     */
    boolean canCombine(ISpellIngredient other);

    /**
     * Combine this with the other.
     * @return the combined ingredient
     */
    @Nullable ISpellIngredient combine(ISpellIngredient other);

    /**
     * Consume this ingredient. Return the remainder of this.
     * @return the remainder of this
     */
    @Nullable ISpellIngredient consume(Level level, BlockPos pos);
}
