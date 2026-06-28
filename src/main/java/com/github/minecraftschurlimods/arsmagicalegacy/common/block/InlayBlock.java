package com.github.minecraftschurlimods.arsmagicalegacy.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;

public class InlayBlock extends BaseRailBlock {
    private static final MapCodec<InlayBlock> CODEC = simpleCodec(InlayBlock::new);
    public static final EnumProperty<RailShape> SHAPE = EnumProperty.create("shape", RailShape.class, e -> !e.isSlope());

    public InlayBlock(Properties properties) {
        super(false, properties);
        registerDefaultState(getStateDefinition().any().setValue(SHAPE, RailShape.NORTH_SOUTH).setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends BaseRailBlock> codec() {
        return CODEC;
    }

    @Override
    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, WATERLOGGED);
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 -> switch (state.getValue(SHAPE)) {
                case SOUTH_EAST -> state.setValue(SHAPE, RailShape.NORTH_WEST);
                case SOUTH_WEST -> state.setValue(SHAPE, RailShape.NORTH_EAST);
                case NORTH_WEST -> state.setValue(SHAPE, RailShape.SOUTH_EAST);
                case NORTH_EAST -> state.setValue(SHAPE, RailShape.SOUTH_WEST);
                default -> state;
            };
            case COUNTERCLOCKWISE_90 -> switch (state.getValue(SHAPE)) {
                case SOUTH_EAST -> state.setValue(SHAPE, RailShape.NORTH_EAST);
                case SOUTH_WEST -> state.setValue(SHAPE, RailShape.SOUTH_EAST);
                case NORTH_WEST -> state.setValue(SHAPE, RailShape.SOUTH_WEST);
                case NORTH_EAST -> state.setValue(SHAPE, RailShape.NORTH_WEST);
                case NORTH_SOUTH -> state.setValue(SHAPE, RailShape.EAST_WEST);
                case EAST_WEST -> state.setValue(SHAPE, RailShape.NORTH_SOUTH);
                default -> state;
            };
            case CLOCKWISE_90 -> switch (state.getValue(SHAPE)) {
                case SOUTH_EAST -> state.setValue(SHAPE, RailShape.SOUTH_WEST);
                case SOUTH_WEST -> state.setValue(SHAPE, RailShape.NORTH_WEST);
                case NORTH_WEST -> state.setValue(SHAPE, RailShape.NORTH_EAST);
                case NORTH_EAST -> state.setValue(SHAPE, RailShape.SOUTH_EAST);
                case NORTH_SOUTH -> state.setValue(SHAPE, RailShape.EAST_WEST);
                case EAST_WEST -> state.setValue(SHAPE, RailShape.NORTH_SOUTH);
                default -> state;
            };
            default -> super.rotate(state, level, pos, rotation);
        };
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        RailShape shape = state.getValue(SHAPE);
        return switch (mirror) {
            case LEFT_RIGHT -> switch (shape) {
                case SOUTH_EAST -> state.setValue(SHAPE, RailShape.NORTH_EAST);
                case SOUTH_WEST -> state.setValue(SHAPE, RailShape.NORTH_WEST);
                case NORTH_WEST -> state.setValue(SHAPE, RailShape.SOUTH_WEST);
                case NORTH_EAST -> state.setValue(SHAPE, RailShape.SOUTH_EAST);
                default -> super.mirror(state, mirror);
            };
            case FRONT_BACK -> switch (shape) {
                case SOUTH_EAST -> state.setValue(SHAPE, RailShape.SOUTH_WEST);
                case SOUTH_WEST -> state.setValue(SHAPE, RailShape.SOUTH_EAST);
                case NORTH_WEST -> state.setValue(SHAPE, RailShape.NORTH_EAST);
                case NORTH_EAST -> state.setValue(SHAPE, RailShape.NORTH_WEST);
                default -> super.mirror(state, mirror);
            };
            default -> super.mirror(state, mirror);
        };
    }

    @Override
    public boolean canMakeSlopes(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }
}
