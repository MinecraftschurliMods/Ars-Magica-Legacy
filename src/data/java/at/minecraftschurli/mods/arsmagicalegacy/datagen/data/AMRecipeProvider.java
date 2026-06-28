package at.minecraftschurli.mods.arsmagicalegacy.datagen.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.recipe.spelltransformation.SpellTransformationBuilder;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class AMRecipeProvider extends RecipeProvider {
    public AMRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        HolderLookup.RegistryLookup<Affinity> affinities = registries.lookupOrThrow(AMRegistries.Keys.AFFINITY);
        HolderLookup.RegistryLookup<Item> items = registries.lookupOrThrow(Registries.ITEM);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, AMItems.OCCULUS.get())
            .pattern("SGS")
            .pattern(" S ")
            .pattern("CTC")
            .define('S', Items.STONE_BRICKS)
            .define('G', Tags.Items.GLASS_BLOCKS)
            .define('C', ItemTags.COALS)
            .define('T', AMTags.Items.GEMS_TOPAZ)
            .unlockedBy(getHasName(AMItems.TOPAZ), has(AMTags.Items.GEMS_TOPAZ))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, AMItems.INSCRIPTION_TABLE.get())
            .pattern("TPF")
            .pattern("SSS")
            .pattern("W W")
            .define('T', Items.TORCH)
            .define('P', AMItems.SPELL_PARCHMENT.get())
            .define('F', Tags.Items.FEATHERS)
            .define('S', ItemTags.WOODEN_SLABS)
            .define('W', ItemTags.PLANKS)
            .unlockedBy(getHasName(AMItems.SPELL_PARCHMENT), has(AMItems.SPELL_PARCHMENT.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1.get())
            .requires(Items.BOOK)
            .requires(Tags.Items.DYES_BLACK)
            .requires(Tags.Items.FEATHERS)
            .requires(Tags.Items.STRINGS)
            .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2.get())
            .requires(Items.BOOK)
            .requires(Tags.Items.DYES_BLACK)
            .requires(ItemTags.WOOL_CARPETS)
            .requires(AMItems.WIZARDS_CHALK.get())
            .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3.get())
            .requires(Items.BOOK)
            .requires(ItemTags.CANDLES)
            .requires(Items.HONEYCOMB)
            .requires(Items.GLASS_BOTTLE)
            .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, new ItemStackTemplate(AMItems.INSCRIPTION_TABLE, 1, DataComponentPatch.builder().set(AMDataComponents.TIER.get(), 1).build()))
            .requires(DataComponentIngredient.of(false, AMDataComponents.TIER, 0, AMItems.INSCRIPTION_TABLE.get()))
            .requires(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1.get())
            .unlockedBy(getHasName(AMItems.INSCRIPTION_TABLE), has(AMItems.INSCRIPTION_TABLE.get()))
            .save(output, id("inscription_table_tier_1"));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, new ItemStackTemplate(AMItems.INSCRIPTION_TABLE, 1, DataComponentPatch.builder().set(AMDataComponents.TIER.get(), 2).build()))
            .requires(DataComponentIngredient.of(false, AMDataComponents.TIER, 1, AMItems.INSCRIPTION_TABLE.get()))
            .requires(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2.get())
            .unlockedBy(getHasName(AMItems.INSCRIPTION_TABLE), has(AMItems.INSCRIPTION_TABLE.get()))
            .save(output, id("inscription_table_tier_2"));
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, new ItemStackTemplate(AMItems.INSCRIPTION_TABLE, 1, DataComponentPatch.builder().set(AMDataComponents.TIER.get(), 3).build()))
            .requires(DataComponentIngredient.of(false, AMDataComponents.TIER, 2, AMItems.INSCRIPTION_TABLE.get()))
            .requires(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3.get())
            .unlockedBy(getHasName(AMItems.INSCRIPTION_TABLE), has(AMItems.INSCRIPTION_TABLE.get()))
            .save(output, id("inscription_table_tier_3"));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, AMItems.ALTAR_CORE.get())
            .pattern("V")
            .pattern("S")
            .define('V', AMTags.Items.DUSTS_VINTEUM)
            .define('S', Tags.Items.STONES)
            .unlockedBy(getHasName(AMItems.VINTEUM_DUST), has(AMTags.Items.DUSTS_VINTEUM))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, AMItems.MAGIC_WALL.get(), 16)
            .pattern("VSV")
            .define('V', AMTags.Items.DUSTS_VINTEUM)
            .define('S', Tags.Items.STONES)
            .unlockedBy(getHasName(AMItems.VINTEUM_DUST), has(AMTags.Items.DUSTS_VINTEUM))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, AMItems.OBELISK.get())
            .pattern("VSV")
            .pattern("SBS")
            .pattern("VSV")
            .define('V', AMTags.Items.DUSTS_VINTEUM)
            .define('S', Tags.Items.STONES)
            .define('B', ItemTags.STONE_BRICKS)
            .unlockedBy(getHasName(AMItems.VINTEUM_DUST), has(AMTags.Items.DUSTS_VINTEUM))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, AMItems.CRYSTAL_WRENCH.get())
            .pattern("GTG")
            .pattern(" G ")
            .pattern(" G ")
            .define('G', Tags.Items.INGOTS_GOLD)
            .define('T', AMTags.Items.GEMS_TOPAZ)
            .unlockedBy(getHasName(AMItems.TOPAZ), has(AMTags.Items.GEMS_TOPAZ))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.TOOLS, AMItems.WIZARDS_CHALK.get())
            .requires(AMTags.Items.DUSTS_VINTEUM)
            .requires(Items.BONE_MEAL)
            .requires(Items.CLAY_BALL)
            .requires(Items.FLINT)
            .requires(Items.PAPER)
            .unlockedBy(getHasName(AMItems.VINTEUM_DUST), has(AMTags.Items.DUSTS_VINTEUM))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.TRANSPORTATION, AMItems.REDSTONE_INLAY.get())
            .pattern("RRR")
            .pattern("RVR")
            .pattern("RRR")
            .define('R', Tags.Items.DUSTS_REDSTONE)
            .define('V', AMTags.Items.DUSTS_VINTEUM)
            .unlockedBy(getHasName(Items.REDSTONE), has(Tags.Items.DUSTS_REDSTONE))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.TRANSPORTATION, AMItems.IRON_INLAY.get())
            .pattern("III")
            .pattern("IAI")
            .pattern("III")
            .define('I', Tags.Items.INGOTS_IRON)
            .define('A', AMTags.Items.DUSTS_ARCANE_ASH)
            .unlockedBy(getHasName(Items.IRON_INGOT), has(Tags.Items.INGOTS_IRON))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.TRANSPORTATION, AMItems.GOLD_INLAY.get())
            .pattern("III")
            .pattern("IVI")
            .pattern("III")
            .define('I', Tags.Items.INGOTS_GOLD)
            .define('V', AMTags.Items.DUSTS_PURIFIED_VINTEUM)
            .unlockedBy(getHasName(Items.GOLD_INGOT), has(Tags.Items.INGOTS_GOLD))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, AMItems.VINTEUM_TORCH.get())
            .pattern("V")
            .pattern("S")
            .define('V', AMTags.Items.DUSTS_VINTEUM)
            .define('S', Tags.Items.RODS_WOODEN)
            .unlockedBy(getHasName(AMItems.VINTEUM_DUST), has(AMTags.Items.DUSTS_VINTEUM))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, AMItems.SPELL_PARCHMENT.get())
            .pattern("S")
            .pattern("P")
            .pattern("S")
            .define('S', Tags.Items.RODS_WOODEN)
            .define('P', Items.PAPER)
            .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, AMItems.SPELL_BOOK.get())
            .pattern("SLL")
            .pattern("SPP")
            .pattern("SLL")
            .define('S', Tags.Items.STRINGS)
            .define('L', Tags.Items.LEATHERS)
            .define('P', Items.PAPER)
            .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, AMItems.MAGITECH_GOGGLES.get())
            .pattern("LLL")
            .pattern("CGC")
            .pattern("TLT")
            .define('L', Tags.Items.LEATHERS)
            .define('C', AMTags.Items.GEMS_CHIMERITE)
            .define('T', AMTags.Items.GEMS_TOPAZ)
            .define('G', Tags.Items.NUGGETS_GOLD)
            .unlockedBy(getHasName(AMItems.TOPAZ), has(AMTags.Items.GEMS_TOPAZ))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, AMItems.MAGE_HELMET.get())
            .pattern("LWL")
            .pattern("WRW")
            .define('W', ItemTags.WOOL)
            .define('L', Tags.Items.LEATHERS)
            .define('R', AMItems.MAGENTA_RUNE)
            .unlockedBy(getHasName(AMItems.MAGENTA_RUNE), has(AMItems.MAGENTA_RUNE))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, AMItems.MAGE_CHESTPLATE.get())
            .pattern("L L")
            .pattern("WRW")
            .pattern("WWW")
            .define('W', ItemTags.WOOL)
            .define('L', Tags.Items.LEATHERS)
            .define('R', AMItems.WHITE_RUNE)
            .unlockedBy(getHasName(AMItems.WHITE_RUNE), has(AMItems.WHITE_RUNE))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, AMItems.MAGE_LEGGINGS.get())
            .pattern("LRL")
            .pattern("W W")
            .pattern("W W")
            .define('W', ItemTags.WOOL)
            .define('L', Tags.Items.LEATHERS)
            .define('R', AMItems.YELLOW_RUNE)
            .unlockedBy(getHasName(AMItems.YELLOW_RUNE), has(AMItems.YELLOW_RUNE))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, AMItems.MAGE_BOOTS.get())
            .pattern("W W")
            .pattern("LRL")
            .define('W', ItemTags.WOOL)
            .define('L', Tags.Items.LEATHERS)
            .define('R', AMItems.BLACK_RUNE)
            .unlockedBy(getHasName(AMItems.BLACK_RUNE), has(AMItems.BLACK_RUNE))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, AMItems.BATTLEMAGE_HELMET.get())
            .pattern("GOG")
            .pattern("ORO")
            .pattern(" E ")
            .define('O', Tags.Items.OBSIDIANS_NORMAL)
            .define('G', Tags.Items.INGOTS_GOLD)
            .define('E', DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.AIR), AMItems.AFFINITY_ESSENCE))
            .define('R', AMItems.YELLOW_RUNE)
            .unlockedBy(getHasName(AMItems.YELLOW_RUNE), has(AMItems.YELLOW_RUNE))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, AMItems.BATTLEMAGE_CHESTPLATE.get())
            .pattern("GEG")
            .pattern("ORO")
            .pattern("OOO")
            .define('O', Tags.Items.OBSIDIANS_NORMAL)
            .define('G', Tags.Items.INGOTS_GOLD)
            .define('E', DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.EARTH), AMItems.AFFINITY_ESSENCE))
            .define('R', AMItems.GREEN_RUNE)
            .unlockedBy(getHasName(AMItems.GREEN_RUNE), has(AMItems.GREEN_RUNE))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, AMItems.BATTLEMAGE_LEGGINGS.get())
            .pattern("GRG")
            .pattern("OEO")
            .pattern("O O")
            .define('O', Tags.Items.OBSIDIANS_NORMAL)
            .define('G', Tags.Items.INGOTS_GOLD)
            .define('E', DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.FIRE), AMItems.AFFINITY_ESSENCE))
            .define('R', AMItems.RED_RUNE)
            .unlockedBy(getHasName(AMItems.RED_RUNE), has(AMItems.RED_RUNE))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, AMItems.BATTLEMAGE_BOOTS.get())
            .pattern("OEO")
            .pattern("GRG")
            .define('O', Tags.Items.OBSIDIANS_NORMAL)
            .define('G', Tags.Items.INGOTS_GOLD)
            .define('E', DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.WATER), AMItems.AFFINITY_ESSENCE))
            .define('R', AMItems.BLUE_RUNE)
            .unlockedBy(getHasName(AMItems.BLUE_RUNE), has(AMItems.BLUE_RUNE))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, AMItems.MANA_CAKE.get(), 3)
            .requires(Tags.Items.CROPS_WHEAT)
            .requires(Items.SUGAR)
            .requires(AMItems.AUM)
            .requires(AMItems.CERUBLOSSOM)
            .unlockedBy(getHasName(AMItems.CERUBLOSSOM), has(AMItems.CERUBLOSSOM))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, AMItems.MANA_MARTINI.get())
            .requires(Items.BAMBOO)
            .requires(Items.ICE)
            .requires(Items.SUGAR)
            .requires(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER), Items.POTION))
            .unlockedBy(getHasName(Items.ICE), has(Items.ICE))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, affinityEssence(affinities, AMMagic.WATER).withCount(2))
            .pattern("AIA")
            .pattern("JEJ")
            .pattern("AIA")
            .define('A', AMTags.Items.DUSTS_ARCANE_ASH)
            .define('E', DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.WATER), AMItems.AFFINITY_ESSENCE))
            .define('I', AMItems.WAKEBLOOM.get())
            .define('J', Items.WATER_BUCKET)
            .unlockedBy(getHasName(AMItems.ARCANE_ASH.get()), has(AMTags.Items.DUSTS_ARCANE_ASH))
            .save(output, id("affinity_essence_water"));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, affinityEssence(affinities, AMMagic.FIRE).withCount(2))
            .pattern("AIA")
            .pattern("JEJ")
            .pattern("AIA")
            .define('A', AMTags.Items.DUSTS_ARCANE_ASH)
            .define('E', DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.FIRE), AMItems.AFFINITY_ESSENCE))
            .define('I', ItemTags.COALS)
            .define('J', Items.BLAZE_POWDER)
            .unlockedBy(getHasName(AMItems.ARCANE_ASH.get()), has(AMTags.Items.DUSTS_ARCANE_ASH))
            .save(output, id("affinity_essence_fire"));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, affinityEssence(affinities, AMMagic.EARTH).withCount(2))
            .pattern("AIA")
            .pattern("JEJ")
            .pattern("AIA")
            .define('A', AMTags.Items.DUSTS_ARCANE_ASH)
            .define('E', DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.EARTH), AMItems.AFFINITY_ESSENCE))
            .define('I', ItemTags.DIRT)
            .define('J', Tags.Items.STONES)
            .unlockedBy(getHasName(AMItems.ARCANE_ASH.get()), has(AMTags.Items.DUSTS_ARCANE_ASH))
            .save(output, id("affinity_essence_earth"));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, affinityEssence(affinities, AMMagic.AIR).withCount(2))
            .pattern("AIA")
            .pattern("JEJ")
            .pattern("AIA")
            .define('A', AMTags.Items.DUSTS_ARCANE_ASH)
            .define('E', DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.AIR), AMItems.AFFINITY_ESSENCE))
            .define('I', Items.FEATHER)
            .define('J', AMItems.TARMA_ROOT.get())
            .unlockedBy(getHasName(AMItems.ARCANE_ASH.get()), has(AMTags.Items.DUSTS_ARCANE_ASH))
            .save(output, id("affinity_essence_air"));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, affinityEssence(affinities, AMMagic.ICE).withCount(2))
            .pattern("AIA")
            .pattern("JEJ")
            .pattern("AIA")
            .define('A', AMTags.Items.DUSTS_ARCANE_ASH)
            .define('E', DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ICE), AMItems.AFFINITY_ESSENCE))
            .define('I', Items.SNOW_BLOCK)
            .define('J', Items.ICE)
            .unlockedBy(getHasName(AMItems.ARCANE_ASH.get()), has(AMTags.Items.DUSTS_ARCANE_ASH))
            .save(output, id("affinity_essence_ice"));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, affinityEssence(affinities, AMMagic.LIGHTNING).withCount(2))
            .pattern("AIA")
            .pattern("JEJ")
            .pattern("AIA")
            .define('A', AMTags.Items.DUSTS_ARCANE_ASH)
            .define('E', DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIGHTNING), AMItems.AFFINITY_ESSENCE))
            .define('I', Tags.Items.DUSTS_REDSTONE)
            .define('J', Tags.Items.DUSTS_GLOWSTONE)
            .unlockedBy(getHasName(AMItems.ARCANE_ASH.get()), has(AMTags.Items.DUSTS_ARCANE_ASH))
            .save(output, id("affinity_essence_lightning"));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, affinityEssence(affinities, AMMagic.NATURE).withCount(2))
            .pattern("AIA")
            .pattern("JEK")
            .pattern("ALA")
            .define('A', AMTags.Items.DUSTS_ARCANE_ASH)
            .define('E', DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.NATURE), AMItems.AFFINITY_ESSENCE))
            .define('I', ItemTags.LEAVES)
            .define('J', Items.LILY_PAD)
            .define('K', Items.CACTUS)
            .define('L', Items.VINE)
            .unlockedBy(getHasName(AMItems.ARCANE_ASH.get()), has(AMTags.Items.DUSTS_ARCANE_ASH))
            .save(output, id("affinity_essence_nature"));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, affinityEssence(affinities, AMMagic.LIFE).withCount(2))
            .pattern("AIA")
            .pattern("JEJ")
            .pattern("AIA")
            .define('A', AMTags.Items.DUSTS_ARCANE_ASH)
            .define('E', DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIFE), AMItems.AFFINITY_ESSENCE))
            .define('I', Tags.Items.EGGS)
            .define('J', Items.GOLDEN_APPLE)
            .unlockedBy(getHasName(AMItems.ARCANE_ASH.get()), has(AMTags.Items.DUSTS_ARCANE_ASH))
            .save(output, id("affinity_essence_life"));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, affinityEssence(affinities, AMMagic.ARCANE).withCount(2))
            .pattern("AAA")
            .pattern("AEA")
            .pattern("AAA")
            .define('A', AMTags.Items.DUSTS_ARCANE_ASH)
            .define('E', DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ARCANE), AMItems.AFFINITY_ESSENCE))
            .unlockedBy(getHasName(AMItems.ARCANE_ASH.get()), has(AMTags.Items.DUSTS_ARCANE_ASH))
            .save(output, id("affinity_essence_arcane"));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, affinityEssence(affinities, AMMagic.ENDER).withCount(2))
            .pattern("AIA")
            .pattern("JEJ")
            .pattern("AIA")
            .define('A', AMTags.Items.DUSTS_ARCANE_ASH)
            .define('E', DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ENDER), AMItems.AFFINITY_ESSENCE))
            .define('I', Tags.Items.ENDER_PEARLS)
            .define('J', Items.ENDER_EYE)
            .unlockedBy(getHasName(AMItems.ARCANE_ASH.get()), has(AMTags.Items.DUSTS_ARCANE_ASH))
            .save(output, id("affinity_essence_ender"));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, AMItems.BLANK_RUNE.get(), 2)
            .pattern(" # ")
            .pattern("###")
            .pattern("## ")
            .define('#', Tags.Items.COBBLESTONES)
            .unlockedBy(getHasName(Items.COBBLESTONE), has(Tags.Items.COBBLESTONES))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.WHITE_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_WHITE)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.ORANGE_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_ORANGE)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.MAGENTA_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_MAGENTA)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.LIGHT_BLUE_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_LIGHT_BLUE)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.YELLOW_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_YELLOW)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.LIME_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_LIME)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.PINK_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_PINK)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.GRAY_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_GRAY)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.LIGHT_GRAY_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_LIGHT_GRAY)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.CYAN_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_CYAN)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.PURPLE_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_PURPLE)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.BLUE_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_BLUE)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.BROWN_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_BROWN)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.GREEN_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_GREEN)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.RED_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_RED)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.BLACK_RUNE.get())
            .requires(AMItems.BLANK_RUNE.get())
            .requires(Tags.Items.DYES_BLACK)
            .unlockedBy(getHasName(AMItems.BLANK_RUNE), has(AMItems.BLANK_RUNE.get()))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, AMItems.RUNE_BAG.get())
            .pattern("SHS")
            .pattern("HRH")
            .pattern("HHH")
            .define('S', Tags.Items.STRINGS)
            .define('H', Items.RABBIT_HIDE)
            .define('R', AMTags.Items.RUNES)
            .unlockedBy(getHasName(Items.RABBIT_HIDE), has(Items.RABBIT_HIDE))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.RUNE_BAG.get())
            .requires(Items.BUNDLE)
            .requires(AMTags.Items.RUNES)
            .unlockedBy("has_rune", has(AMTags.Items.RUNES))
            .save(output, id("rune_bag_from_bundle"));
        oreSmelting(output, List.of(AMItems.CHIMERITE_ORE.get(), AMItems.DEEPSLATE_CHIMERITE_ORE.get()), AMItems.CHIMERITE.get(), 0.7f, 200, "chimerite");
        oreBlasting(output, List.of(AMItems.CHIMERITE_ORE.get(), AMItems.DEEPSLATE_CHIMERITE_ORE.get()), AMItems.CHIMERITE.get(), 0.7f, 100, "chimerite");
        nineBlockStorageRecipes(output, AMItems.CHIMERITE, AMTags.Items.GEMS_CHIMERITE, AMItems.CHIMERITE_BLOCK, AMTags.Items.STORAGE_BLOCKS_CHIMERITE);
        oreSmelting(output, List.of(AMItems.TOPAZ_ORE.get(), AMItems.DEEPSLATE_TOPAZ_ORE.get()), AMItems.TOPAZ.get(), 0.7f, 200, "topaz");
        oreBlasting(output, List.of(AMItems.TOPAZ_ORE.get(), AMItems.DEEPSLATE_TOPAZ_ORE.get()), AMItems.TOPAZ.get(), 0.7f, 100, "topaz");
        nineBlockStorageRecipes(output, AMItems.TOPAZ, AMTags.Items.GEMS_TOPAZ, AMItems.TOPAZ_BLOCK, AMTags.Items.STORAGE_BLOCKS_TOPAZ);
        oreSmelting(output, List.of(AMItems.VINTEUM_ORE.get(), AMItems.DEEPSLATE_VINTEUM_ORE.get()), AMItems.VINTEUM_DUST.get(), 0.7f, 200, "vinteum_dust");
        oreBlasting(output, List.of(AMItems.VINTEUM_ORE.get(), AMItems.DEEPSLATE_VINTEUM_ORE.get()), AMItems.VINTEUM_DUST.get(), 0.7f, 100, "vinteum_dust");
        nineBlockStorageRecipes(output, AMItems.VINTEUM_DUST, AMTags.Items.DUSTS_VINTEUM, AMItems.VINTEUM_BLOCK, AMTags.Items.STORAGE_BLOCKS_VINTEUM);
        oreSmelting(output, List.of(AMItems.MOONSTONE_ORE.get(), AMItems.DEEPSLATE_MOONSTONE_ORE.get()), AMItems.MOONSTONE.get(), 0.7f, 200, "moonstone");
        oreBlasting(output, List.of(AMItems.MOONSTONE_ORE.get(), AMItems.DEEPSLATE_MOONSTONE_ORE.get()), AMItems.MOONSTONE.get(), 0.7f, 100, "moonstone");
        nineBlockStorageRecipes(output, AMItems.MOONSTONE, AMTags.Items.GEMS_MOONSTONE, AMItems.MOONSTONE_BLOCK, AMTags.Items.STORAGE_BLOCKS_MOONSTONE);
        nineBlockStorageRecipes(output, AMItems.SUNSTONE, AMTags.Items.GEMS_SUNSTONE, AMItems.SUNSTONE_BLOCK, AMTags.Items.STORAGE_BLOCKS_SUNSTONE);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.ARCANE_COMPOUND.get())
            .requires(Tags.Items.DUSTS_GLOWSTONE)
            .requires(Tags.Items.DUSTS_GLOWSTONE)
            .requires(Tags.Items.DUSTS_REDSTONE)
            .requires(Tags.Items.DUSTS_REDSTONE)
            .requires(Tags.Items.NETHERRACKS)
            .requires(Tags.Items.NETHERRACKS)
            .requires(Tags.Items.STONES)
            .requires(Tags.Items.STONES)
            .unlockedBy(getHasName(Items.GLOWSTONE_DUST), has(Tags.Items.DUSTS_GLOWSTONE))
            .save(output);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_ARCANE_COMPOUND)), RecipeCategory.MISC, CookingBookCategory.MISC, AMItems.ARCANE_ASH.get(), 0.2f, 200)
            .unlockedBy(getHasName(AMItems.ARCANE_COMPOUND), has(AMTags.Items.DUSTS_ARCANE_COMPOUND))
            .save(output);
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, AMItems.PURIFIED_VINTEUM_DUST.get())
            .requires(AMTags.Items.DUSTS_ARCANE_ASH)
            .requires(AMItems.CERUBLOSSOM.get())
            .requires(AMItems.DESERT_NOVA.get())
            .requires(AMTags.Items.DUSTS_VINTEUM)
            .unlockedBy(getHasName(AMItems.VINTEUM_DUST), has(AMTags.Items.DUSTS_VINTEUM))
            .save(output);
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, AMItems.CRYSTAL_PHYLACTERY.get())
            .pattern(" M ")
            .pattern("GAG")
            .pattern(" W ")
            .define('M', AMTags.Items.GEMS_MOONSTONE)
            .define('G', Tags.Items.GLASS_BLOCKS)
            .define('A', AMTags.Items.DUSTS_ARCANE_ASH)
            .define('W', AMItems.MAGIC_WALL)
            .unlockedBy(getHasName(AMItems.MOONSTONE), has(AMTags.Items.GEMS_MOONSTONE))
            .save(output);
        generateRecipes(AMBlocks.WITCHWOOD_BLOCK_FAMILY.get(), FeatureFlagSet.of(FeatureFlags.VANILLA));
        hangingSign(AMItems.WITCHWOOD_HANGING_SIGN.get(), AMItems.STRIPPED_WITCHWOOD_LOG.get());
        planksFromLogs(AMItems.WITCHWOOD_PLANKS.get(), AMTags.Items.WITCHWOOD_LOGS, 4);
        woodFromLogs(AMBlocks.WITCHWOOD_WOOD.get(), AMBlocks.WITCHWOOD_LOG.get());
        woodFromLogs(AMBlocks.STRIPPED_WITCHWOOD_WOOD.get(), AMBlocks.STRIPPED_WITCHWOOD_LOG.get());
        woodenBoat(AMItems.WITCHWOOD_BOAT, AMItems.WITCHWOOD_PLANKS);
        chestBoat(AMItems.WITCHWOOD_CHEST_BOAT, AMItems.WITCHWOOD_BOAT);
        oneToOneConversion(output, Items.PINK_DYE, AMItems.AUM.get(), "pink_dye");
        oneToOneConversion(output, Items.BLUE_DYE, AMItems.CERUBLOSSOM.get(), "blue_dye");
        oneToOneConversion(output, Items.RED_DYE, AMItems.DESERT_NOVA.get(), "red_dye");
        oneToOneConversion(output, Items.BROWN_DYE, AMItems.TARMA_ROOT.get(), "brown_dye");
        oneToOneConversion(output, Items.MAGENTA_DYE, AMItems.WAKEBLOOM.get(), "magenta_dye");
        drought(output, "dirt", new TagMatchTest(BlockTags.DIRT), Blocks.SAND.defaultBlockState());
        drought(output, "small_flowers", new TagMatchTest(BlockTags.SMALL_FLOWERS), Blocks.DEAD_BUSH.defaultBlockState());
        drought(output, "clay", new BlockMatchTest(Blocks.CLAY), Blocks.SAND.defaultBlockState());
        drought(output, "gravel", new BlockMatchTest(Blocks.GRAVEL), Blocks.SAND.defaultBlockState());
        drought(output, "stone", new BlockMatchTest(Blocks.STONE), Blocks.COBBLESTONE.defaultBlockState());
        drought(output, "infested_stone", new BlockMatchTest(Blocks.INFESTED_STONE), Blocks.INFESTED_COBBLESTONE.defaultBlockState());
        drought(output, "stone_bricks", new BlockMatchTest(Blocks.STONE_BRICKS), Blocks.CRACKED_STONE_BRICKS.defaultBlockState());
        drought(output, "infested_stone_bricks", new BlockMatchTest(Blocks.INFESTED_STONE_BRICKS), Blocks.INFESTED_CRACKED_STONE_BRICKS.defaultBlockState());
        drought(output, "deepslate_bricks", new BlockMatchTest(Blocks.DEEPSLATE_BRICKS), Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState());
        drought(output, "deepslate_tiles", new BlockMatchTest(Blocks.DEEPSLATE_TILES), Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState());
        drought(output, "nether_bricks", new BlockMatchTest(Blocks.NETHER_BRICKS), Blocks.CRACKED_NETHER_BRICKS.defaultBlockState());
        drought(output, "polished_blackstone_bricks", new BlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS), Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.defaultBlockState());
        drought(output, "quartz_block", new BlockMatchTest(Blocks.QUARTZ_BLOCK), Blocks.SMOOTH_QUARTZ.defaultBlockState());
        drought(output, "sandstone", new BlockMatchTest(Blocks.SANDSTONE), Blocks.SMOOTH_SANDSTONE.defaultBlockState());
        drought(output, "red_sandstone", new BlockMatchTest(Blocks.RED_SANDSTONE), Blocks.SMOOTH_RED_SANDSTONE.defaultBlockState());
    }

    private ResourceKey<Recipe<?>> id(String name) {
        return ResourceKey.create(Registries.RECIPE, ArsMagicaApi.id(name));
    }

    /// Adds smelting recipes for the given [Ingredient]s.
    ///
    /// @param output      The [RecipeOutput] to use.
    /// @param ingredients A list of [Ingredient]s.
    /// @param result      The result item to use.
    /// @param experience  The experience to award for this recipe.
    /// @param cookingTime The time this recipe takes.
    /// @param group       The crafting book group to use.
    @SuppressWarnings("SameParameterValue")
    private void oreSmelting(RecipeOutput output, List<ItemLike> ingredients, ItemLike result, float experience, int cookingTime, String group) {
        oreCooking(output, SmeltingRecipe::new, ingredients, result, experience, cookingTime, group, "_from_smelting");
    }

    /// Adds blasting recipes for the given [Ingredient]s.
    ///
    /// @param output      The [RecipeOutput] to use.
    /// @param ingredients A list of [Ingredient]s.
    /// @param result      The result item to use.
    /// @param experience  The experience to award for this recipe.
    /// @param cookingTime The time this recipe takes.
    /// @param group       The crafting book group to use.
    @SuppressWarnings("SameParameterValue")
    private void oreBlasting(RecipeOutput output, List<ItemLike> ingredients, ItemLike result, float experience, int cookingTime, String group) {
        oreCooking(output, BlastingRecipe::new, ingredients, result, experience, cookingTime, group, "_from_blasting");
    }

    /// Adds generic cooking recipes for the given [Ingredient]s.
    ///
    /// @param output        The [RecipeOutput] to use.
    /// @param recipeFactory The [AbstractCookingRecipe.Factory] to use.
    /// @param ingredients   A list of [Ingredient]s.
    /// @param result        The result item to use.
    /// @param experience    The experience to award for this recipe.
    /// @param cookingTime   The time this recipe takes.
    /// @param group         The crafting book group to use.
    /// @param suffix        The suffix to append to the recipe name.
    private <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput output, AbstractCookingRecipe.Factory<T> recipeFactory, List<ItemLike> ingredients, ItemLike result, float experience, int cookingTime, String group, String suffix) {
        for (ItemLike item : ingredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(item), RecipeCategory.MISC, CookingBookCategory.MISC, result, experience, cookingTime, recipeFactory)
                .group(group)
                .unlockedBy(getHasName(item), has(item))
                .save(output, id(getItemName(result) + suffix + "_" + getItemName(item)));
        }
    }

    /// Creates a block -> item and an item -> block recipe.
    ///
    /// @param output      The [RecipeOutput] to use.
    /// @param unpacked    The item to use.
    /// @param unpackedTag The item's associated tag to use.
    /// @param packed      The block to use.
    /// @param packedTag   The block's associated tag to use.
    private void nineBlockStorageRecipes(RecipeOutput output, ItemLike unpacked, TagKey<Item> unpackedTag, ItemLike packed, TagKey<Item> packedTag) {
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, unpacked, 9)
            .requires(packedTag)
            .unlockedBy(getHasName(packed), has(packed))
            .save(output, id(getSimpleRecipeName(unpacked)));
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, packed)
            .define('#', unpackedTag)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .unlockedBy(getHasName(unpacked), has(unpacked))
            .save(output, id(getSimpleRecipeName(packed)));
    }

    /// Creates an 1 item -> 1 item recipe.
    ///
    /// @param output     The [RecipeOutput] to use.
    /// @param result     The result item to use.
    /// @param ingredient The [Ingredient] to use.
    /// @param group      The crafting book group to use.
    private void oneToOneConversion(RecipeOutput output, ItemLike result, ItemLike ingredient, @Nullable String group) {
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, result, 1)
            .requires(ingredient)
            .group(group)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(output, id(getConversionRecipeName(result, ingredient)));
    }

    private void drought(RecipeOutput output, String name, RuleTest ruleTest, BlockState result) {
        new SpellTransformationBuilder(ruleTest, AMSpells.DROUGHT, result).save(output, ResourceKey.create(Registries.RECIPE, ArsMagicaApi.id("drought/" + name)));
    }

    private static ItemStackTemplate affinityEssence(HolderLookup.RegistryLookup<Affinity> lookup, ResourceKey<Affinity> affinity) {
        return AMUtil.template(AMItems.AFFINITY_ESSENCE, AMDataComponents.AFFINITY.get(), lookup.getOrThrow(affinity));
    }

    public static final class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new AMRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "Recipes";
        }
    }
}
