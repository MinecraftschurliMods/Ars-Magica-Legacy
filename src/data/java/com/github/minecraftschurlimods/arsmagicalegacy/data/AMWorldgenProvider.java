package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import net.minecraft.core.HolderSet;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.world.ForgeBiomeModifiers;

import java.util.OptionalInt;

public class AMWorldgenProvider extends WorldgenProvider {
    AMWorldgenProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, existingFileHelper, ArsMagicaAPI.MOD_ID);
    }

    @Override
    protected void generateConfiguredFeatures() {
        cf("chimerite_ore", ore(AMBlocks.CHIMERITE_ORE, AMBlocks.DEEPSLATE_CHIMERITE_ORE, 7, 0F));
        cf("vinteum_ore", ore(AMBlocks.VINTEUM_ORE, AMBlocks.DEEPSLATE_VINTEUM_ORE, 10, 0F));
        cf("topaz_ore", ore(AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0.5F));
        cf("topaz_ore_extra", ore(AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0F));
        cf("aum", flower(64, AMBlocks.AUM));
        cf("cerublossom", flower(64, AMBlocks.CERUBLOSSOM));
        cf("desert_nova", flower(64, AMBlocks.DESERT_NOVA));
        cf("tarma_root", flower(64, AMBlocks.TARMA_ROOT));
        cf("wakebloom", flower(64, AMBlocks.WAKEBLOOM));
        cf("witchwood_tree", tree(AMBlocks.WITCHWOOD_LOG, new DarkOakTrunkPlacer(9, 3, 1), AMBlocks.WITCHWOOD_LEAVES, new DarkOakFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1)), new ThreeLayersFeatureSize(1, 2, 1, 1, 2, OptionalInt.empty())));
        cf("liquid_essence_lake", feature(Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(AMBlocks.LIQUID_ESSENCE.get()), BlockStateProvider.simple(Blocks.GRASS_BLOCK))));
    }

    @Override
    protected void generatePlacedFeatures() {
        pf("chimerite_ore", orePlacement(cf("chimerite_ore"), 6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(16))));
        pf("vinteum_ore", orePlacement(cf("vinteum_ore"), 8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(80))));
        pf("topaz_ore", orePlacement(cf("topaz_ore"), 7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        pf("topaz_ore_extra", orePlacement(cf("topaz_ore_extra"), 100, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480))));
        pf("aum", flowerPlacement(cf("aum"), 32));
        pf("cerublossom", flowerPlacement(cf("cerublossom"), 32));
        pf("desert_nova", flowerPlacement(cf("desert_nova"), 32));
        pf("tarma_root", flowerPlacement(cf("tarma_root"), 32));
        pf("wakebloom", flowerPlacement(cf("wakebloom"), 32));
        pf("witchwood_tree", treePlacement(cf("witchwood_tree"), AMBlocks.WITCHWOOD_SAPLING));
        pf("trees_witchwood", treeVegetation(cf("witchwood_tree"), PlacementUtils.countExtra(1, 0.1F, 0), 8, AMBlocks.WITCHWOOD_SAPLING));
        pf("liquid_essence_lake", placement(cf("liquid_essence_lake"), RarityFilter.onAverageOnceEvery(500), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
    }

    @Override
    protected void generateBiomeModifiers() {
        bm("chimerite_ore", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(bt(BiomeTags.IS_OVERWORLD), HolderSet.direct(pf("chimerite_ore")), GenerationStep.Decoration.UNDERGROUND_ORES));
        bm("vinteum_ore", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(bt(BiomeTags.IS_OVERWORLD), HolderSet.direct(pf("vinteum_ore")), GenerationStep.Decoration.UNDERGROUND_ORES));
        bm("topaz_ore", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(bt(BiomeTags.IS_OVERWORLD), HolderSet.direct(pf("topaz_ore")), GenerationStep.Decoration.UNDERGROUND_ORES));
        bm("topaz_extra_ore", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(and(bt(BiomeTags.IS_OVERWORLD), bt(BiomeTags.IS_MOUNTAIN)), HolderSet.direct(pf("topaz_ore_extra")), GenerationStep.Decoration.UNDERGROUND_ORES));
        bm("aum", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(and(bt(BiomeTags.IS_OVERWORLD), bt(BiomeTags.IS_FOREST)), HolderSet.direct(pf("aum")), GenerationStep.Decoration.VEGETAL_DECORATION));
        bm("cerublossom", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(and(bt(BiomeTags.IS_OVERWORLD), or(bt(BiomeTags.IS_JUNGLE), bt(Tags.Biomes.IS_SWAMP))), HolderSet.direct(pf("cerublossom")), GenerationStep.Decoration.VEGETAL_DECORATION));
        bm("desert_nova", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(and(bt(BiomeTags.IS_OVERWORLD), bt(Tags.Biomes.IS_SANDY)), HolderSet.direct(pf("desert_nova")), GenerationStep.Decoration.VEGETAL_DECORATION));
        bm("tarma_root", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(and(bt(BiomeTags.IS_OVERWORLD), or(bt(BiomeTags.IS_MOUNTAIN), bt(BiomeTags.IS_HILL), bt(Tags.Biomes.IS_UNDERGROUND))), HolderSet.direct(pf("tarma_root")), GenerationStep.Decoration.VEGETAL_DECORATION));
        bm("wakebloom", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(and(bt(BiomeTags.IS_OVERWORLD), or(bt(BiomeTags.IS_JUNGLE), bt(Tags.Biomes.IS_SWAMP))), HolderSet.direct(pf("wakebloom")), GenerationStep.Decoration.VEGETAL_DECORATION));
        bm("witchwood_tree", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(and(bt(BiomeTags.IS_OVERWORLD), bt(Tags.Biomes.IS_SPOOKY)), HolderSet.direct(pf("trees_witchwood")), GenerationStep.Decoration.VEGETAL_DECORATION));
        bm("liquid_essence_lake", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(bt(BiomeTags.IS_OVERWORLD), HolderSet.direct(pf("liquid_essence_lake")), GenerationStep.Decoration.LAKES));
        bm("dryad", ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(and(bt(BiomeTags.IS_OVERWORLD), bt(BiomeTags.IS_FOREST)), new MobSpawnSettings.SpawnerData(AMEntities.DRYAD.get(), 2, 15, 25)));
        bm("mana_creeper", ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(and(bt(BiomeTags.IS_OVERWORLD), not(b(Biomes.DEEP_DARK), bt(Tags.Biomes.IS_MUSHROOM))), new MobSpawnSettings.SpawnerData(AMEntities.MANA_CREEPER.get(), 10, 4, 4)));
    }
}
