package at.minecraftschurli.mods.arsmagicalegacy.block;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WakebloomBlock extends FlowerBlock {
    public WakebloomBlock(Properties properties) {
        super(AMMobEffects.BURNOUT_REDUCTION, 7, properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return (level.getFluidState(pos).is(FluidTags.WATER) || state.is(BlockTags.ICE)) && level.getFluidState(pos.above()).isEmpty();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).getBlock() == Blocks.WATER;
    }
}
