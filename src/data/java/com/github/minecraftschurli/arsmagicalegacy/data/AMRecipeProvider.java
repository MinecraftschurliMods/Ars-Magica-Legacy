package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
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
        ShapedRecipeBuilder.shaped(AMItems.OCCULUS.get())
                .pattern("SGS")
                .pattern(" S ")
                .pattern("CTC")
                .define('S', Items.STONE_BRICKS)
                .define('G', Tags.Items.GLASS)
                .define('C', ItemTags.COALS)
                .define('T', AMTags.Items.GEMS_TOPAZ)
                .unlockedBy("has_topaz", has(AMTags.Items.GEMS_TOPAZ))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.CHIMERITE.get(), 9)
                .requires(AMTags.Items.STORAGE_BLOCKS_CHIMERITE)
                .unlockedBy("has_chimerite_block", has(AMItems.CHIMERITE_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.CHIMERITE_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', AMTags.Items.GEMS_CHIMERITE)
                .unlockedBy("has_chimerite", has(AMItems.CHIMERITE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.TOPAZ.get(), 9)
                .requires(AMTags.Items.STORAGE_BLOCKS_TOPAZ)
                .unlockedBy("has_topaz_block", has(AMItems.TOPAZ_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.TOPAZ_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', AMTags.Items.GEMS_TOPAZ)
                .unlockedBy("has_topaz", has(AMItems.TOPAZ.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.VINTEUM_DUST.get(), 9)
                .requires(AMTags.Items.STORAGE_BLOCKS_VINTEUM)
                .unlockedBy("has_vinteum_block", has(AMItems.VINTEUM_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.VINTEUM_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', AMTags.Items.DUSTS_VINTEUM)
                .unlockedBy("has_vinteum_dust", has(AMItems.VINTEUM_DUST.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.MOONSTONE.get(), 9)
                .requires(AMTags.Items.STORAGE_BLOCKS_MOONSTONE)
                .unlockedBy("has_moonstone_block", has(AMItems.MOONSTONE_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.MOONSTONE_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', AMTags.Items.GEMS_MOONSTONE)
                .unlockedBy("has_moonstone", has(AMItems.MOONSTONE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.SUNSTONE.get(), 9)
                .requires(AMTags.Items.STORAGE_BLOCKS_SUNSTONE)
                .unlockedBy("has_sunstone_block", has(AMItems.SUNSTONE_BLOCK.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.SUNSTONE_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', AMTags.Items.GEMS_SUNSTONE)
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
        ShapedRecipeBuilder.shaped(AMItems.BLANK_RUNE.get(), 2)
                .pattern(" # ")
                .pattern("###")
                .pattern("## ")
                .define('#', Tags.Items.COBBLESTONE)
                .unlockedBy("has_cobblestone", has(Tags.Items.COBBLESTONE))
                .save(consumer);
        var hasBlankRune = has(AMItems.BLANK_RUNE.get());
        for (DyeColor color : DyeColor.values()) {
            ShapelessRecipeBuilder.shapeless(AMItems.COLORED_RUNE.get(color))
                    .requires(AMItems.BLANK_RUNE.get())
                    .requires(color.getTag())
                    .unlockedBy("has_blank_rune", hasBlankRune)
                    .save(consumer);
        }
        ShapedRecipeBuilder.shaped(AMItems.RUNE_BAG.get())
                .pattern("LLL")
                .pattern("W W")
                .pattern("LLL")
                .define('L', Tags.Items.LEATHER)
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
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AMItems.ARCANE_COMPOUND.get()), AMItems.ARCANE_ASH.get(), 0.2F, 200)
                .unlockedBy("has_arcane_compound", has(AMItems.ARCANE_COMPOUND.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.PURIFIED_VINTEUM_DUST.get())
                .requires(AMItems.ARCANE_ASH.get())
                .requires(AMItems.CERUBLOSSOM.get())
                .requires(AMItems.DESERT_NOVA.get())
                .requires(AMTags.Items.DUSTS_VINTEUM)
                .unlockedBy("has_vinteum_dust", has(AMTags.Items.DUSTS_VINTEUM))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.VINTEUM_TORCH.get())
                .pattern("V")
                .pattern("S")
                .define('V', AMTags.Items.DUSTS_VINTEUM)
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_vinteum_dust", has(AMTags.Items.DUSTS_VINTEUM))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.WIZARDS_CHALK.get())
                .requires(AMTags.Items.DUSTS_VINTEUM)
                .requires(Items.BONE_MEAL)
                .requires(Items.CLAY_BALL)
                .requires(Items.FLINT)
                .requires(Items.PAPER)
                .unlockedBy("has_vinteum_dust", has(AMTags.Items.DUSTS_VINTEUM))
                .save(consumer);
    }
}
