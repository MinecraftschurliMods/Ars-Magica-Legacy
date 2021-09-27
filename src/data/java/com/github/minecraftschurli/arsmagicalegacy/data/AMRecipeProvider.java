package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

class AMRecipeProvider extends RecipeProvider {
    public AMRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(AMItems.WITCHWOOD.get(), 3)
                .group("bark")
                .pattern("##")
                .pattern("##")
                .define('#', AMItems.WITCHWOOD_LOG.get())
                .unlockedBy("has_witchwood_log", has(AMItems.WITCHWOOD_LOG.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.STRIPPED_WITCHWOOD.get(), 3)
                .group("bark")
                .pattern("##")
                .pattern("##")
                .define('#', AMItems.STRIPPED_WITCHWOOD_LOG.get())
                .unlockedBy("has_stripped_witchwood_log", has(AMItems.STRIPPED_WITCHWOOD_LOG.get()))
                .save(consumer);
/*
        ShapelessRecipeBuilder.shapeless(AMItems.WITCHWOOD_PLANKS.get(), 4)
                .group("planks")
                .requires(AMTags.Items.WITCHWOOD_LOGS)
                .save(consumer);
*/
        ShapedRecipeBuilder.shaped(AMItems.WITCHWOOD_SLAB.get(), 6)
                .group("wooden_slab")
                .pattern("###")
                .define('#', AMItems.WITCHWOOD_PLANKS.get())
                .unlockedBy("has_witchwood_planks", has(AMItems.WITCHWOOD_PLANKS.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.WITCHWOOD_STAIRS.get(), 4)
                .group("wooden_stairs")
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', AMItems.WITCHWOOD_PLANKS.get())
                .unlockedBy("has_witchwood_planks", has(AMItems.WITCHWOOD_PLANKS.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.WITCHWOOD_FENCE.get(), 3)
                .group("wooden_fence")
                .pattern("#-#")
                .pattern("#-#")
                .define('#', AMItems.WITCHWOOD_PLANKS.get())
                .define('-', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_witchwood_planks", has(AMItems.WITCHWOOD_PLANKS.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.WITCHWOOD_FENCE_GATE.get())
                .group("wooden_fence_gate")
                .pattern("-#-")
                .pattern("-#-")
                .define('#', AMItems.WITCHWOOD_PLANKS.get())
                .define('-', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_witchwood_planks", has(AMItems.WITCHWOOD_PLANKS.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.WITCHWOOD_DOOR.get(), 3)
                .group("wooden_door")
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', AMItems.WITCHWOOD_PLANKS.get())
                .unlockedBy("has_witchwood_planks", has(AMItems.WITCHWOOD_PLANKS.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.WITCHWOOD_TRAPDOOR.get(), 2)
                .group("wooden_trapdoor")
                .pattern("###")
                .pattern("###")
                .define('#', AMItems.WITCHWOOD_PLANKS.get())
                .unlockedBy("has_witchwood_planks", has(AMItems.WITCHWOOD_PLANKS.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.WITCHWOOD_BUTTON.get())
                .group("wooden_button")
                .requires(AMItems.WITCHWOOD_PLANKS.get())
                .unlockedBy("has_witchwood_planks", has(AMItems.WITCHWOOD_PLANKS.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.WITCHWOOD_PRESSURE_PLATE.get())
                .group("wooden_pressure_plate")
                .pattern("##")
                .define('#', AMItems.WITCHWOOD_PLANKS.get())
                .unlockedBy("has_witchwood_planks", has(AMItems.WITCHWOOD_PLANKS.get()))
                .save(consumer);
    }
}
