package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AltarStructureMaterialProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

class AMAltarStructureMaterialProvider extends AltarStructureMaterialProvider {
    AMAltarStructureMaterialProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(ArsMagicaAPI.MOD_ID, generator, existingFileHelper);
    }

    @Override
    protected void createStructureMaterials() {
        addStructureMaterial(BlockFamilies.OAK_PLANKS, 1);
        addStructureMaterial(BlockFamilies.SPRUCE_PLANKS, 1);
        addStructureMaterial(BlockFamilies.BIRCH_PLANKS, 1);
        addStructureMaterial(BlockFamilies.JUNGLE_PLANKS, 1);
        addStructureMaterial(BlockFamilies.ACACIA_PLANKS, 1);
        addStructureMaterial(BlockFamilies.DARK_OAK_PLANKS, 1);
        addStructureMaterial(BlockFamilies.MANGROVE_PLANKS, 1);
        addStructureMaterial(BlockFamilies.COBBLESTONE, 2);
        addStructureMaterial(BlockFamilies.STONE, 2);
        addStructureMaterial(BlockFamilies.MUD_BRICKS, 2);
        addStructureMaterial(BlockFamilies.MOSSY_COBBLESTONE, 2);
        addStructureMaterial(BlockFamilies.COBBLED_DEEPSLATE, 2);
        addStructureMaterial(BlockFamilies.ANDESITE, 2);
        addStructureMaterial(BlockFamilies.DIORITE, 2);
        addStructureMaterial(BlockFamilies.GRANITE, 2);
        addStructureMaterial(BlockFamilies.SANDSTONE, 2);
        addStructureMaterial(BlockFamilies.RED_SANDSTONE, 2);
        addStructureMaterial(BlockFamilies.BRICKS, 3);
        addStructureMaterial(BlockFamilies.STONE_BRICK, 3);
        addStructureMaterial(BlockFamilies.MOSSY_STONE_BRICKS, 3);
        addStructureMaterial(BlockFamilies.POLISHED_DEEPSLATE, 3);
        addStructureMaterial(BlockFamilies.DEEPSLATE_BRICKS, 3);
        addStructureMaterial(BlockFamilies.DEEPSLATE_TILES, 3);
        addStructureMaterial(BlockFamilies.POLISHED_ANDESITE, 3);
        addStructureMaterial(BlockFamilies.POLISHED_DIORITE, 3);
        addStructureMaterial(BlockFamilies.POLISHED_GRANITE, 3);
        addStructureMaterial(BlockFamilies.SMOOTH_SANDSTONE, 3);
        addStructureMaterial(BlockFamilies.SMOOTH_RED_SANDSTONE, 3);
        addStructureMaterial(BlockFamilies.CUT_COPPER, 3);
        addStructureMaterial(BlockFamilies.EXPOSED_CUT_COPPER, 3);
        addStructureMaterial(BlockFamilies.WEATHERED_CUT_COPPER, 3);
        addStructureMaterial(BlockFamilies.OXIDIZED_CUT_COPPER, 3);
        addStructureMaterial(BlockFamilies.WAXED_CUT_COPPER, 3);
        addStructureMaterial(BlockFamilies.WAXED_EXPOSED_CUT_COPPER, 3);
        addStructureMaterial(BlockFamilies.WAXED_WEATHERED_CUT_COPPER, 3);
        addStructureMaterial(BlockFamilies.WAXED_OXIDIZED_CUT_COPPER, 3);
        addStructureMaterial(BlockFamilies.PRISMARINE, 4);
        addStructureMaterial(BlockFamilies.PRISMARINE_BRICKS, 4);
        addStructureMaterial(BlockFamilies.DARK_PRISMARINE, 4);
        addStructureMaterial(BlockFamilies.CRIMSON_PLANKS, 4);
        addStructureMaterial(BlockFamilies.WARPED_PLANKS, 4);
        addStructureMaterial(AMBlockFamilies.WITCHWOOD_PLANKS.get(), 4);
        addStructureMaterial(BlockFamilies.BLACKSTONE, 4);
        addStructureMaterial(BlockFamilies.QUARTZ, 4);
        addStructureMaterial(BlockFamilies.NETHER_BRICKS, 5);
        addStructureMaterial(BlockFamilies.RED_NETHER_BRICKS, 5);
        addStructureMaterial(BlockFamilies.POLISHED_BLACKSTONE, 5);
        addStructureMaterial(BlockFamilies.POLISHED_BLACKSTONE_BRICKS, 5);
        addStructureMaterial(BlockFamilies.SMOOTH_QUARTZ, 5);
        addStructureMaterial(BlockFamilies.END_STONE_BRICKS, 6);
        addStructureMaterial(BlockFamilies.PURPUR, 6);
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
        addCapMaterial("moonstone", AMBlocks.MOONSTONE_BLOCK.get(), 14);
        addCapMaterial("sunstone", AMBlocks.SUNSTONE_BLOCK.get(), 15);
    }
}
