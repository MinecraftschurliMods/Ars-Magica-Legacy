package at.minecraftschurli.mods.arsmagicalegacy.api.plant;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/// Variant of [GrowthType] that uses a default implementation for growing.
/// The default implementation will check for an instance of [BonemealableBlock],
/// and otherwise check for any of the built-in age properties.
public interface BonemealableGrowthType extends GrowthType {
    @Override
    default boolean canGrow(GrowthContext context) {
        if (canHarvest(context)) return false;
        if (hasNonFullAge(context)) return true;
        BlockState state = context.state();
        return state.getBlock() instanceof BonemealableBlock block && block.isValidBonemealTarget(context.level(), context.pos(), state);
    }

    @Override
    default void grow(GrowthContext context) {
        BlockState state = context.state();
        if (state.getBlock() instanceof BonemealableBlock block) {
            ServerLevel level = context.level();
            block.performBonemeal(level, level.getRandom(), context.pos(), state);
        } else {
            increaseAge(context);
        }
    }

    /// @param context The [GrowthContext] to use.
    /// @return Whether the plant has an age property that is not full, i.e., can still grow.
    default boolean hasNonFullAge(GrowthContext context) {
        BlockState state = context.state();
        return state.hasProperty(BlockStateProperties.AGE_1) && state.getValue(BlockStateProperties.AGE_1) < 1
            || state.hasProperty(BlockStateProperties.AGE_2) && state.getValue(BlockStateProperties.AGE_2) < 2
            || state.hasProperty(BlockStateProperties.AGE_3) && state.getValue(BlockStateProperties.AGE_3) < 3
            || state.hasProperty(BlockStateProperties.AGE_4) && state.getValue(BlockStateProperties.AGE_4) < 4
            || state.hasProperty(BlockStateProperties.AGE_5) && state.getValue(BlockStateProperties.AGE_5) < 5
            || state.hasProperty(BlockStateProperties.AGE_7) && state.getValue(BlockStateProperties.AGE_7) < 7
            || state.hasProperty(BlockStateProperties.AGE_15) && state.getValue(BlockStateProperties.AGE_15) < 15
            || state.hasProperty(BlockStateProperties.AGE_25) && state.getValue(BlockStateProperties.AGE_25) < 25;
    }

    /// Helper method for increasing a plant's age property (if it has one) by one.
    ///
    /// @param context The [GrowthContext] to use.
    default void increaseAge(GrowthContext context) {
        ServerLevel level = context.level();
        BlockState state = context.state();
        if (state.hasProperty(BlockStateProperties.AGE_1) && state.getValue(BlockStateProperties.AGE_1) < 1) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_1, state.getValue(BlockStateProperties.AGE_1) + 1));
        } else if (state.hasProperty(BlockStateProperties.AGE_2) && state.getValue(BlockStateProperties.AGE_2) < 2) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_2, state.getValue(BlockStateProperties.AGE_2) + 1));
        } else if (state.hasProperty(BlockStateProperties.AGE_3) && state.getValue(BlockStateProperties.AGE_3) < 3) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_3, state.getValue(BlockStateProperties.AGE_3) + 1));
        } else if (state.hasProperty(BlockStateProperties.AGE_4) && state.getValue(BlockStateProperties.AGE_4) < 4) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_4, state.getValue(BlockStateProperties.AGE_4) + 1));
        } else if (state.hasProperty(BlockStateProperties.AGE_5) && state.getValue(BlockStateProperties.AGE_5) < 5) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_5, state.getValue(BlockStateProperties.AGE_5) + 1));
        } else if (state.hasProperty(BlockStateProperties.AGE_7) && state.getValue(BlockStateProperties.AGE_7) < 7) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_7, state.getValue(BlockStateProperties.AGE_7) + 1));
        } else if (state.hasProperty(BlockStateProperties.AGE_15) && state.getValue(BlockStateProperties.AGE_15) < 15) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_15, state.getValue(BlockStateProperties.AGE_15) + 1));
        } else if (state.hasProperty(BlockStateProperties.AGE_25) && state.getValue(BlockStateProperties.AGE_25) < 25) {
            level.setBlockAndUpdate(context.pos(), state.setValue(BlockStateProperties.AGE_25, state.getValue(BlockStateProperties.AGE_25) + 1));
        }
    }
}
