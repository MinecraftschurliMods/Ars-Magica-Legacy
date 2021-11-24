package com.github.minecraftschurli.arsmagicalegacy.common.block;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMStats;
import com.github.minecraftschurli.arsmagicalegacy.common.util.BlockUtil;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class OcculusBlock extends HorizontalDirectionalBlock {
    private static final VoxelShape SOCKET = BlockUtil.joinShapes(
            box(0, 0, 0, 16, 1, 16),
            box(1.5, 1, 1.5, 14.5, 2, 14.5),
            box(3, 2, 3, 13, 3, 13),
            box(4.5, 3, 4.5, 11.5, 4, 11.5),
            box(6, 4, 6, 10, 8, 10),
            box(5, 7, 6, 6, 9, 10),
            box(10, 7, 6, 11, 9, 10),
            box(6, 7, 5, 10, 9, 6),
            box(6, 7, 10, 10, 9, 11),
            box(7, 8, 11, 9, 10, 12),
            box(11, 8, 7, 12, 10, 9),
            box(4, 8, 7, 5, 10, 9),
            box(7, 8, 4, 9, 10, 5)
    );
    private static final Map<Direction, VoxelShape> SHAPES = ImmutableMap.of(
            Direction.NORTH, BlockUtil.joinShapes(SOCKET,
                                                  box(5, 11, 6.5, 11, 12, 9.5),
                                                  box(5, 14, 6.5, 11, 15, 9.5),
                                                  box(5, 12, 6.5, 6.5, 14, 9.5),
                                                  box(9.5, 12, 6.5, 11, 14, 9.5),
                                                  box(4, 11, 7.5, 12, 15, 8.5),
                                                  box(5, 10, 7.5, 11, 16, 8.5),
                                                  box(6.5, 12, 8.5, 9.5, 14, 9.5)),
            Direction.EAST, BlockUtil.joinShapes(SOCKET,
                                                 box(6.5, 11, 5, 9.5, 12, 11),
                                                 box(6.5, 14, 5, 9.5, 15, 11),
                                                 box(6.5, 12, 9.5, 9.5, 14, 11),
                                                 box(6.5, 12, 5, 9.5, 14, 6.5),
                                                 box(7.5, 11, 4, 8.5, 15, 12),
                                                 box(7.5, 10, 5, 8.5, 16, 11),
                                                 box(6.5, 12, 6.5, 7.5, 14, 9.5)),
            Direction.SOUTH, BlockUtil.joinShapes(SOCKET,
                                                  box(5, 10, 7.5, 11, 16, 8.5),
                                                  box(4, 11, 7.5, 12, 15, 8.5),
                                                  box(5, 11, 6.5, 11, 12, 9.5),
                                                  box(5, 14, 6.5, 11, 15, 9.5),
                                                  box(5, 12, 6.5, 6.5, 14, 9.5),
                                                  box(9.5, 12, 6.5, 11, 14, 9.5),
                                                  box(6.5, 12, 6.5, 9.5, 14, 7.5)),
            Direction.WEST, BlockUtil.joinShapes(SOCKET,
                                                 box(6.5, 11, 5, 9.5, 12, 11),
                                                 box(6.5, 14, 5, 9.5, 15, 11),
                                                 box(6.5, 12, 5, 9.5, 14, 6.5),
                                                 box(6.5, 12, 9.5, 9.5, 14, 11),
                                                 box(7.5, 11, 4, 8.5, 15, 12),
                                                 box(7.5, 10, 5, 8.5, 16, 11),
                                                 box(8.5, 12, 6.5, 9.5, 14, 9.5))
    );

    /**
     * Creates a new OcculusBlock. Sets the properties and default state.
     */
    public OcculusBlock() {
        super(Properties.of(Material.STONE).explosionResistance(5).destroyTime(3));
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES.get(pState.getValue(FACING));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.isSecondaryUseActive()) return InteractionResult.PASS;
        if (pLevel.isClientSide()) {
            var api = ArsMagicaAPI.get();
            if (!api.getMagicHelper().knowsMagic(pPlayer)) {
                pPlayer.sendMessage(new TranslatableComponent("message.%s.prevent".formatted(ArsMagicaAPI.MOD_ID)), pPlayer.getUUID());
            } else {
                api.openOcculusGui(pPlayer);
            }
            return InteractionResult.SUCCESS;
        } else {
            pPlayer.awardStat(AMStats.INTERACT_WITH_OCCULUS);
            return InteractionResult.CONSUME;
        }
    }
}
