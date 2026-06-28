package at.minecraftschurli.mods.arsmagicalegacy.api.constants;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;

/// Holds all [TagKey]s added by Ars Magica: Legacy.
@ApiStatus.NonExtendable
public interface AMTags {
    @ApiStatus.NonExtendable
    interface Blocks {
        TagKey<Block> ORES_CHIMERITE = cTag("ores/chimerite");
        TagKey<Block> ORES_TOPAZ = cTag("ores/topaz");
        TagKey<Block> ORES_VINTEUM = cTag("ores/vinteum");
        TagKey<Block> ORES_MOONSTONE = cTag("ores/moonstone");
        TagKey<Block> ORES_SUNSTONE = cTag("ores/sunstone");
        TagKey<Block> STORAGE_BLOCKS_CHIMERITE = cTag("storage_blocks/chimerite");
        TagKey<Block> STORAGE_BLOCKS_TOPAZ = cTag("storage_blocks/topaz");
        TagKey<Block> STORAGE_BLOCKS_VINTEUM = cTag("storage_blocks/vinteum");
        TagKey<Block> STORAGE_BLOCKS_MOONSTONE = cTag("storage_blocks/moonstone");
        TagKey<Block> STORAGE_BLOCKS_SUNSTONE = cTag("storage_blocks/sunstone");
        TagKey<Block> WITCHWOOD_LOGS = tag("witchwood_logs");
        TagKey<Block> AUM_PLANTABLE_ON = tag("aum_plantable_on");
        TagKey<Block> CERUBLOSSOM_PLANTABLE_ON = tag("cerublossom_plantable_on");
        TagKey<Block> DESERT_NOVA_PLANTABLE_ON = tag("desert_nova_plantable_on");
        TagKey<Block> TARMA_ROOT_PLANTABLE_ON = tag("tarma_root_plantable_on");
        TagKey<Block> DRYADS_SPAWNABLE_ON = tag("dryads_spawnable_on");
        TagKey<Block> WIZARDS_AUTUMN_LEAVES = tag("wizards_autumn_leaves");
        TagKey<Block> ETHERIUM_PROVIDERS = tag("etherium_providers");
        TagKey<Block> ETHERIUM_CONSUMERS = tag("etherium_consumers");

        private static TagKey<Block> cTag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", name));
        }

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, ArsMagicaApi.id(name));
        }
    }

    @ApiStatus.NonExtendable
    interface Items {
        TagKey<Item> ORES_CHIMERITE = cTag("ores/chimerite");
        TagKey<Item> ORES_TOPAZ = cTag("ores/topaz");
        TagKey<Item> ORES_VINTEUM = cTag("ores/vinteum");
        TagKey<Item> ORES_MOONSTONE = cTag("ores/moonstone");
        TagKey<Item> ORES_SUNSTONE = cTag("ores/sunstone");
        TagKey<Item> STORAGE_BLOCKS_CHIMERITE = cTag("storage_blocks/chimerite");
        TagKey<Item> STORAGE_BLOCKS_TOPAZ = cTag("storage_blocks/topaz");
        TagKey<Item> STORAGE_BLOCKS_VINTEUM = cTag("storage_blocks/vinteum");
        TagKey<Item> STORAGE_BLOCKS_MOONSTONE = cTag("storage_blocks/moonstone");
        TagKey<Item> STORAGE_BLOCKS_SUNSTONE = cTag("storage_blocks/sunstone");
        TagKey<Item> GEMS_CHIMERITE = cTag("gems/chimerite");
        TagKey<Item> GEMS_TOPAZ = cTag("gems/topaz");
        TagKey<Item> DUSTS_VINTEUM = cTag("dusts/vinteum");
        TagKey<Item> GEMS_MOONSTONE = cTag("gems/moonstone");
        TagKey<Item> GEMS_SUNSTONE = cTag("gems/sunstone");
        TagKey<Item> DUSTS_ARCANE_COMPOUND = cTag("dusts/arcane_compound");
        TagKey<Item> DUSTS_ARCANE_ASH = cTag("dusts/arcane_ash");
        TagKey<Item> DUSTS_PURIFIED_VINTEUM = cTag("dusts/purified_vinteum");
        TagKey<Item> WITCHWOOD_LOGS = tag("witchwood_logs");
        TagKey<Item> MAGITECH_GOGGLES_REPAIR_ITEMS = tag("repair_items/magitech_goggles");
        TagKey<Item> MAGE_ARMOR_REPAIR_ITEMS = tag("repair_items/mage_armor");
        TagKey<Item> BATTLEMAGE_ARMOR_REPAIR_ITEMS = tag("repair_items/battlemage_armor");
        TagKey<Item> ARCANE_COMPENDIUM_BOOKS = tag("arcane_compendium_books");
        TagKey<Item> INSCRIPTION_TABLE_BOOKS = tag("inscription_table_books");
        TagKey<Item> OCCULUS_FORGET_ALL = tag("occulus_forget_all");
        TagKey<Item> RUNES = tag("runes");
        TagKey<Item> SHOWS_BARS_LAYER = tag("shows_bars_layer");
        TagKey<Item> SHOWS_SPELL_VISUALS = tag("shows_spell_visuals");
        TagKey<Item> SPELLCRAFTING_START = tag("spellcrafting_start");
        TagKey<Item> SPELLCRAFTING_END = tag("spellcrafting_end");

        private static TagKey<Item> cTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", name));
        }

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ArsMagicaApi.id(name));
        }
    }

    @ApiStatus.NonExtendable
    interface EntityTypes {
        TagKey<EntityType<?>> BLACK_AUREM_IMMUNE = tag("black_aurem_immune");
        TagKey<EntityType<?>> AFFECTED_BY_ENDER_THORNS_ABILITY = tag("affected_by_ender_thorns_ability");
        TagKey<EntityType<?>> AFFECTED_BY_SMITE_ABILITY = tag("affected_by_smite_ability");
        TagKey<EntityType<?>> AFFECTED_BY_NAUSEA_ABILITY = tag("affected_by_nausea_ability");
        TagKey<EntityType<?>> ENDER_GUARDIAN_SACRIFICES = tag("ender_guardian_sacrifices");
        TagKey<EntityType<?>> SUMMONING_NOT_SUPPORTED = tag("summoning_not_supported");

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ArsMagicaApi.id(name));
        }
    }

    @ApiStatus.NonExtendable
    interface DamageTypes {
        TagKey<DamageType> AFFECTED_BY_FIRE_RESISTANCE_ABILITY = tag("affected_by_fire_resistance_ability");
        TagKey<DamageType> AFFECTED_BY_RESISTANCE_ABILITY = tag("affected_by_resistance_ability");
        TagKey<DamageType> AFFECTED_BY_FALL_DAMAGE_ABILITY = tag("affected_by_fall_damage_ability");
        TagKey<DamageType> AFFECTED_BY_FEATHER_FALLING_ABILITY = tag("affected_by_feather_falling_ability");
        TagKey<DamageType> AFFECTED_BY_MAGIC_DAMAGE_ABILITY = tag("affected_by_magic_damage_ability");
        TagKey<DamageType> BYPASSES_SHIELD_OVERLOAD = tag("bypasses_shield_overload");
        TagKey<DamageType> IS_SPELL = tag("is_spell");
        TagKey<DamageType> WATER_GUARDIAN_IS_VULNERABLE_TO = tag("is_vulnerable_to/water_guardian");
        TagKey<DamageType> WATER_GUARDIAN_IS_IMMUNE_TO = tag("is_immune_to/water_guardian");
        TagKey<DamageType> WATER_GUARDIAN_IS_HEAL_TO = tag("is_heal_to/water_guardian");
        TagKey<DamageType> FIRE_GUARDIAN_IS_VULNERABLE_TO = tag("is_vulnerable_to/fire_guardian");
        TagKey<DamageType> FIRE_GUARDIAN_IS_IMMUNE_TO = tag("is_immune_to/fire_guardian");
        TagKey<DamageType> FIRE_GUARDIAN_IS_HEAL_TO = tag("is_heal_to/fire_guardian");
        TagKey<DamageType> EARTH_GUARDIAN_IS_VULNERABLE_TO = tag("is_vulnerable_to/earth_guardian");
        TagKey<DamageType> EARTH_GUARDIAN_IS_IMMUNE_TO = tag("is_immune_to/earth_guardian");
        TagKey<DamageType> EARTH_GUARDIAN_IS_HEAL_TO = tag("is_heal_to/earth_guardian");
        TagKey<DamageType> AIR_GUARDIAN_IS_VULNERABLE_TO = tag("is_vulnerable_to/air_guardian");
        TagKey<DamageType> AIR_GUARDIAN_IS_IMMUNE_TO = tag("is_immune_to/air_guardian");
        TagKey<DamageType> AIR_GUARDIAN_IS_HEAL_TO = tag("is_heal_to/air_guardian");
        TagKey<DamageType> ICE_GUARDIAN_IS_VULNERABLE_TO = tag("is_vulnerable_to/ice_guardian");
        TagKey<DamageType> ICE_GUARDIAN_IS_IMMUNE_TO = tag("is_immune_to/ice_guardian");
        TagKey<DamageType> ICE_GUARDIAN_IS_HEAL_TO = tag("is_heal_to/ice_guardian");
        TagKey<DamageType> LIGHTNING_GUARDIAN_IS_VULNERABLE_TO = tag("is_vulnerable_to/lightning_guardian");
        TagKey<DamageType> LIGHTNING_GUARDIAN_IS_IMMUNE_TO = tag("is_immune_to/lightning_guardian");
        TagKey<DamageType> LIGHTNING_GUARDIAN_IS_HEAL_TO = tag("is_heal_to/lightning_guardian");
        TagKey<DamageType> NATURE_GUARDIAN_IS_VULNERABLE_TO = tag("is_vulnerable_to/nature_guardian");
        TagKey<DamageType> NATURE_GUARDIAN_IS_IMMUNE_TO = tag("is_immune_to/nature_guardian");
        TagKey<DamageType> NATURE_GUARDIAN_IS_HEAL_TO = tag("is_heal_to/nature_guardian");
        TagKey<DamageType> LIFE_GUARDIAN_IS_VULNERABLE_TO = tag("is_vulnerable_to/life_guardian");
        TagKey<DamageType> LIFE_GUARDIAN_IS_IMMUNE_TO = tag("is_immune_to/life_guardian");
        TagKey<DamageType> LIFE_GUARDIAN_IS_HEAL_TO = tag("is_heal_to/life_guardian");
        TagKey<DamageType> ARCANE_GUARDIAN_IS_VULNERABLE_TO = tag("is_vulnerable_to/arcane_guardian");
        TagKey<DamageType> ARCANE_GUARDIAN_IS_IMMUNE_TO = tag("is_immune_to/arcane_guardian");
        TagKey<DamageType> ARCANE_GUARDIAN_IS_HEAL_TO = tag("is_heal_to/arcane_guardian");
        TagKey<DamageType> ENDER_GUARDIAN_IS_VULNERABLE_TO = tag("is_vulnerable_to/ender_guardian");
        TagKey<DamageType> ENDER_GUARDIAN_IS_IMMUNE_TO = tag("is_immune_to/ender_guardian");
        TagKey<DamageType> ENDER_GUARDIAN_IS_HEAL_TO = tag("is_heal_to/ender_guardian");

        private static TagKey<DamageType> tag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, ArsMagicaApi.id(name));
        }
    }

    @ApiStatus.NonExtendable
    interface Biomes {
        TagKey<Biome> CAN_SUMMON_WATER_GUARDIAN = tag("can_summon_water_guardian");

        @SuppressWarnings("SameParameterValue")
        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, ArsMagicaApi.id(name));
        }
    }
}
