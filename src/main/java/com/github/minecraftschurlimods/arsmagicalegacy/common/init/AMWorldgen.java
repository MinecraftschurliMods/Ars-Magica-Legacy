package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.worldgen.BlockStatePropertyMatchTest;
import com.github.minecraftschurlimods.arsmagicalegacy.common.worldgen.CompositeMatchTest;
import com.github.minecraftschurlimods.arsmagicalegacy.common.worldgen.MeteoriteFeature;
import com.github.minecraftschurlimods.arsmagicalegacy.common.worldgen.SunstoneOreFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Optional;

public interface AMWorldgen {
    // @formatter:off
    ResourceKey<ConfiguredFeature<?, ?>> CHIMERITE_ORE_CONFIGURED_FEATURE        = configuredFeature("chimerite_ore");
    ResourceKey<ConfiguredFeature<?, ?>> TOPAZ_ORE_CONFIGURED_FEATURE            = configuredFeature("topaz_ore");
    ResourceKey<ConfiguredFeature<?, ?>> TOPAZ_ORE_EXTRA_CONFIGURED_FEATURE      = configuredFeature("topaz_ore_extra");
    ResourceKey<ConfiguredFeature<?, ?>> VINTEUM_ORE_CONFIGURED_FEATURE          = configuredFeature("vinteum_ore");
    ResourceKey<ConfiguredFeature<?, ?>> MOONSTONE_METEORITE_CONFIGURED_FEATURE  = configuredFeature("moonstone_meteorite");
    ResourceKey<ConfiguredFeature<?, ?>> SUNSTONE_ORE_CONFIGURED_FEATURE         = configuredFeature("sunstone_ore");
    ResourceKey<ConfiguredFeature<?, ?>> WITCHWOOD_TREE_CONFIGURED_FEATURE       = configuredFeature("witchwood_tree");
    ResourceKey<ConfiguredFeature<?, ?>> AUM_CONFIGURED_FEATURE                  = configuredFeature("aum");
    ResourceKey<ConfiguredFeature<?, ?>> CERUBLOSSOM_CONFIGURED_FEATURE          = configuredFeature("cerublossom");
    ResourceKey<ConfiguredFeature<?, ?>> DESERT_NOVA_CONFIGURED_FEATURE          = configuredFeature("desert_nova");
    ResourceKey<ConfiguredFeature<?, ?>> TARMA_ROOT_CONFIGURED_FEATURE           = configuredFeature("tarma_root");
    ResourceKey<ConfiguredFeature<?, ?>> WAKEBLOOM_CONFIGURED_FEATURE            = configuredFeature("wakebloom");
    ResourceKey<ConfiguredFeature<?, ?>> LIQUID_ETHERIUM_LAKE_CONFIGURED_FEATURE = configuredFeature("liquid_etherium_lake");

    ResourceKey<PlacedFeature> CHIMERITE_ORE_PLACED_FEATURE        = placedFeature("chimerite_ore");
    ResourceKey<PlacedFeature> TOPAZ_ORE_PLACED_FEATURE            = placedFeature("topaz_ore");
    ResourceKey<PlacedFeature> TOPAZ_ORE_EXTRA_PLACED_FEATURE      = placedFeature("topaz_ore_extra");
    ResourceKey<PlacedFeature> VINTEUM_ORE_PLACED_FEATURE          = placedFeature("vinteum_ore");
    ResourceKey<PlacedFeature> MOONSTONE_METEORITE_PLACED_FEATURE  = placedFeature("moonstone_meteorite");
    ResourceKey<PlacedFeature> SUNSTONE_ORE_PLACED_FEATURE         = placedFeature("sunstone_ore");
    ResourceKey<PlacedFeature> TREES_WITCHWOOD_PLACED_FEATURE      = placedFeature("trees_witchwood");
    ResourceKey<PlacedFeature> AUM_PLACED_FEATURE                  = placedFeature("aum");
    ResourceKey<PlacedFeature> CERUBLOSSOM_PLACED_FEATURE          = placedFeature("cerublossom");
    ResourceKey<PlacedFeature> DESERT_NOVA_PLACED_FEATURE          = placedFeature("desert_nova");
    ResourceKey<PlacedFeature> TARMA_ROOT_PLACED_FEATURE           = placedFeature("tarma_root");
    ResourceKey<PlacedFeature> WAKEBLOOM_PLACED_FEATURE            = placedFeature("wakebloom");
    ResourceKey<PlacedFeature> LIQUID_ETHERIUM_LAKE_PLACED_FEATURE = placedFeature("liquid_etherium_lake");

    ResourceKey<BiomeModifier> OVERWORLD_BIOME_MODIFIER                    = biomeModifier("overworld");
    ResourceKey<BiomeModifier> NETHER_BIOME_MODIFIER                       = biomeModifier("nether");
    ResourceKey<BiomeModifier> NON_OCEAN_BIOME_MODIFIER                    = biomeModifier("non_ocean");
    ResourceKey<BiomeModifier> PLAINS_BIOME_MODIFIER                       = biomeModifier("plains");
    ResourceKey<BiomeModifier> FOREST_BIOME_MODIFIER                       = biomeModifier("forest");
    ResourceKey<BiomeModifier> MOUNTAIN_BIOME_MODIFIER                     = biomeModifier("mountain");
    ResourceKey<BiomeModifier> SANDY_BIOME_MODIFIER                        = biomeModifier("sandy");
    ResourceKey<BiomeModifier> SPOOKY_BIOME_MODIFIER                       = biomeModifier("spooky");
    ResourceKey<BiomeModifier> JUNGLE_OR_SWAMP_BIOME_MODIFIER              = biomeModifier("jungle_or_swamp");
    ResourceKey<BiomeModifier> MOUNTAIN_HILL_OR_UNDERGROUND_BIOME_MODIFIER = biomeModifier("mountain_hill_or_underground");
    ResourceKey<BiomeModifier> SPAWN_DRYADS_BIOME_MODIFIER                 = biomeModifier("spawn_dryads");
    ResourceKey<BiomeModifier> SPAWN_MANA_CREEPERS_BIOME_MODIFIER          = biomeModifier("spawn_mana_creepers");
    // @formatter:on

    TreeGrower WITCHWOOD_TREE_GROWER = new TreeGrower(ArsMagicaApi.MOD_ID + ":witchwood", Optional.of(WITCHWOOD_TREE_CONFIGURED_FEATURE), Optional.empty(), Optional.empty());

    DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<Feature<?>, MeteoriteFeature>   METEORITE    = FEATURES.register("meteorite",    MeteoriteFeature::new);
    DeferredHolder<Feature<?>, SunstoneOreFeature> SUNSTONE_ORE = FEATURES.register("sunstone_ore", SunstoneOreFeature::new);
    // @formatter:on

    DeferredRegister<RuleTestType<?>> RULE_TESTS = DeferredRegister.create(Registries.RULE_TEST, ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<RuleTestType<?>, RuleTestType<BlockStatePropertyMatchTest>> BLOCK_STATE_PROPERTY = RULE_TESTS.register("block_state_property", () -> () -> BlockStatePropertyMatchTest.CODEC);
    DeferredHolder<RuleTestType<?>, RuleTestType<CompositeMatchTest>>          COMPOSITE            = RULE_TESTS.register("composite",            () -> () -> CompositeMatchTest.CODEC);
    // @formatter:on

    /// @param name The name of the [ResourceKey].
    /// @return A [ResourceKey] for a [ConfiguredFeature].
    private static ResourceKey<ConfiguredFeature<?, ?>> configuredFeature(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ArsMagicaApi.id(name));
    }

    /// @param name The name of the [ResourceKey].
    /// @return A [ResourceKey] for a [PlacedFeature].
    private static ResourceKey<PlacedFeature> placedFeature(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ArsMagicaApi.id(name));
    }

    /// @param name The name of the [ResourceKey].
    /// @return A [ResourceKey] for a [BiomeModifier].
    private static ResourceKey<BiomeModifier> biomeModifier(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ArsMagicaApi.id(name));
    }
}
