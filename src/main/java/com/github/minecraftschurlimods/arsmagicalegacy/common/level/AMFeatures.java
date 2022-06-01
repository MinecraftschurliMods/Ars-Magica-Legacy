package com.github.minecraftschurlimods.arsmagicalegacy.common.level;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
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
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class AMFeatures {
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> CHIMERITE_FEATURE;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> VINTEUM_FEATURE;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> TOPAZ_FEATURE;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> TOPAZ_EXTRA_FEATURE;
    public static Holder<PlacedFeature> CHIMERITE_PLACEMENT;
    public static Holder<PlacedFeature> VINTEUM_PLACEMENT;
    public static Holder<PlacedFeature> TOPAZ_PLACEMENT;
    public static Holder<PlacedFeature> TOPAZ_EXTRA_PLACEMENT;
    public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> AUM_FEATURE;
    public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> CERUBLOSSOM_FEATURE;
    public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> DESERT_NOVA_FEATURE;
    public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> TARMA_ROOT_FEATURE;
    public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> WAKEBLOOM_FEATURE;
    public static Holder<PlacedFeature> AUM_PLACEMENT;
    public static Holder<PlacedFeature> CERUBLOSSOM_PLACEMENT;
    public static Holder<PlacedFeature> DESERT_NOVA_PLACEMENT;
    public static Holder<PlacedFeature> TARMA_ROOT_PLACEMENT;
    public static Holder<PlacedFeature> WAKEBLOOM_PLACEMENT;
    public static Holder<ConfiguredFeature<TreeConfiguration, ?>> WITCHWOOD_TREE_FEATURE;
    public static Holder<PlacedFeature> WITCHWOOD_TREE_PLACEMENT;
    public static Holder<PlacedFeature> WITCHWOOD_TREE_VEGETATION;

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
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> ore(String name, Supplier<? extends Block> ore, Supplier<? extends Block> deepslateOre, int veinSize, float airExposureDiscardChance) {
        return feature(name, Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ore.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepslateOre.get().defaultBlockState())), veinSize, airExposureDiscardChance));
    }

    /**
     * @param name                 The name of the feature.
     * @param feature              The configured feature to use.
     * @param veinCount            The amount of veins for this feature.
     * @param heightRangePlacement The height distribution of this feature.
     * @return A placed feature based on the given parameters.
     */
    public static Holder<PlacedFeature> orePlacement(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, int veinCount, HeightRangePlacement heightRangePlacement) {
        return placement(name, feature, CountPlacement.of(veinCount), InSquarePlacement.spread(), heightRangePlacement, BiomeFilter.biome());
    }

    /**
     * @param name   The name of the feature.
     * @param tries  How many tries for this feature will be performed.
     * @param flower The flower block to use.
     * @return A configured flower feature based on the given parameters.
     */
    public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> flower(String name, int tries, Supplier<? extends Block> flower) {
        return feature(name, Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(flower.get())))));
    }

    /**
     * @param name    The name of the feature.
     * @param feature The configured feature to use.
     * @param rarity  The rarity of this feature.
     * @return A placed feature based on the given parameters.
     */
    public static Holder<PlacedFeature> flowerPlacement(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, int rarity) {
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
    public static Holder<ConfiguredFeature<TreeConfiguration, ?>> tree(String name, Supplier<? extends Block> log, TrunkPlacer trunk, Supplier<? extends Block> leaves, FoliagePlacer foliage, FeatureSize size) {
        return feature(name, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log.get()), trunk, BlockStateProvider.simple(leaves.get()), foliage, size).ignoreVines().build());
    }

    /**
     * @param name    The name of the feature.
     * @param feature The configured feature to use.
     * @param sapling The sapling associated with this tree feature.
     * @return A placed feature based on the given parameters.
     */
    public static Holder<PlacedFeature> treePlacement(String name, Holder<ConfiguredFeature<TreeConfiguration, ?>> feature, Supplier<? extends Block> sapling) {
        return PlacementUtils.register(ArsMagicaAPI.MOD_ID + ":" + name, feature, PlacementUtils.filteredByBlockSurvival(sapling.get()));
    }

    /**
     * @param name     The name of the feature.
     * @param feature  The configured feature to use.
     * @param modifier The tree placement modifier to use.
     * @param rarity   The rarity of this feature.
     * @param sapling  The sapling associated with this tree feature.
     * @return A placed feature based on the given parameters.
     */
    public static Holder<PlacedFeature> treeVegetation(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier modifier, int rarity, Supplier<? extends Block> sapling) {
        List<PlacementModifier> list = new ArrayList<>(VegetationPlacements.treePlacement(modifier, sapling.get()));
        list.add(RarityFilter.onAverageOnceEvery(rarity));
        return placement(name, feature, list.toArray(new PlacementModifier[0]));
    }

    private static <T extends FeatureConfiguration> Holder<ConfiguredFeature<T, ?>> feature(String name, Feature<T> feature, T configuration) {
        return FeatureUtils.register(ArsMagicaAPI.MOD_ID + ":" + name, feature, configuration);
    }

    private static Holder<PlacedFeature> placement(String name, Holder<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
        return PlacementUtils.register(ArsMagicaAPI.MOD_ID + ":" + name, feature, modifiers);
    }

    /**
     * Called for each biome, adds this mod's features to biomes.
     *
     * @param event The biome loading event provided by the event bus.
     */
    @Internal
    public static void biomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        MobSpawnSettingsBuilder spawn = event.getSpawns();
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
            if (category != Biome.BiomeCategory.MUSHROOM) {
                spawn.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AMEntities.MANA_CREEPER.get(), 2, 1, 1));
            }
            if (category == Biome.BiomeCategory.FOREST || category == Biome.BiomeCategory.JUNGLE || category == Biome.BiomeCategory.PLAINS || category == Biome.BiomeCategory.TAIGA) {
                spawn.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(AMEntities.DRYAD.get(), 2, 15, 25));
            }
        }
    }
}
