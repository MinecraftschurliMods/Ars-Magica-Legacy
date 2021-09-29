package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

class AMRecipeProvider extends RecipeProvider {
    public AMRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(AMItems.CHIMERITE.get(), 9)
                .requires(AMItems.CHIMERITE_BLOCK.get())
                .unlockedBy("has_chimerite_block", has(AMItems.CHIMERITE_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.CHIMERITE_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#',AMItems.CHIMERITE.get())
                .unlockedBy("has_chimerite", has(AMItems.CHIMERITE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.TOPAZ.get(), 9)
                .requires(AMItems.TOPAZ_BLOCK.get())
                .unlockedBy("has_topaz_block", has(AMItems.TOPAZ_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.TOPAZ_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#',AMItems.TOPAZ.get())
                .unlockedBy("has_topaz", has(AMItems.TOPAZ.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.VINTEUM_DUST.get(), 9)
                .requires(AMItems.VINTEUM_BLOCK.get())
                .unlockedBy("has_vinteum_block", has(AMItems.VINTEUM_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.VINTEUM_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#',AMItems.VINTEUM_DUST.get())
                .unlockedBy("has_vinteum_dust", has(AMItems.VINTEUM_DUST.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.MOONSTONE.get(), 9)
                .requires(AMItems.MOONSTONE_BLOCK.get())
                .unlockedBy("has_moonstone_block", has(AMItems.MOONSTONE_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.MOONSTONE_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#',AMItems.MOONSTONE.get())
                .unlockedBy("has_moonstone", has(AMItems.MOONSTONE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.SUNSTONE.get(), 9)
                .requires(AMItems.SUNSTONE_BLOCK.get())
                .unlockedBy("has_sunstone_block", has(AMItems.SUNSTONE_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.SUNSTONE_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#',AMItems.SUNSTONE.get())
                .unlockedBy("has_sunstone", has(AMItems.SUNSTONE.get()))
                .save(consumer);

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
        ShapedRecipeBuilder.shaped(AMItems.BLANK_RUNE.get(),2)
                .pattern(" # ")
                .pattern("###")
                .pattern("## ")
                .define('#',Tags.Items.COBBLESTONE)
                .unlockedBy("has_cobblestone", has(Tags.Items.COBBLESTONE))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.WHITE_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_WHITE)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.ORANGE_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_ORANGE)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.MAGENTA_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_MAGENTA)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.LIGHT_BLUE_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_LIGHT_BLUE)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.YELLOW_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_YELLOW)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.LIME_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_LIME)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.PINK_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_PINK)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.GRAY_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_GRAY)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.LIGHT_GRAY_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_LIGHT_GRAY)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.CYAN_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_CYAN)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.PURPLE_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_PURPLE)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.BLUE_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_BLUE)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.BROWN_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_BROWN)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.GREEN_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_GREEN)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.RED_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_RED)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.BLACK_RUNE.get())
                .requires(AMItems.BLANK_RUNE.get())
                .requires(Tags.Items.DYES_BLACK)
                .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.RUNE_BAG.get())
                .pattern("LLL")
                .pattern("W W")
                .pattern("LLL")
                .define('L',Tags.Items.LEATHER)
                .define('W', ItemTags.WOOL)
                .unlockedBy("has_leather", has(Tags.Items.LEATHER))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.ARCANE_COMPOUND.get())
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .requires(Tags.Items.DUSTS_GLOWSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.DUSTS_REDSTONE)
                .requires(Tags.Items.NETHERRACK)
                .requires(Tags.Items.NETHERRACK)
                .requires(Tags.Items.STONE)
                .requires(Tags.Items.STONE)
                .unlockedBy("has_glowstone", has(Tags.Items.DUSTS_GLOWSTONE))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.PURIFIED_VINTEUM_DUST.get())
                .requires(AMItems.ARCANE_ASH.get())
                .requires(AMItems.CERUBLOSSOM.get())
                .requires(AMItems.DESERT_NOVA.get())
                .requires(AMItems.VINTEUM_DUST.get())
                .unlockedBy("has_vinteum_dust", has(AMItems.VINTEUM_DUST.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.VINTEUM_TORCH.get())
                .pattern("V")
                .pattern("S")
                .define('V',AMItems.VINTEUM_DUST.get())
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_vinteum_dust", has(AMItems.VINTEUM_DUST.get()))
                .save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AMItems.ARCANE_COMPOUND.get()), AMItems.ARCANE_ASH.get(), 0.2F, 200)
                .unlockedBy("has_arcane_compound", has(AMItems.ARCANE_COMPOUND.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.WIZARDS_CHALK.get())
                .requires(AMItems.VINTEUM_DUST.get())
                .requires(Items.BONE_MEAL)
                .requires(Items.CLAY_BALL)
                .requires(Items.FLINT)
                .requires(Items.PAPER)
                .unlockedBy("has_vinteum_dust", has(AMItems.VINTEUM_DUST.get()))
                .save(consumer);
    }
}
