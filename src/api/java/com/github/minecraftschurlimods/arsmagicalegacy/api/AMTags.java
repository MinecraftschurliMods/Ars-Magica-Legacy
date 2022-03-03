package com.github.minecraftschurlimods.arsmagicalegacy.api;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

/**
 * Class holding the references for all tags added by this mod.
 */
public final class AMTags {
    private static final String FORGE = "forge";

    private AMTags() {
    }

    /**
     * Holds the mod's item tags.
     */
    public static final class Items {
        public static final TagKey<Item> WITCHWOOD_LOGS = tag("witchwood_logs");
        public static final TagKey<Item> ORES_CHIMERITE = forgeTag("ores/chimerite");
        public static final TagKey<Item> ORES_TOPAZ = forgeTag("ores/topaz");
        public static final TagKey<Item> ORES_VINTEUM = forgeTag("ores/vinteum");
        public static final TagKey<Item> ORES_MOONSTONE = forgeTag("ores/moonstone");
        public static final TagKey<Item> ORES_SUNSTONE = forgeTag("ores/sunstone");
        public static final TagKey<Item> STORAGE_BLOCKS_CHIMERITE = forgeTag("storage_blocks/chimerite");
        public static final TagKey<Item> STORAGE_BLOCKS_TOPAZ = forgeTag("storage_blocks/topaz");
        public static final TagKey<Item> STORAGE_BLOCKS_VINTEUM = forgeTag("storage_blocks/vinteum");
        public static final TagKey<Item> STORAGE_BLOCKS_MOONSTONE = forgeTag("storage_blocks/moonstone");
        public static final TagKey<Item> STORAGE_BLOCKS_SUNSTONE = forgeTag("storage_blocks/sunstone");
        public static final TagKey<Item> GEMS_CHIMERITE = forgeTag("gems/chimerite");
        public static final TagKey<Item> GEMS_TOPAZ = forgeTag("gems/topaz");
        public static final TagKey<Item> DUSTS_VINTEUM = forgeTag("dusts/vinteum");
        public static final TagKey<Item> GEMS_MOONSTONE = forgeTag("gems/moonstone");
        public static final TagKey<Item> GEMS_SUNSTONE = forgeTag("gems/sunstone");
        public static final TagKey<Item> DUSTS_ARCANE_COMPOUND = forgeTag("dusts/arcane_compound");
        public static final TagKey<Item> DUSTS_ARCANE_ASH = forgeTag("dusts/arcane_ash");
        public static final TagKey<Item> DUSTS_PURIFIED_VINTEUM = forgeTag("dusts/purified_vinteum");
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

        private static TagKey<Item> forgeTag(String name) {
            return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(FORGE, name));
        }

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
        }
    }

    /**
     * Holds the mod's block tags.
     */
    public static final class Blocks {
        public static final TagKey<Block> WITCHWOOD_LOGS = tag("witchwood_logs");
        public static final TagKey<Block> ORES_CHIMERITE = forgeTag("ores/chimerite");
        public static final TagKey<Block> ORES_TOPAZ = forgeTag("ores/topaz");
        public static final TagKey<Block> ORES_VINTEUM = forgeTag("ores/vinteum");
        public static final TagKey<Block> ORES_MOONSTONE = forgeTag("ores/moonstone");
        public static final TagKey<Block> ORES_SUNSTONE = forgeTag("ores/sunstone");
        public static final TagKey<Block> STORAGE_BLOCKS_CHIMERITE = forgeTag("storage_blocks/chimerite");
        public static final TagKey<Block> STORAGE_BLOCKS_TOPAZ = forgeTag("storage_blocks/topaz");
        public static final TagKey<Block> STORAGE_BLOCKS_VINTEUM = forgeTag("storage_blocks/vinteum");
        public static final TagKey<Block> STORAGE_BLOCKS_MOONSTONE = forgeTag("storage_blocks/moonstone");
        public static final TagKey<Block> STORAGE_BLOCKS_SUNSTONE = forgeTag("storage_blocks/sunstone");

        private static TagKey<Block> forgeTag(String name) {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(FORGE, name));
        }

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
        }
    }

    /**
     * Holds the mod's fluid tags.
     */
    public static final class Fluids {
        private static TagKey<Fluid> forgeTag(String name) {
            return TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation(FORGE, name));
        }

        private static TagKey<Fluid> tag(String name) {
            return TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
        }
    }

    /**
     * Holds the mod's entity type tags.
     */
    public static final class EntityTypes {
        private static TagKey<EntityType<?>> forgeTag(String name) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(FORGE, name));
        }

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
        }
    }
}
