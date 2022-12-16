package com.github.minecraftschurlimods.arsmagicalegacy.common.block.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class CustomWallHangingSignBlock extends WallHangingSignBlock {

    public CustomWallHangingSignBlock(Properties properties, WoodType woodType) {
        super(properties, woodType);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_250745_, BlockState p_250905_) {
        return new CustomHangingSignBlockEntity(p_250745_, p_250905_);
    }
}
