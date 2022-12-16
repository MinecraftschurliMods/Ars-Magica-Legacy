package com.github.minecraftschurlimods.arsmagicalegacy.common.level;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class WitchwoodTreeGrower extends AbstractMegaTreeGrower {
    private static final ResourceKey<ConfiguredFeature<?, ?>> WITCHWOOD_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ArsMagicaAPI.MOD_ID, "witchwood_tree"));

    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pLargeHive) {
        return null;
    }

    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource pRandom) {
        return WITCHWOOD_TREE;
    }
}
