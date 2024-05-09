package com.github.minecraftschurlimods.arsmagicalegacy.api;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

/**
 * Class holding the references for all tags added by this mod.
 */
public final class AMTags {
    private static final String C = "c";

    private AMTags() {}

    /**
     * Holds the mod's item tags.
     */
    public static final class Items {
        public static final TagKey<Item> WITCHWOOD_LOGS = tag("witchwood_logs");
        public static final TagKey<Item> ORES_CHIMERITE = cTag("ores/chimerite");
        public static final TagKey<Item> ORES_TOPAZ = cTag("ores/topaz");
        public static final TagKey<Item> ORES_VINTEUM = cTag("ores/vinteum");
        public static final TagKey<Item> ORES_MOONSTONE = cTag("ores/moonstone");
        public static final TagKey<Item> ORES_SUNSTONE = cTag("ores/sunstone");
        public static final TagKey<Item> STORAGE_BLOCKS_CHIMERITE = cTag("storage_blocks/chimerite");
        public static final TagKey<Item> STORAGE_BLOCKS_TOPAZ = cTag("storage_blocks/topaz");
        public static final TagKey<Item> STORAGE_BLOCKS_VINTEUM = cTag("storage_blocks/vinteum");
        public static final TagKey<Item> STORAGE_BLOCKS_MOONSTONE = cTag("storage_blocks/moonstone");
        public static final TagKey<Item> STORAGE_BLOCKS_SUNSTONE = cTag("storage_blocks/sunstone");
        public static final TagKey<Item> GEMS_CHIMERITE = cTag("gems/chimerite");
        public static final TagKey<Item> GEMS_TOPAZ = cTag("gems/topaz");
        public static final TagKey<Item> DUSTS_VINTEUM = cTag("dusts/vinteum");
        public static final TagKey<Item> GEMS_MOONSTONE = cTag("gems/moonstone");
        public static final TagKey<Item> GEMS_SUNSTONE = cTag("gems/sunstone");
        public static final TagKey<Item> DUSTS_ARCANE_COMPOUND = cTag("dusts/arcane_compound");
        public static final TagKey<Item> DUSTS_ARCANE_ASH = cTag("dusts/arcane_ash");
        public static final TagKey<Item> DUSTS_PURIFIED_VINTEUM = cTag("dusts/purified_vinteum");
        public static final TagKey<Item> RUNES = tag("runes");
        public static final TagKey<Item> RUNES_COLORLESS = tag("runes/colorless");
        public static final TagKey<Item> RUNES_BLACK = tag("runes/black");
        public static final TagKey<Item> RUNES_BLUE = tag("runes/blue");
        public static final TagKey<Item> RUNES_BROWN = tag("runes/brown");
        public static final TagKey<Item> RUNES_CYAN = tag("runes/cyan");
        public static final TagKey<Item> RUNES_GRAY = tag("runes/gray");
        public static final TagKey<Item> RUNES_GREEN = tag("runes/green");
        public static final TagKey<Item> RUNES_LIGHT_BLUE = tag("runes/light_blue");
        public static final TagKey<Item> RUNES_LIGHT_GRAY = tag("runes/light_gray");
        public static final TagKey<Item> RUNES_LIME = tag("runes/lime");
        public static final TagKey<Item> RUNES_MAGENTA = tag("runes/magenta");
        public static final TagKey<Item> RUNES_ORANGE = tag("runes/orange");
        public static final TagKey<Item> RUNES_PINK = tag("runes/pink");
        public static final TagKey<Item> RUNES_PURPLE = tag("runes/purple");
        public static final TagKey<Item> RUNES_RED = tag("runes/red");
        public static final TagKey<Item> RUNES_WHITE = tag("runes/white");
        public static final TagKey<Item> RUNES_YELLOW = tag("runes/yellow");
        public static final TagKey<Item> INSCRIPTION_TABLE_BOOKS = tag("inscription_table_books");
        public static final TagKey<Item> SPELLCRAFTING_START = tag("spellcrafting_start");
        public static final TagKey<Item> SPELLCRAFTING_END = tag("spellcrafting_end");

        private static TagKey<Item> cTag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(C, name));
        }

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
        }
    }

    /**
     * Holds the mod's block tags.
     */
    public static final class Blocks {
        public static final TagKey<Block> WITCHWOOD_LOGS = tag("witchwood_logs");
        public static final TagKey<Block> ORES_CHIMERITE = cTag("ores/chimerite");
        public static final TagKey<Block> ORES_TOPAZ = cTag("ores/topaz");
        public static final TagKey<Block> ORES_VINTEUM = cTag("ores/vinteum");
        public static final TagKey<Block> ORES_MOONSTONE = cTag("ores/moonstone");
        public static final TagKey<Block> ORES_SUNSTONE = cTag("ores/sunstone");
        public static final TagKey<Block> STORAGE_BLOCKS_CHIMERITE = cTag("storage_blocks/chimerite");
        public static final TagKey<Block> STORAGE_BLOCKS_TOPAZ = cTag("storage_blocks/topaz");
        public static final TagKey<Block> STORAGE_BLOCKS_VINTEUM = cTag("storage_blocks/vinteum");
        public static final TagKey<Block> STORAGE_BLOCKS_MOONSTONE = cTag("storage_blocks/moonstone");
        public static final TagKey<Block> STORAGE_BLOCKS_SUNSTONE = cTag("storage_blocks/sunstone");

        private static TagKey<Block> cTag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(C, name));
        }

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
        }
    }

    /**
     * Holds the mod's fluid tags.
     */
    public static final class Fluids {
    }

    /**
     * Holds the mod's entity type tags.
     */
    public static final class EntityTypes {
    }

    /**
     * Holds the mod's biome tags.
     */
    public static final class Biomes {
        public static final TagKey<Biome> CAN_SPAWN_WATER_GUARDIAN = tag("can_spawn_water_guardian");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
        }
    }
}
