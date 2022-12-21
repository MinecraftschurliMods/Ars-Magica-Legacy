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
    public boolean place(FeaturePlaceContext<OreConfiguration> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        Random random = pContext.random();
        OreConfiguration oreconfiguration = pContext.config();
        BlockPos blockpos = pContext.origin();
        int i = random.nextInt(oreconfiguration.size + 1);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(int j = 0; j < i; ++j) {
            this.offsetTargetPos(blockpos$mutableblockpos, random, blockpos, Math.min(j, 7));
            BlockState blockstate = worldgenlevel.getBlockState(blockpos$mutableblockpos);

            for(OreConfiguration.TargetBlockState oreconfiguration$targetblockstate : oreconfiguration.targetStates) {
                if (canPlaceOre(blockstate, worldgenlevel::getBlockState, random, oreconfiguration$targetblockstate, blockpos$mutableblockpos)) {
                    worldgenlevel.setBlock(blockpos$mutableblockpos, oreconfiguration$targetblockstate.state, 2);
                    break;
                }
            }
        }

        return true;
    }

    private void offsetTargetPos(BlockPos.MutableBlockPos pMutablePos, Random pRandom, BlockPos pPos, int pMagnitude) {
        int i = this.getRandomPlacementInOneAxisRelativeToOrigin(pRandom, pMagnitude);
        int j = this.getRandomPlacementInOneAxisRelativeToOrigin(pRandom, pMagnitude);
        int k = this.getRandomPlacementInOneAxisRelativeToOrigin(pRandom, pMagnitude);
        pMutablePos.setWithOffset(pPos, i, j, k);
    }

    private int getRandomPlacementInOneAxisRelativeToOrigin(Random pRandom, int pMagnitude) {
        return Math.round((pRandom.nextFloat() - pRandom.nextFloat()) * (float)pMagnitude);
    }
    public static boolean canPlaceOre(BlockState pState, Function<BlockPos, BlockState> pAdjacentStateAccessor, Random pRandom, OreConfiguration.TargetBlockState pTargetState, BlockPos.MutableBlockPos pMatablePos) {
        if (!pTargetState.target.test(pState, pRandom)) {
            return false;
        } else {
            return checkNeighbors(pAdjacentStateAccessor, pMatablePos, blockState -> {
                FluidState fluidState = blockState.getFluidState();
                return fluidState.is(FluidTags.LAVA) && fluidState.isSource();
            });
        }
    }
}
