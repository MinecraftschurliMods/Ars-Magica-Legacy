package com.github.minecraftschurlimods.arsmagicalegacy.common.block;

import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.BlackAuremBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class BlackAuremBlock extends EtheriumGeneratorBlock {
    private static final VoxelShape SHAPE = Block.box(6, 6, 6, 10, 10, 10);

    public BlackAuremBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BlackAuremBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    @Nullable
    public BlockEntity getBlockEntity(Level level, BlockPos pos, BlockState state) {
        return level.getBlockEntity(pos);
    }
}
