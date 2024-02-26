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

import java.util.function.Predicate;

public class InlayBlock extends BaseRailBlock {
    private static final MapCodec<InlayBlock> CODEC = simpleCodec(InlayBlock::new);
    public static final EnumProperty<RailShape> SHAPE = EnumProperty.create("shape", RailShape.class, ((Predicate<RailShape>) RailShape::isAscending).negate());

    public InlayBlock(Properties pProperties) {
        super(false, pProperties);
        registerDefaultState(stateDefinition.any().setValue(SHAPE, RailShape.NORTH_SOUTH).setValue(WATERLOGGED, false));
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
    public boolean canMakeSlopes(BlockState pState, BlockGetter world, BlockPos pos) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(SHAPE, WATERLOGGED);
    }

    @Override
    public BlockState rotate(BlockState pState, LevelAccessor world, BlockPos pos, Rotation direction) {
        return switch (direction) {
            case CLOCKWISE_180 -> switch (pState.getValue(SHAPE)) {
                case SOUTH_EAST -> pState.setValue(SHAPE, RailShape.NORTH_WEST);
                case SOUTH_WEST -> pState.setValue(SHAPE, RailShape.NORTH_EAST);
                case NORTH_WEST -> pState.setValue(SHAPE, RailShape.SOUTH_EAST);
                case NORTH_EAST -> pState.setValue(SHAPE, RailShape.SOUTH_WEST);
                default -> pState;
            };
            case COUNTERCLOCKWISE_90 -> switch (pState.getValue(SHAPE)) {
                case SOUTH_EAST -> pState.setValue(SHAPE, RailShape.NORTH_EAST);
                case SOUTH_WEST -> pState.setValue(SHAPE, RailShape.SOUTH_EAST);
                case NORTH_WEST -> pState.setValue(SHAPE, RailShape.SOUTH_WEST);
                case NORTH_EAST -> pState.setValue(SHAPE, RailShape.NORTH_WEST);
                case NORTH_SOUTH -> pState.setValue(SHAPE, RailShape.EAST_WEST);
                case EAST_WEST -> pState.setValue(SHAPE, RailShape.NORTH_SOUTH);
                default -> pState;
            };
            case CLOCKWISE_90 -> switch (pState.getValue(SHAPE)) {
                case SOUTH_EAST -> pState.setValue(SHAPE, RailShape.SOUTH_WEST);
                case SOUTH_WEST -> pState.setValue(SHAPE, RailShape.NORTH_WEST);
                case NORTH_WEST -> pState.setValue(SHAPE, RailShape.NORTH_EAST);
                case NORTH_EAST -> pState.setValue(SHAPE, RailShape.SOUTH_EAST);
                case NORTH_SOUTH -> pState.setValue(SHAPE, RailShape.EAST_WEST);
                case EAST_WEST -> pState.setValue(SHAPE, RailShape.NORTH_SOUTH);
                default -> pState;
            };
            default -> pState;
        };
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        RailShape shape = pState.getValue(SHAPE);
        return switch (pMirror) {
            case LEFT_RIGHT -> switch (shape) {
                case SOUTH_EAST -> pState.setValue(SHAPE, RailShape.NORTH_EAST);
                case SOUTH_WEST -> pState.setValue(SHAPE, RailShape.NORTH_WEST);
                case NORTH_WEST -> pState.setValue(SHAPE, RailShape.SOUTH_WEST);
                case NORTH_EAST -> pState.setValue(SHAPE, RailShape.SOUTH_EAST);
                default -> super.mirror(pState, pMirror);
            };
            case FRONT_BACK -> switch (shape) {
                case SOUTH_EAST -> pState.setValue(SHAPE, RailShape.SOUTH_WEST);
                case SOUTH_WEST -> pState.setValue(SHAPE, RailShape.SOUTH_EAST);
                case NORTH_WEST -> pState.setValue(SHAPE, RailShape.NORTH_EAST);
                case NORTH_EAST -> pState.setValue(SHAPE, RailShape.NORTH_WEST);
                default -> super.mirror(pState, pMirror);
            };
            default -> super.mirror(pState, pMirror);
        };
    }
}
