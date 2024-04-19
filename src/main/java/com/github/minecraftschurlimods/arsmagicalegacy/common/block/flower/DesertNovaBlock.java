package com.github.minecraftschurlimods.arsmagicalegacy.common.block.flower;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;

public class DesertNovaBlock extends FlowerBlock {
    public DesertNovaBlock() {
        super(() -> MobEffects.FIRE_RESISTANCE, 7, BlockBehaviour.Properties.ofLegacyCopy(Blocks.POPPY));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(Tags.Blocks.SAND);
    }
}
