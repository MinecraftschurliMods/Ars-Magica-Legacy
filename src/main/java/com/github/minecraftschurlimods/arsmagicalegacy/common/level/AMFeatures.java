package com.github.minecraftschurlimods.arsmagicalegacy.common.level;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;
import java.util.function.Supplier;

public final class AMFeatures {
    public static ConfiguredFeature<?, ?> CHIMERITE_FEATURE;
    public static ConfiguredFeature<?, ?> VINTEUM_FEATURE;
    public static ConfiguredFeature<?, ?> TOPAZ_FEATURE;
    public static ConfiguredFeature<?, ?> TOPAZ_EXTRA_FEATURE;
    public static ConfiguredFeature<?, ?> AUM_FEATURE;
    public static ConfiguredFeature<?, ?> CERUBLOSSOM_FEATURE;
    public static ConfiguredFeature<?, ?> DESERT_NOVA_FEATURE;
    public static ConfiguredFeature<?, ?> TARMA_ROOT_FEATURE;
    public static ConfiguredFeature<?, ?> WAKEBLOOM_FEATURE;
    public static PlacedFeature CHIMERITE_PLACEMENT;
    public static PlacedFeature VINTEUM_PLACEMENT;
    public static PlacedFeature TOPAZ_PLACEMENT;
    public static PlacedFeature TOPAZ_EXTRA_PLACEMENT;
    public static PlacedFeature AUM_PLACEMENT;
    public static PlacedFeature CERUBLOSSOM_PLACEMENT;
    public static PlacedFeature DESERT_NOVA_PLACEMENT;
    public static PlacedFeature TARMA_ROOT_PLACEMENT;
    public static PlacedFeature WAKEBLOOM_PLACEMENT;

    private AMFeatures() {
    }

    /**
     * @param name                     The name of the feature.
     * @param ore                      The ore block to use.
     * @param deepslateOre             The deepslate ore block to use.
     * @param veinSize                 The vein size of this ore.
     * @param airExposureDiscardChance A 0..1 float that represents the probability of the feature being discarded if the vein touches air.
     * @return A configured ore feature based on the given parameters.
     */
    public static ConfiguredFeature<?, ?> ore(String name, Supplier<? extends Block> ore, Supplier<? extends Block> deepslateOre, int veinSize, float airExposureDiscardChance) {
        return FeatureUtils.register(ArsMagicaAPI.MOD_ID + ":" + name, Feature.ORE.configured(new OreConfiguration(List.of(
                OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ore.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepslateOre.get().defaultBlockState())
        ), veinSize, airExposureDiscardChance)));
    }

    /**
     * @param name                 The name of the feature.
     * @param feature              The configured feature to use.
     * @param veinCount            The amount of veins for this feature.
     * @param heightRangePlacement The height distribution of this feature.
     * @return A placed feature based on the given parameters.
     */
    public static PlacedFeature orePlacement(String name, ConfiguredFeature<?, ?> feature, int veinCount, HeightRangePlacement heightRangePlacement) {
        return PlacementUtils.register(ArsMagicaAPI.MOD_ID + ":" + name, feature.placed(List.of(
                CountPlacement.of(veinCount),
                InSquarePlacement.spread(),
                heightRangePlacement,
                BiomeFilter.biome()
        )));
    }

    /**
     * @param name   The name of the feature.
     * @param tries  How many tries for this feature will be performed.
     * @param flower The flower block to use.
     * @return A configured flower feature based on the given parameters.
     */
    public static ConfiguredFeature<?, ?> flower(String name, int tries, Supplier<? extends Block> flower) {
        return FeatureUtils.register(ArsMagicaAPI.MOD_ID + ":" + name, Feature.RANDOM_PATCH.configured(FeatureUtils.simpleRandomPatchConfiguration(tries, Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(flower.get()))).onlyWhenEmpty())));
    }

    /**
     * @param name    The name of the feature.
     * @param feature The configured feature to use.
     * @param rarity  The rarity of this feature.
     * @return A placed feature based on the given parameters.
     */
    public static PlacedFeature flowerPlacement(String name, ConfiguredFeature<?, ?> feature, int rarity) {
        return PlacementUtils.register(name, feature.placed(
                RarityFilter.onAverageOnceEvery(rarity),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome())
        );
    }
}
