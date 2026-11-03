package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.worldgen.BlockStatePropertyMatchTest;
import at.minecraftschurli.mods.arsmagicalegacy.worldgen.CompositeMatchTest;
import at.minecraftschurli.mods.arsmagicalegacy.worldgen.MeteoriteFeature;
import at.minecraftschurli.mods.arsmagicalegacy.worldgen.SunstoneOreFeature;
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
    ResourceKey<ConfiguredFeature<?, ?>> CHIMERITE_ORE_FEATURE        = feature("chimerite_ore");
    ResourceKey<ConfiguredFeature<?, ?>> TOPAZ_ORE_FEATURE            = feature("topaz_ore");
    ResourceKey<ConfiguredFeature<?, ?>> TOPAZ_ORE_EXTRA_FEATURE      = feature("topaz_ore_extra");
    ResourceKey<ConfiguredFeature<?, ?>> VINTEUM_ORE_FEATURE          = feature("vinteum_ore");
    ResourceKey<ConfiguredFeature<?, ?>> MOONSTONE_METEORITE_FEATURE  = feature("moonstone_meteorite");
    ResourceKey<ConfiguredFeature<?, ?>> SUNSTONE_ORE_FEATURE         = feature("sunstone_ore");
    ResourceKey<ConfiguredFeature<?, ?>> WITCHWOOD_TREE_FEATURE       = feature("witchwood_tree");
    ResourceKey<ConfiguredFeature<?, ?>> AUM_FEATURE                  = feature("aum");
    ResourceKey<ConfiguredFeature<?, ?>> CERUBLOSSOM_FEATURE          = feature("cerublossom");
    ResourceKey<ConfiguredFeature<?, ?>> DESERT_NOVA_FEATURE          = feature("desert_nova");
    ResourceKey<ConfiguredFeature<?, ?>> TARMA_ROOT_FEATURE           = feature("tarma_root");
    ResourceKey<ConfiguredFeature<?, ?>> WAKEBLOOM_FEATURE            = feature("wakebloom");
    ResourceKey<ConfiguredFeature<?, ?>> LIQUID_ETHERIUM_LAKE_FEATURE = feature("liquid_etherium_lake");

    ResourceKey<PlacedFeature> CHIMERITE_ORE_PLACEMENT        = placement("chimerite_ore");
    ResourceKey<PlacedFeature> TOPAZ_ORE_PLACEMENT            = placement("topaz_ore");
    ResourceKey<PlacedFeature> TOPAZ_ORE_EXTRA_PLACEMENT      = placement("topaz_ore_extra");
    ResourceKey<PlacedFeature> VINTEUM_ORE_PLACEMENT          = placement("vinteum_ore");
    ResourceKey<PlacedFeature> MOONSTONE_METEORITE_PLACEMENT  = placement("moonstone_meteorite");
    ResourceKey<PlacedFeature> SUNSTONE_ORE_PLACEMENT         = placement("sunstone_ore");
    ResourceKey<PlacedFeature> TREES_WITCHWOOD_PLACEMENT      = placement("trees_witchwood");
    ResourceKey<PlacedFeature> AUM_PLACEMENT                  = placement("aum");
    ResourceKey<PlacedFeature> CERUBLOSSOM_PLACEMENT          = placement("cerublossom");
    ResourceKey<PlacedFeature> DESERT_NOVA_PLACEMENT          = placement("desert_nova");
    ResourceKey<PlacedFeature> TARMA_ROOT_PLACEMENT           = placement("tarma_root");
    ResourceKey<PlacedFeature> WAKEBLOOM_PLACEMENT            = placement("wakebloom");
    ResourceKey<PlacedFeature> LIQUID_ETHERIUM_LAKE_PLACEMENT = placement("liquid_etherium_lake");

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

    TreeGrower WITCHWOOD_TREE_GROWER = new TreeGrower(ArsMagicaApi.MOD_ID + ":witchwood", Optional.of(WITCHWOOD_TREE_FEATURE), Optional.empty(), Optional.empty());

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
    private static ResourceKey<ConfiguredFeature<?, ?>> feature(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ArsMagicaApi.id(name));
    }

    /// @param name The name of the [ResourceKey].
    /// @return A [ResourceKey] for a [PlacedFeature].
    private static ResourceKey<PlacedFeature> placement(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ArsMagicaApi.id(name));
    }

    /// @param name The name of the [ResourceKey].
    /// @return A [ResourceKey] for a [BiomeModifier].
    private static ResourceKey<BiomeModifier> biomeModifier(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ArsMagicaApi.id(name));
    }
}
