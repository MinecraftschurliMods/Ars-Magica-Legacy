package com.github.minecraftschurli.arsmagicalegacy.common.level;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.function.Supplier;

public class Ores {
    public static ConfiguredFeature<?, ?> CHIMERITE_FEATURE;
    public static ConfiguredFeature<?, ?> VINTEUM_FEATURE;
    public static ConfiguredFeature<?, ?> TOPAZ_FEATURE;
    public static ConfiguredFeature<?, ?> TOPAZ_EXTRA_FEATURE;
    public static PlacedFeature CHIMERITE_PLACEMENT;
    public static PlacedFeature VINTEUM_PLACEMENT;
    public static PlacedFeature TOPAZ_PLACEMENT;
    public static PlacedFeature TOPAZ_EXTRA_PLACEMENT;

    public static ConfiguredFeature<?, ?> ore(String name, Supplier<Block> ore, Supplier<Block> deepslateOre, int veinSize, float airExposureDiscardChance) {
        return FeatureUtils.register(ArsMagicaAPI.MOD_ID + ":" + name, Feature.ORE.configured(new OreConfiguration(List.of(
                OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ore.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepslateOre.get().defaultBlockState())
        ), veinSize, airExposureDiscardChance)));
    }

    public static PlacedFeature placement(String name, ConfiguredFeature<?, ?> feature, int veinCount, HeightRangePlacement heightRangePlacement) {
        return PlacementUtils.register(ArsMagicaAPI.MOD_ID + ":" + name, feature.placed(List.of(
                CountPlacement.of(veinCount),
                InSquarePlacement.spread(),
                heightRangePlacement,
                BiomeFilter.biome()
        )));
    }
}
