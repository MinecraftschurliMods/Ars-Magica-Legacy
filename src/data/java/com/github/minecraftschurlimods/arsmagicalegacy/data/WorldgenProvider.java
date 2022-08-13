package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
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
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.holdersets.AndHolderSet;
import net.minecraftforge.registries.holdersets.OrHolderSet;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 *
 */
public abstract class WorldgenProvider implements DataProvider {
    private final String namespace;
    private final Map<ResourceLocation, ConfiguredFeature<?, ?>> cfs = new HashMap<>();
    private final Map<ResourceLocation, PlacedFeature> pfs = new HashMap<>();
    private final Map<ResourceLocation, BiomeModifier> bms = new HashMap<>();
    private final RegistryAccess.Writable registryAccess;
    private final JsonCodecProvider<ConfiguredFeature<?, ?>> cfProvider;
    private final JsonCodecProvider<PlacedFeature> pfProvider;
    private final JsonCodecProvider<BiomeModifier> bmProvider;

    public WorldgenProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, String namespace) {
        this.namespace = namespace;
        this.registryAccess = RegistryAccess.builtinCopy();
        RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, registryAccess);
        this.cfProvider = JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, namespace, ops, Registry.CONFIGURED_FEATURE_REGISTRY, cfs);
        this.pfProvider = JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, namespace, ops, Registry.PLACED_FEATURE_REGISTRY, pfs);
        this.bmProvider = JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, namespace, ops, ForgeRegistries.Keys.BIOME_MODIFIERS, bms);
    }

    @Override
    public void run(CachedOutput p_236071_) throws IOException {
        cfs.clear();
        pfs.clear();
        bms.clear();
        generateConfiguredFeatures();
        generatePlacedFeatures();
        generateBiomeModifiers();
        cfProvider.run(p_236071_);
        pfProvider.run(p_236071_);
        bmProvider.run(p_236071_);
    }

    @Override
    public String getName() {
        return "WorldgenProvider["+namespace+"]";
    }

    protected void cf(ResourceLocation name, ConfiguredFeature<?, ?> cf) {
        cfs.put(name, cf);
    }

    protected void cf(String name, ConfiguredFeature<?, ?> cf) {
        cf(new ResourceLocation(namespace, name), cf);
    }

    protected void pf(ResourceLocation name, PlacedFeature pf) {
        pfs.put(name, pf);
    }

    protected void pf(String name, PlacedFeature pf) {
        pf(new ResourceLocation(namespace, name), pf);
    }

    protected void bm(ResourceLocation name, BiomeModifier bm) {
        bms.put(name, bm);
    }

    protected void bm(String name, BiomeModifier bm) {
        bm(new ResourceLocation(namespace, name), bm);
    }

    protected Holder<ConfiguredFeature<?, ?>> cf(ResourceLocation name) {
        return getHolderOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY, name);
    }

    protected Holder<ConfiguredFeature<?, ?>> cf(String name) {
        return cf(new ResourceLocation(namespace, name));
    }

    protected Holder<PlacedFeature> pf(ResourceLocation name) {
        return getHolderOrThrow(Registry.PLACED_FEATURE_REGISTRY, name);
    }

    protected Holder<PlacedFeature> pf(String name) {
        return pf(new ResourceLocation(namespace, name));
    }

    protected HolderSet<Biome> bt(TagKey<Biome> tag) {
        return registryAccess.registryOrThrow(Registry.BIOME_REGISTRY).getOrCreateTag(tag);
    }

    protected abstract void generateConfiguredFeatures();

    protected abstract void generatePlacedFeatures();

    protected abstract void generateBiomeModifiers();

    protected static HolderSet<Biome> and(HolderSet<Biome>... holders) {
        return new AndHolderSet<>(Arrays.asList(holders));
    }

    protected static HolderSet<Biome> or(HolderSet<Biome>... holders) {
        return new OrHolderSet<>(Arrays.asList(holders));
    }

    @NotNull
    private <T> Holder<T> getHolderOrThrow(ResourceKey<Registry<T>> registryKey, ResourceLocation location) {
        return registryAccess.registryOrThrow(registryKey).getOrCreateHolderOrThrow(ResourceKey.create(registryKey, location));
    }

    /**
     * @param ore                      The ore block to use.
     * @param deepslateOre             The deepslate ore block to use.
     * @param veinSize                 The vein size of this ore.
     * @param airExposureDiscardChance A 0..1 float that represents the probability of the feature being discarded if the vein touches air.
     * @return A configured ore feature based on the given parameters.
     */
    protected static ConfiguredFeature<OreConfiguration, ?> ore(Supplier<? extends Block> ore, Supplier<? extends Block> deepslateOre, int veinSize, float airExposureDiscardChance) {
        return WorldgenProvider.feature(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ore.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, deepslateOre.get().defaultBlockState())), veinSize, airExposureDiscardChance));
    }

    /**
     * @param feature              The configured feature to use.
     * @param veinCount            The amount of veins for this feature.
     * @param heightRangePlacement The height distribution of this feature.
     * @return A placed feature based on the given parameters.
     */
    protected static PlacedFeature orePlacement(Holder<ConfiguredFeature<?, ?>> feature, int veinCount, HeightRangePlacement heightRangePlacement) {
        return WorldgenProvider.placement(feature, CountPlacement.of(veinCount), InSquarePlacement.spread(), heightRangePlacement, BiomeFilter.biome());
    }

    /**
     * @param tries  How many tries for this feature will be performed.
     * @param flower The flower block to use.
     * @return A configured flower feature based on the given parameters.
     */
    protected static ConfiguredFeature<RandomPatchConfiguration, ?> flower(int tries, Supplier<? extends Block> flower) {
        return WorldgenProvider.feature(Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(flower.get())))));
    }

    /**
     * @param feature The configured feature to use.
     * @param rarity  The rarity of this feature.
     * @return A placed feature based on the given parameters.
     */
    protected static PlacedFeature flowerPlacement(Holder<ConfiguredFeature<?, ?>> feature, int rarity) {
        return WorldgenProvider.placement(feature, RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }

    /**
     * @param log     The log block to use.
     * @param trunk   The trunk placer to use.
     * @param leaves  The leaves block to use.
     * @param foliage The foliage placer to use.
     * @param size    The feature size to use.
     * @return A configured tree feature based on the given parameters.
     */
    protected static ConfiguredFeature<TreeConfiguration, ?> tree(Supplier<? extends Block> log, TrunkPlacer trunk, Supplier<? extends Block> leaves, FoliagePlacer foliage, FeatureSize size) {
        return WorldgenProvider.feature(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log.get()), trunk, BlockStateProvider.simple(leaves.get()), foliage, size).ignoreVines().build());
    }

    /**
     * @param feature The configured feature to use.
     * @param sapling The sapling associated with this tree feature.
     * @return A placed feature based on the given parameters.
     */
    protected static PlacedFeature treePlacement(Holder<ConfiguredFeature<?, ?>> feature, Supplier<? extends Block> sapling) {
        return new PlacedFeature(feature, List.of(PlacementUtils.filteredByBlockSurvival(sapling.get())));
    }

    /**
     * @param feature  The configured feature to use.
     * @param modifier The tree placement modifier to use.
     * @param rarity   The rarity of this feature.
     * @param sapling  The sapling associated with this tree feature.
     * @return A placed feature based on the given parameters.
     */
    protected static PlacedFeature treeVegetation(Holder<ConfiguredFeature<?, ?>> feature, PlacementModifier modifier, int rarity, Supplier<? extends Block> sapling) {
        List<PlacementModifier> list = new ArrayList<>(VegetationPlacements.treePlacement(modifier, sapling.get()));
        list.add(RarityFilter.onAverageOnceEvery(rarity));
        return WorldgenProvider.placement(feature, list.toArray(new PlacementModifier[0]));
    }

    protected static <T extends FeatureConfiguration> ConfiguredFeature<T, ?> feature(Feature<T> feature, T configuration) {
        return new ConfiguredFeature<>(feature, configuration);
    }

    protected static PlacedFeature placement(Holder<ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
        return new PlacedFeature(feature, Arrays.asList(modifiers));
    }
}
