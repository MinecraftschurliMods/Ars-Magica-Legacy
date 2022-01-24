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

    public static ConfiguredFeature<?, ?> ore(String name, Supplier<? extends Block> ore, Supplier<? extends Block> deepslateOre, int veinSize, float airExposureDiscardChance) {
        return FeatureUtils.register(ArsMagicaAPI.MOD_ID + ":" + name, Feature.ORE.configured(new OreConfiguration(List.of(
                OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ore.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepslateOre.get().defaultBlockState())
        ), veinSize, airExposureDiscardChance)));
    }

    public static PlacedFeature orePlacement(String name, ConfiguredFeature<?, ?> feature, int veinCount, HeightRangePlacement heightRangePlacement) {
        return PlacementUtils.register(ArsMagicaAPI.MOD_ID + ":" + name, feature.placed(List.of(
                CountPlacement.of(veinCount),
                InSquarePlacement.spread(),
                heightRangePlacement,
                BiomeFilter.biome()
        )));
    }

    public static ConfiguredFeature<?, ?> flower(String name, int tries, Supplier<? extends Block> flower) {
        return FeatureUtils.register(ArsMagicaAPI.MOD_ID + ":" + name, Feature.RANDOM_PATCH.configured(FeatureUtils.simpleRandomPatchConfiguration(tries, Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(flower.get()))).onlyWhenEmpty())));
    }

    public static PlacedFeature flowerPlacement(String name, ConfiguredFeature<?, ?> feature, int rarity) {
        return PlacementUtils.register(name, feature.placed(
                RarityFilter.onAverageOnceEvery(rarity),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome())
        );
    }
}
