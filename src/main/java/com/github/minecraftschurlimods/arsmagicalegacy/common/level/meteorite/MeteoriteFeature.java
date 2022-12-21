package com.github.minecraftschurlimods.arsmagicalegacy.common.level.meteorite;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Random;

public class MeteoriteFeature extends Feature<MeteoriteConfiguration> {
    public MeteoriteFeature() {
        super(MeteoriteConfiguration.CODEC);
    }

    private static void placeIfBreakable(Level level, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos).getDestroySpeed(level, pos) != -1) {
            level.setBlock(pos, state, Block.UPDATE_ALL);
        }
    }

    @Override
    public boolean place(FeaturePlaceContext<MeteoriteConfiguration> pContext) {
        Level level = pContext.level().getLevel();
        BlockPos origin = pContext.origin();
        Random random = pContext.random();
        MeteoriteConfiguration config = pContext.config();
        BlockState base = config.baseState();
        BlockState rare = config.rareState();
        BlockState fluid = config.fluidState();
        int meteoriteRadius = config.meteoriteRadius();
        int meteoriteHeight = config.meteoriteHeight();
        int craterRadius = config.craterRadius();
        int craterHeight = config.craterHeight();
        float rareChance = config.rareChance();
        boolean placeCrater = config.placeCrater();
        boolean placeLake = random.nextFloat() < config.placeLake();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        if (placeCrater) {
            for (int y = origin.getY() - craterHeight; y <= origin.getY(); y++) {
                pos.setY(y);
                for (int x = origin.getX() - craterRadius; x <= origin.getX() + craterRadius; x++) {
                    pos.setX(x);
                    for (int z = origin.getZ() - craterRadius; z <= origin.getZ() + craterRadius; z++) {
                        pos.setZ(z);
                        int dx = origin.getX() - x;
                        int dz = origin.getZ() - z;
                        if (y > origin.getY() - meteoriteRadius + 1 + (dx * dx + dz * dz) * 0.02) {
                            placeIfBreakable(level, pos, placeLake && y < origin.getY() && level.getBlockState(pos).getMaterial().isSolid() ? fluid : Blocks.AIR.defaultBlockState());
                        }
                    }
                }
            }
        }
        for (int y = origin.getY() - meteoriteHeight; y <= origin.getY(); y++) {
            pos.setY(y);
            for (int x = origin.getX() - meteoriteRadius; x <= origin.getX() + meteoriteRadius; x++) {
                pos.setX(x);
                for (int z = origin.getZ() - meteoriteRadius; z <= origin.getZ() + meteoriteRadius; z++) {
                    pos.setZ(z);
                    int dx = origin.getX() - x;
                    int dy = origin.getY() - y;
                    int dz = origin.getZ() - z;
                    if (dx * dx * 0.75 + dy * dy * (y > origin.getY() ? 1.5 : 0.75) + dz * dz * 0.75 < meteoriteRadius * meteoriteRadius) {
                        placeIfBreakable(level, pos, random.nextFloat() < rareChance ? rare : base);
                    }
                }
            }
        }
        if (placeCrater) {
            for (int y = origin.getY() - craterHeight; y <= origin.getY(); y++) {
                pos.setY(y);
                for (int x = origin.getX() - craterRadius; x <= origin.getX() + craterRadius; x++) {
                    pos.setX(x);
                    for (int z = origin.getZ() - craterRadius; z <= origin.getZ() + craterRadius; z++) {
                        pos.setZ(z);
                        BlockState state = level.getBlockState(pos);
                        if (state.getMaterial().isReplaceable()) {
                            if (!level.isEmptyBlock(pos.above())) {
                                level.setBlock(pos, level.getBlockState(pos.above()), Block.UPDATE_ALL);
                            } else {
                                int dx = origin.getX() - x;
                                int dy = origin.getY() - y;
                                int dz = origin.getZ() - z;
                                final double dist = dx * dx + dy * dy + dz * dz;
                                final BlockState state1 = level.getBlockState(pos.below());
                                if (!state1.getMaterial().isReplaceable()) {
                                    double height = craterRadius * (random.nextDouble() * 0.5 + 0.3) - Math.abs(dist - craterRadius * 1.5);
                                    if (!state1.isAir() && height > 0 && random.nextDouble() > 0.5) {
                                        placeIfBreakable(level, pos, random.nextFloat() < rareChance ? rare : base);
                                    }
                                }
                            }
                        } else if (level.isEmptyBlock(pos.above()) && random.nextDouble() > 0.5) {
                            int dx = origin.getX() - x;
                            int dy = origin.getY() - y;
                            int dz = origin.getZ() - z;
                            if (dx * dx + dy * dy + dz * dz < craterRadius * 1.5) {
                                placeIfBreakable(level, pos, random.nextFloat() < rareChance ? rare : base);
                            }
                        }
                    }
                }
            }
        }
        if (placeLake) {
            BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
            for (int y = origin.getY() - craterHeight; y <= origin.getY(); y++) {
                pos.setY(y);
                for (int x = origin.getX() - craterRadius; x <= origin.getX() + craterRadius; x++) {
                    pos.setX(x);
                    for (int z = origin.getZ() - craterRadius; z <= origin.getZ() + craterRadius; z++) {
                        pos.setZ(z);
                        int dx = origin.getX() - x;
                        int dz = origin.getZ() - z;
                        if (y > origin.getY() - meteoriteRadius + 1 + (dx * dx + dz * dz) * 0.02 && level.getBlockState(blockPos).isAir()) {
                            placeIfBreakable(level, blockPos, fluid);
                        }
                    }
                }
            }
        }
        return true;
    }
}
