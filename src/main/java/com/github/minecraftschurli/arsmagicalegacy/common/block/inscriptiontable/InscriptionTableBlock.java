package com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMStats;
import com.github.minecraftschurli.arsmagicalegacy.common.util.BlockUtil;
import com.github.minecraftschurli.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
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

    @Override
    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.BLOCK;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(FACING)) {
            case NORTH -> {
                return pState.getValue(HALF) == Half.LEFT ? RIGHT_Z : LEFT_Z;
            }
            case EAST -> {
                return pState.getValue(HALF) == Half.LEFT ? LEFT_X : RIGHT_X;
            }
            case SOUTH -> {
                return pState.getValue(HALF) == Half.LEFT ? LEFT_Z : RIGHT_Z;
            }
            case WEST -> {
                return pState.getValue(HALF) == Half.LEFT ? RIGHT_X : LEFT_X;
            }
            default -> throw new IllegalStateException("Unexpected value: " + pState.getValue(FACING));
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        if (pContext.getLevel().getBlockState(pContext.getClickedPos().relative(pContext.getHorizontalDirection().getCounterClockWise())).canBeReplaced(pContext))
            return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection()).setValue(HALF, Half.RIGHT);
        return null;
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (pState.getValue(HALF) != Half.LEFT) {
            pLevel.setBlock(pPos.relative(pState.getValue(FACING).getCounterClockWise()), pState.setValue(HALF, Half.LEFT), Block.UPDATE_ALL);
        }
    }

    @Override
    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool) {
        super.playerDestroy(pLevel, pPlayer, pPos, Blocks.AIR.defaultBlockState(), pBlockEntity, pTool);
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        boolean right = pState.getValue(HALF) == Half.RIGHT;
        BlockPos pos = right ? pPos.relative(pState.getValue(FACING).getCounterClockWise()) : pPos.relative(pState.getValue(FACING).getClockWise());
        BlockState state = pLevel.getBlockState(pos);
        if (state.getBlock() == this && (state.getValue(HALF) == Half.RIGHT) != right) {
            pLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 35);
            pLevel.levelEvent(pPlayer, 2001, pos, Block.getId(state));
            ItemStack stack = pPlayer.getMainHandItem();
            if (!pLevel.isClientSide() && !pPlayer.isCreative()) {
                Block.dropResources(pState, pLevel, pPos, null, pPlayer, stack);
                Block.dropResources(state, pLevel, pos, null, pPlayer, stack);
            }
        }
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.isSecondaryUseActive()) return InteractionResult.PASS;
        if (pLevel.isClientSide()) return InteractionResult.SUCCESS;
        ArsMagicaAPI.IArsMagicaAPI api = ArsMagicaAPI.get();
        if (!api.getMagicHelper().knowsMagic(pPlayer)) {
            pPlayer.sendMessage(new TranslatableComponent(TranslationConstants.MAGIC_UNKNOWN_MESSAGE), pPlayer.getUUID());
            return InteractionResult.FAIL;
        }
        if (pState.getValue(InscriptionTableBlock.HALF) == Half.LEFT) pPos = pPos.relative(pState.getValue(InscriptionTableBlock.FACING).getClockWise());
        NetworkHooks.openGui((ServerPlayer) pPlayer, ((InscriptionTableBlockEntity) pLevel.getBlockEntity(pPos)), pPos);
        pPlayer.awardStat(AMStats.INTERACT_WITH_INSCRIPTION_TABLE);
        return InteractionResult.CONSUME;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return pState.getValue(InscriptionTableBlock.HALF) == Half.RIGHT ? new InscriptionTableBlockEntity(pPos, pState) : null;
    }

    public enum Half implements StringRepresentable {
        LEFT, RIGHT;

        @Override
        public String getSerializedName() {
            return name().toLowerCase();
        }
    }
}
