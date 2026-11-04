package at.minecraftschurli.mods.arsmagicalegacy.datagen.data;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMWorldgen;
import at.minecraftschurli.mods.arsmagicalegacy.worldgen.HolderSets;
import at.minecraftschurli.mods.arsmagicalegacy.worldgen.MeteoriteFeature;
import at.minecraftschurli.mods.arsmagicalegacy.worldgen.WitchwoodForest;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
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
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
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
        HolderGetter<PlacedFeature> placements = bootstrap.lookup(Registries.PLACED_FEATURE);
        registerOre(bootstrap, AMWorldgen.CHIMERITE_ORE_FEATURE, AMBlocks.CHIMERITE_ORE, AMBlocks.DEEPSLATE_CHIMERITE_ORE, 7, 0);
        registerOre(bootstrap, AMWorldgen.TOPAZ_ORE_FEATURE, AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0.5f);
        registerOre(bootstrap, AMWorldgen.TOPAZ_ORE_EXTRA_FEATURE, AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0);
        registerOre(bootstrap, AMWorldgen.VINTEUM_ORE_FEATURE, AMBlocks.VINTEUM_ORE, AMBlocks.DEEPSLATE_VINTEUM_ORE, 10, 0);
        register(bootstrap, AMWorldgen.MOONSTONE_METEORITE_FEATURE, AMWorldgen.METEORITE.get(), new MeteoriteFeature.Configuration(
            Blocks.STONE.defaultBlockState(),
            AMBlocks.MOONSTONE_ORE.get().defaultBlockState(),
            5,
            3,
            0.1f));
        register(bootstrap, AMWorldgen.SUNSTONE_ORE_FEATURE, AMWorldgen.SUNSTONE_ORE.get(), new OreConfiguration(
            List.of(OreConfiguration.target(new TagMatchTest(BlockTags.BASE_STONE_NETHER), AMBlocks.SUNSTONE_ORE.get().defaultBlockState())),
            4,
            0f));
        register(bootstrap, AMWorldgen.WITCHWOOD_TREE_FEATURE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(AMBlocks.WITCHWOOD_LOG.get()),
            new DarkOakTrunkPlacer(9, 3, 1),
            BlockStateProvider.simple(AMBlocks.WITCHWOOD_LEAVES.get()),
            new DarkOakFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1)),
            new ThreeLayersFeatureSize(1, 2, 1, 1, 2, OptionalInt.empty())).ignoreVines().build());
        registerFlower(bootstrap, AMWorldgen.AUM_FEATURE, AMBlocks.AUM);
        registerFlower(bootstrap, AMWorldgen.CERUBLOSSOM_FEATURE, AMBlocks.CERUBLOSSOM);
        registerFlower(bootstrap, AMWorldgen.DESERT_NOVA_FEATURE, AMBlocks.DESERT_NOVA);
        registerFlower(bootstrap, AMWorldgen.TARMA_ROOT_FEATURE, AMBlocks.TARMA_ROOT);
        registerFlower(bootstrap, AMWorldgen.WAKEBLOOM_FEATURE, AMBlocks.WAKEBLOOM);
        register(bootstrap, AMWorldgen.LIQUID_ETHERIUM_LAKE_FEATURE, Feature.LAKE, new LakeFeature.Configuration(
            BlockStateProvider.simple(AMBlocks.LIQUID_ETHERIUM.get()),
            BlockStateProvider.simple(Blocks.STONE)));
        register(bootstrap, WitchwoodForest.MAGIC_FLOWERS_FEATURE, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(WeightedList.<BlockState>builder()
            .add(AMBlocks.AUM.get().defaultBlockState())
            .add(AMBlocks.CERUBLOSSOM.get().defaultBlockState())
            .add(AMBlocks.DESERT_NOVA.get().defaultBlockState())
            .add(AMBlocks.TARMA_ROOT.get().defaultBlockState())
            .add(AMBlocks.WAKEBLOOM.get().defaultBlockState()))));
        register(bootstrap, WitchwoodForest.TREES_FEATURE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
            new WeightedPlacedFeature(placements.getOrThrow(WitchwoodForest.DARK_FOREST_VEGETATION_PLACEMENT), 0.25f),
            new WeightedPlacedFeature(placements.getOrThrow(WitchwoodForest.TREES_FLOWER_FOREST_PLACEMENT), 0.25f),
            new WeightedPlacedFeature(placements.getOrThrow(WitchwoodForest.TREES_BIRCH_AND_OAK_LEAF_LITTER_PLACEMENT), 0.25f)
        ), placements.getOrThrow(WitchwoodForest.WITCHWOOD_PLACEMENT)));
        register(bootstrap, WitchwoodForest.BIOME_FLOWERS_FEATURE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
            new WeightedPlacedFeature(placements.getOrThrow(WitchwoodForest.FOREST_FLOWERS_PLACEMENT), 0.5f),
            new WeightedPlacedFeature(placements.getOrThrow(WitchwoodForest.FLOWER_FOREST_FLOWERS_PLACEMENT), 0.25f)
        ), placements.getOrThrow(WitchwoodForest.MAGIC_FLOWERS_PLACEMENT)));
        register(bootstrap, WitchwoodForest.FLOWERS_FEATURE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
            new WeightedPlacedFeature(placements.getOrThrow(WitchwoodForest.FLOWER_DEFAULT_PLACEMENT), 0.5f),
            new WeightedPlacedFeature(placements.getOrThrow(WitchwoodForest.FLOWER_FLOWER_FOREST_PLACEMENT), 0.25f)
        ), placements.getOrThrow(WitchwoodForest.MAGIC_FLOWERS_PLACEMENT)));
        register(bootstrap, WitchwoodForest.GRASS_FEATURE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(
            new WeightedPlacedFeature(placements.getOrThrow(WitchwoodForest.PATCH_GRASS_BADLANDS_PLACEMENT), 0.33f)
        ), placements.getOrThrow(WitchwoodForest.PATCH_GRASS_FOREST_PLACEMENT)));
    }

    public static void addPlacedFeatures(BootstrapContext<PlacedFeature> bootstrap) {
        registerOre(bootstrap, AMWorldgen.CHIMERITE_ORE_PLACEMENT, AMWorldgen.CHIMERITE_ORE_FEATURE, 6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(16)));
        registerOre(bootstrap, AMWorldgen.TOPAZ_ORE_PLACEMENT, AMWorldgen.TOPAZ_ORE_FEATURE, 7, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80)));
        registerOre(bootstrap, AMWorldgen.TOPAZ_ORE_EXTRA_PLACEMENT, AMWorldgen.TOPAZ_ORE_EXTRA_FEATURE, 100, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480)));
        registerOre(bootstrap, AMWorldgen.VINTEUM_ORE_PLACEMENT, AMWorldgen.VINTEUM_ORE_FEATURE, 8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(80)));
        register(bootstrap, AMWorldgen.MOONSTONE_METEORITE_PLACEMENT, AMWorldgen.MOONSTONE_METEORITE_FEATURE, List.of(
            RarityFilter.onAverageOnceEvery(400),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            HeightRangePlacement.uniform(VerticalAnchor.absolute(56), VerticalAnchor.absolute(180)),
            BiomeFilter.biome()));
        registerOre(bootstrap, AMWorldgen.SUNSTONE_ORE_PLACEMENT, AMWorldgen.SUNSTONE_ORE_FEATURE, 32, HeightRangePlacement.uniform(VerticalAnchor.absolute(31), VerticalAnchor.absolute(33)));
        register(bootstrap, AMWorldgen.TREES_WITCHWOOD_PLACEMENT, AMWorldgen.WITCHWOOD_TREE_FEATURE, treePlacement(PlacementUtils.countExtra(1, 0.1f, 1))
            .add(BiomeFilter.biome())
            .add(BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(AMBlocks.WITCHWOOD_SAPLING.get().defaultBlockState(), BlockPos.ZERO)))
            .add(RarityFilter.onAverageOnceEvery(8))
            .build());
        registerFlower(bootstrap, AMWorldgen.AUM_PLACEMENT, AMWorldgen.AUM_FEATURE, 32, 96);
        registerFlower(bootstrap, AMWorldgen.CERUBLOSSOM_PLACEMENT, AMWorldgen.CERUBLOSSOM_FEATURE, 32, 96);
        registerFlower(bootstrap, AMWorldgen.DESERT_NOVA_PLACEMENT, AMWorldgen.DESERT_NOVA_FEATURE, 32, 96);
        registerFlower(bootstrap, AMWorldgen.TARMA_ROOT_PLACEMENT, AMWorldgen.TARMA_ROOT_FEATURE, 32, 96);
        registerFlower(bootstrap, AMWorldgen.WAKEBLOOM_PLACEMENT, AMWorldgen.WAKEBLOOM_FEATURE, 32, 96);
        register(bootstrap, AMWorldgen.LIQUID_ETHERIUM_LAKE_PLACEMENT, AMWorldgen.LIQUID_ETHERIUM_LAKE_FEATURE, List.of(
            RarityFilter.onAverageOnceEvery(50),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome()));
        register(bootstrap, WitchwoodForest.MAGIC_FLOWERS_PLACEMENT, WitchwoodForest.MAGIC_FLOWERS_FEATURE, List.of(
            RarityFilter.onAverageOnceEvery(2),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            CountPlacement.of(96)));
        register(bootstrap, WitchwoodForest.WITCHWOOD_PLACEMENT, AMWorldgen.WITCHWOOD_TREE_FEATURE, treePlacement(PlacementUtils.countExtra(1, 0.1f, 1))
            .add(BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(AMBlocks.WITCHWOOD_SAPLING.get().defaultBlockState(), BlockPos.ZERO)))
            .add(RarityFilter.onAverageOnceEvery(2))
            .build());
        register(bootstrap, WitchwoodForest.DARK_FOREST_VEGETATION_PLACEMENT, VegetationFeatures.DARK_FOREST_VEGETATION, treePlacement(CountPlacement.of(16)).build());
        register(bootstrap, WitchwoodForest.TREES_FLOWER_FOREST_PLACEMENT, VegetationFeatures.TREES_FLOWER_FOREST, treePlacement(PlacementUtils.countExtra(6, 0.1F, 1)).build());
        register(bootstrap, WitchwoodForest.TREES_BIRCH_AND_OAK_LEAF_LITTER_PLACEMENT, VegetationFeatures.TREES_BIRCH_AND_OAK_LEAF_LITTER, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)).build());
        register(bootstrap, WitchwoodForest.FOREST_FLOWERS_PLACEMENT, VegetationFeatures.FOREST_FLOWERS, List.of(
            RarityFilter.onAverageOnceEvery(7),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            CountPlacement.of(ClampedInt.of(UniformInt.of(-1, 3), 0, 3))));
        register(bootstrap, WitchwoodForest.FLOWER_FOREST_FLOWERS_PLACEMENT, VegetationFeatures.FOREST_FLOWERS, List.of(
            RarityFilter.onAverageOnceEvery(7),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1))));
        register(bootstrap, WitchwoodForest.FLOWER_DEFAULT_PLACEMENT, VegetationFeatures.FLOWER_DEFAULT, List.of(
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP));
        register(bootstrap, WitchwoodForest.FLOWER_FLOWER_FOREST_PLACEMENT, VegetationFeatures.FLOWER_FLOWER_FOREST, List.of(
            CountPlacement.of(3),
            RarityFilter.onAverageOnceEvery(2),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            CountPlacement.of(96),
            RandomOffsetPlacement.ofTriangle(6, 2),
            BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)));
        register(bootstrap, WitchwoodForest.PATCH_GRASS_BADLANDS_PLACEMENT, VegetationFeatures.GRASS, List.of(
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            CountPlacement.of(32),
            RandomOffsetPlacement.ofTriangle(7, 3),
            BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)));
        register(bootstrap, WitchwoodForest.PATCH_GRASS_FOREST_PLACEMENT, VegetationFeatures.GRASS, List.of(
            CountPlacement.of(2),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            CountPlacement.of(32),
            RandomOffsetPlacement.ofTriangle(7, 3),
            BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)));
        register(bootstrap, WitchwoodForest.GEODES_PLACEMENT, CaveFeatures.AMETHYST_GEODE, List.of(
            RarityFilter.onAverageOnceEvery(8),
            InSquarePlacement.spread(),
            HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(50)),
            BiomeFilter.biome()));
        register(bootstrap, WitchwoodForest.METEORITES_PLACEMENT, AMWorldgen.MOONSTONE_METEORITE_FEATURE, List.of(
            RarityFilter.onAverageOnceEvery(200),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            HeightRangePlacement.uniform(VerticalAnchor.absolute(56), VerticalAnchor.absolute(180)),
            BiomeFilter.biome()));
        register(bootstrap, WitchwoodForest.TREES_PLACEMENT, WitchwoodForest.TREES_FEATURE, List.of(
            PlacementUtils.countExtra(8, 0.1f, 1),
            SurfaceWaterDepthFilter.forMaxDepth(0),
            PlacementUtils.HEIGHTMAP_OCEAN_FLOOR));
        register(bootstrap, WitchwoodForest.BIOME_FLOWERS_PLACEMENT, WitchwoodForest.BIOME_FLOWERS_FEATURE, List.of(
            RarityFilter.onAverageOnceEvery(7),
            PlacementUtils.HEIGHTMAP,
            CountPlacement.of(ClampedInt.of(UniformInt.of(-2, 2), 0, 2)),
            BiomeFilter.biome()));
        register(bootstrap, WitchwoodForest.FLOWERS_PLACEMENT, WitchwoodForest.FLOWERS_FEATURE, List.of(
            RarityFilter.onAverageOnceEvery(2),
            PlacementUtils.HEIGHTMAP,
            BiomeFilter.biome(),
            CountPlacement.of(48),
            RandomOffsetPlacement.ofTriangle(3, 1),
            BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)));
        register(bootstrap, WitchwoodForest.GRASS_PLACEMENT, WitchwoodForest.GRASS_FEATURE, List.of(
            PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
            BiomeFilter.biome(),
            RandomOffsetPlacement.ofTriangle(7, 3),
            BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)));
    }

    public static void addBiomes(BootstrapContext<Biome> bootstrap) {
        HolderGetter<PlacedFeature> placedFeatures = bootstrap.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = bootstrap.lookup(Registries.CONFIGURED_CARVER);
        MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(mobs);
        BiomeDefaultFeatures.commonSpawns(mobs);
        mobs.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
        mobs.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 4, 4));
        mobs.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(AMEntities.DRYAD.get(), 15, 25));
        mobs.addSpawn(MobCategory.MONSTER, 10, new MobSpawnSettings.SpawnerData(AMEntities.MANA_CREEPER.get(), 1, 4));
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generation);
        generation.addFeature(GenerationStep.Decoration.LAKES, AMWorldgen.LIQUID_ETHERIUM_LAKE_PLACEMENT);
        generation.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WitchwoodForest.METEORITES_PLACEMENT);
        generation.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WitchwoodForest.GEODES_PLACEMENT);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generation);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generation);
        BiomeDefaultFeatures.addDefaultSprings(generation);
        BiomeDefaultFeatures.addSurfaceFreezing(generation);
        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WitchwoodForest.TREES_PLACEMENT);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WitchwoodForest.BIOME_FLOWERS_PLACEMENT);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WitchwoodForest.FLOWERS_PLACEMENT);
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WitchwoodForest.GRASS_PLACEMENT);
        BiomeDefaultFeatures.addBushes(generation);
        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        BiomeDefaultFeatures.addLeafLitterPatch(generation);
        BiomeDefaultFeatures.addDefaultExtraVegetation(generation, true);
        bootstrap.register(WitchwoodForest.BIOME, new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.7f)
            .downfall(0.8f)
            .setAttribute(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(SoundEvents.MUSIC_BIOME_FOREST))
            .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x6699ff)
            .specialEffects(new BiomeSpecialEffects.Builder().waterColor(0x0a2a72).foliageColorOverride(0xb889cd).grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST).build())
            .mobSpawnSettings(mobs.build())
            .generationSettings(generation.build())
            .build());
    }

    public static void addBiomeModifiers(BootstrapContext<BiomeModifier> bootstrap) {
        register(bootstrap,
            AMWorldgen.OVERWORLD_BIOME_MODIFIER,
            HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD),
            GenerationStep.Decoration.UNDERGROUND_ORES,
            AMWorldgen.CHIMERITE_ORE_PLACEMENT, AMWorldgen.TOPAZ_ORE_PLACEMENT, AMWorldgen.VINTEUM_ORE_PLACEMENT);
        register(bootstrap,
            AMWorldgen.NETHER_BIOME_MODIFIER,
            HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_NETHER),
            GenerationStep.Decoration.UNDERGROUND_ORES,
            AMWorldgen.SUNSTONE_ORE_PLACEMENT);
        register(bootstrap,
            AMWorldgen.NON_OCEAN_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.not(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OCEAN))),
            GenerationStep.Decoration.LOCAL_MODIFICATIONS,
            AMWorldgen.MOONSTONE_METEORITE_PLACEMENT);
        register(bootstrap,
            AMWorldgen.PLAINS_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_PLAINS)),
            GenerationStep.Decoration.LAKES,
            AMWorldgen.LIQUID_ETHERIUM_LAKE_PLACEMENT);
        register(bootstrap,
            AMWorldgen.FOREST_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_FOREST)),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            AMWorldgen.AUM_PLACEMENT);
        register(bootstrap,
            AMWorldgen.MOUNTAIN_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_MOUNTAIN)),
            GenerationStep.Decoration.UNDERGROUND_ORES,
            AMWorldgen.TOPAZ_ORE_EXTRA_PLACEMENT);
        register(bootstrap,
            AMWorldgen.SANDY_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_SANDY)),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            AMWorldgen.DESERT_NOVA_PLACEMENT);
        register(bootstrap,
            AMWorldgen.SPOOKY_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_SPOOKY)),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            AMWorldgen.TREES_WITCHWOOD_PLACEMENT);
        register(bootstrap,
            AMWorldgen.JUNGLE_OR_SWAMP_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.or(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_JUNGLE), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_SWAMP))),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            AMWorldgen.CERUBLOSSOM_PLACEMENT, AMWorldgen.WAKEBLOOM_PLACEMENT);
        register(bootstrap,
            AMWorldgen.MOUNTAIN_HILL_OR_UNDERGROUND_BIOME_MODIFIER,
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.or(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_MOUNTAIN), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_HILL), HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_UNDERGROUND))),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            AMWorldgen.TARMA_ROOT_PLACEMENT);
        bootstrap.register(AMWorldgen.SPAWN_DRYADS_BIOME_MODIFIER, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.biomeTag(bootstrap, BiomeTags.IS_FOREST)),
            new Weighted<>(new MobSpawnSettings.SpawnerData(AMEntities.DRYAD.get(), 15, 25), 4)
        ));
        bootstrap.register(AMWorldgen.SPAWN_MANA_CREEPERS_BIOME_MODIFIER, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
            HolderSets.and(HolderSets.biomeTag(bootstrap, Tags.Biomes.IS_OVERWORLD), HolderSets.not(HolderSets.biomeTag(bootstrap, Tags.Biomes.NO_DEFAULT_MONSTERS))),
            new Weighted<>(new MobSpawnSettings.SpawnerData(AMEntities.MANA_CREEPER.get(), 1, 4), 10)
        ));
    }

    private static ImmutableList.Builder<PlacementModifier> treePlacement(PlacementModifier frequency) {
        return ImmutableList.<PlacementModifier>builder()
            .add(frequency)
            .add(InSquarePlacement.spread())
            .add(SurfaceWaterDepthFilter.forMaxDepth(0))
            .add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR);
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
