package com.github.minecraftschurlimods.arsmagicalegacy.common.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class AMBrewingRecipe implements IBrewingRecipe {
    private final Potion inputPotion;
    private final Ingredient ingredient;
    private final Potion outputPotion;

    public AMBrewingRecipe(Potion inputPotion, Ingredient ingredient, Potion outputPotion) {
        this.inputPotion = inputPotion;
        this.ingredient = ingredient;
        this.outputPotion = outputPotion;
    }

    @Override
    public boolean isInput(ItemStack input) {
        Item item = input.getItem();
        return PotionUtils.getPotion(input) == inputPotion && (item == Items.POTION || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION || item == Items.GLASS_BOTTLE);
    }

    @Override
    public boolean isIngredient(ItemStack stack) {
        return ingredient.test(stack);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack stack) {
        if (!input.isEmpty() && !stack.isEmpty() && isIngredient(stack)) {
            ItemStack result = PotionUtils.setPotion(input.copy(), outputPotion);
            return result != input ? result : ItemStack.EMPTY;
        }
        return ItemStack.EMPTY;
    }
}
