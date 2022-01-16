package com.github.minecraftschurlimods.arsmagicalegacy.common.level;

import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class WitchwoodTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ConfiguredFeature<?, ?> getConfiguredFeature(Random pRandom, boolean pLargeHive) {
        return AMFeatures.WITCHWOOD_TREE_FEATURE;
    }
}
