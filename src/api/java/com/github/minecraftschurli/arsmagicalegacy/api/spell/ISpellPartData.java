package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.mojang.datafixers.util.Either;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Set;

/**
 * Interface representing spell part data
 */
public interface ISpellPartData {

    /**
     * Get the recipe for the associated spell part.
     *
     * @return the recipe for the associated spell part
     */
    List<ISpellIngredient> recipe();

    /**
     * Get the affinities for the associated spell part.
     *
     * @return the affinities for the associated spell part
     */
    Set<IAffinity> affinities();

    /**
     * Get the reagents consumed when casting the spell.
     *
     * @return the reagents consumed when casting the spell
     */
    List<Either<Ingredient, ItemStack>> reagents();

    /**
     * Get the mana cost of the associated spell part.
     *
     * @return the mana cost of the associated spell part
     */
    float manaCost();

    /**
     * Get the burnout of the associated spell part.
     *
     * @return the burnout of the associated spell part
     */
    float burnout();
}
