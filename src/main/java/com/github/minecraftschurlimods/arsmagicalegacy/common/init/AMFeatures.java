package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
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
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.BIOME_MODIFIERS;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.BIOME_MODIFIER_SERIALIZERS;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.CONFIGURED_FEATURES;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.PLACED_FEATURES;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@NonExtendable
public interface AMFeatures {
    RegistryObject<ConfiguredFeature<OreConfiguration, ?>>         CHIMERITE_FEATURE      = CONFIGURED_FEATURES.register("chimerite_ore",   () -> ore(AMBlocks.CHIMERITE_ORE, AMBlocks.DEEPSLATE_CHIMERITE_ORE, 7, 0F));
    RegistryObject<ConfiguredFeature<OreConfiguration, ?>>         VINTEUM_FEATURE        = CONFIGURED_FEATURES.register("vinteum_ore",     () -> ore(AMBlocks.VINTEUM_ORE, AMBlocks.DEEPSLATE_VINTEUM_ORE, 10, 0F));
    RegistryObject<ConfiguredFeature<OreConfiguration, ?>>         TOPAZ_FEATURE          = CONFIGURED_FEATURES.register("topaz_ore",       () -> ore(AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0.5F));
    RegistryObject<ConfiguredFeature<OreConfiguration, ?>>         TOPAZ_EXTRA_FEATURE    = CONFIGURED_FEATURES.register("topaz_ore_extra", () -> ore(AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0F));
    RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> AUM_FEATURE            = CONFIGURED_FEATURES.register("aum",             () -> flower(64, AMBlocks.AUM));
    RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> CERUBLOSSOM_FEATURE    = CONFIGURED_FEATURES.register("cerublossom",     () -> flower(64, AMBlocks.CERUBLOSSOM));
    RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> DESERT_NOVA_FEATURE    = CONFIGURED_FEATURES.register("desert_nova",     () -> flower(64, AMBlocks.DESERT_NOVA));
    RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> TARMA_ROOT_FEATURE     = CONFIGURED_FEATURES.register("tarma_root",      () -> flower(64, AMBlocks.TARMA_ROOT));
    RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> WAKEBLOOM_FEATURE      = CONFIGURED_FEATURES.register("wakebloom",       () -> flower(64, AMBlocks.WAKEBLOOM));
    RegistryObject<ConfiguredFeature<TreeConfiguration, ?>>        WITCHWOOD_TREE_FEATURE = CONFIGURED_FEATURES.register("witchwood_tree",  () -> tree(AMBlocks.WITCHWOOD_LOG, new DarkOakTrunkPlacer(9, 3, 1), AMBlocks.WITCHWOOD_LEAVES, new DarkOakFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1)), new ThreeLayersFeatureSize(1, 2, 1, 1, 2, OptionalInt.empty())));

    RegistryObject<PlacedFeature> CHIMERITE_PLACEMENT       = PLACED_FEATURES.register("chimerite_placement", () -> orePlacement(CHIMERITE_FEATURE.getHolder().get(), 6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(16))));
    RegistryObject<PlacedFeature> VINTEUM_PLACEMENT         = PLACED_FEATURES.register("vinteum_ore",         () -> orePlacement(VINTEUM_FEATURE.getHolder().get(), 8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(80))));
    RegistryObject<PlacedFeature> TOPAZ_PLACEMENT           = PLACED_FEATURES.register("topaz_ore",           () -> orePlacement(TOPAZ_FEATURE.getHolder().get(), 7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
    RegistryObject<PlacedFeature> TOPAZ_EXTRA_PLACEMENT     = PLACED_FEATURES.register("topaz_ore_extra",     () -> orePlacement(TOPAZ_EXTRA_FEATURE.getHolder().get(), 100, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480))));
    RegistryObject<PlacedFeature> AUM_PLACEMENT             = PLACED_FEATURES.register("aum",                 () -> flowerPlacement(AUM_FEATURE.getHolder().get(), 32));
    RegistryObject<PlacedFeature> CERUBLOSSOM_PLACEMENT     = PLACED_FEATURES.register("cerublossom",         () -> flowerPlacement(CERUBLOSSOM_FEATURE.getHolder().get(), 32));
    RegistryObject<PlacedFeature> DESERT_NOVA_PLACEMENT     = PLACED_FEATURES.register("desert_nova",         () -> flowerPlacement(DESERT_NOVA_FEATURE.getHolder().get(), 32));
    RegistryObject<PlacedFeature> TARMA_ROOT_PLACEMENT      = PLACED_FEATURES.register("tarma_root",          () -> flowerPlacement(TARMA_ROOT_FEATURE.getHolder().get(), 32));
    RegistryObject<PlacedFeature> WAKEBLOOM_PLACEMENT       = PLACED_FEATURES.register("wakebloom",           () -> flowerPlacement(WAKEBLOOM_FEATURE.getHolder().get(), 32));
    RegistryObject<PlacedFeature> WITCHWOOD_TREE_PLACEMENT  = PLACED_FEATURES.register("witchwood_tree",      () -> treePlacement(WITCHWOOD_TREE_FEATURE.getHolder().get(), AMBlocks.WITCHWOOD_SAPLING));
    RegistryObject<PlacedFeature> WITCHWOOD_TREE_VEGETATION = PLACED_FEATURES.register("trees_witchwood",     () -> treeVegetation(WITCHWOOD_TREE_FEATURE.getHolder().get(), PlacementUtils.countExtra(1, 0.1F, 0), 8, AMBlocks.WITCHWOOD_SAPLING));

    RegistryObject<Codec<BiomeModifier>> MODIFIER_CODEC = BIOME_MODIFIER_SERIALIZERS.register("modifier", () -> Codec.unit(new Modifier()));
    RegistryObject<BiomeModifier> MODIFIER = BIOME_MODIFIERS.register("modifier", Modifier::new);

    /**
     * @param ore                      The ore block to use.
     * @param deepslateOre             The deepslate ore block to use.
     * @param veinSize                 The vein size of this ore.
     * @param airExposureDiscardChance A 0..1 float that represents the probability of the feature being discarded if the vein touches air.
     * @return A configured ore feature based on the given parameters.
     */
    static ConfiguredFeature<OreConfiguration, ?> ore(Supplier<? extends Block> ore, Supplier<? extends Block> deepslateOre, int veinSize, float airExposureDiscardChance) {
        return feature(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ore.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepslateOre.get().defaultBlockState())), veinSize, airExposureDiscardChance));
    }

    /**
     * @param feature              The configured feature to use.
     * @param veinCount            The amount of veins for this feature.
     * @param heightRangePlacement The height distribution of this feature.
     * @return A placed feature based on the given parameters.
     */
    static PlacedFeature orePlacement(Holder<ConfiguredFeature<OreConfiguration, ?>> feature, int veinCount, HeightRangePlacement heightRangePlacement) {
        return placement(Holder.hackyErase(feature), CountPlacement.of(veinCount), InSquarePlacement.spread(), heightRangePlacement, BiomeFilter.biome());
    }

    /**
     * @param tries  How many tries for this feature will be performed.
     * @param flower The flower block to use.
     * @return A configured flower feature based on the given parameters.
     */
    static ConfiguredFeature<RandomPatchConfiguration, ?> flower(int tries, Supplier<? extends Block> flower) {
        return feature(Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(flower.get())))));
    }

    /**
     * @param feature The configured feature to use.
     * @param rarity  The rarity of this feature.
     * @return A placed feature based on the given parameters.
     */
    static PlacedFeature flowerPlacement(Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> feature, int rarity) {
        return placement(Holder.hackyErase(feature), RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }

    /**
     * @param log     The log block to use.
     * @param trunk   The trunk placer to use.
     * @param leaves  The leaves block to use.
     * @param foliage The foliage placer to use.
     * @param size    The feature size to use.
     * @return A configured tree feature based on the given parameters.
     */
    static ConfiguredFeature<TreeConfiguration, ?> tree(Supplier<? extends Block> log, TrunkPlacer trunk, Supplier<? extends Block> leaves, FoliagePlacer foliage, FeatureSize size) {
        return feature(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log.get()), trunk, BlockStateProvider.simple(leaves.get()), foliage, size).ignoreVines().build());
    }

    /**
     * @param feature The configured feature to use.
     * @param sapling The sapling associated with this tree feature.
     * @return A placed feature based on the given parameters.
     */
    static PlacedFeature treePlacement(Holder<ConfiguredFeature<TreeConfiguration, ?>> feature, Supplier<? extends Block> sapling) {
        return new PlacedFeature(Holder.hackyErase(feature), List.of(PlacementUtils.filteredByBlockSurvival(sapling.get())));
    }

    /**
     * @param feature  The configured feature to use.
     * @param modifier The tree placement modifier to use.
     * @param rarity   The rarity of this feature.
     * @param sapling  The sapling associated with this tree feature.
     * @return A placed feature based on the given parameters.
     */
    static PlacedFeature treeVegetation(Holder<ConfiguredFeature<TreeConfiguration, ?>> feature, PlacementModifier modifier, int rarity, Supplier<? extends Block> sapling) {
        List<PlacementModifier> list = new ArrayList<>(VegetationPlacements.treePlacement(modifier, sapling.get()));
        list.add(RarityFilter.onAverageOnceEvery(rarity));
        return placement(Holder.hackyErase(feature), list.toArray(new PlacementModifier[0]));
    }

    private static <T extends FeatureConfiguration> ConfiguredFeature<T, ?> feature(Feature<T> feature, T configuration) {
        return new ConfiguredFeature<>(feature, configuration);
    }

    private static PlacedFeature placement(Holder<ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
        return new PlacedFeature(feature, Arrays.asList(modifiers));
    }

    class Modifier implements BiomeModifier {

        @Override
        public void modify(final Holder<Biome> biome, final Phase phase, final ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.ADD) {
                BiomeGenerationSettingsBuilder generationSettings = builder.getGenerationSettings();
                MobSpawnSettingsBuilder spawn = builder.getMobSpawnSettings();
                if (biome.is(BiomeTags.IS_OVERWORLD)) {
                    generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CHIMERITE_PLACEMENT.getHolder().get());
                    generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, VINTEUM_PLACEMENT.getHolder().get());
                    generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, TOPAZ_PLACEMENT.getHolder().get());
                    if (biome.is(BiomeTags.IS_MOUNTAIN)) {
                        generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, TOPAZ_EXTRA_PLACEMENT.getHolder().get());
                    }
                    if (biome.is(BiomeTags.IS_FOREST)) {
                        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AUM_PLACEMENT.getHolder().get());
                        spawn.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(AMEntities.DRYAD.get(), 2, 15, 25));
                    }
                    if (biome.is(BiomeTags.IS_JUNGLE) || biome.is(Tags.Biomes.IS_SWAMP)) {
                        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CERUBLOSSOM_PLACEMENT.getHolder().get());
                        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WAKEBLOOM_PLACEMENT.getHolder().get());
                    }
                    if (biome.is(Tags.Biomes.IS_SANDY)) {
                        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DESERT_NOVA_PLACEMENT.getHolder().get());
                    }
                    if (biome.is(BiomeTags.IS_MOUNTAIN) || biome.is(BiomeTags.IS_HILL) || biome.is(Tags.Biomes.IS_UNDERGROUND)) {
                        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TARMA_ROOT_PLACEMENT.getHolder().get());
                    }
                    if (biome.is(Tags.Biomes.IS_SPOOKY)) {
                        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WITCHWOOD_TREE_VEGETATION.getHolder().get());
                    }
                }
            }
        }

        @Override
        public Codec<? extends BiomeModifier> codec() {
            return MODIFIER_CODEC.get();
        }
    }
}
