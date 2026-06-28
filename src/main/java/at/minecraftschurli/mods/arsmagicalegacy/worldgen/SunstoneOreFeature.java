package at.minecraftschurli.mods.arsmagicalegacy.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.material.FluidState;

import java.util.function.Function;

public class SunstoneOreFeature extends Feature<OreConfiguration> {
    public SunstoneOreFeature() {
        super(OreConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<OreConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        OreConfiguration config = context.config();
        BlockPos origin = context.origin();
        int i = random.nextInt(config.size + 1);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int j = 0; j < i; j++) {
            offsetTargetPos(pos, random, origin, Math.min(j, 7));
            BlockState state = level.getBlockState(pos);
            for (OreConfiguration.TargetBlockState target : config.targetStates) {
                if (canPlaceOre(state, level::getBlockState, random, target, pos)) {
                    level.setBlock(pos, target.state, 2);
                    break;
                }
            }
        }
        return true;
    }

    private static boolean canPlaceOre(BlockState state, Function<BlockPos, BlockState> adjacentStateAccessor, RandomSource random, OreConfiguration.TargetBlockState targetState, BlockPos.MutableBlockPos pos) {
        if (!targetState.target.test(state, random)) return false;
        return checkNeighbors(adjacentStateAccessor, pos, s -> {
            FluidState fluidState = s.getFluidState();
            return fluidState.is(FluidTags.LAVA) && fluidState.isSource();
        });
    }

    private static void offsetTargetPos(BlockPos.MutableBlockPos mutablePos, RandomSource random, BlockPos pos, int magnitude) {
        mutablePos.setWithOffset(pos, getRandomRelativePlacement(random, magnitude), getRandomRelativePlacement(random, magnitude), getRandomRelativePlacement(random, magnitude));
    }

    private static int getRandomRelativePlacement(RandomSource random, int magnitude) {
        return Math.round((random.nextFloat() - random.nextFloat()) * (float) magnitude);
    }
}
