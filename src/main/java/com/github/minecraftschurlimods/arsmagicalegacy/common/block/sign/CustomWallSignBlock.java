package com.github.minecraftschurlimods.arsmagicalegacy.common.block.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class CustomWallSignBlock extends WallSignBlock {
    public CustomWallSignBlock(WoodType woodType, Properties properties) {
        super(woodType, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CustomSignBlockEntity(pPos, pState);
    }
}
