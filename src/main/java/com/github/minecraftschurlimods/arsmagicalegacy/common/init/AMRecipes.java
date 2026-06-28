package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.recipe.spelltransformation.SpellTransformationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMRecipes {
    DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ArsMagicaApi.MOD_ID);
    DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SpellTransformationRecipe>> SPELL_TRANSFORMATION_SERIALIZER =
        RECIPE_SERIALIZERS.register("spell_transformation", () -> new RecipeSerializer<>(SpellTransformationRecipe.CODEC, SpellTransformationRecipe.STREAM_CODEC));

    DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, ArsMagicaApi.MOD_ID);
    DeferredHolder<RecipeType<?>, RecipeType<SpellTransformationRecipe>> SPELL_TRANSFORMATION_TYPE =
        RECIPE_TYPES.register("spell_transformation", () -> RecipeType.simple(ArsMagicaApi.id("spell_transformation")));

    RecipeBookCategory SPELL_RECIPE_BOOK_CATEGORY = new RecipeBookCategory();
}
