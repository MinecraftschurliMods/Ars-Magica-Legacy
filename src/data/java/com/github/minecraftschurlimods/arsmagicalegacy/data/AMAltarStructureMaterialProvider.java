package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AltarStructureMaterialProvider;
import com.google.gson.JsonElement;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraftforge.common.data.ExistingFileHelper;

class AMAltarStructureMaterialProvider extends AltarStructureMaterialProvider {
    AMAltarStructureMaterialProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(ArsMagicaAPI.MOD_ID, generator, existingFileHelper, registryOps);
    }

    @Override
    protected void generate() {
        add(builder(BlockFamilies.OAK_PLANKS, 1));
        add(builder(BlockFamilies.SPRUCE_PLANKS, 1));
        add(builder(BlockFamilies.BIRCH_PLANKS, 1));
        add(builder(BlockFamilies.JUNGLE_PLANKS, 1));
        add(builder(BlockFamilies.ACACIA_PLANKS, 1));
        add(builder(BlockFamilies.DARK_OAK_PLANKS, 1));
        add(builder(BlockFamilies.MANGROVE_PLANKS, 1));
        add(builder(BlockFamilies.COBBLESTONE, 2));
        add(builder(BlockFamilies.STONE, 2));
        add(builder(BlockFamilies.MUD_BRICKS, 2));
        add(builder(BlockFamilies.MOSSY_COBBLESTONE, 2));
        add(builder(BlockFamilies.COBBLED_DEEPSLATE, 2));
        add(builder(BlockFamilies.ANDESITE, 2));
        add(builder(BlockFamilies.DIORITE, 2));
        add(builder(BlockFamilies.GRANITE, 2));
        add(builder(BlockFamilies.SANDSTONE, 2));
        add(builder(BlockFamilies.RED_SANDSTONE, 2));
        add(builder(BlockFamilies.BRICKS, 3));
        add(builder(BlockFamilies.STONE_BRICK, 3));
        add(builder(BlockFamilies.MOSSY_STONE_BRICKS, 3));
        add(builder(BlockFamilies.POLISHED_DEEPSLATE, 3));
        add(builder(BlockFamilies.DEEPSLATE_BRICKS, 3));
        add(builder(BlockFamilies.DEEPSLATE_TILES, 3));
        add(builder(BlockFamilies.POLISHED_ANDESITE, 3));
        add(builder(BlockFamilies.POLISHED_DIORITE, 3));
        add(builder(BlockFamilies.POLISHED_GRANITE, 3));
        add(builder(BlockFamilies.SMOOTH_SANDSTONE, 3));
        add(builder(BlockFamilies.SMOOTH_RED_SANDSTONE, 3));
        add(builder(BlockFamilies.CUT_COPPER, 3));
        add(builder(BlockFamilies.EXPOSED_CUT_COPPER, 3));
        add(builder(BlockFamilies.WEATHERED_CUT_COPPER, 3));
        add(builder(BlockFamilies.OXIDIZED_CUT_COPPER, 3));
        add(builder(BlockFamilies.WAXED_CUT_COPPER, 3));
        add(builder(BlockFamilies.WAXED_EXPOSED_CUT_COPPER, 3));
        add(builder(BlockFamilies.WAXED_WEATHERED_CUT_COPPER, 3));
        add(builder(BlockFamilies.WAXED_OXIDIZED_CUT_COPPER, 3));
        add(builder(BlockFamilies.PRISMARINE, 4));
        add(builder(BlockFamilies.PRISMARINE_BRICKS, 4));
        add(builder(BlockFamilies.DARK_PRISMARINE, 4));
        add(builder(BlockFamilies.CRIMSON_PLANKS, 4));
        add(builder(BlockFamilies.WARPED_PLANKS, 4));
        add(builder(AMBlockFamilies.WITCHWOOD_PLANKS.get(), 4));
        add(builder(BlockFamilies.BLACKSTONE, 4));
        add(builder(BlockFamilies.QUARTZ, 4));
        add(builder(BlockFamilies.NETHER_BRICKS, 5));
        add(builder(BlockFamilies.RED_NETHER_BRICKS, 5));
        add(builder(BlockFamilies.POLISHED_BLACKSTONE, 5));
        add(builder(BlockFamilies.POLISHED_BLACKSTONE_BRICKS, 5));
        add(builder(BlockFamilies.SMOOTH_QUARTZ, 5));
        add(builder(BlockFamilies.END_STONE_BRICKS, 6));
        add(builder(BlockFamilies.PURPUR, 6));
    }
}
