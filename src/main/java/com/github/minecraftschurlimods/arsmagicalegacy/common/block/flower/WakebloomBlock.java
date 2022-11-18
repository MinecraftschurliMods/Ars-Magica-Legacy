package com.github.minecraftschurlimods.arsmagicalegacy.common.block.flower;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;

public class WakebloomBlock extends FlowerBlock {
    public WakebloomBlock() {
        super(MobEffects.REGENERATION, 7, BlockBehaviour.Properties.copy(Blocks.POPPY));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return (level.getFluidState(pos).getType() == Fluids.WATER || state.getMaterial() == Material.ICE) && level.getFluidState(pos.above()).getType() == Fluids.EMPTY;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).getBlock() == Blocks.WATER;
    }

    @Override
    public MobEffect getSuspiciousStewEffect() {
        return AMMobEffects.BURNOUT_REDUCTION.get();
    }
}
