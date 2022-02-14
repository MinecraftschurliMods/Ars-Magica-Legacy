package com.github.minecraftschurlimods.arsmagicalegacy.common.level;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
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
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class AMFeatures {
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
        return feature(name, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ore.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepslateOre.get().defaultBlockState())), veinSize, airExposureDiscardChance));
    }

    /**
     * @param name                 The name of the feature.
     * @param feature              The configured feature to use.
     * @param veinCount            The amount of veins for this feature.
     * @param heightRangePlacement The height distribution of this feature.
     * @return A placed feature based on the given parameters.
     */
    public static PlacedFeature orePlacement(String name, ConfiguredFeature<?, ?> feature, int veinCount, HeightRangePlacement heightRangePlacement) {
        return placement(name, feature, CountPlacement.of(veinCount), InSquarePlacement.spread(), heightRangePlacement, BiomeFilter.biome());
    }

    /**
     * @param name   The name of the feature.
     * @param tries  How many tries for this feature will be performed.
     * @param flower The flower block to use.
     * @return A configured flower feature based on the given parameters.
     */
    public static ConfiguredFeature<?, ?> flower(String name, int tries, Supplier<? extends Block> flower) {
        return feature(name, Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(tries, Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(flower.get()))).onlyWhenEmpty()));
    }

    /**
     * @param name    The name of the feature.
     * @param feature The configured feature to use.
     * @param rarity  The rarity of this feature.
     * @return A placed feature based on the given parameters.
     */
    public static PlacedFeature flowerPlacement(String name, ConfiguredFeature<?, ?> feature, int rarity) {
        return placement(name, feature, RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }

    /**
     * @param name    The name of the feature.
     * @param log     The log block to use.
     * @param trunk   The trunk placer to use.
     * @param leaves  The leaves block to use.
     * @param foliage The foliage placer to use.
     * @param size    The feature size to use.
     * @return A configured tree feature based on the given parameters.
     */
    public static ConfiguredFeature<?, ?> tree(String name, Supplier<? extends Block> log, TrunkPlacer trunk, Supplier<? extends Block> leaves, FoliagePlacer foliage, FeatureSize size) {
        return feature(name, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log.get()), trunk, BlockStateProvider.simple(leaves.get()), foliage, size).ignoreVines().build());
    }

    /**
     * @param name    The name of the feature.
     * @param feature The configured feature to use.
     * @param sapling The sapling associated with this tree feature.
     * @return A placed feature based on the given parameters.
     */
    public static PlacedFeature treePlacement(String name, ConfiguredFeature<?, ?> feature, Supplier<? extends Block> sapling) {
        return PlacementUtils.register(ArsMagicaAPI.MOD_ID + ":" + name, feature.filteredByBlockSurvival(sapling.get()));
    }

    /**
     * @param name     The name of the feature.
     * @param feature  The configured feature to use.
     * @param modifier The tree placement modifier to use.
     * @param rarity   The rarity of this feature.
     * @param sapling  The sapling associated with this tree feature.
     * @return A placed feature based on the given parameters.
     */
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

    /**
     * Called for each biome, adds this mod's features to biomes.
     *
     * @param event The biome loading event provided by the event bus.
     */
    @Internal
    public static void biomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        ResourceLocation biome = event.getName();
        Biome.BiomeCategory category = event.getCategory();
        if (category != Biome.BiomeCategory.NETHER && category != Biome.BiomeCategory.THEEND) {
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CHIMERITE_PLACEMENT);
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, VINTEUM_PLACEMENT);
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, TOPAZ_PLACEMENT);
            if (category == Biome.BiomeCategory.MOUNTAIN) {
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, TOPAZ_EXTRA_PLACEMENT);
            }
            if (category == Biome.BiomeCategory.FOREST) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AUM_PLACEMENT);
            }
            if (category == Biome.BiomeCategory.JUNGLE || category == Biome.BiomeCategory.SWAMP) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CERUBLOSSOM_PLACEMENT);
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WAKEBLOOM_PLACEMENT);
            }
            if (category == Biome.BiomeCategory.DESERT || category == Biome.BiomeCategory.MESA) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DESERT_NOVA_PLACEMENT);
            }
            if (category == Biome.BiomeCategory.MOUNTAIN || category == Biome.BiomeCategory.EXTREME_HILLS || category == Biome.BiomeCategory.UNDERGROUND) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TARMA_ROOT_PLACEMENT);
            }
            if (biome != null && BiomeDictionary.getTypes(ResourceKey.create(Registry.BIOME_REGISTRY, biome)).contains(BiomeDictionary.Type.SPOOKY)) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WITCHWOOD_TREE_VEGETATION);
            }
        }
    }
}
