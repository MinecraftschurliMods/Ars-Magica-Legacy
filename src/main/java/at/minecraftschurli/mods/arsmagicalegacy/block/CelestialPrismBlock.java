package at.minecraftschurli.mods.arsmagicalegacy.block;

import at.minecraftschurli.mods.arsmagicalegacy.blockentity.CelestialPrismBlockEntity;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.mods.arsmagicalegacy.util.StringRepresentableEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class CelestialPrismBlock extends EtheriumGeneratorBlock {
    public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);
    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 16, 14);

    public CelestialPrismBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(PART, Part.LOWER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PART);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        for (int i = 0; i <= 1; i++) {
            BlockPos pos = context.getClickedPos().above(i);
            if (level.isOutsideBuildHeight(pos) || !level.getBlockState(pos).canBeReplaced(context)) return null;
        }
        return super.getStateForPlacement(context);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (state.getValue(PART) == Part.LOWER) {
            level.setBlockAndUpdate(pos.above(), state.setValue(PART, Part.UPPER));
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockPos otherPos = state.getValue(PART) == Part.LOWER ? pos.above() : pos.below();
        BlockState otherState = level.getBlockState(otherPos);
        if (!otherState.is(this)) return super.playerWillDestroy(level, pos, state, player);
        level.removeBlock(otherPos, false);
        spawnDestroyParticles(level, player, otherPos, otherState);
        level.gameEvent(GameEvent.BLOCK_DESTROY, otherPos, GameEvent.Context.of(player, otherState));
        if (!level.isClientSide()) {
            dropResources(otherState, level, otherPos, level.getBlockEntity(otherPos));
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(PART) == Part.LOWER ? new CelestialPrismBlockEntity(pos, state) : null;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == AMBlockEntities.CELESTIAL_PRISM.get() && state.getValue(PART) == Part.LOWER ? super.getTicker(level, state, type) : null;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    @Nullable
    public BlockEntity getBlockEntity(Level level, BlockPos pos, BlockState state) {
        if (!state.is(this)) return null;
        pos = switch (state.getValue(PART)) {
            case LOWER -> pos;
            case UPPER -> pos.below(1);
        };
        return level.getBlockState(pos).is(this) && level.getBlockState(pos).getValue(PART) == Part.LOWER && level.getBlockEntity(pos) instanceof CelestialPrismBlockEntity blockEntity ? blockEntity : null;
    }

    public enum Part implements StringRepresentableEnum {
        LOWER, UPPER
    }
}
