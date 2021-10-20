package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.mojang.datafixers.util.Either;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Set;

public interface ISpellPartData {
    List<ISpellIngredient> recipe();
    Set<IAffinity> affinities();
    List<Either<Ingredient, ItemStack>> reagents();
    float manaCost();
    float burnout();
}
