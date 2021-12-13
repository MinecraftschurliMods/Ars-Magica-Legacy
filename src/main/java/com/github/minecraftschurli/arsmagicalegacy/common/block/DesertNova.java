package com.github.minecraftschurli.arsmagicalegacy.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

public class DesertNova extends FlowerBlock {
    public DesertNova() {
        super(MobEffects.FIRE_RESISTANCE, 7, BlockBehaviour.Properties.copy(Blocks.POPPY));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return Tags.Blocks.SAND.contains(level.getBlockState(pos.below()).getBlock());
    }
}
