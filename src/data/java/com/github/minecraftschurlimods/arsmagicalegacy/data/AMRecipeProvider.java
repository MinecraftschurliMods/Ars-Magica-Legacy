package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAffinities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.FilledBucketTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.PartialNBTIngredient;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

class AMRecipeProvider extends RecipeProvider {
    public AMRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        var helper = ArsMagicaAPI.get().getAffinityHelper();
        generateRecipes(consumer, AMBlockFamilies.WITCHWOOD_PLANKS.get());
        planksFromLogs(consumer, AMBlocks.WITCHWOOD_PLANKS.get(), AMTags.Items.WITCHWOOD_LOGS);
        woodFromLogs(consumer, AMBlocks.WITCHWOOD.get(), AMBlocks.WITCHWOOD_LOG.get());
        woodFromLogs(consumer, AMBlocks.STRIPPED_WITCHWOOD.get(), AMBlocks.STRIPPED_WITCHWOOD_LOG.get());
        nineBlockStorageRecipes(consumer, AMItems.VINTEUM_DUST.get(), AMTags.Items.DUSTS_VINTEUM, AMBlocks.VINTEUM_BLOCK.get(), AMTags.Items.STORAGE_BLOCKS_VINTEUM);
        nineBlockStorageRecipes(consumer, AMItems.CHIMERITE.get(), AMTags.Items.GEMS_CHIMERITE, AMBlocks.CHIMERITE_BLOCK.get(), AMTags.Items.STORAGE_BLOCKS_CHIMERITE);
        nineBlockStorageRecipes(consumer, AMItems.TOPAZ.get(), AMTags.Items.GEMS_TOPAZ, AMBlocks.TOPAZ_BLOCK.get(), AMTags.Items.STORAGE_BLOCKS_TOPAZ);
        nineBlockStorageRecipes(consumer, AMItems.MOONSTONE.get(), AMTags.Items.GEMS_MOONSTONE, AMBlocks.MOONSTONE_BLOCK.get(), AMTags.Items.STORAGE_BLOCKS_MOONSTONE);
        nineBlockStorageRecipes(consumer, AMItems.SUNSTONE.get(), AMTags.Items.GEMS_SUNSTONE, AMBlocks.SUNSTONE_BLOCK.get(), AMTags.Items.STORAGE_BLOCKS_SUNSTONE);
        oreSmelting(consumer, List.of(AMItems.VINTEUM_ORE.get(), AMItems.DEEPSLATE_VINTEUM_ORE.get()), AMItems.VINTEUM_DUST.get(), 0.7F, 200, "vinteum_dust");
        oreSmelting(consumer, List.of(AMItems.CHIMERITE_ORE.get(), AMItems.DEEPSLATE_CHIMERITE_ORE.get()), AMItems.CHIMERITE.get(), 0.7F, 200, "chimerite_dust");
        oreSmelting(consumer, List.of(AMItems.TOPAZ_ORE.get(), AMItems.DEEPSLATE_TOPAZ_ORE.get()), AMItems.TOPAZ.get(), 0.7F, 200, "topaz_dust");
        oreSmelting(consumer, List.of(AMItems.MOONSTONE_ORE.get(), AMItems.DEEPSLATE_MOONSTONE_ORE.get()), AMItems.MOONSTONE.get(), 0.7F, 200, "moonstone_dust");
        oreBlasting(consumer, List.of(AMItems.VINTEUM_ORE.get(), AMItems.DEEPSLATE_VINTEUM_ORE.get()), AMItems.VINTEUM_DUST.get(), 0.7F, 100, "vinteum_dust");
        oreBlasting(consumer, List.of(AMItems.CHIMERITE_ORE.get(), AMItems.DEEPSLATE_CHIMERITE_ORE.get()), AMItems.CHIMERITE.get(), 0.7F, 100, "chimerite_dust");
        oreBlasting(consumer, List.of(AMItems.TOPAZ_ORE.get(), AMItems.DEEPSLATE_TOPAZ_ORE.get()), AMItems.TOPAZ.get(), 0.7F, 100, "topaz_dust");
        oreBlasting(consumer, List.of(AMItems.MOONSTONE_ORE.get(), AMItems.DEEPSLATE_MOONSTONE_ORE.get()), AMItems.MOONSTONE.get(), 0.7F, 100, "moonstone_dust");
        ShapelessRecipeBuilder.shapeless(Items.PINK_DYE)
                .requires(AMItems.AUM.get())
                .unlockedBy("item", has(AMItems.AUM.get()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":pink_dye");
        ShapelessRecipeBuilder.shapeless(Items.BLUE_DYE)
                .requires(AMItems.CERUBLOSSOM.get())
                .unlockedBy("item", has(AMItems.CERUBLOSSOM.get()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":blue_dye");
        ShapelessRecipeBuilder.shapeless(Items.RED_DYE)
                .requires(AMItems.DESERT_NOVA.get())
                .unlockedBy("item", has(AMItems.DESERT_NOVA.get()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":red_dye");
        ShapelessRecipeBuilder.shapeless(Items.BROWN_DYE)
                .requires(AMItems.TARMA_ROOT.get())
                .unlockedBy("item", has(AMItems.TARMA_ROOT.get()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":brown_dye");
        ShapelessRecipeBuilder.shapeless(Items.MAGENTA_DYE)
                .requires(AMItems.WAKEBLOOM.get())
                .unlockedBy("item", has(AMItems.WAKEBLOOM.get()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":magenta_dye");
        ShapelessNBTRecipeBuilder.shapeless(ArsMagicaAPI.get().getBookStack().getItem(), ArsMagicaAPI.get().getBookStack().getOrCreateTag())
                .requires(Items.BOOK)
                .requires(AMItems.LIQUID_ESSENCE_BUCKET.get())
                .unlockedBy("has_liquid_essence_bucket", has(AMItems.LIQUID_ESSENCE_BUCKET.get()))
                .unlockedBy("in_liquid_essence", insideOf(AMBlocks.LIQUID_ESSENCE.get()))
                .unlockedBy("filled_liquid_essence_bucket", FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(AMItems.LIQUID_ESSENCE_BUCKET.get()).build()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":arcane_compendium");
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
        ShapedRecipeBuilder.shaped(AMItems.INSCRIPTION_TABLE.get())
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
        ShapelessRecipeBuilder.shapeless(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1.get())
                .requires(Items.BOOK)
                .requires(Tags.Items.DYES_BLACK)
                .requires(Tags.Items.FEATHERS)
                .requires(Tags.Items.STRING)
                .unlockedBy("has_book", has(Items.BOOK))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2.get())
                .requires(Items.BOOK)
                .requires(Tags.Items.DYES_BLACK)
                .requires(ItemTags.WOOL_CARPETS)
                .requires(AMItems.WIZARDS_CHALK.get())
                .unlockedBy("has_book", has(Items.BOOK))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3.get())
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
        ShapelessNBTRecipeBuilder.shapeless(AMItems.INSCRIPTION_TABLE.get(), newTag)
                .requires(AMItems.INSCRIPTION_TABLE.get())
                .requires(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1.get())
                .unlockedBy("has_inscription_table", has(AMItems.INSCRIPTION_TABLE.get()))
                .save(consumer, new ResourceLocation(ArsMagicaAPI.MOD_ID, "inscription_table_tier_1"));
        oldBlockStateTag.putString("tier", "1");
        oldTag.put("BlockStateTag", oldBlockStateTag);
        newBlockStateTag.putString("tier", "2");
        newTag.put("BlockStateTag", newBlockStateTag);
        ShapelessNBTRecipeBuilder.shapeless(AMItems.INSCRIPTION_TABLE.get(), newTag)
                .requires(PartialNBTIngredient.of(AMItems.INSCRIPTION_TABLE.get(), oldTag))
                .requires(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2.get())
                .unlockedBy("has_inscription_table", has(AMItems.INSCRIPTION_TABLE.get()))
                .save(consumer, new ResourceLocation(ArsMagicaAPI.MOD_ID, "inscription_table_tier_2"));
        oldBlockStateTag.putString("tier", "2");
        oldTag.put("BlockStateTag", oldBlockStateTag);
        newBlockStateTag.putString("tier", "3");
        newTag.put("BlockStateTag", newBlockStateTag);
        ShapelessNBTRecipeBuilder.shapeless(AMItems.INSCRIPTION_TABLE.get(), newTag)
                .requires(PartialNBTIngredient.of(AMItems.INSCRIPTION_TABLE.get(), oldTag))
                .requires(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3.get())
                .unlockedBy("has_inscription_table", has(AMItems.INSCRIPTION_TABLE.get()))
                .save(consumer, new ResourceLocation(ArsMagicaAPI.MOD_ID, "inscription_table_tier_3"));
        ShapedRecipeBuilder.shaped(AMItems.ALTAR_CORE.get())
                .pattern("V")
                .pattern("S")
                .define('V', AMTags.Items.DUSTS_VINTEUM)
                .define('S', Tags.Items.STONE)
                .unlockedBy("has_vinteum_dust", has(AMTags.Items.DUSTS_VINTEUM))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.MAGIC_WALL.get(), 16)
                .pattern("VSV")
                .define('V', AMTags.Items.DUSTS_VINTEUM)
                .define('S', Tags.Items.STONE)
                .unlockedBy("has_vinteum_dust", has(AMTags.Items.DUSTS_VINTEUM))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.OBELISK.get())
                .pattern("VSV")
                .pattern("SBS")
                .pattern("VSV")
                .define('V', AMTags.Items.DUSTS_VINTEUM)
                .define('S', Tags.Items.STONE)
                .define('B', ItemTags.STONE_BRICKS)
                .unlockedBy("has_vinteum_dust", has(AMTags.Items.DUSTS_VINTEUM))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.MAGITECH_GOGGLES.get())
                .pattern("LLL")
                .pattern("CGC")
                .pattern("TLT")
                .define('L', Items.LEATHER)
                .define('C', AMTags.Items.GEMS_CHIMERITE)
                .define('T', AMTags.Items.GEMS_TOPAZ)
                .define('G', Tags.Items.NUGGETS_GOLD)
                .unlockedBy("has_topaz", has(AMTags.Items.GEMS_TOPAZ))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.CRYSTAL_WRENCH.get())
                .pattern("GTG")
                .pattern(" G ")
                .pattern(" G ")
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('T', AMTags.Items.GEMS_TOPAZ)
                .unlockedBy("has_topaz", has(AMTags.Items.GEMS_TOPAZ))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.WIZARDS_CHALK.get())
                .requires(AMTags.Items.DUSTS_VINTEUM)
                .requires(Items.BONE_MEAL)
                .requires(Items.CLAY_BALL)
                .requires(Items.FLINT)
                .requires(Items.PAPER)
                .unlockedBy("has_vinteum_dust", has(AMTags.Items.DUSTS_VINTEUM))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.BLANK_RUNE.get(), 2)
                .pattern(" # ")
                .pattern("###")
                .pattern("## ")
                .define('#', Tags.Items.COBBLESTONE)
                .unlockedBy("has_cobblestone", has(Tags.Items.COBBLESTONE))
                .save(consumer);
        for (DyeColor color : DyeColor.values()) {
            ShapelessRecipeBuilder.shapeless(AMItems.COLORED_RUNE.get(color))
                    .requires(AMItems.BLANK_RUNE.get())
                    .requires(color.getTag())
                    .unlockedBy("has_blank_rune", has(AMItems.BLANK_RUNE.get()))
                    .save(consumer);
        }
        ShapedRecipeBuilder.shaped(AMItems.RUNE_BAG.get())
                .pattern("SHS")
                .pattern("HRH")
                .pattern("HHH")
                .define('S', Tags.Items.STRING)
                .define('H', Items.RABBIT_HIDE)
                .define('R', AMTags.Items.RUNES)
                .unlockedBy("has_rabbit_hide", has(Items.RABBIT_HIDE))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.RUNE_BAG.get())
                .requires(Items.BUNDLE)
                .requires(AMTags.Items.RUNES)
                .unlockedBy("has_rune", has(AMTags.Items.RUNES))
                .save(consumer, "rune_bag_from_bundle");
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
        ShapedRecipeBuilder.shaped(AMItems.IRON_INLAY.get())
                .pattern("###")
                .pattern("#V#")
                .pattern("###")
                .define('#', Tags.Items.INGOTS_IRON)
                .define('V', AMItems.PURIFIED_VINTEUM_DUST.get())
                .unlockedBy("has_purified_vinteum_dust", has(AMItems.PURIFIED_VINTEUM_DUST.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.REDSTONE_INLAY.get())
                .pattern("###")
                .pattern("#V#")
                .pattern("###")
                .define('#', Tags.Items.DUSTS_REDSTONE)
                .define('V', AMItems.PURIFIED_VINTEUM_DUST.get())
                .unlockedBy("has_purified_vinteum_dust", has(AMItems.PURIFIED_VINTEUM_DUST.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.GOLD_INLAY.get())
                .pattern("###")
                .pattern("#V#")
                .pattern("###")
                .define('#', Tags.Items.INGOTS_GOLD)
                .define('V', AMItems.PURIFIED_VINTEUM_DUST.get())
                .unlockedBy("has_purified_vinteum_dust", has(AMItems.PURIFIED_VINTEUM_DUST.get()))
                .save(consumer);
        ShapedNBTRecipeBuilder.shaped(AMItems.AFFINITY_ESSENCE.get(), 2, helper.getEssenceForAffinity(AMAffinities.WATER.get()).getOrCreateTag())
                .pattern("AIA")
                .pattern("JEJ")
                .pattern("AIA")
                .define('A', AMItems.ARCANE_ASH.get())
                .define('E', PartialNBTIngredient.of(AMItems.AFFINITY_ESSENCE.get(), helper.getEssenceForAffinity(AMAffinities.WATER.get()).getOrCreateTag()))
                .define('I', AMItems.WAKEBLOOM.get())
                .define('J', Items.WATER_BUCKET)
                .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_water");
        ShapedNBTRecipeBuilder.shaped(AMItems.AFFINITY_ESSENCE.get(), 2, helper.getEssenceForAffinity(AMAffinities.FIRE.get()).getOrCreateTag())
                .pattern("AIA")
                .pattern("JEJ")
                .pattern("AIA")
                .define('A', AMItems.ARCANE_ASH.get())
                .define('E', PartialNBTIngredient.of(AMItems.AFFINITY_ESSENCE.get(), helper.getEssenceForAffinity(AMAffinities.FIRE.get()).getOrCreateTag()))
                .define('I', ItemTags.COALS)
                .define('J', Items.BLAZE_POWDER)
                .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_fire");
        ShapedNBTRecipeBuilder.shaped(AMItems.AFFINITY_ESSENCE.get(), 2, helper.getEssenceForAffinity(AMAffinities.EARTH.get()).getOrCreateTag())
                .pattern("AIA")
                .pattern("JEJ")
                .pattern("AIA")
                .define('A', AMItems.ARCANE_ASH.get())
                .define('E', PartialNBTIngredient.of(AMItems.AFFINITY_ESSENCE.get(), helper.getEssenceForAffinity(AMAffinities.EARTH.get()).getOrCreateTag()))
                .define('I', ItemTags.DIRT)
                .define('J', Tags.Items.STONE)
                .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_earth");
        ShapedNBTRecipeBuilder.shaped(AMItems.AFFINITY_ESSENCE.get(), 2, helper.getEssenceForAffinity(AMAffinities.AIR.get()).getOrCreateTag())
                .pattern("AIA")
                .pattern("JEJ")
                .pattern("AIA")
                .define('A', AMItems.ARCANE_ASH.get())
                .define('E', PartialNBTIngredient.of(AMItems.AFFINITY_ESSENCE.get(), helper.getEssenceForAffinity(AMAffinities.AIR.get()).getOrCreateTag()))
                .define('I', Items.FEATHER)
                .define('J', AMItems.TARMA_ROOT.get())
                .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_air");
        ShapedNBTRecipeBuilder.shaped(AMItems.AFFINITY_ESSENCE.get(), 2, helper.getEssenceForAffinity(AMAffinities.ICE.get()).getOrCreateTag())
                .pattern("AIA")
                .pattern("JEJ")
                .pattern("AIA")
                .define('A', AMItems.ARCANE_ASH.get())
                .define('E', PartialNBTIngredient.of(AMItems.AFFINITY_ESSENCE.get(), helper.getEssenceForAffinity(AMAffinities.ICE.get()).getOrCreateTag()))
                .define('I', Items.SNOW_BLOCK)
                .define('J', Items.ICE)
                .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_ice");
        ShapedNBTRecipeBuilder.shaped(AMItems.AFFINITY_ESSENCE.get(), 2, helper.getEssenceForAffinity(AMAffinities.LIGHTNING.get()).getOrCreateTag())
                .pattern("AIA")
                .pattern("JEJ")
                .pattern("AIA")
                .define('A', AMItems.ARCANE_ASH.get())
                .define('E', PartialNBTIngredient.of(AMItems.AFFINITY_ESSENCE.get(), helper.getEssenceForAffinity(AMAffinities.LIGHTNING.get()).getOrCreateTag()))
                .define('I', Tags.Items.DUSTS_REDSTONE)
                .define('J', Tags.Items.DUSTS_GLOWSTONE)
                .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_lightning");
        ShapedNBTRecipeBuilder.shaped(AMItems.AFFINITY_ESSENCE.get(), 2, helper.getEssenceForAffinity(AMAffinities.NATURE.get()).getOrCreateTag())
                .pattern("AIA")
                .pattern("JEK")
                .pattern("ALA")
                .define('A', AMItems.ARCANE_ASH.get())
                .define('E', PartialNBTIngredient.of(AMItems.AFFINITY_ESSENCE.get(), helper.getEssenceForAffinity(AMAffinities.NATURE.get()).getOrCreateTag()))
                .define('I', ItemTags.LEAVES)
                .define('J', Items.LILY_PAD)
                .define('K', Items.CACTUS)
                .define('L', Items.VINE)
                .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_nature");
        ShapedNBTRecipeBuilder.shaped(AMItems.AFFINITY_ESSENCE.get(), 2, helper.getEssenceForAffinity(AMAffinities.LIFE.get()).getOrCreateTag())
                .pattern("AIA")
                .pattern("JEJ")
                .pattern("AIA")
                .define('A', AMItems.ARCANE_ASH.get())
                .define('E', PartialNBTIngredient.of(AMItems.AFFINITY_ESSENCE.get(), helper.getEssenceForAffinity(AMAffinities.LIFE.get()).getOrCreateTag()))
                .define('I', Tags.Items.EGGS)
                .define('J', Items.GOLDEN_APPLE)
                .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_life");
        ShapedNBTRecipeBuilder.shaped(AMItems.AFFINITY_ESSENCE.get(), 2, helper.getEssenceForAffinity(AMAffinities.ARCANE.get()).getOrCreateTag())
                .pattern("AAA")
                .pattern("AEA")
                .pattern("AAA")
                .define('A', AMItems.ARCANE_ASH.get())
                .define('E', PartialNBTIngredient.of(AMItems.AFFINITY_ESSENCE.get(), helper.getEssenceForAffinity(AMAffinities.ARCANE.get()).getOrCreateTag()))
                .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_arcane");
        ShapedNBTRecipeBuilder.shaped(AMItems.AFFINITY_ESSENCE.get(), 2, helper.getEssenceForAffinity(AMAffinities.ENDER.get()).getOrCreateTag())
                .pattern("AIA")
                .pattern("JEJ")
                .pattern("AIA")
                .define('A', AMItems.ARCANE_ASH.get())
                .define('E', PartialNBTIngredient.of(AMItems.AFFINITY_ESSENCE.get(), helper.getEssenceForAffinity(AMAffinities.ENDER.get()).getOrCreateTag()))
                .define('I', Tags.Items.ENDER_PEARLS)
                .define('J', Items.ENDER_EYE)
                .unlockedBy("has_arcane_ash", has(AMItems.ARCANE_ASH.get()))
                .save(consumer, ArsMagicaAPI.MOD_ID + ":affinity_essence_ender");
        ShapedRecipeBuilder.shaped(AMItems.SPELL_PARCHMENT.get())
                .pattern("S")
                .pattern("P")
                .pattern("S")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('P', Items.PAPER)
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.SPELL_BOOK.get())
                .pattern("SLL")
                .pattern("SPP")
                .pattern("SLL")
                .define('S', Tags.Items.STRING)
                .define('L', Tags.Items.LEATHER)
                .define('P', Items.PAPER)
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.MANA_CAKE.get())
                .requires(Tags.Items.CROPS_WHEAT)
                .requires(Items.SUGAR)
                .requires(AMItems.AUM.get())
                .requires(AMItems.CERUBLOSSOM.get())
                .unlockedBy("has_cerublossom", has(AMItems.CERUBLOSSOM.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(AMItems.MANA_MARTINI.get())
                .requires(Items.BAMBOO)
                .requires(Items.ICE)
                .requires(AMTags.Items.DUSTS_VINTEUM)
                .requires(PartialNBTIngredient.of(Items.POTION, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER).getOrCreateTag()))
                .unlockedBy("has_ice", has(Items.ICE))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.MAGE_HELMET.get())
                .pattern("LWL")
                .pattern("WRW")
                .define('W', ItemTags.WOOL)
                .define('L', Items.LEATHER)
                .define('R', AMItems.COLORED_RUNE.get(DyeColor.MAGENTA))
                .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.MAGENTA)))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.MAGE_CHESTPLATE.get())
                .pattern("L L")
                .pattern("WRW")
                .pattern("WWW")
                .define('W', ItemTags.WOOL)
                .define('L', Items.LEATHER)
                .define('R', AMItems.COLORED_RUNE.get(DyeColor.WHITE))
                .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.WHITE)))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.MAGE_LEGGINGS.get())
                .pattern("LRL")
                .pattern("W W")
                .pattern("W W")
                .define('W', ItemTags.WOOL)
                .define('L', Items.LEATHER)
                .define('R', AMItems.COLORED_RUNE.get(DyeColor.YELLOW))
                .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.YELLOW)))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.MAGE_BOOTS.get())
                .pattern("W W")
                .pattern("LRL")
                .define('W', ItemTags.WOOL)
                .define('L', Items.LEATHER)
                .define('R', AMItems.COLORED_RUNE.get(DyeColor.BLACK))
                .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.BLACK)))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.BATTLEMAGE_HELMET.get())
                .pattern("GOG")
                .pattern("ORO")
                .pattern(" E ")
                .define('O', Items.OBSIDIAN)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('E', PartialNBTIngredient.of(AMItems.AFFINITY_ESSENCE.get(), helper.getEssenceForAffinity(AMAffinities.AIR.get()).getOrCreateTag()))
                .define('R', AMItems.COLORED_RUNE.get(DyeColor.RED))
                .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.RED)))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.BATTLEMAGE_CHESTPLATE.get())
                .pattern("GEG")
                .pattern("ORO")
                .pattern("OOO")
                .define('O', Items.OBSIDIAN)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('E', PartialNBTIngredient.of(AMItems.AFFINITY_ESSENCE.get(), helper.getEssenceForAffinity(AMAffinities.EARTH.get()).getOrCreateTag()))
                .define('R', AMItems.COLORED_RUNE.get(DyeColor.RED))
                .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.RED)))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.BATTLEMAGE_LEGGINGS.get())
                .pattern("GRG")
                .pattern("OEO")
                .pattern("O O")
                .define('O', Items.OBSIDIAN)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('E', PartialNBTIngredient.of(AMItems.AFFINITY_ESSENCE.get(), helper.getEssenceForAffinity(AMAffinities.FIRE.get()).getOrCreateTag()))
                .define('R', AMItems.COLORED_RUNE.get(DyeColor.RED))
                .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.RED)))
                .save(consumer);
        ShapedRecipeBuilder.shaped(AMItems.BATTLEMAGE_BOOTS.get())
                .pattern("OEO")
                .pattern("GRG")
                .define('O', Items.OBSIDIAN)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('E', PartialNBTIngredient.of(AMItems.AFFINITY_ESSENCE.get(), helper.getEssenceForAffinity(AMAffinities.WATER.get()).getOrCreateTag()))
                .define('R', AMItems.COLORED_RUNE.get(DyeColor.RED))
                .unlockedBy("has_rune", has(AMItems.COLORED_RUNE.get(DyeColor.RED)))
                .save(consumer);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, TagKey<Item> pUnpackedTag, ItemLike pPacked, TagKey<Item> pPackedTag) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, pUnpacked, pUnpackedTag, pPacked, pPackedTag, getSimpleRecipeName(pPacked), null, getSimpleRecipeName(pUnpacked), null);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, TagKey<Item> pUnpackedTag, ItemLike pPacked, TagKey<Item> pPackedTag, String pPackingRecipeName, @Nullable String pPackingRecipeGroup, String pUnpackingRecipeName, @Nullable String pUnpackingRecipeGroup) {
        ShapelessRecipeBuilder.shapeless(pUnpacked, 9).requires(pPackedTag).group(pUnpackingRecipeGroup).unlockedBy(getHasName(pPacked), has(pPacked)).save(pFinishedRecipeConsumer, new ResourceLocation(ArsMagicaAPI.MOD_ID, pUnpackingRecipeName));
        ShapedRecipeBuilder.shaped(pPacked).define('#', pUnpackedTag).pattern("###").pattern("###").pattern("###").group(pPackingRecipeGroup).unlockedBy(getHasName(pUnpacked), has(pUnpacked)).save(pFinishedRecipeConsumer, new ResourceLocation(ArsMagicaAPI.MOD_ID, pPackingRecipeName));
    }

    private static class ShapedNBTRecipeBuilder {
        private final Item result;
        private final int count;
        private final CompoundTag tag;
        private final List<String> rows = new ArrayList<>();
        private final Map<Character, Ingredient> key = new LinkedHashMap<>();
        private final Advancement.Builder advancement = Advancement.Builder.advancement();

        public ShapedNBTRecipeBuilder(ItemLike result, int count, CompoundTag tag) {
            this.result = result.asItem();
            this.count = count;
            this.tag = tag;
        }

        public static ShapedNBTRecipeBuilder shaped(ItemLike result, CompoundTag compound) {
            return new ShapedNBTRecipeBuilder(result, 1, compound);
        }

        public static ShapedNBTRecipeBuilder shaped(ItemLike result, int count, CompoundTag compound) {
            return new ShapedNBTRecipeBuilder(result, count, compound);
        }

        public ShapedNBTRecipeBuilder define(Character symbol, TagKey<Item> tag) {
            return define(symbol, Ingredient.of(tag));
        }

        public ShapedNBTRecipeBuilder define(Character symbol, ItemLike item) {
            return define(symbol, Ingredient.of(item));
        }

        public ShapedNBTRecipeBuilder define(Character symbol, Ingredient item) {
            if (key.containsKey(symbol)) {
                throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
            } else if (symbol == ' ') {
                throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined!");
            } else {
                key.put(symbol, item);
                return this;
            }
        }

        public ShapedNBTRecipeBuilder pattern(String pattern) {
            if (!rows.isEmpty() && pattern.length() != rows.get(0).length()) {
                throw new IllegalArgumentException("Pattern must be the same width on every line!");
            } else {
                rows.add(pattern);
                return this;
            }
        }

        public ShapedNBTRecipeBuilder unlockedBy(String name, CriterionTriggerInstance criterion) {
            advancement.addCriterion(name, criterion);
            return this;
        }

        public void save(Consumer<FinishedRecipe> consumer) {
            save(consumer, ForgeRegistries.ITEMS.getKey(result));
        }

        public void save(Consumer<FinishedRecipe> consumer, String save) {
            ResourceLocation saveTo = new ResourceLocation(save);
            if (saveTo.equals(ForgeRegistries.ITEMS.getKey(result.asItem()))) {
                throw new IllegalStateException("Shaped Recipe " + save + " should remove its 'save' argument!");
            } else {
                save(consumer, saveTo);
            }
        }

        public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
            ensureValid(id);
            advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
            consumer.accept(new Result(id, result, count, tag, "", rows, key, advancement, new ResourceLocation(id.getNamespace(), "recipes/" + result.getItemCategory().getRecipeFolderName() + "/" + id.getPath())));
        }

        private void ensureValid(ResourceLocation id) {
            if (rows.isEmpty()) throw new IllegalStateException("No pattern is defined for shaped recipe " + id + "!");
            else {
                Set<Character> set = Sets.newHashSet(key.keySet());
                set.remove(' ');
                for (String s : rows) {
                    for (int i = 0; i < s.length(); ++i) {
                        char c0 = s.charAt(i);
                        if (!key.containsKey(c0) && c0 != ' ') {
                            throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + c0 + "'!");
                        }
                        set.remove(c0);
                    }
                }
                if (!set.isEmpty())
                    throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id + "!");
                else if (rows.size() == 1 && rows.get(0).length() == 1)
                    throw new IllegalStateException("Shaped recipe " + id + " only takes in a single item - should it be a shapeless recipe instead?");
                else if (advancement.getCriteria().isEmpty())
                    throw new IllegalStateException("No way of obtaining recipe " + id + "!");
            }
        }

        public static class Result implements FinishedRecipe {
            private final ResourceLocation id;
            private final Item result;
            private final int count;
            private final CompoundTag compound;
            private final List<String> pattern;
            private final Map<Character, Ingredient> key;
            private final Advancement.Builder advancement;
            private final ResourceLocation advancementId;

            public Result(ResourceLocation id, Item result, int count, CompoundTag compound, String group, List<String> pattern, Map<Character, Ingredient> key, Advancement.Builder advancement, ResourceLocation advancementId) {
                this.id = id;
                this.result = result;
                this.count = count;
                this.compound = compound;
                this.pattern = pattern;
                this.key = key;
                this.advancement = advancement;
                this.advancementId = advancementId;
            }

            public void serializeRecipeData(JsonObject json) {
                JsonArray patternJson = new JsonArray();
                for (String s : pattern) {
                    patternJson.add(s);
                }
                json.add("pattern", patternJson);
                JsonObject keyJson = new JsonObject();
                for (Map.Entry<Character, Ingredient> entry : key.entrySet()) {
                    keyJson.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
                }
                json.add("key", keyJson);
                JsonObject resultJson = new JsonObject();
                resultJson.addProperty("item", ForgeRegistries.ITEMS.getKey(result).toString());
                if (count > 1) {
                    resultJson.addProperty("count", count);
                }
                resultJson.addProperty("nbt", NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, compound).toString());
                json.add("result", resultJson);
            }

            public RecipeSerializer<?> getType() {
                return RecipeSerializer.SHAPED_RECIPE;
            }

            public ResourceLocation getId() {
                return id;
            }

            public JsonObject serializeAdvancement() {
                return advancement.serializeToJson();
            }

            public ResourceLocation getAdvancementId() {
                return advancementId;
            }
        }
    }

    private static class ShapelessNBTRecipeBuilder {
        private final Item result;
        private final int count;
        private final CompoundTag compound;
        private final List<Ingredient> ingredients = new ArrayList<>();
        private final Advancement.Builder advancement = Advancement.Builder.advancement();

        private ShapelessNBTRecipeBuilder(ItemLike result, int count, CompoundTag compound) {
            this.result = result.asItem();
            this.count = count;
            this.compound = compound;
        }

        public static ShapelessNBTRecipeBuilder shapeless(ItemLike result, CompoundTag compound) {
            return new ShapelessNBTRecipeBuilder(result, 1, compound);
        }

        public static ShapelessNBTRecipeBuilder shapeless(ItemLike result, int count, CompoundTag compound) {
            return new ShapelessNBTRecipeBuilder(result, count, compound);
        }

        public ShapelessNBTRecipeBuilder requires(TagKey<Item> tag) {
            return requires(Ingredient.of(tag));
        }

        public ShapelessNBTRecipeBuilder requires(ItemLike item) {
            return requires(item, 1);
        }

        public ShapelessNBTRecipeBuilder requires(ItemLike item, int quantity) {
            for (int i = 0; i < quantity; ++i) {
                requires(Ingredient.of(item));
            }
            return this;
        }

        public ShapelessNBTRecipeBuilder requires(Ingredient ingredient) {
            return requires(ingredient, 1);
        }

        public ShapelessNBTRecipeBuilder requires(Ingredient ingredient, int quantity) {
            for (int i = 0; i < quantity; ++i) {
                ingredients.add(ingredient);
            }
            return this;
        }

        public ShapelessNBTRecipeBuilder unlockedBy(String name, CriterionTriggerInstance criterion) {
            this.advancement.addCriterion(name, criterion);
            return this;
        }

        public void save(Consumer<FinishedRecipe> consumer) {
            this.save(consumer, ForgeRegistries.ITEMS.getKey(result.asItem()));
        }

        public void save(Consumer<FinishedRecipe> consumer, String save) {
            ResourceLocation saveTo = new ResourceLocation(save);
            if (saveTo.equals(ForgeRegistries.ITEMS.getKey(result.asItem()))) {
                throw new IllegalStateException("Shapeless Recipe " + save + " should remove its 'save' argument!");
            } else {
                save(consumer, saveTo);
            }
        }

        public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
            ensureValid(id);
            advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
            consumer.accept(new ShapelessNBTRecipeBuilder.Result(id, result, count, compound, ingredients, advancement, new ResourceLocation(id.getNamespace(), "recipes/" + result.getItemCategory().getRecipeFolderName() + "/" + id.getPath())));
        }

        private void ensureValid(ResourceLocation id) {
            if (advancement.getCriteria().isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + id + "!");
            }
        }

        public static class Result implements FinishedRecipe {
            private final ResourceLocation id;
            private final Item result;
            private final int count;
            private final CompoundTag compound;
            private final List<Ingredient> ingredients;
            private final Advancement.Builder advancement;
            private final ResourceLocation advancementId;

            public Result(ResourceLocation id, Item result, int count, CompoundTag compound, List<Ingredient> ingredients, Advancement.Builder advancementBuilder, ResourceLocation advancementId) {
                this.id = id;
                this.result = result;
                this.count = count;
                this.compound = compound;
                this.ingredients = ingredients;
                this.advancement = advancementBuilder;
                this.advancementId = advancementId;
            }

            public void serializeRecipeData(JsonObject json) {
                JsonArray ingredientsJson = new JsonArray();
                for (Ingredient ingredient : ingredients) {
                    ingredientsJson.add(ingredient.toJson());
                }
                json.add("ingredients", ingredientsJson);
                JsonObject resultJson = new JsonObject();
                resultJson.addProperty("item", ForgeRegistries.ITEMS.getKey(result).toString());
                if (count > 1) {
                    resultJson.addProperty("count", count);
                }
                resultJson.addProperty("nbt", NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, compound).toString());
                json.add("result", resultJson);
            }

            public RecipeSerializer<?> getType() {
                return RecipeSerializer.SHAPELESS_RECIPE;
            }

            public ResourceLocation getId() {
                return id;
            }

            public JsonObject serializeAdvancement() {
                return advancement.serializeToJson();
            }

            public ResourceLocation getAdvancementId() {
                return advancementId;
            }
        }
    }
}
