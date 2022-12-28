package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMFeatures;
import com.github.minecraftschurlimods.arsmagicalegacy.common.level.meteorite.MeteoriteConfiguration;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.world.ForgeBiomeModifiers;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;

class AMWorldgenProvider extends WorldgenProvider {
    AMWorldgenProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, existingFileHelper, ArsMagicaAPI.MOD_ID);
    }

    @Override
    protected void generateConfiguredFeatures() {
        cf("chimerite_ore", ore(AMBlocks.CHIMERITE_ORE, AMBlocks.DEEPSLATE_CHIMERITE_ORE, 7, 0F));
        cf("vinteum_ore", ore(AMBlocks.VINTEUM_ORE, AMBlocks.DEEPSLATE_VINTEUM_ORE, 10, 0F));
        cf("topaz_ore", ore(AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0.5F));
        cf("topaz_ore_extra", ore(AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0F));
        cf("sunstone_ore", sunstoneOre(AMBlocks.SUNSTONE_ORE, 4));
        cf("moonstone_meteorite", meteorite(Blocks.STONE, AMBlocks.MOONSTONE_ORE, AMBlocks.LIQUID_ESSENCE, 7, 5, 0.1f));
        cf("aum", flower(64, AMBlocks.AUM));
        cf("cerublossom", flower(64, AMBlocks.CERUBLOSSOM));
        cf("desert_nova", flower(64, AMBlocks.DESERT_NOVA));
        cf("tarma_root", flower(64, AMBlocks.TARMA_ROOT));
        cf("wakebloom", flower(64, AMBlocks.WAKEBLOOM));
        cf("witchwood_tree", tree(AMBlocks.WITCHWOOD_LOG, new DarkOakTrunkPlacer(9, 3, 1), AMBlocks.WITCHWOOD_LEAVES, new DarkOakFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1)), new ThreeLayersFeatureSize(1, 2, 1, 1, 2, OptionalInt.empty())));
    }

    @Override
    protected void generatePlacedFeatures() {
        pf("chimerite_ore", orePlacement(cf("chimerite_ore"), 6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(16))));
        pf("vinteum_ore", orePlacement(cf("vinteum_ore"), 8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(80))));
        pf("topaz_ore", orePlacement(cf("topaz_ore"), 7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        pf("topaz_ore_extra", orePlacement(cf("topaz_ore_extra"), 100, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480))));
        pf("sunstone_ore", orePlacement(cf("sunstone_ore"), 32, HeightRangePlacement.uniform(VerticalAnchor.absolute(31), VerticalAnchor.absolute(33))));
        pf("moonstone_meteorite", meteoritePlacement(cf("moonstone_meteorite"), 1, 56, 72));
        pf("aum", flowerPlacement(cf("aum"), 32));
        pf("cerublossom", flowerPlacement(cf("cerublossom"), 32));
        pf("desert_nova", flowerPlacement(cf("desert_nova"), 32));
        pf("tarma_root", flowerPlacement(cf("tarma_root"), 32));
        pf("wakebloom", flowerPlacement(cf("wakebloom"), 32));
        pf("witchwood_tree", treePlacement(cf("witchwood_tree"), AMBlocks.WITCHWOOD_SAPLING));
        pf("trees_witchwood", treeVegetation(cf("witchwood_tree"), PlacementUtils.countExtra(1, 0.1F, 0), 8, AMBlocks.WITCHWOOD_SAPLING));
    }

    @Override
    protected void generateBiomeModifiers() {
        bm("chimerite_ore", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(bt(BiomeTags.IS_OVERWORLD), HolderSet.direct(pf("chimerite_ore")), GenerationStep.Decoration.UNDERGROUND_ORES));
        bm("vinteum_ore", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(bt(BiomeTags.IS_OVERWORLD), HolderSet.direct(pf("vinteum_ore")), GenerationStep.Decoration.UNDERGROUND_ORES));
        bm("topaz_ore", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(bt(BiomeTags.IS_OVERWORLD), HolderSet.direct(pf("topaz_ore")), GenerationStep.Decoration.UNDERGROUND_ORES));
        bm("topaz_extra_ore", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(and(bt(BiomeTags.IS_OVERWORLD), bt(BiomeTags.IS_MOUNTAIN)), HolderSet.direct(pf("topaz_ore_extra")), GenerationStep.Decoration.UNDERGROUND_ORES));
        bm("sunstone_ore", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(bt(BiomeTags.IS_NETHER), HolderSet.direct(pf("sunstone_ore")), GenerationStep.Decoration.UNDERGROUND_ORES));
        bm("moonstone_meteoreite", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(and(bt(BiomeTags.IS_OVERWORLD), not(bt(BiomeTags.IS_OCEAN))), HolderSet.direct(pf("moonstone_meteoreite")), GenerationStep.Decoration.LOCAL_MODIFICATIONS));
        bm("aum", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(and(bt(BiomeTags.IS_OVERWORLD), bt(BiomeTags.IS_FOREST)), HolderSet.direct(pf("aum")), GenerationStep.Decoration.VEGETAL_DECORATION));
        bm("cerublossom", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(and(bt(BiomeTags.IS_OVERWORLD), or(bt(BiomeTags.IS_JUNGLE), bt(Tags.Biomes.IS_SWAMP))), HolderSet.direct(pf("cerublossom")), GenerationStep.Decoration.VEGETAL_DECORATION));
        bm("desert_nova", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(and(bt(BiomeTags.IS_OVERWORLD), bt(Tags.Biomes.IS_SANDY)), HolderSet.direct(pf("desert_nova")), GenerationStep.Decoration.VEGETAL_DECORATION));
        bm("tarma_root", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(and(bt(BiomeTags.IS_OVERWORLD), or(bt(BiomeTags.IS_MOUNTAIN), bt(BiomeTags.IS_HILL), bt(Tags.Biomes.IS_UNDERGROUND))), HolderSet.direct(pf("tarma_root")), GenerationStep.Decoration.VEGETAL_DECORATION));
        bm("wakebloom", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(and(bt(BiomeTags.IS_OVERWORLD), or(bt(BiomeTags.IS_JUNGLE), bt(Tags.Biomes.IS_SWAMP))), HolderSet.direct(pf("wakebloom")), GenerationStep.Decoration.VEGETAL_DECORATION));
        bm("witchwood_tree", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(and(bt(BiomeTags.IS_OVERWORLD), bt(Tags.Biomes.IS_SPOOKY)), HolderSet.direct(pf("trees_witchwood")), GenerationStep.Decoration.VEGETAL_DECORATION));
        bm("dryad", ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(and(bt(BiomeTags.IS_OVERWORLD), bt(BiomeTags.IS_FOREST)), new MobSpawnSettings.SpawnerData(AMEntities.DRYAD.get(), 2, 15, 25)));
        bm("mana_creeper", ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(and(bt(BiomeTags.IS_OVERWORLD), not(b(Biomes.DEEP_DARK), bt(Tags.Biomes.IS_MUSHROOM))), new MobSpawnSettings.SpawnerData(AMEntities.MANA_CREEPER.get(), 10, 1, 4)));
    }

    private static ConfiguredFeature<?,?> sunstoneOre(Supplier<Block> ore, int veinSize) {
        return feature(AMFeatures.SUNSTONE_ORE_FEATURE.get(), new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, ore.get().defaultBlockState())), veinSize, 0F));
    }

    private static ConfiguredFeature<?, ?> meteorite(Block baseState, Supplier<Block> rareState, Supplier<LiquidBlock> fluidState, int meteoriteRadius, int meteoriteHeight, float rareChance) {
        return feature(AMFeatures.METEORITE.get(), new MeteoriteConfiguration(baseState.defaultBlockState(), rareState.get().defaultBlockState(), fluidState.get().defaultBlockState(), meteoriteRadius, meteoriteHeight, rareChance));
    }

    private static PlacedFeature meteoritePlacement(Holder<ConfiguredFeature<?, ?>> feature, int rarity, int minHeight, int maxHeight) {
        return placement(feature, RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, HeightRangePlacement.uniform(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)), BiomeFilter.biome());
    }
}
