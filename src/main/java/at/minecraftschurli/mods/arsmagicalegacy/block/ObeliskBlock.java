package at.minecraftschurli.mods.arsmagicalegacy.block;

import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.ObeliskFuel;
import at.minecraftschurli.mods.arsmagicalegacy.blockentity.ObeliskBlockEntity;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.mods.arsmagicalegacy.util.StringRepresentableEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;
import org.jspecify.annotations.Nullable;

public class ObeliskBlock extends EtheriumGeneratorBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);

    public ObeliskBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false).setValue(PART, Part.LOWER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, LIT, PART);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        for (int i = 0; i <= 2; i++) {
            BlockPos pos = context.getClickedPos().above(i);
            if (level.isOutsideBuildHeight(pos) || !level.getBlockState(pos).canBeReplaced(context)) return null;
        }
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (state.getValue(PART) != Part.LOWER) return;
        level.setBlockAndUpdate(pos.above(), state.setValue(PART, Part.MIDDLE));
        level.setBlockAndUpdate(pos.above(2), state.setValue(PART, Part.UPPER));
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        Part part = state.getValue(PART);
        destroy(level, player, switch (part) {
            case UPPER -> pos.below();
            case MIDDLE -> pos.above();
            case LOWER -> pos.above(2);
        });
        destroy(level, player, switch (part) {
            case UPPER -> pos.below(2);
            case MIDDLE -> pos.below();
            case LOWER -> pos.above();
        });
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!ObeliskFuel.isFuel(stack)) return InteractionResult.CONSUME;
        ObeliskBlockEntity blockEntity = getBlockEntity(level, pos, state);
        if (blockEntity == null) return InteractionResult.CONSUME;
        ItemStack slotStack = blockEntity.getItem(0).copy();
        if (!slotStack.isEmpty() && !ItemStack.isSameItemSameComponents(slotStack, stack)) return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        if (slotStack.isEmpty()) {
            blockEntity.setItem(0, stack.copyWithCount(1));
        } else {
            slotStack.grow(1);
            blockEntity.setItem(0, slotStack);
        }
        stack.shrink(1);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
        return AbstractContainerMenu.getRedstoneSignalFromContainer(getBlockEntity(level, pos, state));
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(PART) == Part.LOWER ? new ObeliskBlockEntity(pos, state) : null;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == AMBlockEntities.OBELISK.get() && state.getValue(PART) == Part.LOWER ? super.getTicker(level, state, type) : null;
    }

    @Override
    @Nullable
    public ObeliskBlockEntity getBlockEntity(Level level, BlockPos pos, BlockState state) {
        if (!state.is(this)) return null;
        pos = switch (state.getValue(PART)) {
            case LOWER -> pos;
            case MIDDLE -> pos.below();
            case UPPER -> pos.below(2);
        };
        return level.getBlockState(pos).is(this) && level.getBlockState(pos).getValue(PART) == Part.LOWER && level.getBlockEntity(pos) instanceof ObeliskBlockEntity blockEntity ? blockEntity : null;
    }

    @SuppressWarnings("unused")
    @Nullable
    public static ResourceHandler<ItemResource> getItemHandler(Level level, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, @Nullable Direction direction) {
        if (!(state.getBlock() instanceof ObeliskBlock block)) return null;
        ObeliskBlockEntity obelisk = block.getBlockEntity(level, pos, state);
        return obelisk == null ? null : VanillaContainerWrapper.of(obelisk);
    }

    private void destroy(Level level, Player player, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!state.is(this)) return;
        level.removeBlock(pos, false);
        spawnDestroyParticles(level, player, pos, state);
        level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(player, state));
        if (!level.isClientSide()) {
            dropResources(state, level, pos, level.getBlockEntity(pos));
        }
    }

    public enum Part implements StringRepresentableEnum {
        LOWER, MIDDLE, UPPER
    }
}
