package com.github.minecraftschurlimods.arsmagicalegacy.common.recipe.spelltransformation;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import org.jspecify.annotations.Nullable;

public class SpellTransformationBuilder implements RecipeBuilder {
    private final SpellTransformationRecipe recipe;

    public SpellTransformationBuilder(RuleTest ruleTest, Holder<SpellPart> spellPart, BlockState result) {
        recipe = new SpellTransformationRecipe(ruleTest, spellPart, result);
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, BuiltInRegistries.BLOCK.getKey(recipe.result().getBlock()));
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> resourceKey) {
        recipeOutput.accept(resourceKey, recipe, null);
    }
}
