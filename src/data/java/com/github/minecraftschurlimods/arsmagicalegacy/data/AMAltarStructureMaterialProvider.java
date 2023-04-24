package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AltarStructureMaterialProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;

import java.util.function.Consumer;

class AMAltarStructureMaterialProvider extends AltarStructureMaterialProvider {
    AMAltarStructureMaterialProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void generate(Consumer<Builder> consumer) {
        builder("oak_planks", Blocks.OAK_PLANKS, (StairBlock) Blocks.OAK_STAIRS, 1).build(consumer);
        builder("spruce_planks", Blocks.SPRUCE_PLANKS, (StairBlock) Blocks.SPRUCE_STAIRS, 1).build(consumer);
        builder("birch_planks", Blocks.BIRCH_PLANKS, (StairBlock) Blocks.BIRCH_STAIRS, 1).build(consumer);
        builder("jungle_planks", Blocks.JUNGLE_PLANKS, (StairBlock) Blocks.JUNGLE_STAIRS, 1).build(consumer);
        builder("acacia_planks", Blocks.ACACIA_PLANKS, (StairBlock) Blocks.ACACIA_STAIRS, 1).build(consumer);
        builder("dark_oak_planks", Blocks.DARK_OAK_PLANKS, (StairBlock) Blocks.DARK_OAK_STAIRS, 1).build(consumer);
        builder("cobblestone", Blocks.COBBLESTONE, (StairBlock) Blocks.COBBLESTONE_STAIRS, 2).build(consumer);
        builder("mossy_cobblestone", Blocks.MOSSY_COBBLESTONE, (StairBlock) Blocks.MOSSY_COBBLESTONE_STAIRS, 2).build(consumer);
        builder("cobbled_deepslate", Blocks.COBBLED_DEEPSLATE, (StairBlock) Blocks.COBBLED_DEEPSLATE_STAIRS, 2).build(consumer);
        builder("andesite", Blocks.ANDESITE, (StairBlock) Blocks.ANDESITE_STAIRS, 2).build(consumer);
        builder("diorite", Blocks.DIORITE, (StairBlock) Blocks.DIORITE_STAIRS, 2).build(consumer);
        builder("granite", Blocks.GRANITE, (StairBlock) Blocks.GRANITE_STAIRS, 2).build(consumer);
        builder("sandstone", Blocks.SANDSTONE, (StairBlock) Blocks.SANDSTONE_STAIRS, 2).build(consumer);
        builder("red_sandstone", Blocks.RED_SANDSTONE, (StairBlock) Blocks.RED_SANDSTONE_STAIRS, 2).build(consumer);
        builder("bricks", Blocks.BRICKS, (StairBlock) Blocks.BRICK_STAIRS, 3).build(consumer);
        builder("stone_bricks", Blocks.STONE_BRICKS, (StairBlock) Blocks.STONE_BRICK_STAIRS, 3).build(consumer);
        builder("mossy_stone_bricks", Blocks.MOSSY_STONE_BRICKS, (StairBlock) Blocks.MOSSY_STONE_BRICK_STAIRS, 3).build(consumer);
        builder("polished_deepslate", Blocks.POLISHED_DEEPSLATE, (StairBlock) Blocks.POLISHED_DEEPSLATE_STAIRS, 3).build(consumer);
        builder("deepslate_bricks", Blocks.DEEPSLATE_BRICKS, (StairBlock) Blocks.DEEPSLATE_BRICK_STAIRS, 3).build(consumer);
        builder("deepslate_tiles", Blocks.DEEPSLATE_TILES, (StairBlock) Blocks.DEEPSLATE_TILE_STAIRS, 3).build(consumer);
        builder("polished_andesite", Blocks.POLISHED_ANDESITE, (StairBlock) Blocks.POLISHED_ANDESITE_STAIRS, 3).build(consumer);
        builder("polished_diorite", Blocks.POLISHED_DIORITE, (StairBlock) Blocks.POLISHED_DIORITE_STAIRS, 3).build(consumer);
        builder("polished_granite", Blocks.POLISHED_GRANITE, (StairBlock) Blocks.POLISHED_GRANITE_STAIRS, 3).build(consumer);
        builder("smooth_sandstone", Blocks.SMOOTH_SANDSTONE, (StairBlock) Blocks.SMOOTH_SANDSTONE_STAIRS, 3).build(consumer);
        builder("smooth_red_sandstone", Blocks.SMOOTH_RED_SANDSTONE, (StairBlock) Blocks.SMOOTH_RED_SANDSTONE_STAIRS, 3).build(consumer);
        builder("cut_copper", Blocks.CUT_COPPER, (StairBlock) Blocks.CUT_COPPER_STAIRS, 3).build(consumer);
        builder("exposed_cut_copper", Blocks.EXPOSED_CUT_COPPER, (StairBlock) Blocks.EXPOSED_CUT_COPPER_STAIRS, 3).build(consumer);
        builder("weathered_cut_copper", Blocks.WEATHERED_CUT_COPPER, (StairBlock) Blocks.WEATHERED_CUT_COPPER_STAIRS, 3).build(consumer);
        builder("oxidized_cut_copper", Blocks.OXIDIZED_CUT_COPPER, (StairBlock) Blocks.OXIDIZED_CUT_COPPER_STAIRS, 3).build(consumer);
        builder("waxed_cut_copper", Blocks.WAXED_CUT_COPPER, (StairBlock) Blocks.WAXED_CUT_COPPER_STAIRS, 3).build(consumer);
        builder("waxed_exposed_cut_copper", Blocks.WAXED_EXPOSED_CUT_COPPER, (StairBlock) Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS, 3).build(consumer);
        builder("waxed_weathered_cut_copper", Blocks.WAXED_WEATHERED_CUT_COPPER, (StairBlock) Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS, 3).build(consumer);
        builder("waxed_oxidized_cut_copper", Blocks.WAXED_OXIDIZED_CUT_COPPER, (StairBlock) Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS, 3).build(consumer);
        builder("prismarine", Blocks.PRISMARINE, (StairBlock) Blocks.PRISMARINE_STAIRS, 4).build(consumer);
        builder("prismarine_bricks", Blocks.PRISMARINE_BRICKS, (StairBlock) Blocks.PRISMARINE_BRICK_STAIRS, 4).build(consumer);
        builder("dark_prismarine", Blocks.DARK_PRISMARINE, (StairBlock) Blocks.DARK_PRISMARINE_STAIRS, 4).build(consumer);
        builder("crimson_planks", Blocks.CRIMSON_PLANKS, (StairBlock) Blocks.CRIMSON_STAIRS, 4).build(consumer);
        builder("warped_planks", Blocks.WARPED_PLANKS, (StairBlock) Blocks.WARPED_STAIRS, 4).build(consumer);
        builder("witchwood_planks", AMBlocks.WITCHWOOD_PLANKS.get(), AMBlocks.WITCHWOOD_STAIRS.get(), 4).build(consumer);
        builder("blackstone", Blocks.BLACKSTONE, (StairBlock) Blocks.BLACKSTONE_STAIRS, 4).build(consumer);
        builder("quartz_block", Blocks.QUARTZ_BLOCK, (StairBlock) Blocks.QUARTZ_STAIRS, 4).build(consumer);
        builder("nether_bricks", Blocks.NETHER_BRICKS, (StairBlock) Blocks.NETHER_BRICK_STAIRS, 5).build(consumer);
        builder("red_nether_bricks", Blocks.RED_NETHER_BRICKS, (StairBlock) Blocks.RED_NETHER_BRICK_STAIRS, 5).build(consumer);
        builder("polished_blackstone", Blocks.POLISHED_BLACKSTONE, (StairBlock) Blocks.POLISHED_BLACKSTONE_STAIRS, 5).build(consumer);
        builder("polished_blackstone_bricks", Blocks.POLISHED_BLACKSTONE_BRICKS, (StairBlock) Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, 5).build(consumer);
        builder("smooth_quartz", Blocks.SMOOTH_QUARTZ, (StairBlock) Blocks.SMOOTH_QUARTZ_STAIRS, 5).build(consumer);
        builder("end_stone_bricks", Blocks.END_STONE_BRICKS, (StairBlock) Blocks.END_STONE_BRICK_STAIRS, 6).build(consumer);
        builder("purpur_block", Blocks.PURPUR_BLOCK, (StairBlock) Blocks.PURPUR_STAIRS, 6).build(consumer);
    }
}
