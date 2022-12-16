package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMFeatures;
import com.github.minecraftschurlimods.arsmagicalegacy.common.level.meteorite.MeteoriteConfiguration;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biomes;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.Tags;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;

class AMWorldgenProvider {

    static class CF extends WorldgenProvider.ConfiguredFeatureProvider {

        CF() {
            super(ArsMagicaAPI.MOD_ID);
        }

        @Override
        public void generate() {
            ore("chimerite_ore", AMBlocks.CHIMERITE_ORE, AMBlocks.DEEPSLATE_CHIMERITE_ORE, 7, 0F);
            ore("vinteum_ore", AMBlocks.VINTEUM_ORE, AMBlocks.DEEPSLATE_VINTEUM_ORE, 10, 0F);
            ore("topaz_ore", AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0.5F);
            ore("topaz_ore_extra", AMBlocks.TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE, 4, 0F);
            add("sunstone_ore", sunstoneOre(AMBlocks.SUNSTONE_ORE, 4));
            add("moonstone_meteorite", meteorite(Blocks.STONE, AMBlocks.MOONSTONE_ORE, AMBlocks.LIQUID_ESSENCE, 7, 5, 0.1f));
            flower("aum", 64, AMBlocks.AUM);
            flower("cerublossom", 64, AMBlocks.CERUBLOSSOM);
            flower("desert_nova", 64, AMBlocks.DESERT_NOVA);
            flower("tarma_root", 64, AMBlocks.TARMA_ROOT);
            flower("wakebloom", 64, AMBlocks.WAKEBLOOM);
            tree("witchwood_tree", AMBlocks.WITCHWOOD_LOG, new DarkOakTrunkPlacer(9, 3, 1), AMBlocks.WITCHWOOD_LEAVES, new DarkOakFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1)), new ThreeLayersFeatureSize(1, 2, 1, 1, 2, OptionalInt.empty()));
        }

        private static ConfiguredFeature<?,?> sunstoneOre(Supplier<Block> ore, int veinSize) {
            return new ConfiguredFeature<>(AMFeatures.SUNSTONE_ORE_FEATURE.get(), new OreConfiguration(List.of(OreConfiguration.target(new TagMatchTest(BlockTags.BASE_STONE_NETHER), ore.get().defaultBlockState())), veinSize, 0F));
        }

        private static ConfiguredFeature<?, ?> meteorite(Block baseState, Supplier<Block> rareState, Supplier<LiquidBlock> fluidState, int meteoriteRadius, int meteoriteHeight, float rareChance) {
            return new ConfiguredFeature<>(AMFeatures.METEORITE.get(), new MeteoriteConfiguration(baseState.defaultBlockState(), rareState.get().defaultBlockState(), fluidState.get().defaultBlockState(), meteoriteRadius, meteoriteHeight, rareChance));
        }
    }

    static class PF extends WorldgenProvider.PlacedFeatureProvider {

        PF() {
            super(ArsMagicaAPI.MOD_ID);
        }

        @Override
        public void generate() {
            orePlacement("chimerite_ore", 6, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(16)));
            orePlacement("vinteum_ore", 8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(80)));
            orePlacement("topaz_ore", 7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)));
            orePlacement("topaz_ore_extra", 100, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480)));
            orePlacement("sunstone_ore", 32, HeightRangePlacement.uniform(VerticalAnchor.absolute(31), VerticalAnchor.absolute(33)));
            add("moonstone_meteorite", meteoritePlacement(cf("moonstone_meteorite"), 128, 56, 180));
            flowerPlacement("aum", 32);
            flowerPlacement("cerublossom", 32);
            flowerPlacement("desert_nova", 32);
            flowerPlacement("tarma_root", 32);
            flowerPlacement("wakebloom", 32);
            treePlacement("witchwood_tree", AMBlocks.WITCHWOOD_SAPLING);
            treeVegetation("trees_witchwood", cf("witchwood_tree"), PlacementUtils.countExtra(1, 0.1F, 0), 8, AMBlocks.WITCHWOOD_SAPLING);
        }

        private static PlacedFeature meteoritePlacement(Holder<ConfiguredFeature<?, ?>> feature, int rarity, int minHeight, int maxHeight) {
            return placement(feature, RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, HeightRangePlacement.uniform(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)), BiomeFilter.biome());
        }
    }

    static class BM extends WorldgenProvider.BiomeModifierProvider {

        BM() {
            super(ArsMagicaAPI.MOD_ID);
        }

        @Override
        public void generate() {
            addOre("chimerite_ore", tag(BiomeTags.IS_OVERWORLD));
            addOre("vinteum_ore", tag(BiomeTags.IS_OVERWORLD));
            addOre("topaz_ore", tag(BiomeTags.IS_OVERWORLD));
            addOre("topaz_ore_extra", and(tag(BiomeTags.IS_OVERWORLD), tag(BiomeTags.IS_MOUNTAIN)));
            addOre("sunstone_ore", tag(BiomeTags.IS_NETHER));
            addFeature("moonstone_meteorite", and(tag(BiomeTags.IS_OVERWORLD), not(tag(BiomeTags.IS_OCEAN))), GenerationStep.Decoration.LOCAL_MODIFICATIONS);
            addVegetation("aum", and(tag(BiomeTags.IS_OVERWORLD), tag(BiomeTags.IS_FOREST)));
            addVegetation("cerublossom", and(tag(BiomeTags.IS_OVERWORLD), or(tag(BiomeTags.IS_JUNGLE), tag(Tags.Biomes.IS_SWAMP))));
            addVegetation("desert_nova", and(tag(BiomeTags.IS_OVERWORLD), tag(Tags.Biomes.IS_SANDY)));
            addVegetation("tarma_root", and(tag(BiomeTags.IS_OVERWORLD), or(tag(BiomeTags.IS_MOUNTAIN), tag(BiomeTags.IS_HILL), tag(Tags.Biomes.IS_UNDERGROUND))));
            addVegetation("wakebloom", and(tag(BiomeTags.IS_OVERWORLD), or(tag(BiomeTags.IS_JUNGLE), tag(Tags.Biomes.IS_SWAMP))));
            addVegetation("witchwood_tree", and(tag(BiomeTags.IS_OVERWORLD), tag(Tags.Biomes.IS_SPOOKY)));
            addSpawn(AMEntities.DRYAD.get(), 2, 15, 25, and(tag(BiomeTags.IS_OVERWORLD), tag(BiomeTags.IS_FOREST)));
            addSpawn(AMEntities.MANA_CREEPER.get(), 10, 1, 4, and(tag(BiomeTags.IS_OVERWORLD), not(b(Biomes.DEEP_DARK), tag(Tags.Biomes.IS_MUSHROOM))));
        }
    }
}
