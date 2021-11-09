package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public interface ISpellIngredient { // TODO IMPLEMENT
    ResourceLocation getType();
}
