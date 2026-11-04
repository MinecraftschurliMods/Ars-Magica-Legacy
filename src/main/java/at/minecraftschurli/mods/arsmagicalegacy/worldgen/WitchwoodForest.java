package at.minecraftschurli.mods.arsmagicalegacy.worldgen;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public interface WitchwoodForest {
    ResourceKey<Biome> BIOME = ResourceKey.create(Registries.BIOME, ArsMagicaApi.id("witchwood_forest"));

    // @formatter:off
    ResourceKey<ConfiguredFeature<?, ?>> MAGIC_FLOWERS_FEATURE = feature("magic_flowers");
    ResourceKey<ConfiguredFeature<?, ?>> TREES_FEATURE         = feature("trees");
    ResourceKey<ConfiguredFeature<?, ?>> BIOME_FLOWERS_FEATURE = feature("biome_flowers");
    ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_FEATURE       = feature("flowers");
    ResourceKey<ConfiguredFeature<?, ?>> GRASS_FEATURE         = feature("grass");

    ResourceKey<PlacedFeature> MAGIC_FLOWERS_PLACEMENT                   = placement("magic_flowers");
    ResourceKey<PlacedFeature> WITCHWOOD_PLACEMENT                       = placement("witchwood");
    ResourceKey<PlacedFeature> DARK_FOREST_VEGETATION_PLACEMENT          = placement("dark_forest_vegetation");
    ResourceKey<PlacedFeature> TREES_FLOWER_FOREST_PLACEMENT             = placement("trees_flower_forest");
    ResourceKey<PlacedFeature> TREES_BIRCH_AND_OAK_LEAF_LITTER_PLACEMENT = placement("trees_birch_and_oak_leaf_litter");
    ResourceKey<PlacedFeature> FOREST_FLOWERS_PLACEMENT                  = placement("forest_flowers");
    ResourceKey<PlacedFeature> FLOWER_FOREST_FLOWERS_PLACEMENT           = placement("flower_forest_flowers");
    ResourceKey<PlacedFeature> FLOWER_DEFAULT_PLACEMENT                  = placement("flower_default");
    ResourceKey<PlacedFeature> FLOWER_FLOWER_FOREST_PLACEMENT            = placement("flower_flower_forest");
    ResourceKey<PlacedFeature> PATCH_GRASS_BADLANDS_PLACEMENT            = placement("patch_grass_badlands");
    ResourceKey<PlacedFeature> PATCH_GRASS_FOREST_PLACEMENT              = placement("patch_grass_forest");
    ResourceKey<PlacedFeature> GEODES_PLACEMENT                          = placement("geodes");
    ResourceKey<PlacedFeature> METEORITES_PLACEMENT                      = placement("meteorites");
    ResourceKey<PlacedFeature> TREES_PLACEMENT                           = placement("trees");
    ResourceKey<PlacedFeature> BIOME_FLOWERS_PLACEMENT                   = placement("biome_flowers");
    ResourceKey<PlacedFeature> FLOWERS_PLACEMENT                         = placement("flowers");
    ResourceKey<PlacedFeature> GRASS_PLACEMENT                           = placement("grass");
    // @formatter:on

    private static ResourceKey<ConfiguredFeature<?, ?>> feature(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ArsMagicaApi.id(name).withPrefix("witchwood_forest/"));
    }

    private static ResourceKey<PlacedFeature> placement(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ArsMagicaApi.id(name).withPrefix("witchwood_forest/"));
    }
}
