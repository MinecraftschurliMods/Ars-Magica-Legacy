package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.SpellTransformationProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.data.ExistingFileHelper;

class AMSpellTransformationProvider extends SpellTransformationProvider {
    public AMSpellTransformationProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(ArsMagicaAPI.MOD_ID, generator, existingFileHelper);
    }

    @Override
    protected void createSpellTransformations() {
        addSpellTransformation("dirt_to_sand_drought", new TagMatchTest(BlockTags.DIRT), Blocks.SAND.defaultBlockState(), AMSpellParts.DROUGHT.get());
        addSpellTransformation("small_flowers_to_dead_bush_drought", new TagMatchTest(BlockTags.SMALL_FLOWERS), Blocks.DEAD_BUSH.defaultBlockState(), AMSpellParts.DROUGHT.get());
        addSpellTransformation("clay_to_sand_drought", new BlockMatchTest(Blocks.CLAY), Blocks.SAND.defaultBlockState(), AMSpellParts.DROUGHT.get());
        addSpellTransformation("gravel_to_sand_drought", new BlockMatchTest(Blocks.GRAVEL), Blocks.SAND.defaultBlockState(), AMSpellParts.DROUGHT.get());
        addSpellTransformation("stone_to_cobblestone_drought", new BlockMatchTest(Blocks.STONE), Blocks.COBBLESTONE.defaultBlockState(), AMSpellParts.DROUGHT.get());
        addSpellTransformation("infested_stone_to_infested_cobblestone_drought", new BlockMatchTest(Blocks.INFESTED_STONE), Blocks.INFESTED_COBBLESTONE.defaultBlockState(), AMSpellParts.DROUGHT.get());
        addSpellTransformation("stone_bricks_to_cracked_stone_bricks_drought", new BlockMatchTest(Blocks.STONE_BRICKS), Blocks.CRACKED_STONE_BRICKS.defaultBlockState(), AMSpellParts.DROUGHT.get());
        addSpellTransformation("infested_stone_bricks_to_infested_cracked_stone_bricks_drought", new BlockMatchTest(Blocks.INFESTED_STONE_BRICKS), Blocks.INFESTED_CRACKED_STONE_BRICKS.defaultBlockState(), AMSpellParts.DROUGHT.get());
        addSpellTransformation("deepslate_bricks_to_cracked_deepslate_bricks_drought", new BlockMatchTest(Blocks.DEEPSLATE_BRICKS), Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState(), AMSpellParts.DROUGHT.get());
        addSpellTransformation("deepslate_tiles_to_cracked_deepslate_tiles_drought", new BlockMatchTest(Blocks.DEEPSLATE_TILES), Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState(), AMSpellParts.DROUGHT.get());
        addSpellTransformation("nether_bricks_to_cracked_nether_bricks_drought", new BlockMatchTest(Blocks.NETHER_BRICKS), Blocks.CRACKED_NETHER_BRICKS.defaultBlockState(), AMSpellParts.DROUGHT.get());
        addSpellTransformation("polished_blackstone_bricks_to_cracked_polished_blackstone_bricks_drought", new BlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS), Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.defaultBlockState(), AMSpellParts.DROUGHT.get());
        addSpellTransformation("quartz_block_to_smooth_quartz_drought", new BlockMatchTest(Blocks.QUARTZ_BLOCK), Blocks.SMOOTH_QUARTZ.defaultBlockState(), AMSpellParts.DROUGHT.get());
        addSpellTransformation("sandstone_to_smooth_sandstone_drought", new BlockMatchTest(Blocks.SANDSTONE), Blocks.SMOOTH_SANDSTONE.defaultBlockState(), AMSpellParts.DROUGHT.get());
        addSpellTransformation("red_sandstone_to_smooth_red_sandstone_drought", new BlockMatchTest(Blocks.RED_SANDSTONE), Blocks.SMOOTH_RED_SANDSTONE.defaultBlockState(), AMSpellParts.DROUGHT.get());
    }
}
