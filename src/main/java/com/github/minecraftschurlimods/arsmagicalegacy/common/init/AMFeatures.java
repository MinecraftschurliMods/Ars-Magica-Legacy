package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.level.meteorite.MeteoriteConfiguration;
import com.github.minecraftschurlimods.arsmagicalegacy.common.level.meteorite.MeteoriteFeature;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
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
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;

@SuppressWarnings("SameParameterValue")
@NonExtendable
public interface AMFeatures {
    RegistryObject<MeteoriteFeature> METEORITE = AMRegistries.FEATURES.register("meteorite", MeteoriteFeature::new);

    RegistryObject<ConfiguredFeature<?, ?>> MOONSTONE_METEORITE       = meteorite("moonstone_meteorite", Blocks.STONE, AMBlocks.MOONSTONE_ORE, Blocks.WATER, 7, 5, 0.1f);
    RegistryObject<ConfiguredFeature<?, ?>> CHIMERITE_ORE             = ore("chimerite_ore", AMBlocks.CHIMERITE_ORE, AMBlocks.DEEPSLATE_CHIMERITE_ORE, 7, 0F);
    RegistryObject<ConfiguredFeature<?, ?>> VINTEUM_ORE               = ore("vinteum_ore", AMBlocks.VINTEUM_ORE, AMBlocks.DEEPSLATE_VINTEUM_ORE, 10, 0F);
    RegistryObject<ConfiguredFeature<?, ?>> TOPAZ_ORE                 = ore("topaz_ore", AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0.5F);
    RegistryObject<ConfiguredFeature<?, ?>> TOPAZ_ORE_EXTRA           = ore("topaz_ore_extra", AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0F);
    RegistryObject<ConfiguredFeature<?, ?>> AUM                       = flower("aum", 64, AMBlocks.AUM);
    RegistryObject<ConfiguredFeature<?, ?>> CERUBLOSSOM               = flower("cerublossom", 64, AMBlocks.CERUBLOSSOM);
    RegistryObject<ConfiguredFeature<?, ?>> DESERT_NOVA               = flower("desert_nova", 64, AMBlocks.DESERT_NOVA);
    RegistryObject<ConfiguredFeature<?, ?>> TARMA_ROOT                = flower("tarma_root", 64, AMBlocks.TARMA_ROOT);
    RegistryObject<ConfiguredFeature<?, ?>> WAKEBLOOM                 = flower("wakebloom", 64, AMBlocks.WAKEBLOOM);
    RegistryObject<ConfiguredFeature<?, ?>> WITCHWOOD_TREE            = tree("witchwood_tree", AMBlocks.WITCHWOOD_LOG, new DarkOakTrunkPlacer(9, 3, 1), AMBlocks.WITCHWOOD_LEAVES, new DarkOakFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1)), new ThreeLayersFeatureSize(1, 2, 1, 1, 2, OptionalInt.empty()));

    RegistryObject<PlacedFeature> MOONSTONE_METEORITE_PLACEMENT       = meteoritePlacement("moonstone_meteorite", MOONSTONE_METEORITE, 12, 56, 72);
    RegistryObject<PlacedFeature> CHIMERITE_ORE_PLACEMENT             = orePlacement("chimerite_ore", AMFeatures.CHIMERITE_ORE, 6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(16)));
    RegistryObject<PlacedFeature> VINTEUM_ORE_PLACEMENT               = orePlacement("vinteum_ore", AMFeatures.VINTEUM_ORE, 8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(80)));
    RegistryObject<PlacedFeature> TOPAZ_ORE_PLACEMENT                 = orePlacement("topaz_ore", AMFeatures.TOPAZ_ORE, 7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)));
    RegistryObject<PlacedFeature> TOPAZ_EXTRA_ORE_PLACEMENT           = orePlacement("topaz_ore_extra", AMFeatures.TOPAZ_ORE_EXTRA, 100, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480)));
    RegistryObject<PlacedFeature> AUM_PLACEMENT                       = flowerPlacement("aum", AMFeatures.AUM, 32);
    RegistryObject<PlacedFeature> CERUBLOSSOM_PLACEMENT               = flowerPlacement("cerublossom", AMFeatures.CERUBLOSSOM, 32);
    RegistryObject<PlacedFeature> DESERT_NOVA_PLACEMENT               = flowerPlacement("desert_nova", AMFeatures.DESERT_NOVA, 32);
    RegistryObject<PlacedFeature> TARMA_ROOT_PLACEMENT                = flowerPlacement("tarma_root", AMFeatures.TARMA_ROOT, 32);
    RegistryObject<PlacedFeature> WAKEBLOOM_PLACEMENT                 = flowerPlacement("wakebloom", AMFeatures.WAKEBLOOM, 32);
    RegistryObject<PlacedFeature> WITCHWOOD_TREE_PLACEMENT            = treePlacement("witchwood_tree", AMFeatures.WITCHWOOD_TREE, PlacementUtils.countExtra(1, 0.1F, 0), 8, AMBlocks.WITCHWOOD_SAPLING);

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }

    private static RegistryObject<ConfiguredFeature<?, ?>> ore(String name, Supplier<? extends Block> ore, Supplier<? extends Block> deepslateOre, int veinSize, float airExposureDiscardChance) {
        return feature(name, Feature.ORE, () -> new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ore.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepslateOre.get().defaultBlockState())), veinSize, airExposureDiscardChance));
    }

    private static RegistryObject<PlacedFeature> orePlacement(String name, RegistryObject<ConfiguredFeature<?, ?>> feature, int veinCount, HeightRangePlacement heightRangePlacement) {
        return placement(name, feature, List.of(CountPlacement.of(veinCount), InSquarePlacement.spread(), heightRangePlacement, BiomeFilter.biome()));
    }

    private static RegistryObject<ConfiguredFeature<?, ?>> flower(String name, int tries, Supplier<? extends Block> flower) {
        return feature(name, Feature.RANDOM_PATCH, () -> FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(flower.get())))));
    }

    private static RegistryObject<PlacedFeature> flowerPlacement(String name, RegistryObject<ConfiguredFeature<?, ?>> feature, int rarity) {
        return placement(name, feature, List.of(RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    }

    private static RegistryObject<ConfiguredFeature<?, ?>> tree(String name, Supplier<? extends Block> log, TrunkPlacer trunk, Supplier<? extends Block> leaves, FoliagePlacer foliage, FeatureSize size) {
        return feature(name, Feature.TREE, () -> new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log.get()), trunk, BlockStateProvider.simple(leaves.get()), foliage, size).ignoreVines().build());
    }

    private static RegistryObject<PlacedFeature> treePlacement(String name, RegistryObject<ConfiguredFeature<?, ?>> feature, PlacementModifier modifier, int rarity, Supplier<? extends Block> sapling) {
        return placement(name, feature, () -> {
            List<PlacementModifier> list = new ArrayList<>(VegetationPlacements.treePlacement(modifier, sapling.get()));
            list.add(RarityFilter.onAverageOnceEvery(rarity));
            return list;
        });
    }

    private static RegistryObject<ConfiguredFeature<?, ?>> meteorite(String name, Block baseState, Supplier<Block> rareState, Block fluidState, int meteoriteRadius, int meteoriteHeight, float rareChance) {
        return feature(name, METEORITE, () -> new MeteoriteConfiguration(baseState.defaultBlockState(), rareState.get().defaultBlockState(), fluidState.defaultBlockState(), meteoriteRadius, meteoriteHeight, rareChance));
    }

    private static RegistryObject<PlacedFeature> meteoritePlacement(String name, RegistryObject<ConfiguredFeature<?, ?>> feature, int rarity, int minHeight, int maxHeight) {
        return placement(name, feature, List.of(RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, HeightRangePlacement.uniform(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)), BiomeFilter.biome()));
    }

    private static <T extends FeatureConfiguration> RegistryObject<ConfiguredFeature<?, ?>> feature(String name, Feature<T> feature, Supplier<T> configuration) {
        return AMRegistries.CONFIGURED_FEATURES.register(name, () -> new ConfiguredFeature<>(feature, configuration.get()));
    }

    private static <T extends FeatureConfiguration> RegistryObject<ConfiguredFeature<?, ?>> feature(String name, RegistryObject<? extends Feature<T>> feature, Supplier<T> configuration) {
        return AMRegistries.CONFIGURED_FEATURES.register(name, () -> new ConfiguredFeature<>(feature.get(), configuration.get()));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static RegistryObject<PlacedFeature> placement(String name, RegistryObject<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> modifiers) {
        return AMRegistries.PLACED_FEATURES.register(name, () -> new PlacedFeature(feature.getHolder().get(), modifiers));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static RegistryObject<PlacedFeature> placement(String name, RegistryObject<ConfiguredFeature<?, ?>> feature, Supplier<List<PlacementModifier>> modifiers) {
        return AMRegistries.PLACED_FEATURES.register(name, () -> new PlacedFeature(feature.getHolder().get(), modifiers.get()));
    }

    /**
     * Called for each biome, adds this mod's features to biomes.
     *
     * @param event The biome loading event provided by the event bus.
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Internal
    static void biomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        MobSpawnSettingsBuilder spawn = event.getSpawns();
        ResourceLocation biome = event.getName();
        Biome.BiomeCategory category = event.getCategory();
        if (category != Biome.BiomeCategory.NETHER && category != Biome.BiomeCategory.THEEND) {
            if (category != Biome.BiomeCategory.OCEAN) {
                builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, MOONSTONE_METEORITE_PLACEMENT.getHolder().get());
            }
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CHIMERITE_ORE_PLACEMENT.getHolder().get());
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, VINTEUM_ORE_PLACEMENT.getHolder().get());
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, TOPAZ_ORE_PLACEMENT.getHolder().get());
            if (category == Biome.BiomeCategory.MOUNTAIN) {
                builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, TOPAZ_EXTRA_ORE_PLACEMENT.getHolder().get());
            }
            if (category == Biome.BiomeCategory.FOREST) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AUM_PLACEMENT.getHolder().get());
            }
            if (category == Biome.BiomeCategory.JUNGLE || category == Biome.BiomeCategory.SWAMP) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CERUBLOSSOM_PLACEMENT.getHolder().get());
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WAKEBLOOM_PLACEMENT.getHolder().get());
            }
            if (category == Biome.BiomeCategory.DESERT || category == Biome.BiomeCategory.MESA) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DESERT_NOVA_PLACEMENT.getHolder().get());
            }
            if (category == Biome.BiomeCategory.MOUNTAIN || category == Biome.BiomeCategory.EXTREME_HILLS || category == Biome.BiomeCategory.UNDERGROUND) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TARMA_ROOT_PLACEMENT.getHolder().get());
            }
            if (biome != null && BiomeDictionary.getTypes(ResourceKey.create(Registry.BIOME_REGISTRY, biome)).contains(BiomeDictionary.Type.SPOOKY)) {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WITCHWOOD_TREE_PLACEMENT.getHolder().get());
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
