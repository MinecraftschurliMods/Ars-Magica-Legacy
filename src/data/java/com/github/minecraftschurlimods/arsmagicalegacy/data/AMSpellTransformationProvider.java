package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.SpellTransformationProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellTransformation;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

class AMSpellTransformationProvider extends SpellTransformationProvider {
    public AMSpellTransformationProvider() {
        super(ArsMagicaAPI.MOD_ID);
    }

    @Override
    public void generate() {
        add("dirt_to_sand_drought", new SpellTransformation(new TagMatchTest(BlockTags.DIRT), Blocks.SAND.defaultBlockState(), AMSpellParts.DROUGHT.getId()));
        add("small_flowers_to_dead_bush_drought", new SpellTransformation(new TagMatchTest(BlockTags.SMALL_FLOWERS), Blocks.DEAD_BUSH.defaultBlockState(), AMSpellParts.DROUGHT.getId()));
        add("clay_to_sand_drought", new SpellTransformation(new BlockMatchTest(Blocks.CLAY), Blocks.SAND.defaultBlockState(), AMSpellParts.DROUGHT.getId()));
        add("gravel_to_sand_drought", new SpellTransformation(new BlockMatchTest(Blocks.GRAVEL), Blocks.SAND.defaultBlockState(), AMSpellParts.DROUGHT.getId()));
        add("stone_to_cobblestone_drought", new SpellTransformation(new BlockMatchTest(Blocks.STONE), Blocks.COBBLESTONE.defaultBlockState(), AMSpellParts.DROUGHT.getId()));
        add("infested_stone_to_infested_cobblestone_drought", new SpellTransformation(new BlockMatchTest(Blocks.INFESTED_STONE), Blocks.INFESTED_COBBLESTONE.defaultBlockState(), AMSpellParts.DROUGHT.getId()));
        add("stone_bricks_to_cracked_stone_bricks_drought", new SpellTransformation(new BlockMatchTest(Blocks.STONE_BRICKS), Blocks.CRACKED_STONE_BRICKS.defaultBlockState(), AMSpellParts.DROUGHT.getId()));
        add("infested_stone_bricks_to_infested_cracked_stone_bricks_drought", new SpellTransformation(new BlockMatchTest(Blocks.INFESTED_STONE_BRICKS), Blocks.INFESTED_CRACKED_STONE_BRICKS.defaultBlockState(), AMSpellParts.DROUGHT.getId()));
        add("deepslate_bricks_to_cracked_deepslate_bricks_drought", new SpellTransformation(new BlockMatchTest(Blocks.DEEPSLATE_BRICKS), Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState(), AMSpellParts.DROUGHT.getId()));
        add("deepslate_tiles_to_cracked_deepslate_tiles_drought", new SpellTransformation(new BlockMatchTest(Blocks.DEEPSLATE_TILES), Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState(), AMSpellParts.DROUGHT.getId()));
        add("nether_bricks_to_cracked_nether_bricks_drought", new SpellTransformation(new BlockMatchTest(Blocks.NETHER_BRICKS), Blocks.CRACKED_NETHER_BRICKS.defaultBlockState(), AMSpellParts.DROUGHT.getId()));
        add("polished_blackstone_bricks_to_cracked_polished_blackstone_bricks_drought", new SpellTransformation(new BlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS), Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.defaultBlockState(), AMSpellParts.DROUGHT.getId()));
        add("quartz_block_to_smooth_quartz_drought", new SpellTransformation(new BlockMatchTest(Blocks.QUARTZ_BLOCK), Blocks.SMOOTH_QUARTZ.defaultBlockState(), AMSpellParts.DROUGHT.getId()));
        add("sandstone_to_smooth_sandstone_drought", new SpellTransformation(new BlockMatchTest(Blocks.SANDSTONE), Blocks.SMOOTH_SANDSTONE.defaultBlockState(), AMSpellParts.DROUGHT.getId()));
        add("red_sandstone_to_smooth_red_sandstone_drought", new SpellTransformation(new BlockMatchTest(Blocks.RED_SANDSTONE), Blocks.SMOOTH_RED_SANDSTONE.defaultBlockState(), AMSpellParts.DROUGHT.getId()));
    }
}
