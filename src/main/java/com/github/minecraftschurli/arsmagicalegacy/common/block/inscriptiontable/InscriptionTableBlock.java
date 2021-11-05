package com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable;

import com.github.minecraftschurli.arsmagicalegacy.common.util.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class InscriptionTableBlock extends Block implements EntityBlock {
    public static final IntegerProperty TIER = IntegerProperty.create("tier", 0, 3);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Half> HALF = EnumProperty.create("half", Half.class);
    private static final VoxelShape LEFT_X = BlockUtil.joinShapes(box(0, 14, 0, 16, 16, 16), box(0, 13, 1, 1, 14, 16), box(15, 13, 1, 16, 14, 16), box(0, 12, 0, 16, 14, 1), box(6, 5, 3, 10, 11, 4), box(4, 3, 3, 12, 5, 4), box(5, 11, 2, 11, 14, 4), box(0, 0, 3, 4, 5, 5), box(12, 0, 3, 16, 5, 5), box(4, 3, 3, 12, 5, 4));
    private static final VoxelShape LEFT_Z = BlockUtil.joinShapes(box(0, 14, 0, 16, 16, 16), box(0, 13, 0, 15, 14, 1), box(0, 13, 15, 15, 14, 16), box(15, 12, 0, 16, 14, 16), box(12, 5, 6, 13, 11, 10), box(12, 3, 4, 13, 5, 12), box(12, 11, 5, 14, 14, 11), box(11, 0, 0, 13, 5, 4), box(11, 0, 12, 13, 5, 16), box(12, 3, 4, 13, 5, 12));
    private static final VoxelShape RIGHT_X = BlockUtil.joinShapes(box(0, 14, 0, 16, 16, 16), box(0, 13, 0, 1, 14, 15), box(15, 13, 0, 16, 14, 15), box(0, 12, 15, 16, 14, 16), box(6, 5, 12, 10, 11, 13), box(4, 3, 12, 12, 5, 13), box(5, 11, 12, 11, 14, 14), box(0, 0, 11, 4, 5, 13), box(12, 0, 11, 16, 5, 13), box(4, 3, 12, 12, 5, 13));
    private static final VoxelShape RIGHT_Z = BlockUtil.joinShapes(box(0, 14, 0, 16, 16, 16), box(1, 13, 0, 16, 14, 1), box(1, 13, 15, 16, 14, 16), box(0, 12, 0, 1, 14, 16), box(3, 5, 6, 4, 11, 10), box(3, 3, 4, 4, 5, 12), box(2, 11, 5, 4, 14, 11), box(3, 0, 0, 5, 5, 4), box(3, 0, 12, 5, 5, 16), box(3, 3, 4, 4, 5, 12));

    public InscriptionTableBlock() {
        super(BlockBehaviour.Properties.of(Material.WOOD).strength(2).lightLevel(state -> 1).noOcclusion());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TIER, FACING, HALF);
    }

    @NotNull
    @Override
    public PushReaction getPistonPushReaction(@NotNull BlockState pState) {
        return PushReaction.BLOCK;
    }

    @NotNull
    @Override
    public VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        switch (pState.getValue(FACING)) {
            case NORTH -> {
                return pState.getValue(HALF) == Half.LEFT ? LEFT_Z : RIGHT_Z;
            }
            case EAST -> {
                return pState.getValue(HALF) == Half.LEFT ? RIGHT_X : LEFT_X;
            }
            case SOUTH -> {
                return pState.getValue(HALF) == Half.LEFT ? RIGHT_Z : LEFT_Z;
            }
            case WEST -> {
                return pState.getValue(HALF) == Half.LEFT ? LEFT_X : RIGHT_X;
            }
            default -> throw new IllegalStateException("Unexpected value: " + pState.getValue(FACING));
        }
    }

    @Override
    public void onPlace(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pOldState, boolean pIsMoving) {
        if (pState.getValue(HALF) != Half.LEFT) {
            pLevel.setBlock(pPos.relative(pState.getValue(FACING).getClockWise()), pState.setValue(HALF, Half.LEFT), 3);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        if (pContext.getLevel().getBlockState(pContext.getClickedPos().relative(pContext.getHorizontalDirection().getClockWise())).canBeReplaced(pContext))
            return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection()).setValue(HALF, Half.RIGHT);
        return null;
    }

    @Override
    public void playerDestroy(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull BlockPos pPos, @NotNull BlockState pState, BlockEntity pBlockEntity, @NotNull ItemStack pTool) {
        super.playerDestroy(pLevel, pPlayer, pPos, Blocks.AIR.defaultBlockState(), pBlockEntity, pTool);
    }

    @Override
    public void playerWillDestroy(Level pLevel, @NotNull BlockPos pPos, BlockState pState, @NotNull Player pPlayer) {
        boolean left = pState.getValue(HALF) == Half.LEFT;
        BlockPos pos = left ? pPos.relative(pState.getValue(FACING).getCounterClockWise()) : pPos.relative(pState.getValue(FACING).getClockWise());
        BlockState state = pLevel.getBlockState(pos);
        if (state.getBlock() == this && (state.getValue(HALF) == Half.LEFT) != left) {
            pLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 35);
            pLevel.levelEvent(pPlayer, 2001, pos, Block.getId(state));
            ItemStack stack = pPlayer.getMainHandItem();
            if (!pLevel.isClientSide && !pPlayer.isCreative()) {
/*
                BlockPos bePos = pState.getValue(HALF) == Half.LEFT ? pPos.relative(pState.getValue(FACING)) : pPos;
                InscriptionTableTileEntity be = (InscriptionTableTileEntity) pLevel.getTileEntity(bePos);
*/
                Block.dropResources(pState, pLevel, pPos, null/*be*/, pPlayer, stack);
                Block.dropResources(state, pLevel, pos, null/*be*/, pPlayer, stack);
            }
        }
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return null;
    }

    public enum Half implements StringRepresentable {
        LEFT, RIGHT;

        @NotNull
        @Override
        public String getSerializedName() {
            return name().toLowerCase();
        }
    }
}
