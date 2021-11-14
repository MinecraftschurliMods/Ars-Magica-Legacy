package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.data.AltarStructureMaterialProvider;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;

public class AMAltarStructureMaterialProvider extends AltarStructureMaterialProvider {
    public AMAltarStructureMaterialProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void createStructureMaterials() {
        addStructureMaterial("oak_planks", Blocks.OAK_PLANKS, (StairBlock) Blocks.OAK_STAIRS, 1);
        addStructureMaterial("acacia_planks", Blocks.ACACIA_PLANKS, (StairBlock) Blocks.ACACIA_STAIRS, 1);
        addStructureMaterial("birch_planks", Blocks.BIRCH_PLANKS, (StairBlock) Blocks.BIRCH_STAIRS, 1);
        addStructureMaterial("dark_oak_planks", Blocks.DARK_OAK_PLANKS, (StairBlock) Blocks.DARK_OAK_STAIRS, 1);
        addStructureMaterial("spruce_planks", Blocks.SPRUCE_PLANKS, (StairBlock) Blocks.SPRUCE_STAIRS, 1);
        addStructureMaterial("jungle_planks", Blocks.JUNGLE_PLANKS, (StairBlock) Blocks.JUNGLE_STAIRS, 1);
        addStructureMaterial("stone_bricks", Blocks.STONE_BRICKS, (StairBlock) Blocks.STONE_BRICK_STAIRS, 1);
        addStructureMaterial("sandstone", Blocks.SANDSTONE, (StairBlock) Blocks.SANDSTONE_STAIRS, 1);
        addStructureMaterial("bricks", Blocks.BRICKS, (StairBlock) Blocks.BRICK_STAIRS, 2);
        addStructureMaterial("red_sandstone", Blocks.RED_SANDSTONE, (StairBlock) Blocks.RED_SANDSTONE_STAIRS, 2);
        addStructureMaterial("witchwood_planks", AMBlocks.WITCHWOOD_PLANKS.get(), AMBlocks.WITCHWOOD_STAIRS.get(), 3);
        addStructureMaterial("quartz", Blocks.QUARTZ_BLOCK, (StairBlock) Blocks.QUARTZ_STAIRS, 3);
        addStructureMaterial("nether_bricks", Blocks.NETHER_BRICKS, (StairBlock) Blocks.NETHER_BRICK_STAIRS, 3);
        addStructureMaterial("blackstone", Blocks.POLISHED_BLACKSTONE_BRICKS, (StairBlock) Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, 3);
        addStructureMaterial("purpur", Blocks.PURPUR_BLOCK, (StairBlock) Blocks.PURPUR_STAIRS, 4);

        addCapMaterial("glass", Blocks.GLASS, 1);
        addCapMaterial("coal", Blocks.COAL_BLOCK, 2);
        addCapMaterial("redstone", Blocks.REDSTONE_BLOCK, 3);
        addCapMaterial("iron", Blocks.IRON_BLOCK, 4);
        addCapMaterial("lapis", Blocks.LAPIS_BLOCK, 5);
        addCapMaterial("gold", Blocks.GOLD_BLOCK, 6);
        addCapMaterial("diamond", Blocks.DIAMOND_BLOCK, 7);
        addCapMaterial("emerald", Blocks.EMERALD_BLOCK, 8);
        addCapMaterial("moonstone", AMBlocks.MOONSTONE_BLOCK.get(), 9);
        addCapMaterial("sunstone", AMBlocks.SUNSTONE_BLOCK.get(), 10);
    }
}
