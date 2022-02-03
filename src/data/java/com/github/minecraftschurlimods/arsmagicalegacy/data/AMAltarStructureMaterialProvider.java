package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AltarStructureMaterialProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;

class AMAltarStructureMaterialProvider extends AltarStructureMaterialProvider {
    AMAltarStructureMaterialProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void createStructureMaterials() {
        addStructureMaterial("oak_planks", Blocks.OAK_PLANKS, (StairBlock) Blocks.OAK_STAIRS, 1);
        addStructureMaterial("spruce_planks", Blocks.SPRUCE_PLANKS, (StairBlock) Blocks.SPRUCE_STAIRS, 1);
        addStructureMaterial("birch_planks", Blocks.BIRCH_PLANKS, (StairBlock) Blocks.BIRCH_STAIRS, 1);
        addStructureMaterial("jungle_planks", Blocks.JUNGLE_PLANKS, (StairBlock) Blocks.JUNGLE_STAIRS, 1);
        addStructureMaterial("acacia_planks", Blocks.ACACIA_PLANKS, (StairBlock) Blocks.ACACIA_STAIRS, 1);
        addStructureMaterial("dark_oak_planks", Blocks.DARK_OAK_PLANKS, (StairBlock) Blocks.DARK_OAK_STAIRS, 1);
        addStructureMaterial("cobblestone", Blocks.COBBLESTONE, (StairBlock) Blocks.COBBLESTONE_STAIRS, 2);
        addStructureMaterial("mossy_cobblestone", Blocks.MOSSY_COBBLESTONE, (StairBlock) Blocks.MOSSY_COBBLESTONE_STAIRS, 2);
        addStructureMaterial("cobbled_deepslate", Blocks.COBBLED_DEEPSLATE, (StairBlock) Blocks.COBBLED_DEEPSLATE_STAIRS, 2);
        addStructureMaterial("andesite", Blocks.ANDESITE, (StairBlock) Blocks.ANDESITE_STAIRS, 2);
        addStructureMaterial("diorite", Blocks.DIORITE, (StairBlock) Blocks.DIORITE_STAIRS, 2);
        addStructureMaterial("granite", Blocks.GRANITE, (StairBlock) Blocks.GRANITE_STAIRS, 2);
        addStructureMaterial("sandstone", Blocks.SANDSTONE, (StairBlock) Blocks.SANDSTONE_STAIRS, 2);
        addStructureMaterial("red_sandstone", Blocks.RED_SANDSTONE, (StairBlock) Blocks.RED_SANDSTONE_STAIRS, 2);
        addStructureMaterial("bricks", Blocks.BRICKS, (StairBlock) Blocks.BRICK_STAIRS, 3);
        addStructureMaterial("stone_bricks", Blocks.STONE_BRICKS, (StairBlock) Blocks.STONE_BRICK_STAIRS, 3);
        addStructureMaterial("mossy_stone_bricks", Blocks.MOSSY_STONE_BRICKS, (StairBlock) Blocks.MOSSY_STONE_BRICK_STAIRS, 3);
        addStructureMaterial("polished_deepslate", Blocks.POLISHED_DEEPSLATE, (StairBlock) Blocks.POLISHED_DEEPSLATE_STAIRS, 3);
        addStructureMaterial("deepslate_bricks", Blocks.DEEPSLATE_BRICKS, (StairBlock) Blocks.DEEPSLATE_BRICK_STAIRS, 3);
        addStructureMaterial("deepslate_tiles", Blocks.DEEPSLATE_TILES, (StairBlock) Blocks.DEEPSLATE_TILE_STAIRS, 3);
        addStructureMaterial("polished_andesite", Blocks.POLISHED_ANDESITE, (StairBlock) Blocks.POLISHED_ANDESITE_STAIRS, 3);
        addStructureMaterial("polished_diorite", Blocks.POLISHED_DIORITE, (StairBlock) Blocks.POLISHED_DIORITE_STAIRS, 3);
        addStructureMaterial("polished_granite", Blocks.POLISHED_GRANITE, (StairBlock) Blocks.POLISHED_GRANITE_STAIRS, 3);
        addStructureMaterial("smooth_sandstone", Blocks.SMOOTH_SANDSTONE, (StairBlock) Blocks.SMOOTH_SANDSTONE_STAIRS, 3);
        addStructureMaterial("smooth_red_sandstone", Blocks.SMOOTH_RED_SANDSTONE, (StairBlock) Blocks.SMOOTH_RED_SANDSTONE_STAIRS, 3);
        addStructureMaterial("cut_copper", Blocks.CUT_COPPER, (StairBlock) Blocks.CUT_COPPER_STAIRS, 3);
        addStructureMaterial("exposed_cut_copper", Blocks.EXPOSED_CUT_COPPER, (StairBlock) Blocks.EXPOSED_CUT_COPPER_STAIRS, 3);
        addStructureMaterial("weathered_cut_copper", Blocks.WEATHERED_CUT_COPPER, (StairBlock) Blocks.WEATHERED_CUT_COPPER_STAIRS, 3);
        addStructureMaterial("oxidized_cut_copper", Blocks.OXIDIZED_CUT_COPPER, (StairBlock) Blocks.OXIDIZED_CUT_COPPER_STAIRS, 3);
        addStructureMaterial("waxed_cut_copper", Blocks.WAXED_CUT_COPPER, (StairBlock) Blocks.WAXED_CUT_COPPER_STAIRS, 3);
        addStructureMaterial("waxed_exposed_cut_copper", Blocks.WAXED_EXPOSED_CUT_COPPER, (StairBlock) Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS, 3);
        addStructureMaterial("waxed_weathered_cut_copper", Blocks.WAXED_WEATHERED_CUT_COPPER, (StairBlock) Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS, 3);
        addStructureMaterial("waxed_oxidized_cut_copper", Blocks.WAXED_OXIDIZED_CUT_COPPER, (StairBlock) Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS, 3);
        addStructureMaterial("prismarine", Blocks.PRISMARINE, (StairBlock) Blocks.PRISMARINE_STAIRS, 4);
        addStructureMaterial("prismarine_bricks", Blocks.PRISMARINE_BRICKS, (StairBlock) Blocks.PRISMARINE_BRICK_STAIRS, 4);
        addStructureMaterial("dark_prismarine", Blocks.DARK_PRISMARINE, (StairBlock) Blocks.DARK_PRISMARINE_STAIRS, 4);
        addStructureMaterial("crimson_planks", Blocks.CRIMSON_PLANKS, (StairBlock) Blocks.CRIMSON_STAIRS, 4);
        addStructureMaterial("warped_planks", Blocks.WARPED_PLANKS, (StairBlock) Blocks.WARPED_STAIRS, 4);
//        addStructureMaterial("witchwood_planks", AMBlocks.WITCHWOOD_PLANKS.get(), AMBlocks.WITCHWOOD_STAIRS.get(), 4);
        addStructureMaterial("blackstone", Blocks.BLACKSTONE, (StairBlock) Blocks.BLACKSTONE_STAIRS, 4);
        addStructureMaterial("quartz_block", Blocks.QUARTZ_BLOCK, (StairBlock) Blocks.QUARTZ_STAIRS, 4);
        addStructureMaterial("nether_bricks", Blocks.NETHER_BRICKS, (StairBlock) Blocks.NETHER_BRICK_STAIRS, 5);
        addStructureMaterial("red_nether_bricks", Blocks.RED_NETHER_BRICKS, (StairBlock) Blocks.RED_NETHER_BRICK_STAIRS, 5);
        addStructureMaterial("polished_blackstone", Blocks.POLISHED_BLACKSTONE, (StairBlock) Blocks.POLISHED_BLACKSTONE_STAIRS, 5);
        addStructureMaterial("polished_blackstone_bricks", Blocks.POLISHED_BLACKSTONE_BRICKS, (StairBlock) Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, 5);
        addStructureMaterial("smooth_quartz", Blocks.SMOOTH_QUARTZ, (StairBlock) Blocks.SMOOTH_QUARTZ_STAIRS, 5);
        addStructureMaterial("end_stone_bricks", Blocks.END_STONE_BRICKS, (StairBlock) Blocks.END_STONE_BRICK_STAIRS, 6);
        addStructureMaterial("purpur_block", Blocks.PURPUR_BLOCK, (StairBlock) Blocks.PURPUR_STAIRS, 6);
        addCapMaterial("glass", Blocks.GLASS, 1);
        addCapMaterial("coal", Blocks.COAL_BLOCK, 2);
        addCapMaterial("copper", Blocks.COPPER_BLOCK, 3);
        addCapMaterial("exposed_copper", Blocks.EXPOSED_COPPER, 3);
        addCapMaterial("weathered_copper", Blocks.WEATHERED_COPPER, 3);
        addCapMaterial("oxidized_copper", Blocks.OXIDIZED_COPPER, 3);
        addCapMaterial("waxed_copper", Blocks.WAXED_COPPER_BLOCK, 3);
        addCapMaterial("waxed_exposed_copper", Blocks.WAXED_EXPOSED_COPPER, 3);
        addCapMaterial("waxed_weathered_copper", Blocks.WAXED_WEATHERED_COPPER, 3);
        addCapMaterial("waxed_oxidized_copper", Blocks.WAXED_OXIDIZED_COPPER, 3);
        addCapMaterial("iron", Blocks.IRON_BLOCK, 4);
        addCapMaterial("redstone", Blocks.REDSTONE_BLOCK, 5);
        addCapMaterial("vinteum", AMBlocks.VINTEUM_BLOCK.get(), 6);
        addCapMaterial("chimerite", AMBlocks.CHIMERITE_BLOCK.get(), 7);
        addCapMaterial("lapis", Blocks.LAPIS_BLOCK, 8);
        addCapMaterial("gold", Blocks.GOLD_BLOCK, 9);
        addCapMaterial("topaz", AMBlocks.TOPAZ_BLOCK.get(), 10);
        addCapMaterial("diamond", Blocks.DIAMOND_BLOCK, 11);
        addCapMaterial("emerald", Blocks.EMERALD_BLOCK, 12);
        addCapMaterial("netherite", Blocks.NETHERITE_BLOCK, 13);
//        addCapMaterial("moonstone", AMBlocks.MOONSTONE_BLOCK.get(), 14);
//        addCapMaterial("sunstone", AMBlocks.SUNSTONE_BLOCK.get(), 15);
    }
}
