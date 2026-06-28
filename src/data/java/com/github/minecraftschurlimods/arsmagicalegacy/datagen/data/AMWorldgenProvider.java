package com.github.minecraftschurlimods.arsmagicalegacy.datagen.data;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMWorldgen;
import com.github.minecraftschurlimods.arsmagicalegacy.common.worldgen.HolderSets;
import com.github.minecraftschurlimods.arsmagicalegacy.common.worldgen.MeteoriteFeature;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;
import java.util.OptionalInt;

@SuppressWarnings("SameParameterValue")
public final class AMWorldgenProvider {
    @SuppressWarnings("deprecation")
    public static void addConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap) {
        registerOre(bootstrap, AMWorldgen.CHIMERITE_ORE_CONFIGURED_FEATURE, AMBlocks.CHIMERITE_ORE, AMBlocks.DEEPSLATE_CHIMERITE_ORE, 7, 0);
        registerOre(bootstrap, AMWorldgen.TOPAZ_ORE_CONFIGURED_FEATURE, AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0.5f);
        registerOre(bootstrap, AMWorldgen.TOPAZ_ORE_EXTRA_CONFIGURED_FEATURE, AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0);
        registerOre(bootstrap, AMWorldgen.VINTEUM_ORE_CONFIGURED_FEATURE, AMBlocks.VINTEUM_ORE, AMBlocks.DEEPSLATE_VINTEUM_ORE, 10, 0);
        register(bootstrap, AMWorldgen.MOONSTONE_METEORITE_CONFIGURED_FEATURE, AMWorldgen.METEORITE.get(), new MeteoriteFeature.Configuration(
            Blocks.STONE.defaultBlockState(),
            AMBlocks.MOONSTONE_ORE.get().defaultBlockState(),
            5,
            3,
            0.1f));
        register(bootstrap, AMWorldgen.SUNSTONE_ORE_CONFIGURED_FEATURE, AMWorldgen.SUNSTONE_ORE.get(), new OreConfiguration(
            List.of(OreConfiguration.target(new TagMatchTest(BlockTags.BASE_STONE_NETHER), AMBlocks.SUNSTONE_ORE.get().defaultBlockState())),
            4,
            0f));
        register(bootstrap, AMWorldgen.WITCHWOOD_TREE_CONFIGURED_FEATURE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(AMBlocks.WITCHWOOD_LOG.get()),
            new DarkOakTrunkPlacer(9, 3, 1),
            BlockStateProvider.simple(AMBlocks.WITCHWOOD_LEAVES.get()),
            new DarkOakFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1)),
            new ThreeLayersFeatureSize(1, 2, 1, 1, 2, OptionalInt.empty())).ignoreVines().build());
        registerFlower(bootstrap, AMWorldgen.AUM_CONFIGURED_FEATURE, AMBlocks.AUM);
        registerFlower(bootstrap, AMWorldgen.CERUBLOSSOM_CONFIGURED_FEATURE, AMBlocks.CERUBLOSSOM);
        registerFlower(bootstrap, AMWorldgen.DESERT_NOVA_CONFIGURED_FEATURE, AMBlocks.DESERT_NOVA);
        registerFlower(bootstrap, AMWorldgen.TARMA_ROOT_CONFIGURED_FEATURE, AMBlocks.TARMA_ROOT);
        registerFlower(bootstrap, AMWorldgen.WAKEBLOOM_CONFIGURED_FEATURE, AMBlocks.WAKEBLOOM);
        register(bootstrap, AMWorldgen.LIQUID_ETHERIUM_LAKE_CONFIGURED_FEATURE, Feature.LAKE, new LakeFeature.Configuration(
            BlockStateProvider.simple(AMBlocks.LIQUID_ETHERIUM.get()),
            BlockStateProvider.simple(Blocks.STONE)));
    }

    public static void addPlacedFeatures(BootstrapContext<PlacedFeature> bootstrap) {
        registerOre(bootstrap, AMWorldgen.CHIMERITE_ORE_PLACED_FEATURE, AMWorldgen.CHIMERITE_ORE_CONFIGURED_FEATURE, 6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(16)));
        registerOre(bootstrap, AMWorldgen.TOPAZ_ORE_PLACED_FEATURE, AMWorldgen.TOPAZ_ORE_CONFIGURED_FEATURE, 7, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80)));
        registerOre(bootstrap, AMWorldgen.TOPAZ_ORE_EXTRA_PLACED_FEATURE, AMWorldgen.TOPAZ_ORE_EXTRA_CONFIGURED_FEATURE, 100, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480)));
        registerOre(bootstrap, AMWorldgen.VINTEUM_ORE_PLACED_FEATURE, AMWorldgen.VINTEUM_ORE_CONFIGURED_FEATURE, 8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(80)));
        register(bootstrap, AMWorldgen.MOONSTONE_METEORITE_PLACED_FEATURE, AMWorldgen.MOONSTONE_METEORITE_CONFIGURED_FEATURE, List.of(
            RarityFilter.onAverageOnceEvery(400),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            HeightRangePlacement.uniform(VerticalAnchor.absolute(56), VerticalAnchor.absolute(180)),
            BiomeFilter.biome()));
        registerOre(bootstrap, AMWorldgen.SUNSTONE_ORE_PLACED_FEATURE, AMWorldgen.SUNSTONE_ORE_CONFIGURED_FEATURE, 32, HeightRangePlacement.uniform(VerticalAnchor.absolute(31), VerticalAnchor.absolute(33)));
        register(bootstrap, AMWorldgen.TREES_WITCHWOOD_PLACED_FEATURE, AMWorldgen.WITCHWOOD_TREE_CONFIGURED_FEATURE, ImmutableList.<PlacementModifier>builder()
            .addAll(VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1f, 1), AMBlocks.WITCHWOOD_SAPLING.get()))
            .add(RarityFilter.onAverageOnceEvery(8))
            .build());
        registerFlower(bootstrap, AMWorldgen.AUM_PLACED_FEATURE, AMWorldgen.AUM_CONFIGURED_FEATURE, 32, 96);
        registerFlower(bootstrap, AMWorldgen.CERUBLOSSOM_PLACED_FEATURE, AMWorldgen.CERUBLOSSOM_CONFIGURED_FEATURE, 32, 96);
        registerFlower(bootstrap, AMWorldgen.DESERT_NOVA_PLACED_FEATURE, AMWorldgen.DESERT_NOVA_CONFIGURED_FEATURE, 32, 96);
        registerFlower(bootstrap, AMWorldgen.TARMA_ROOT_PLACED_FEATURE, AMWorldgen.TARMA_ROOT_CONFIGURED_FEATURE, 32, 96);
        registerFlower(bootstrap, AMWorldgen.WAKEBLOOM_PLACED_FEATURE, AMWorldgen.WAKEBLOOM_CONFIGURED_FEATURE, 32, 96);
        register(bootstrap, AMWorldgen.LIQUID_ETHERIUM_LAKE_PLACED_FEATURE, AMWorldgen.LIQUID_ETHERIUM_LAKE_CONFIGURED_FEATURE, List.of(
            RarityFilter.onAverageOnceEvery(50),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome()));
    }

    public static void addBiomeModifiers(BootstrapContext<BiomeModifier> bootstrap) {
        register(bootstrap,
            AMWorldgen.OVERWORLD_BIOME_MODIFIER,
            HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD),
            GenerationStep.Decoration.UNDERGROUND_ORES,
            AMWorldgen.CHIMERITE_ORE_PLACED_FEATURE, AMWorldgen.TOPAZ_ORE_PLACED_FEATURE, AMWorldgen.VINTEUM_ORE_PLACED_FEATURE);
        register(bootstrap,
            AMWorldgen.NETHER_BIOME_MODIFIER,
            HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_NETHER),
            GenerationStep.Decoration.UNDERGROUND_ORES,
            AMWorldgen.SUNSTONE_ORE_PLACED_FEATURE);
        register(bootstrap,
            AMWorldgen.NON_OCEAN_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.not(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OCEAN))),
            GenerationStep.Decoration.LOCAL_MODIFICATIONS,
            AMWorldgen.MOONSTONE_METEORITE_PLACED_FEATURE);
        register(bootstrap,
            AMWorldgen.PLAINS_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_PLAINS)),
            GenerationStep.Decoration.LAKES,
            AMWorldgen.LIQUID_ETHERIUM_LAKE_PLACED_FEATURE);
        register(bootstrap,
            AMWorldgen.FOREST_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_FOREST)),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            AMWorldgen.AUM_PLACED_FEATURE);
        register(bootstrap,
            AMWorldgen.MOUNTAIN_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_MOUNTAIN)),
            GenerationStep.Decoration.UNDERGROUND_ORES,
            AMWorldgen.TOPAZ_ORE_EXTRA_PLACED_FEATURE);
        register(bootstrap,
            AMWorldgen.SANDY_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_SANDY)),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            AMWorldgen.DESERT_NOVA_PLACED_FEATURE);
        register(bootstrap,
            AMWorldgen.SPOOKY_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_SPOOKY)),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            AMWorldgen.TREES_WITCHWOOD_PLACED_FEATURE);
        register(bootstrap,
            AMWorldgen.JUNGLE_OR_SWAMP_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.or(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_JUNGLE), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_SWAMP))),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            AMWorldgen.CERUBLOSSOM_PLACED_FEATURE, AMWorldgen.WAKEBLOOM_PLACED_FEATURE);
        register(bootstrap,
            AMWorldgen.MOUNTAIN_HILL_OR_UNDERGROUND_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.or(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_MOUNTAIN), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_HILL), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_UNDERGROUND))),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            AMWorldgen.TARMA_ROOT_PLACED_FEATURE);
        bootstrap.register(AMWorldgen.SPAWN_DRYADS_BIOME_MODIFIER, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, BiomeTags.IS_FOREST)),
            new Weighted<>(new MobSpawnSettings.SpawnerData(AMEntities.DRYAD.get(), 15, 25), 4)
        ));
        bootstrap.register(AMWorldgen.SPAWN_MANA_CREEPERS_BIOME_MODIFIER, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.not(HolderSets.biomeTag(bootstrap, Tags.Biomes.NO_DEFAULT_MONSTERS))),
            new Weighted<>(new MobSpawnSettings.SpawnerData(AMEntities.MANA_CREEPER.get(), 1, 4), 10)
        ));
    }

    /// Registers a [ConfiguredFeature].
    ///
    /// @param bootstrap The [BootstrapContext] to use.
    /// @param key       The [ResourceKey] to use.
    /// @param feature   The registered [Feature] type to use.
    /// @param config    The [FeatureConfiguration] to use.
    /// @param <F>       The exact type of the [Feature].
    /// @param <C>       The exact type of the [FeatureConfiguration].
    private static <F extends Feature<C>, C extends FeatureConfiguration> void register(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, C config) {
        bootstrap.register(key, new ConfiguredFeature<>(feature, config));
    }

    /// Registers a [ConfiguredFeature] for an ore.
    ///
    /// @param bootstrap                The [BootstrapContext] to use.
    /// @param key                      The [ResourceKey] to use.
    /// @param ore                      The ore block to place.
    /// @param deepslateOre             The deepslate ore block to place.
    /// @param veinSize                 The ore vein size.
    /// @param airExposureDiscardChance The chance that a vein will be discarded if it touches air.
    private static void registerOre(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap, ResourceKey<ConfiguredFeature<?, ?>> key, DeferredBlock<?> ore, DeferredBlock<?> deepslateOre, int veinSize, float airExposureDiscardChance) {
        register(bootstrap, key, Feature.ORE, new OreConfiguration(
            List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), ore.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), deepslateOre.get().defaultBlockState())),
            veinSize,
            airExposureDiscardChance
        ));
    }

    /// Registers a [ConfiguredFeature] for a flower.
    ///
    /// @param bootstrap The [BootstrapContext] to use.
    /// @param key       The [ResourceKey] to use.
    /// @param flower    The flower to place.
    private static void registerFlower(BootstrapContext<ConfiguredFeature<?, ?>> bootstrap, ResourceKey<ConfiguredFeature<?, ?>> key, DeferredBlock<?> flower) {
        register(bootstrap, key, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(flower.get())));
    }

    /// Registers a [PlacedFeature].
    ///
    /// @param bootstrap         The [BootstrapContext] to use.
    /// @param key               The [ResourceKey] to use.
    /// @param configuredFeature The [ConfiguredFeature] to use as a base.
    /// @param modifiers         The [PlacementModifier]s to apply to the [PlacedFeature].
    private static void register(BootstrapContext<PlacedFeature> bootstrap, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers) {
        bootstrap.register(key, new PlacedFeature(bootstrap.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(configuredFeature), modifiers));
    }

    /// Registers a [PlacedFeature] for an ore.
    ///
    /// @param bootstrap            The [BootstrapContext] to use.
    /// @param key                  The [ResourceKey] to use.
    /// @param configuredFeature    The [ConfiguredFeature] to use as a base.
    /// @param veinCount            How common veins should be.
    /// @param heightRangePlacement The height range distribution to use.
    private static void registerOre(BootstrapContext<PlacedFeature> bootstrap, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> configuredFeature, int veinCount, HeightRangePlacement heightRangePlacement) {
        register(bootstrap, key, configuredFeature, List.of(CountPlacement.of(veinCount), InSquarePlacement.spread(), heightRangePlacement, BiomeFilter.biome()));
    }

    /// Registers a [PlacedFeature] for a flower.
    ///
    /// @param bootstrap         The [BootstrapContext] to use.
    /// @param key               The [ResourceKey] to use.
    /// @param configuredFeature The [ConfiguredFeature] to use as a base.
    /// @param rarity            How rare patches should be.
    /// @param count             The amount of placement tries.
    private static void registerFlower(BootstrapContext<PlacedFeature> bootstrap, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> configuredFeature, int rarity, int count) {
        register(bootstrap, key, configuredFeature, List.of(RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome(), CountPlacement.of(count), RandomOffsetPlacement.ofTriangle(6, 2), BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)));
    }

    /// Registers a [BiomeModifiers.AddFeaturesBiomeModifier].
    ///
    /// @param bootstrap The [BootstrapContext] to use.
    /// @param key       The [ResourceKey] to use.
    /// @param biomes    A [HolderSet] of biomes where the features will be added.
    /// @param step      The generation step to use.
    /// @param features  The keys of the features to generate.
    @SafeVarargs
    private static void register(BootstrapContext<BiomeModifier> bootstrap, ResourceKey<BiomeModifier> key, HolderSet<Biome> biomes, GenerationStep.Decoration step, ResourceKey<PlacedFeature>... features) {
        bootstrap.register(key, new BiomeModifiers.AddFeaturesBiomeModifier(biomes, HolderSets.direct(bootstrap, Registries.PLACED_FEATURE, features), step));
    }
}
