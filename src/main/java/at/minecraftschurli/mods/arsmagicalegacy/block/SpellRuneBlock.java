package at.minecraftschurli.mods.arsmagicalegacy.block;

import at.minecraftschurli.mods.arsmagicalegacy.blockentity.SpellRuneBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class SpellRuneBlock extends Block implements EntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final Map<Direction, VoxelShape> SHAPES = Map.of(
        Direction.DOWN, Block.box(0, 0, 0, 16, 0.5, 16),
        Direction.UP, Block.box(0, 15.5, 0, 16, 16, 16),
        Direction.NORTH, Block.box(0, 0, 0, 16, 16, 0.5),
        Direction.SOUTH, Block.box(0, 0, 15.5, 16, 16, 16),
        Direction.WEST, Block.box(0, 0, 0, 0.5, 16, 16),
        Direction.EAST, Block.box(15.5, 0, 0, 16, 16, 16)
    );

    public SpellRuneBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.DOWN));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getClickedFace().getOpposite());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SpellRuneBlockEntity(pos, state);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (level.getBlockEntity(pos) instanceof SpellRuneBlockEntity spellRune) {
            spellRune.cast(level, pos, entity);
        }
    }
}
