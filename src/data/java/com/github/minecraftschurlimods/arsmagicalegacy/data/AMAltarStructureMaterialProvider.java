package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AltarStructureMaterialProvider;
import com.google.gson.JsonElement;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

class AMAltarStructureMaterialProvider extends AltarStructureMaterialProvider {
    AMAltarStructureMaterialProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(ArsMagicaAPI.MOD_ID, generator, existingFileHelper, registryOps);
    }

    @Override
    protected void generate(Consumer<Builder> consumer) {
        builder(BlockFamilies.OAK_PLANKS, 1).build(consumer);
        builder(BlockFamilies.SPRUCE_PLANKS, 1).build(consumer);
        builder(BlockFamilies.BIRCH_PLANKS, 1).build(consumer);
        builder(BlockFamilies.JUNGLE_PLANKS, 1).build(consumer);
        builder(BlockFamilies.ACACIA_PLANKS, 1).build(consumer);
        builder(BlockFamilies.DARK_OAK_PLANKS, 1).build(consumer);
        builder(BlockFamilies.MANGROVE_PLANKS, 1).build(consumer);
        builder(BlockFamilies.COBBLESTONE, 2).build(consumer);
        builder(BlockFamilies.STONE, 2).build(consumer);
        builder(BlockFamilies.MUD_BRICKS, 2).build(consumer);
        builder(BlockFamilies.MOSSY_COBBLESTONE, 2).build(consumer);
        builder(BlockFamilies.COBBLED_DEEPSLATE, 2).build(consumer);
        builder(BlockFamilies.ANDESITE, 2).build(consumer);
        builder(BlockFamilies.DIORITE, 2).build(consumer);
        builder(BlockFamilies.GRANITE, 2).build(consumer);
        builder(BlockFamilies.SANDSTONE, 2).build(consumer);
        builder(BlockFamilies.RED_SANDSTONE, 2).build(consumer);
        builder(BlockFamilies.BRICKS, 3).build(consumer);
        builder(BlockFamilies.STONE_BRICK, 3).build(consumer);
        builder(BlockFamilies.MOSSY_STONE_BRICKS, 3).build(consumer);
        builder(BlockFamilies.POLISHED_DEEPSLATE, 3).build(consumer);
        builder(BlockFamilies.DEEPSLATE_BRICKS, 3).build(consumer);
        builder(BlockFamilies.DEEPSLATE_TILES, 3).build(consumer);
        builder(BlockFamilies.POLISHED_ANDESITE, 3).build(consumer);
        builder(BlockFamilies.POLISHED_DIORITE, 3).build(consumer);
        builder(BlockFamilies.POLISHED_GRANITE, 3).build(consumer);
        builder(BlockFamilies.SMOOTH_SANDSTONE, 3).build(consumer);
        builder(BlockFamilies.SMOOTH_RED_SANDSTONE, 3).build(consumer);
        builder(BlockFamilies.CUT_COPPER, 3).build(consumer);
        builder(BlockFamilies.EXPOSED_CUT_COPPER, 3).build(consumer);
        builder(BlockFamilies.WEATHERED_CUT_COPPER, 3).build(consumer);
        builder(BlockFamilies.OXIDIZED_CUT_COPPER, 3).build(consumer);
        builder(BlockFamilies.WAXED_CUT_COPPER, 3).build(consumer);
        builder(BlockFamilies.WAXED_EXPOSED_CUT_COPPER, 3).build(consumer);
        builder(BlockFamilies.WAXED_WEATHERED_CUT_COPPER, 3).build(consumer);
        builder(BlockFamilies.WAXED_OXIDIZED_CUT_COPPER, 3).build(consumer);
        builder(BlockFamilies.PRISMARINE, 4).build(consumer);
        builder(BlockFamilies.PRISMARINE_BRICKS, 4).build(consumer);
        builder(BlockFamilies.DARK_PRISMARINE, 4).build(consumer);
        builder(BlockFamilies.CRIMSON_PLANKS, 4).build(consumer);
        builder(BlockFamilies.WARPED_PLANKS, 4).build(consumer);
        builder(AMBlockFamilies.WITCHWOOD_PLANKS.get(), 4).build(consumer);
        builder(BlockFamilies.BLACKSTONE, 4).build(consumer);
        builder(BlockFamilies.QUARTZ, 4).build(consumer);
        builder(BlockFamilies.NETHER_BRICKS, 5).build(consumer);
        builder(BlockFamilies.RED_NETHER_BRICKS, 5).build(consumer);
        builder(BlockFamilies.POLISHED_BLACKSTONE, 5).build(consumer);
        builder(BlockFamilies.POLISHED_BLACKSTONE_BRICKS, 5).build(consumer);
        builder(BlockFamilies.SMOOTH_QUARTZ, 5).build(consumer);
        builder(BlockFamilies.END_STONE_BRICKS, 6).build(consumer);
        builder(BlockFamilies.PURPUR, 6).build(consumer);
    }
}
