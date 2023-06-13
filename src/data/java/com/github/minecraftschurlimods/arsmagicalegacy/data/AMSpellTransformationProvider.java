package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.SpellTransformationProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.data.ExistingFileHelper;

class AMSpellTransformationProvider extends SpellTransformationProvider {
    public AMSpellTransformationProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(ArsMagicaAPI.MOD_ID, generator, existingFileHelper, registryOps);
    }

    @Override
    protected void generate() {
        add(builder("dirt_to_sand_drought", new TagMatchTest(BlockTags.DIRT), Blocks.SAND.defaultBlockState(), AMSpellParts.DROUGHT));
        add(builder("small_flowers_to_dead_bush_drought", new TagMatchTest(BlockTags.SMALL_FLOWERS), Blocks.DEAD_BUSH.defaultBlockState(), AMSpellParts.DROUGHT));
        add(builder("clay_to_sand_drought", new BlockMatchTest(Blocks.CLAY), Blocks.SAND.defaultBlockState(), AMSpellParts.DROUGHT));
        add(builder("gravel_to_sand_drought", new BlockMatchTest(Blocks.GRAVEL), Blocks.SAND.defaultBlockState(), AMSpellParts.DROUGHT));
        add(builder("stone_to_cobblestone_drought", new BlockMatchTest(Blocks.STONE), Blocks.COBBLESTONE.defaultBlockState(), AMSpellParts.DROUGHT));
        add(builder("infested_stone_to_infested_cobblestone_drought", new BlockMatchTest(Blocks.INFESTED_STONE), Blocks.INFESTED_COBBLESTONE.defaultBlockState(), AMSpellParts.DROUGHT));
        add(builder("stone_bricks_to_cracked_stone_bricks_drought", new BlockMatchTest(Blocks.STONE_BRICKS), Blocks.CRACKED_STONE_BRICKS.defaultBlockState(), AMSpellParts.DROUGHT));
        add(builder("infested_stone_bricks_to_infested_cracked_stone_bricks_drought", new BlockMatchTest(Blocks.INFESTED_STONE_BRICKS), Blocks.INFESTED_CRACKED_STONE_BRICKS.defaultBlockState(), AMSpellParts.DROUGHT));
        add(builder("deepslate_bricks_to_cracked_deepslate_bricks_drought", new BlockMatchTest(Blocks.DEEPSLATE_BRICKS), Blocks.CRACKED_DEEPSLATE_BRICKS.defaultBlockState(), AMSpellParts.DROUGHT));
        add(builder("deepslate_tiles_to_cracked_deepslate_tiles_drought", new BlockMatchTest(Blocks.DEEPSLATE_TILES), Blocks.CRACKED_DEEPSLATE_TILES.defaultBlockState(), AMSpellParts.DROUGHT));
        add(builder("nether_bricks_to_cracked_nether_bricks_drought", new BlockMatchTest(Blocks.NETHER_BRICKS), Blocks.CRACKED_NETHER_BRICKS.defaultBlockState(), AMSpellParts.DROUGHT));
        add(builder("polished_blackstone_bricks_to_cracked_polished_blackstone_bricks_drought", new BlockMatchTest(Blocks.POLISHED_BLACKSTONE_BRICKS), Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.defaultBlockState(), AMSpellParts.DROUGHT));
        add(builder("quartz_block_to_smooth_quartz_drought", new BlockMatchTest(Blocks.QUARTZ_BLOCK), Blocks.SMOOTH_QUARTZ.defaultBlockState(), AMSpellParts.DROUGHT));
        add(builder("sandstone_to_smooth_sandstone_drought", new BlockMatchTest(Blocks.SANDSTONE), Blocks.SMOOTH_SANDSTONE.defaultBlockState(), AMSpellParts.DROUGHT));
        add(builder("red_sandstone_to_smooth_red_sandstone_drought", new BlockMatchTest(Blocks.RED_SANDSTONE), Blocks.SMOOTH_RED_SANDSTONE.defaultBlockState(), AMSpellParts.DROUGHT));
    }
}
