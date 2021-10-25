package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

public interface ISpellDataManager {
    ISpellPartData getDataForPart(ISpellPart part);

    @SuppressWarnings("unchecked")
    <T extends ISpellIngredient> void registerSpellIngredientType(ResourceLocation type, Codec<T> codec);

    Codec<ISpellIngredient> getSpellIngredientCodec(ResourceLocation type);
}
