package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractDatapackRegistryProvider;
import com.google.common.base.Preconditions;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.Weight;
import net.minecraft.world.entity.EntityType;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.holdersets.AndHolderSet;
import net.neoforged.neoforge.registries.holdersets.NotHolderSet;
import net.neoforged.neoforge.registries.holdersets.OrHolderSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public abstract sealed class WorldgenProvider<T> extends AbstractDatapackRegistryProvider<T> {

    protected WorldgenProvider(String namespace, ResourceKey<? extends Registry<T>> registryKey) {
        super(registryKey, namespace);
    }

    public non-sealed abstract static class ConfiguredFeatureProvider extends WorldgenProvider<ConfiguredFeature<?, ?>> {

        protected ConfiguredFeatureProvider(String namespace) {
            super(namespace, Registries.CONFIGURED_FEATURE);
        }

        protected void ore(String name, Supplier<? extends Block> ore, Supplier<? extends Block> deepslateOre, int veinSize, float airExposureDiscardChance) {
            add(name, ore(ore, deepslateOre, veinSize, airExposureDiscardChance));
        }

        protected void flower(String name, int tries, Supplier<? extends Block> flower) {
            add(name, flower(tries, flower));
        }

        protected void tree(String name, Supplier<? extends Block> log, TrunkPlacer trunk, Supplier<? extends Block> leaves, FoliagePlacer foliage, FeatureSize size) {
            add(name, tree(log, trunk, leaves, foliage, size));
        }

        protected <C extends FeatureConfiguration> void feature(String name, Feature<C> feature, C config) {
            add(name, feature(feature, config));
        }

        /**
         * @param ore                      The ore block to use.
         * @param deepslateOre             The deepslate ore block to use.
         * @param veinSize                 The vein size of this ore.
         * @param airExposureDiscardChance A 0..1 float that represents the probability of the feature being discarded if the vein touches air.
         * @return A configured ore feature based on the given parameters.
         */
        private static ConfiguredFeature<OreConfiguration, ?> ore(Supplier<? extends Block> ore, Supplier<? extends Block> deepslateOre, int veinSize, float airExposureDiscardChance) {
            return feature(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), ore.get().defaultBlockState()), OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), deepslateOre.get().defaultBlockState())), veinSize, airExposureDiscardChance));
        }

        /**
         * @param tries  How many tries for this feature will be performed.
         * @param flower The flower block to use.
         * @return A configured flower feature based on the given parameters.
         */
        private static ConfiguredFeature<RandomPatchConfiguration, ?> flower(int tries, Supplier<? extends Block> flower) {
            return feature(Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(flower.get())))));
        }

        /**
         * @param log     The log block to use.
         * @param trunk   The trunk placer to use.
         * @param leaves  The leaves block to use.
         * @param foliage The foliage placer to use.
         * @param size    The feature size to use.
         * @return A configured tree feature based on the given parameters.
         */
        private static ConfiguredFeature<TreeConfiguration, ?> tree(Supplier<? extends Block> log, TrunkPlacer trunk, Supplier<? extends Block> leaves, FoliagePlacer foliage, FeatureSize size) {
            return feature(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log.get()), trunk, BlockStateProvider.simple(leaves.get()), foliage, size).ignoreVines().build());
        }

        private static <T extends FeatureConfiguration> ConfiguredFeature<T, ?> feature(Feature<T> feature, T configuration) {
            return new ConfiguredFeature<>(feature, configuration);
        }
    }

    public non-sealed abstract static class PlacedFeatureProvider extends WorldgenProvider<PlacedFeature> {

        protected PlacedFeatureProvider(String namespace) {
            super(namespace, Registries.PLACED_FEATURE);
        }

        protected void orePlacement(String name, Holder<ConfiguredFeature<?, ?>> feature, int veinCount, HeightRangePlacement heightRangePlacement) {
            add(name, orePlacement(feature, veinCount, heightRangePlacement));
        }

        protected void orePlacement(String name, int veinCount, HeightRangePlacement heightRangePlacement) {
            orePlacement(name, cf(name), veinCount, heightRangePlacement);
        }

        protected void flowerPlacement(String name, Holder<ConfiguredFeature<?, ?>> feature, int rarity) {
            add(name, flowerPlacement(feature, rarity));
        }

        protected void flowerPlacement(String name, int rarity) {
            flowerPlacement(name, cf(name), rarity);
        }

        protected void treePlacement(String name, Holder<ConfiguredFeature<?, ?>> feature, Supplier<? extends Block> sapling) {
            add(name, treePlacement(feature, sapling));
        }

        protected void treePlacement(String name, Supplier<? extends Block> sapling) {
            treePlacement(name, cf(name), sapling);
        }

        protected void treeVegetation(String name, Holder<ConfiguredFeature<?, ?>> feature, PlacementModifier modifier, int rarity, Supplier<? extends Block> sapling) {
            add(name, treeVegetation(feature, modifier, rarity, sapling));
        }

        protected void treeVegetation(String name, PlacementModifier modifier, int rarity, Supplier<? extends Block> sapling) {
            treeVegetation(name, cf(name), modifier, rarity, sapling);
        }

        protected void placement(String name, Holder<ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
            add(name, placement(feature, modifiers));
        }

        protected void placement(String name, PlacementModifier... modifiers) {
            add(name, placement(cf(name), modifiers));
        }

        /**
         * @param feature              The configured feature to use.
         * @param veinCount            The amount of veins for this feature.
         * @param heightRangePlacement The height distribution of this feature.
         * @return A placed feature based on the given parameters.
         */
        private static PlacedFeature orePlacement(Holder<ConfiguredFeature<?, ?>> feature, int veinCount, HeightRangePlacement heightRangePlacement) {
            return placement(feature, CountPlacement.of(veinCount), InSquarePlacement.spread(), heightRangePlacement, BiomeFilter.biome());
        }

        /**
         * @param feature The configured feature to use.
         * @param rarity  The rarity of this feature.
         * @return A placed feature based on the given parameters.
         */
        private static PlacedFeature flowerPlacement(Holder<ConfiguredFeature<?, ?>> feature, int rarity) {
            return placement(feature, RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        }

        /**
         * @param feature The configured feature to use.
         * @param sapling The sapling associated with this tree feature.
         * @return A placed feature based on the given parameters.
         */
        private static PlacedFeature treePlacement(Holder<ConfiguredFeature<?, ?>> feature, Supplier<? extends Block> sapling) {
            return new PlacedFeature(feature, List.of(PlacementUtils.filteredByBlockSurvival(sapling.get())));
        }

        /**
         * @param feature  The configured feature to use.
         * @param modifier The tree placement modifier to use.
         * @param rarity   The rarity of this feature.
         * @param sapling  The sapling associated with this tree feature.
         * @return A placed feature based on the given parameters.
         */
        private static PlacedFeature treeVegetation(Holder<ConfiguredFeature<?, ?>> feature, PlacementModifier modifier, int rarity, Supplier<? extends Block> sapling) {
            List<PlacementModifier> list = new ArrayList<>(VegetationPlacements.treePlacement(modifier, sapling.get()));
            list.add(RarityFilter.onAverageOnceEvery(rarity));
            return placement(feature, list.toArray(new PlacementModifier[0]));
        }

        protected static PlacedFeature placement(Holder<ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
            return new PlacedFeature(feature, Arrays.asList(modifiers));
        }

        protected Holder<ConfiguredFeature<?, ?>> cf(String name) {
            return holder(Registries.CONFIGURED_FEATURE, name);
        }
    }

    public non-sealed abstract static class BiomeModifierProvider extends WorldgenProvider<BiomeModifier> {

        protected BiomeModifierProvider(String namespace) {
            super(namespace, NeoForgeRegistries.Keys.BIOME_MODIFIERS);
        }

        protected void addSpawn(EntityType<?> type, int weight, int minCount, int maxCount, HolderSet<Biome> biomes) {
            addSpawn(BuiltInRegistries.ENTITY_TYPE.getKey(type).getPath(), biomes, new MobSpawnSettings.SpawnerData(type, weight, minCount, maxCount));
        }

        protected void addSpawn(EntityType<?> type, Weight weight, int minCount, int maxCount, HolderSet<Biome> biomes) {
            addSpawn(BuiltInRegistries.ENTITY_TYPE.getKey(type).getPath(), biomes, new MobSpawnSettings.SpawnerData(type, weight, minCount, maxCount));
        }

        protected void addSpawn(String name, HolderSet<Biome> biomes, MobSpawnSettings.SpawnerData spawnerData) {
            add(name, addSpawn(biomes, spawnerData));
        }

        private BiomeModifier addSpawn(HolderSet<Biome> biomes, MobSpawnSettings.SpawnerData spawnerData) {
            return BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(biomes, spawnerData);
        }

        protected void addVegetation(String name, HolderSet<Biome> biomes) {
            addFeature(name, biomes, GenerationStep.Decoration.VEGETAL_DECORATION);
        }

        protected void addOre(String name, HolderSet<Biome> biomes) {
            addFeature(name, biomes, GenerationStep.Decoration.UNDERGROUND_ORES);
        }

        protected void addFeature(String name, HolderSet<Biome> biomes, GenerationStep.Decoration step) {
            addFeature(name, biomes, pf(name), step);
        }

        protected void addFeature(String name, HolderSet<Biome> biomes, Holder<PlacedFeature> feature, GenerationStep.Decoration step) {
            addFeatures(name, biomes, HolderSet.direct(feature), step);
        }

        protected void addFeatures(String name, HolderSet<Biome> biomes, HolderSet<PlacedFeature> features, GenerationStep.Decoration step) {
            add(name, addFeatures(biomes, features, step));
        }

        private BiomeModifier addFeatures(HolderSet<Biome> biomes, HolderSet<PlacedFeature> features, GenerationStep.Decoration step) {
            return new BiomeModifiers.AddFeaturesBiomeModifier(biomes, features, step);
        }

        protected HolderSet<Biome> b(ResourceKey<Biome> key) {
            return HolderSet.direct(holder(key));
        }

        @SafeVarargs
        protected static HolderSet<Biome> and(HolderSet<Biome>... holders) {
            Preconditions.checkArgument(holders.length > 0);
            return holders.length == 1 ? holders[0] : new AndHolderSet<>(Arrays.asList(holders));
        }

        @SafeVarargs
        protected static HolderSet<Biome> or(HolderSet<Biome>... holders) {
            Preconditions.checkArgument(holders.length > 0);
            return holders.length == 1 ? holders[0] : new OrHolderSet<>(Arrays.asList(holders));
        }

        @SafeVarargs
        protected static HolderSet<Biome> not(HolderSet<Biome>... holders) {
            return new NotHolderSetWrapper<>(or(holders));
        }

        protected Holder<PlacedFeature> pf(String name) {
            return holder(Registries.PLACED_FEATURE, name);
        }

        private static class NotHolderSetWrapper<T> extends NotHolderSet<T> {
            public NotHolderSetWrapper(HolderSet<T> value) {
                super(null, value);
            }

            @Override
            public boolean canSerializeIn(HolderOwner<T> holderOwner) {
                return true;
            }
        }
    }
}
