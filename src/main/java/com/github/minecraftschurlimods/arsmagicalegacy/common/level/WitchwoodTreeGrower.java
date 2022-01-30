package com.github.minecraftschurlimods.arsmagicalegacy.common.level;

import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class WitchwoodTreeGrower extends AbstractMegaTreeGrower {
    @Nullable
    @Override
    protected ConfiguredFeature<?, ?> getConfiguredFeature(Random pRandom, boolean pLargeHive) {
        return null;
    }

    @Nullable
    @Override
    protected ConfiguredFeature<?, ?> getConfiguredMegaFeature(Random pRandom) {
        return AMFeatures.WITCHWOOD_TREE_FEATURE;
    }
}
