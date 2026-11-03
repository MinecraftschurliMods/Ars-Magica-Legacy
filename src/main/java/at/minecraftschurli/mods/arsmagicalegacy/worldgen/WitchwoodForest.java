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
    ResourceKey<ConfiguredFeature<?, ?>> TREES_FEATURE         = configuredFeature("trees");
    ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_FEATURE       = configuredFeature("flowers");
    ResourceKey<ConfiguredFeature<?, ?>> BIOME_FLOWERS_FEATURE = configuredFeature("biome_flowers");
    ResourceKey<ConfiguredFeature<?, ?>> GRASS_FEATURE         = configuredFeature("grass");

    ResourceKey<PlacedFeature> WITCHWOOD_PLACEMENT                       = placedFeature("witchwood");
    ResourceKey<PlacedFeature> DARK_FOREST_VEGETATION_PLACEMENT          = placedFeature("dark_forest_vegetation");
    ResourceKey<PlacedFeature> TREES_FLOWER_FOREST_PLACEMENT             = placedFeature("trees_flower_forest");
    ResourceKey<PlacedFeature> TREES_BIRCH_AND_OAK_LEAF_LITTER_PLACEMENT = placedFeature("trees_birch_and_oak_leaf_litter");
    ResourceKey<PlacedFeature> FOREST_FLOWERS_PLACEMENT                  = placedFeature("forest_flowers");
    ResourceKey<PlacedFeature> FLOWER_FOREST_FLOWERS_PLACEMENT           = placedFeature("flower_forest_flowers");
    ResourceKey<PlacedFeature> FLOWER_DEFAULT_PLACEMENT                  = placedFeature("flower_default");
    ResourceKey<PlacedFeature> FLOWER_FLOWER_FOREST_PLACEMENT            = placedFeature("flower_flower_forest");
    ResourceKey<PlacedFeature> PATCH_GRASS_BADLANDS_PLACEMENT            = placedFeature("patch_grass_badlands");
    ResourceKey<PlacedFeature> PATCH_GRASS_FOREST_PLACEMENT              = placedFeature("patch_grass_forest");
    ResourceKey<PlacedFeature> GEODES_PLACEMENT                          = placedFeature("geodes");
    ResourceKey<PlacedFeature> METEORITES_PLACEMENT                      = placedFeature("meteorites");
    ResourceKey<PlacedFeature> TREES_PLACEMENT                           = placedFeature("trees");
    ResourceKey<PlacedFeature> FLOWERS_PLACEMENT                         = placedFeature("flowers");
    ResourceKey<PlacedFeature> BIOME_FLOWERS_PLACEMENT                   = placedFeature("biome_flowers");
    ResourceKey<PlacedFeature> GRASS_PLACEMENT                           = placedFeature("grass");
    // @formatter:on

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredFeature(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ArsMagicaApi.id(name).withPrefix("witchwood_forest/"));
    }

    private static ResourceKey<PlacedFeature> placedFeature(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ArsMagicaApi.id(name).withPrefix("witchwood_forest/"));
    }
}
