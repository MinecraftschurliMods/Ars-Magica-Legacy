package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAffinities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import net.minecraft.advancements.critereon.FilledBucketTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.NBTIngredient;

import java.util.List;
import java.util.Optional;

class AMRecipeProvider extends RecipeProvider {
    public AMRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        var helper = ArsMagicaAPI.get().getAffinityHelper();
        generateRecipes(consumer, AMBlockFamilies.WITCHWOOD_PLANKS.get(), FeatureFlagSet.of(FeatureFlags.VANILLA));
        hangingSign(consumer, AMItems.WITCHWOOD_HANGING_SIGN.get(), AMBlocks.STRIPPED_WITCHWOOD_LOG.get());
        planksFromLogs(consumer, AMBlocks.WITCHWOOD_PLANKS.get(), AMTags.Items.WITCHWOOD_LOGS, 4);
        woodFromLogs(consumer, AMBlocks.WITCHWOOD.get(), AMBlocks.WITCHWOOD_LOG.get());
        woodFromLogs(consumer, AMBlocks.STRIPPED_WITCHWOOD.get(), AMBlocks.STRIPPED_WITCHWOOD_LOG.get());
        nineBlockStorageRecipes(consumer, AMItems.VINTEUM_DUST, AMTags.Items.DUSTS_VINTEUM, AMBlocks.VINTEUM_BLOCK, AMTags.Items.STORAGE_BLOCKS_VINTEUM);
        nineBlockStorageRecipes(consumer, AMItems.CHIMERITE, AMTags.Items.GEMS_CHIMERITE, AMBlocks.CHIMERITE_BLOCK, AMTags.Items.STORAGE_BLOCKS_CHIMERITE);
        nineBlockStorageRecipes(consumer, AMItems.TOPAZ, AMTags.Items.GEMS_TOPAZ, AMBlocks.TOPAZ_BLOCK, AMTags.Items.STORAGE_BLOCKS_TOPAZ);
        nineBlockStorageRecipes(consumer, AMItems.MOONSTONE, AMTags.Items.GEMS_MOONSTONE, AMBlocks.MOONSTONE_BLOCK, AMTags.Items.STORAGE_BLOCKS_MOONSTONE);
        nineBlockStorageRecipes(consumer, AMItems.SUNSTONE, AMTags.Items.GEMS_SUNSTONE, AMBlocks.SUNSTONE_BLOCK, AMTags.Items.STORAGE_BLOCKS_SUNSTONE);
        oreSmelting(consumer, List.of(AMItems.VINTEUM_ORE.get(), AMItems.DEEPSLATE_VINTEUM_ORE.get()), RecipeCategory.MISC, AMItems.VINTEUM_DUST.get(), 0.7F, 200, "vinteum_dust");
        oreSmelting(consumer, List.of(AMItems.CHIMERITE_ORE.get(), AMItems.DEEPSLATE_CHIMERITE_ORE.get()), RecipeCategory.MISC, AMItems.CHIMERITE.get(), 0.7F, 200, "chimerite_dust");
        oreSmelting(consumer, List.of(AMItems.TOPAZ_ORE.get(), AMItems.DEEPSLATE_TOPAZ_ORE.get()), RecipeCategory.MISC, AMItems.TOPAZ.get(), 0.7F, 200, "topaz_dust");
        oreSmelting(consumer, List.of(AMItems.MOONSTONE_ORE.get(), AMItems.DEEPSLATE_MOONSTONE_ORE.get()), RecipeCategory.MISC, AMItems.MOONSTONE.get(), 0.7F, 200, "moonstone_dust");
        oreBlasting(consumer, List.of(AMItems.VINTEUM_ORE.get(), AMItems.DEEPSLATE_VINTEUM_ORE.get()), RecipeCategory.MISC, AMItems.VINTEUM_DUST.get(), 0.7F, 100, "vinteum_dust");
        oreBlasting(consumer, List.of(AMItems.CHIMERITE_ORE.get(), AMItems.DEEPSLATE_CHIMERITE_ORE.get()), RecipeCategory.MISC, AMItems.CHIMERITE.get(), 0.7F, 100, "chimerite_dust");
        oreBlasting(consumer, List.of(AMItems.TOPAZ_ORE.get(), AMItems.DEEPSLATE_TOPAZ_ORE.get()), RecipeCategory.MISC, AMItems.TOPAZ.get(), 0.7F, 100, "topaz_dust");
        oreBlasting(consumer, List.of(AMItems.MOONSTONE_ORE.get(), AMItems.DEEPSLATE_MOONSTONE_ORE.get()), RecipeCategory.MISC, AMItems.MOONSTONE.get(), 0.7F, 100, "moonstone_dust");
        oneToOneConversionRecipe(consumer, Items.PINK_DYE, AMItems.AUM.get(), "pink_dye");
        oneToOneConversionRecipe(consumer, Items.BLUE_DYE, AMItems.CERUBLOSSOM.get(), "blue_dye");
        oneToOneConversionRecipe(consumer, Items.RED_DYE, AMItems.DESERT_NOVA.get(), "red_dye");
        oneToOneConversionRecipe(consumer, Items.BROWN_DYE, AMItems.TARMA_ROOT.get(), "brown_dye");
        oneToOneConversionRecipe(consumer, Items.MAGENTA_DYE, AMItems.WAKEBLOOM.get(), "magenta_dye");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ArsMagicaAPI.get().getBookStack())
                              .requires(Items.BOOK)
                              .requires(AMItems.LIQUID_ESSENCE_BUCKET.get())
                              .unlockedBy("has_liquid_essence_bucket", has(AMItems.LIQUID_ESSENCE_BUCKET.get()))
                              .unlockedBy("in_liquid_essence", insideOf(AMBlocks.LIQUID_ESSENCE.get()))
                              .unlockedBy("filled_liquid_essence_bucket", FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(AMItems.LIQUID_ESSENCE_BUCKET.get())))
                              .save(consumer, ArsMagicaAPI.MOD_ID + ":arcane_compendium");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AMItems.OCCULUS.get())
                           .pattern("SGS")
                           .pattern(" S ")
                           .pattern("CTC")
                           .define('S', Items.STONE_BRICKS)
                           .define('G', Tags.Items.GLASS)
                           .define('C', ItemTags.COALS)
                           .define('T', AMTags.Items.GEMS_TOPAZ)
                           .unlockedBy("has_topaz", has(AMTags.Items.GEMS_TOPAZ))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AMItems.INSCRIPTION_TABLE.get())
                           .pattern("TPF")
                           .pattern("SSS")
                           .pattern("W W")
                           .define('T', Items.TORCH)
                           .define('P', AMItems.SPELL_PARCHMENT.get())
                           .define('F', Tags.Items.FEATHERS)
                           .define('S', ItemTags.WOODEN_SLABS)
                           .define('W', ItemTags.PLANKS)
                           .unlockedBy("has_spell_parchment", has(AMItems.SPELL_PARCHMENT.get()))
                           .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1.get())
                              .requires(Items.BOOK)
                              .requires(Tags.Items.DYES_BLACK)
                              .requires(Tags.Items.FEATHERS)
                              .requires(Tags.Items.STRING)
                              .unlockedBy("has_book", has(Items.BOOK))
                              .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2.get())
                              .requires(Items.BOOK)
                              .requires(Tags.Items.DYES_BLACK)
                              .requires(ItemTags.WOOL_CARPETS)
                              .requires(AMItems.WIZARDS_CHALK.get())
                              .unlockedBy("has_book", has(Items.BOOK))
                              .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3.get())
                              .requires(Items.BOOK)
                              .requires(ItemTags.CANDLES)
                              .requires(Items.HONEYCOMB)
                              .requires(Items.GLASS_BOTTLE)
                              .unlockedBy("has_book", has(Items.BOOK))
                              .save(consumer);
        CompoundTag oldTag = new CompoundTag();
        CompoundTag newTag = new CompoundTag();
        CompoundTag oldBlockStateTag = new CompoundTag();
        CompoundTag newBlockStateTag = new CompoundTag();
        oldBlockStateTag.putString("tier", "0");
        oldTag.put("BlockStateTag", oldBlockStateTag);
        newBlockStateTag.putString("tier", "1");
        newTag.put("BlockStateTag", newBlockStateTag);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, new ItemStack(AMItems.INSCRIPTION_TABLE.get(), 1, Optional.of(newTag.copy())))
                              .requires(AMItems.INSCRIPTION_TABLE.get())
                              .requires(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1.get())
                              .unlockedBy("has_inscription_table", has(AMItems.INSCRIPTION_TABLE.get()))
                              .save(consumer, new ResourceLocation(ArsMagicaAPI.MOD_ID, "inscription_table_tier_1"));
        oldBlockStateTag.putString("tier", "1");
        oldTag.put("BlockStateTag", oldBlockStateTag);
        newBlockStateTag.putString("tier", "2");
        newTag.put("BlockStateTag", newBlockStateTag);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, new ItemStack(AMItems.INSCRIPTION_TABLE.get(), 1, Optional.of(newTag.copy())))
                              .requires(NBTIngredient.of(false, oldTag, AMItems.INSCRIPTION_TABLE.get()))
                              .requires(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2.get())
                              .unlockedBy("has_inscription_table", has(AMItems.INSCRIPTION_TABLE.get()))
                              .save(consumer, new ResourceLocation(ArsMagicaAPI.MOD_ID, "inscription_table_tier_2"));
        oldBlockStateTag.putString("tier", "2");
        oldTag.put("BlockStateTag", oldBlockStateTag);
        newBlockStateTag.putString("tier", "3");
        newTag.put("BlockStateTag", newBlockStateTag);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, new ItemStack(AMItems.INSCRIPTION_TABLE.get(), 1, Optional.of(newTag.copy())))
                              .requires(NBTIngredient.of(false, oldTag, AMItems.INSCRIPTION_TABLE.get()))
                              .requires(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3.get())
                              .unlockedBy("has_inscription_table", has(AMItems.INSCRIPTION_TABLE.get()))
                              .save(consumer, new ResourceLocation(ArsMagicaAPI.MOD_ID, "inscription_table_tier_3"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AMItems.ALTAR_CORE.get())
                           .pattern("V")
                           .pattern("S")
                           .define('V', AMTags.Items.DUSTS_VINTEUM)
                           .define('S', Tags.Items.STONE)
                           .unlockedBy("has_vinteum_dust", has(AMTags.Items.DUSTS_VINTEUM))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AMItems.MAGIC_WALL.get(), 16)
                           .pattern("VSV")
                           .define('V', AMTags.Items.DUSTS_VINTEUM)
                           .define('S', Tags.Items.STONE)
                           .unlockedBy("has_vinteum_dust", has(AMTags.Items.DUSTS_VINTEUM))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AMItems.OBELISK.get())
                           .pattern("VSV")
                           .pattern("SBS")
                           .pattern("VSV")
                           .define('V', AMTags.Items.DUSTS_VINTEUM)
                           .define('S', Tags.Items.STONE)
                           .define('B', ItemTags.STONE_BRICKS)
                           .unlockedBy("has_vinteum_dust", has(AMTags.Items.DUSTS_VINTEUM))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, AMItems.MAGITECH_GOGGLES.get())
                           .pattern("LLL")
                           .pattern("CGC")
                           .pattern("TLT")
                           .define('L', Items.LEATHER)
                           .define('C', AMTags.Items.GEMS_CHIMERITE)
                           .define('T', AMTags.Items.GEMS_TOPAZ)
                           .define('G', Tags.Items.NUGGETS_GOLD)
                           .unlockedBy("has_topaz", has(AMTags.Items.GEMS_TOPAZ))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, AMItems.CRYSTAL_WRENCH.get())
                           .pattern("GTG")
                           .pattern(" G ")
                           .pattern(" G ")
                           .define('G', Tags.Items.INGOTS_GOLD)
                           .define('T', AMTags.Items.GEMS_TOPAZ)
                           .unlockedBy("has_topaz", has(AMTags.Items.GEMS_TOPAZ))
                           .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, AMItems.WIZARDS_CHALK.get())
                              .requires(AMTags.Items.DUSTS_VINTEUM)
                              .requires(Items.BONE_MEAL)
                              .requires(Items.CLAY_BALL)
                              .requires(Items.FLINT)
                              .requires(Items.PAPER)
                              .unlockedBy("has_vinteum_dust", has(AMTags.Items.DUSTS_VINTEUM))
                              .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AMItems.BLANK_RUNE.get(), 2)
                           .pattern(" # ")
                           .pattern("###")
                           .pattern("## ")
                           .define('#', Tags.Items.COBBLESTONE)
                           .unlockedBy("has_cobblestone", has(Tags.Items.COBBLESTONE))
                           .save(consumer);
        for (DyeColor color : DyeColor.values()) {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AMItems.COLORED_RUNE.get(color))
                                  .requires(AMItems.BLANK_RUNE.get())
                                  .requires(color.getTag())
                                  .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                                  .save(consumer);
        }
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, AMItems.RUNE_BAG.get())
                           .pattern("SHS")
                           .pattern("HRH")
                           .pattern("HHH")
                           .define('S', Tags.Items.STRING)
                           .define('H', Items.RABBIT_HIDE)
                           .define('R', AMTags.Items.RUNES)
                           .unlockedBy("has_rabbit_hide", has(Items.RABBIT_HIDE))
                           .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AMItems.RUNE_BAG.get())
                              .requires(Items.BUNDLE)
                              .requires(AMTags.Items.RUNES)
                              .unlockedBy("has_rune", has(AMTags.Items.RUNES))
                              .save(consumer, "rune_bag_from_bundle");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AMItems.ARCANE_COMPOUND.get())
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
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AMItems.ARCANE_COMPOUND.get()), RecipeCategory.MISC, AMItems.ARCANE_ASH.get(), 0.2F, 200)
                                  .unlockedBy("has_arcane_compound", has(AMItems.ARCANE_COMPOUND.get()))
                                  .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AMItems.PURIFIED_VINTEUM_DUST.get())
                              .requires(AMItems.ARCANE_ASH.get())
                              .requires(AMItems.CERUBLOSSOM.get())
                              .requires(AMItems.DESERT_NOVA.get())
                              .requires(AMTags.Items.DUSTS_VINTEUM)
                              .unlockedBy("has_vinteum_dust", has(AMTags.Items.DUSTS_VINTEUM))
                              .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AMItems.VINTEUM_TORCH.get())
                           .pattern("V")
                           .pattern("S")
                           .define('V', AMTags.Items.DUSTS_VINTEUM)
                           .define('S', Tags.Items.RODS_WOODEN)
                           .unlockedBy("has_vinteum_dust", has(AMTags.Items.DUSTS_VINTEUM))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, AMItems.IRON_INLAY.get())
                           .pattern("###")
                           .pattern("#V#")
                           .pattern("###")
                           .define('#', Tags.Items.INGOTS_IRON)
                           .define('V', AMItems.PURIFIED_VINTEUM_DUST.get())
                           .unlockedBy("has_purified_vinteum_dust", has(AMItems.PURIFIED_VINTEUM_DUST.get()))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, AMItems.REDSTONE_INLAY.get())
                           .pattern("###")
                           .pattern("#V#")
                           .pattern("###")
                           .define('#', Tags.Items.DUSTS_REDSTONE)
                           .define('V', AMItems.PURIFIED_VINTEUM_DUST.get())
                           .unlockedBy("has_purified_vinteum_dust", has(AMItems.PURIFIED_VINTEUM_DUST.get()))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, AMItems.GOLD_INLAY.get())
                           .pattern("###")
                           .pattern("#V#")
                           .pattern("###")
                           .define('#', Tags.Items.INGOTS_GOLD)
                           .define('V', AMItems.PURIFIED_VINTEUM_DUST.get())
                           .unlockedBy("has_purified_vinteum_dust", has(AMItems.PURIFIED_VINTEUM_DUST.get()))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, helper.getEssenceForAffinity(AMAffinities.WATER).copyWithCount(2))
                           .pattern("AIA")
                           .pattern("JEJ")
                           .pattern("AIA")
                           .define('A', AMItems.ARCANE_ASH.get())
                           .define('E', NBTIngredient.of(false, helper.getEssenceForAffinity(AMAffinities.WATER)))
                           .define('I', AMItems.WAKEBLOOM.get())
                           .define('J', Items.WATER_BUCKET)
                           .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                           .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_water");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, helper.getEssenceForAffinity(AMAffinities.FIRE).copyWithCount(2))
                           .pattern("AIA")
                           .pattern("JEJ")
                           .pattern("AIA")
                           .define('A', AMItems.ARCANE_ASH.get())
                           .define('E', NBTIngredient.of(false, helper.getEssenceForAffinity(AMAffinities.FIRE)))
                           .define('I', ItemTags.COALS)
                           .define('J', Items.BLAZE_POWDER)
                           .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                           .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_fire");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, helper.getEssenceForAffinity(AMAffinities.EARTH).copyWithCount(2))
                           .pattern("AIA")
                           .pattern("JEJ")
                           .pattern("AIA")
                           .define('A', AMItems.ARCANE_ASH.get())
                           .define('E', NBTIngredient.of(false, helper.getEssenceForAffinity(AMAffinities.EARTH)))
                           .define('I', ItemTags.DIRT)
                           .define('J', Tags.Items.STONE)
                           .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                           .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_earth");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, helper.getEssenceForAffinity(AMAffinities.AIR).copyWithCount(2))
                           .pattern("AIA")
                           .pattern("JEJ")
                           .pattern("AIA")
                           .define('A', AMItems.ARCANE_ASH.get())
                           .define('E', NBTIngredient.of(false, helper.getEssenceForAffinity(AMAffinities.AIR)))
                           .define('I', Items.FEATHER)
                           .define('J', AMItems.TARMA_ROOT.get())
                           .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                           .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_air");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, helper.getEssenceForAffinity(AMAffinities.ICE).copyWithCount(2))
                           .pattern("AIA")
                           .pattern("JEJ")
                           .pattern("AIA")
                           .define('A', AMItems.ARCANE_ASH.get())
                           .define('E', NBTIngredient.of(false, helper.getEssenceForAffinity(AMAffinities.ICE)))
                           .define('I', Items.SNOW_BLOCK)
                           .define('J', Items.ICE)
                           .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                           .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_ice");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, helper.getEssenceForAffinity(AMAffinities.LIGHTNING).copyWithCount(2))
                           .pattern("AIA")
                           .pattern("JEJ")
                           .pattern("AIA")
                           .define('A', AMItems.ARCANE_ASH.get())
                           .define('E', NBTIngredient.of(false, helper.getEssenceForAffinity(AMAffinities.LIGHTNING)))
                           .define('I', Tags.Items.DUSTS_REDSTONE)
                           .define('J', Tags.Items.DUSTS_GLOWSTONE)
                           .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                           .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_lightning");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, helper.getEssenceForAffinity(AMAffinities.NATURE).copyWithCount(2))
                           .pattern("AIA")
                           .pattern("JEK")
                           .pattern("ALA")
                           .define('A', AMItems.ARCANE_ASH.get())
                           .define('E', NBTIngredient.of(false, helper.getEssenceForAffinity(AMAffinities.NATURE)))
                           .define('I', ItemTags.LEAVES)
                           .define('J', Items.LILY_PAD)
                           .define('K', Items.CACTUS)
                           .define('L', Items.VINE)
                           .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                           .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_nature");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, helper.getEssenceForAffinity(AMAffinities.LIFE).copyWithCount(2))
                           .pattern("AIA")
                           .pattern("JEJ")
                           .pattern("AIA")
                           .define('A', AMItems.ARCANE_ASH.get())
                           .define('E', NBTIngredient.of(false, helper.getEssenceForAffinity(AMAffinities.LIFE)))
                           .define('I', Tags.Items.EGGS)
                           .define('J', Items.GOLDEN_APPLE)
                           .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                           .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_life");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, helper.getEssenceForAffinity(AMAffinities.ARCANE).copyWithCount(2))
                           .pattern("AAA")
                           .pattern("AEA")
                           .pattern("AAA")
                           .define('A', AMItems.ARCANE_ASH.get())
                           .define('E', NBTIngredient.of(false, helper.getEssenceForAffinity(AMAffinities.ARCANE)))
                           .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                           .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_arcane");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, helper.getEssenceForAffinity(AMAffinities.ENDER).copyWithCount(2))
                           .pattern("AIA")
                           .pattern("JEJ")
                           .pattern("AIA")
                           .define('A', AMItems.ARCANE_ASH.get())
                           .define('E', NBTIngredient.of(false, helper.getEssenceForAffinity(AMAffinities.ENDER)))
                           .define('I', Tags.Items.ENDER_PEARLS)
                           .define('J', Items.ENDER_EYE)
                           .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                           .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_ender");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AMItems.SPELL_PARCHMENT.get())
                           .pattern("S")
                           .pattern("P")
                           .pattern("S")
                           .define('S', Tags.Items.RODS_WOODEN)
                           .define('P', Items.PAPER)
                           .unlockedBy("has_paper", has(Items.PAPER))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItems.SPELL_BOOK.get())
                           .pattern("SLL")
                           .pattern("SPP")
                           .pattern("SLL")
                           .define('S', Tags.Items.STRING)
                           .define('L', Tags.Items.LEATHER)
                           .define('P', Items.PAPER)
                           .unlockedBy("has_paper", has(Items.PAPER))
                           .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, AMItems.MANA_CAKE.get(), 3)
                              .requires(Tags.Items.CROPS_WHEAT)
                              .requires(Items.SUGAR)
                              .requires(AMItems.AUM.get())
                              .requires(AMItems.CERUBLOSSOM.get())
                              .unlockedBy("has_cerublossom", has(AMItems.CERUBLOSSOM.get()))
                              .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, AMItems.MANA_MARTINI.get())
                              .requires(Tags.Items.RODS_WOODEN)
                              .requires(Tags.Items.CROPS_POTATO)
                              .requires(Items.ICE)
                              .requires(Items.SUGAR)
                              .requires(NBTIngredient.of(false, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)))
                              .unlockedBy("has_ice", has(Items.ICE))
                              .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItems.MAGE_HELMET.get())
                           .pattern("LWL")
                           .pattern("WRW")
                           .define('W', ItemTags.WOOL)
                           .define('L', Items.LEATHER)
                           .define('R', AMItems.COLORED_RUNE.get(DyeColor.MAGENTA))
                           .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.MAGENTA)))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItems.MAGE_CHESTPLATE.get())
                           .pattern("L L")
                           .pattern("WRW")
                           .pattern("WWW")
                           .define('W', ItemTags.WOOL)
                           .define('L', Items.LEATHER)
                           .define('R', AMItems.COLORED_RUNE.get(DyeColor.WHITE))
                           .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.WHITE)))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItems.MAGE_LEGGINGS.get())
                           .pattern("LRL")
                           .pattern("W W")
                           .pattern("W W")
                           .define('W', ItemTags.WOOL)
                           .define('L', Items.LEATHER)
                           .define('R', AMItems.COLORED_RUNE.get(DyeColor.YELLOW))
                           .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItems.MAGE_BOOTS.get())
                           .pattern("W W")
                           .pattern("LRL")
                           .define('W', ItemTags.WOOL)
                           .define('L', Items.LEATHER)
                           .define('R', AMItems.COLORED_RUNE.get(DyeColor.BLACK))
                           .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.BLACK)))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItems.BATTLEMAGE_HELMET.get())
                           .pattern("GOG")
                           .pattern("ORO")
                           .pattern(" E ")
                           .define('O', Items.OBSIDIAN)
                           .define('G', Tags.Items.INGOTS_GOLD)
                           .define('E', NBTIngredient.of(false, helper.getEssenceForAffinity(AMAffinities.AIR)))
                           .define('R', AMItems.COLORED_RUNE.get(DyeColor.RED))
                           .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.RED)))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItems.BATTLEMAGE_CHESTPLATE.get())
                           .pattern("GEG")
                           .pattern("ORO")
                           .pattern("OOO")
                           .define('O', Items.OBSIDIAN)
                           .define('G', Tags.Items.INGOTS_GOLD)
                           .define('E', NBTIngredient.of(false, helper.getEssenceForAffinity(AMAffinities.EARTH)))
                           .define('R', AMItems.COLORED_RUNE.get(DyeColor.RED))
                           .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.RED)))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItems.BATTLEMAGE_LEGGINGS.get())
                           .pattern("GRG")
                           .pattern("OEO")
                           .pattern("O O")
                           .define('O', Items.OBSIDIAN)
                           .define('G', Tags.Items.INGOTS_GOLD)
                           .define('E', NBTIngredient.of(false, helper.getEssenceForAffinity(AMAffinities.FIRE)))
                           .define('R', AMItems.COLORED_RUNE.get(DyeColor.RED))
                           .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.RED)))
                           .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItems.BATTLEMAGE_BOOTS.get())
                           .pattern("OEO")
                           .pattern("GRG")
                           .define('O', Items.OBSIDIAN)
                           .define('G', Tags.Items.INGOTS_GOLD)
                           .define('E', NBTIngredient.of(false, helper.getEssenceForAffinity(AMAffinities.WATER)))
                           .define('R', AMItems.COLORED_RUNE.get(DyeColor.RED))
                           .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.RED)))
                           .save(consumer);
    }

    protected static void nineBlockStorageRecipes(RecipeOutput pFinishedRecipeConsumer, ItemLike pUnpacked, TagKey<Item> pUnpackedTag, ItemLike pPacked, TagKey<Item> pPackedTag) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, pUnpacked, pUnpackedTag, pPacked, pPackedTag, getSimpleRecipeName(pPacked), getSimpleRecipeName(pUnpacked));
    }

    protected static void nineBlockStorageRecipes(RecipeOutput pFinishedRecipeConsumer, ItemLike pUnpacked, TagKey<Item> pUnpackedTag, ItemLike pPacked, TagKey<Item> pPackedTag, String pPackingRecipeName, String pUnpackingRecipeName) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, pUnpacked, 9)
                              .requires(pPackedTag)
                              .unlockedBy(getHasName(pPacked), has(pPacked))
                              .save(pFinishedRecipeConsumer, new ResourceLocation(ArsMagicaAPI.MOD_ID, pUnpackingRecipeName));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, pPacked)
                           .define('#', pUnpackedTag)
                           .pattern("###")
                           .pattern("###")
                           .pattern("###")
                           .unlockedBy(getHasName(pUnpacked), has(pUnpacked))
                           .save(pFinishedRecipeConsumer, new ResourceLocation(ArsMagicaAPI.MOD_ID, pPackingRecipeName));
    }
}
