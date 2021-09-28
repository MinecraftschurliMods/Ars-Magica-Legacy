package com.github.minecraftschurli.arsmagicalegacy.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class WizardsChalkBlock extends Block {
    private static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 15);
    private static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;

    public WizardsChalkBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(VARIANT, 0).setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(VARIANT, HORIZONTAL_FACING);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return super.getPickBlock(state, target, world, pos, player);
    }

    @NotNull
    @Override
    public PushReaction getPistonPushReaction(@NotNull BlockState pState) {
        return PushReaction.DESTROY;
    }

    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return Block.box(0.125, 0, 0.125, 0.875, 0.02, 0.875);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(VARIANT, pContext.getLevel().random.nextInt(16)).setValue(HORIZONTAL_FACING, pContext.getHorizontalDirection());
    }
}
