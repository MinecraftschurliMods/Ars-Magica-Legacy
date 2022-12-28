package com.github.minecraftschurlimods.arsmagicalegacy.common.level.meteorite;

import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class MeteoriteFeature extends Feature<MeteoriteConfiguration> {
    public MeteoriteFeature() {
        super(MeteoriteConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<MeteoriteConfiguration> pContext) {
        WorldGenLevel level = pContext.level();
        BlockPos origin = pContext.origin();
        RandomSource random = pContext.random();
        MeteoriteConfiguration config = pContext.config();
        while (origin.getY() > level.getMinBuildHeight() + config.height()) {
            if (!level.isEmptyBlock(origin.below())) {
                BlockState state = level.getBlockState(origin.below());
                if (isDirt(state) || isStone(state)) break;
            }
            origin = origin.below();
        }
        if (origin.getY() <= level.getMinBuildHeight() + config.height()) return false;
        for (int i = 0; i < config.height(); i++) {
            int x = random.nextInt(config.width());
            int y = random.nextInt(config.width());
            int z = random.nextInt(config.width());
            float f = (float) (x + y + z) * 0.333F + 0.5F;
            for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-x, -y, -z), origin.offset(x, y, z))) {
                if (pos.distSqr(origin) <= f * (0.95 + AMUtil.nextDouble(random, 0.1))) {
                    level.setBlock(pos, config.fluidState(), Block.UPDATE_CLIENTS);
                } else if (pos.distSqr(origin) <= f * f) {
                    level.setBlock(pos, random.nextDouble() < config.rareChance() ? config.rareState() : config.baseState(), Block.UPDATE_CLIENTS);
                }
            }
            origin = origin.offset(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
        }
        return true;
    }
}
