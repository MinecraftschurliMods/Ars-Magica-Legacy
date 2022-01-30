package com.github.minecraftschurlimods.arsmagicalegacy.common.level;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class AMFeatures {
    private AMFeatures() {
    }

    public static ConfiguredFeature<?, ?> CHIMERITE_FEATURE;
    public static ConfiguredFeature<?, ?> VINTEUM_FEATURE;
    public static ConfiguredFeature<?, ?> TOPAZ_FEATURE;
    public static ConfiguredFeature<?, ?> TOPAZ_EXTRA_FEATURE;
    public static PlacedFeature CHIMERITE_PLACEMENT;
    public static PlacedFeature VINTEUM_PLACEMENT;
    public static PlacedFeature TOPAZ_PLACEMENT;
    public static PlacedFeature TOPAZ_EXTRA_PLACEMENT;
    public static ConfiguredFeature<?, ?> AUM_FEATURE;
    public static ConfiguredFeature<?, ?> CERUBLOSSOM_FEATURE;
    public static ConfiguredFeature<?, ?> DESERT_NOVA_FEATURE;
    public static ConfiguredFeature<?, ?> TARMA_ROOT_FEATURE;
    public static ConfiguredFeature<?, ?> WAKEBLOOM_FEATURE;
    public static PlacedFeature AUM_PLACEMENT;
    public static PlacedFeature CERUBLOSSOM_PLACEMENT;
    public static PlacedFeature DESERT_NOVA_PLACEMENT;
    public static PlacedFeature TARMA_ROOT_PLACEMENT;
    public static PlacedFeature WAKEBLOOM_PLACEMENT;
    public static ConfiguredFeature<?, ?> WITCHWOOD_TREE_FEATURE;
    public static PlacedFeature WITCHWOOD_TREE_PLACEMENT;
    public static PlacedFeature WITCHWOOD_TREE_VEGETATION;

    public static ConfiguredFeature<?, ?> ore(String name, Supplier<? extends Block> ore, Supplier<? extends Block> deepslateOre, int veinSize, float airExposureDiscardChance) {
        return feature(name, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ore.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepslateOre.get().defaultBlockState())), veinSize, airExposureDiscardChance));
    }

    public static PlacedFeature orePlacement(String name, ConfiguredFeature<?, ?> feature, int veinCount, HeightRangePlacement heightRangePlacement) {
        return placement(name, feature, CountPlacement.of(veinCount), InSquarePlacement.spread(), heightRangePlacement, BiomeFilter.biome());
    }

    public static ConfiguredFeature<?, ?> flower(String name, int tries, Supplier<? extends Block> flower) {
        return feature(name, Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(tries, Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(flower.get()))).onlyWhenEmpty()));
    }

    public static PlacedFeature flowerPlacement(String name, ConfiguredFeature<?, ?> feature, int rarity) {
        return placement(name, feature, RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }

    public static ConfiguredFeature<?, ?> tree(String name, Supplier<? extends Block> log, TrunkPlacer trunk, Supplier<? extends Block> leaves, FoliagePlacer foliage, FeatureSize size) {
        return feature(name, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log.get()), trunk, BlockStateProvider.simple(leaves.get()), foliage, size).ignoreVines().build());
    }

    public static PlacedFeature treePlacement(String name, ConfiguredFeature<?, ?> feature, Supplier<? extends Block> sapling) {
        return PlacementUtils.register(ArsMagicaAPI.MOD_ID + ":" + name, feature.filteredByBlockSurvival(sapling.get()));
    }

    public static PlacedFeature treeVegetation(String name, ConfiguredFeature<?, ?> feature, PlacementModifier modifier, int rarity, Supplier<? extends Block> sapling) {
        List<PlacementModifier> list = new ArrayList<>(VegetationPlacements.treePlacement(modifier, sapling.get()));
        list.add(RarityFilter.onAverageOnceEvery(rarity));
        return placement(name, feature, list.toArray(new PlacementModifier[0]));
    }

    private static <T extends FeatureConfiguration> ConfiguredFeature<T, ?> feature(String name, Feature<T> feature, T configuration) {
        return FeatureUtils.register(ArsMagicaAPI.MOD_ID + ":" + name, feature.configured(configuration));
    }

    private static PlacedFeature placement(String name, ConfiguredFeature<?, ?> feature, PlacementModifier... modifiers) {
        return PlacementUtils.register(ArsMagicaAPI.MOD_ID + ":" + name, feature.placed(modifiers));
    }
}
