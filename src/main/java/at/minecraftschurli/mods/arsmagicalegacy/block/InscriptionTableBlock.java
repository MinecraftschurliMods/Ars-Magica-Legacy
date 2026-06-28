package at.minecraftschurli.mods.arsmagicalegacy.block;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.blockentity.InscriptionTableBlockEntity;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import at.minecraftschurli.mods.arsmagicalegacy.util.StringRepresentableEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class InscriptionTableBlock extends Block implements EntityBlock {
    public static final IntegerProperty TIER = IntegerProperty.create("tier", 0, 3);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Half> HALF = EnumProperty.create("half", Half.class);
    private static final VoxelShape LEFT_X = AMUtil.joinShapes(
        box(0, 14, 0, 16, 16, 16),
        box(0, 13, 1, 1, 14, 16),
        box(15, 13, 1, 16, 14, 16),
        box(0, 12, 0, 16, 14, 1),
        box(6, 5, 3, 10, 11, 4),
        box(4, 3, 3, 12, 5, 4),
        box(5, 11, 2, 11, 14, 4),
        box(0, 0, 3, 4, 5, 5),
        box(12, 0, 3, 16, 5, 5),
        box(4, 3, 3, 12, 5, 4));
    private static final VoxelShape LEFT_Z = AMUtil.joinShapes(
        box(0, 14, 0, 16, 16, 16),
        box(0, 13, 0, 15, 14, 1),
        box(0, 13, 15, 15, 14, 16),
        box(15, 12, 0, 16, 14, 16),
        box(12, 5, 6, 13, 11, 10),
        box(12, 3, 4, 13, 5, 12),
        box(12, 11, 5, 14, 14, 11),
        box(11, 0, 0, 13, 5, 4),
        box(11, 0, 12, 13, 5, 16),
        box(12, 3, 4, 13, 5, 12));
    private static final VoxelShape RIGHT_X = AMUtil.joinShapes(
        box(0, 14, 0, 16, 16, 16),
        box(0, 13, 0, 1, 14, 15),
        box(15, 13, 0, 16, 14, 15),
        box(0, 12, 15, 16, 14, 16),
        box(6, 5, 12, 10, 11, 13),
        box(4, 3, 12, 12, 5, 13),
        box(5, 11, 12, 11, 14, 14),
        box(0, 0, 11, 4, 5, 13),
        box(12, 0, 11, 16, 5, 13),
        box(4, 3, 12, 12, 5, 13));
    private static final VoxelShape RIGHT_Z = AMUtil.joinShapes(
        box(0, 14, 0, 16, 16, 16),
        box(1, 13, 0, 16, 14, 1),
        box(1, 13, 15, 16, 14, 16),
        box(0, 12, 0, 1, 14, 16),
        box(3, 5, 6, 4, 11, 10),
        box(3, 3, 4, 4, 5, 12),
        box(2, 11, 5, 4, 14, 11),
        box(3, 0, 0, 5, 5, 4),
        box(3, 0, 12, 5, 5, 16),
        box(3, 3, 4, 4, 5, 12));

    public InscriptionTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(HALF) == Half.RIGHT ? new InscriptionTableBlockEntity(pos, state) : null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIER, FACING, HALF);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> state.getValue(HALF) == Half.LEFT ? RIGHT_Z : LEFT_Z;
            case EAST -> state.getValue(HALF) == Half.LEFT ? LEFT_X : RIGHT_X;
            case SOUTH -> state.getValue(HALF) == Half.LEFT ? LEFT_Z : RIGHT_Z;
            default -> state.getValue(HALF) == Half.LEFT ? RIGHT_X : LEFT_X;
        };
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();
        if (!context.getLevel().getBlockState(context.getClickedPos().relative(direction.getCounterClockWise())).canBeReplaced(context)) return null;
        BlockState state = defaultBlockState().setValue(FACING, direction).setValue(HALF, Half.RIGHT);
        ItemStack stack = context.getItemInHand();
        return stack.has(AMDataComponents.TIER) ? state.setValue(TIER, stack.get(AMDataComponents.TIER)) : state;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (state.getValue(HALF) == Half.RIGHT) {
            level.setBlockAndUpdate(pos.relative(state.getValue(FACING).getCounterClockWise()), state.setValue(HALF, Half.LEFT));
        }
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.has(AMDataComponents.TIER)) {
            state = state.setValue(TIER, stack.get(AMDataComponents.TIER));
        }
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        boolean right = isRight(state);
        BlockPos otherPos = right ? pos.relative(state.getValue(FACING).getCounterClockWise()) : pos.relative(state.getValue(FACING).getClockWise());
        BlockState otherState = level.getBlockState(otherPos);
        if (otherState.is(this) && isRight(otherState) != right) {
            spawnDestroyParticles(level, player, otherPos, otherState);
            if (!level.isClientSide()) {
                level.setBlock(otherPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_SUPPRESS_DROPS);
                if (!player.isCreative()) {
                    dropResources(otherState, level, otherPos, null, player, player.getMainHandItem());
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.isSecondaryUseActive()) return InteractionResult.PASS;
        if (!ArsMagicaApi.magicHelper().knowsMagic(player)) {
            player.sendOverlayMessage(AMTranslations.PREVENT_BLOCK);
            return InteractionResult.SUCCESS;
        }
        if (!isRight(state)) {
            pos = pos.relative(state.getValue(FACING).getClockWise());
        }
        if (level.getBlockEntity(pos) instanceof InscriptionTableBlockEntity blockEntity) {
            player.openMenu(blockEntity, pos);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
        ItemStack stack = super.getCloneItemStack(level, pos, state, includeData, player);
        int tier = state.getValue(TIER);
        if (tier > 0) {
            stack.set(AMDataComponents.TIER, tier);
        }
        return stack;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    private boolean isRight(BlockState state) {
        return state.getValue(HALF) == Half.RIGHT;
    }

    public enum Half implements StringRepresentableEnum {
        LEFT, RIGHT
    }
}
