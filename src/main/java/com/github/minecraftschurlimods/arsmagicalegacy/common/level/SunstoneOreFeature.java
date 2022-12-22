package com.github.minecraftschurlimods.arsmagicalegacy.common.level;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.material.FluidState;

import java.util.Random;
import java.util.function.Function;

public class SunstoneOreFeature extends Feature<OreConfiguration> {
    public SunstoneOreFeature() {
        super(OreConfiguration.CODEC);
    }

    public static boolean canPlaceOre(BlockState pState, Function<BlockPos, BlockState> pAdjacentStateAccessor, Random pRandom, OreConfiguration.TargetBlockState pTargetState, BlockPos.MutableBlockPos pPos) {
        if (!pTargetState.target.test(pState, pRandom)) return false;
        return checkNeighbors(pAdjacentStateAccessor, pPos, state -> {
            FluidState fluidState = state.getFluidState();
            return fluidState.is(FluidTags.LAVA) && fluidState.isSource();
        });
    }

    private static void offsetTargetPos(BlockPos.MutableBlockPos pMutablePos, Random pRandom, BlockPos pPos, int pMagnitude) {
        pMutablePos.setWithOffset(pPos, getRandomRelativePlacement(pRandom, pMagnitude), getRandomRelativePlacement(pRandom, pMagnitude), getRandomRelativePlacement(pRandom, pMagnitude));
    }

    private static int getRandomRelativePlacement(Random pRandom, int pMagnitude) {
        return Math.round((pRandom.nextFloat() - pRandom.nextFloat()) * (float) pMagnitude);
    }

    @Override
    public boolean place(FeaturePlaceContext<OreConfiguration> pContext) {
        WorldGenLevel level = pContext.level();
        Random random = pContext.random();
        OreConfiguration config = pContext.config();
        BlockPos origin = pContext.origin();
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
}
