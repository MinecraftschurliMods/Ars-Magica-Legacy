package com.github.minecraftschurlimods.arsmagicalegacy.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.Tags;

public class TarmaRootBlock extends FlowerBlock {
    public TarmaRootBlock() {
        super(MobEffects.DIG_SLOWDOWN, 7, Properties.copy(Blocks.POPPY));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Block block = level.getBlockState(pos.below()).getBlock();
        return Tags.Blocks.SAND.contains(block) || Tags.Blocks.DIRT.contains(block) || BlockTags.STONE_ORE_REPLACEABLES.contains(block) || BlockTags.DEEPSLATE_ORE_REPLACEABLES.contains(block) || block == Blocks.CLAY || block == Blocks.GRAVEL;
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.CAVE;
    }
}
