package com.github.minecraftschurli.arsmagicalegacy.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

/**
 * Class holding the reference classes for all non forge/vanilla tags used by this mod
 */
public final class AMTags {
    private static final String FORGE = "forge";

    private AMTags() {
    }

    /**
     * Holder class for all {@link Item} {@link Tag Tags}
     */
    public static final class Items {
        public static final Tag.Named<Item> WITCHWOOD_LOGS = tag("witchwood_logs");
        public static final Tag.Named<Item> ORES_CHIMERITE = forgeTag("ores/chimerite");
        public static final Tag.Named<Item> ORES_TOPAZ = forgeTag("ores/topaz");
        public static final Tag.Named<Item> ORES_VINTEUM = forgeTag("ores/vinteum");
        public static final Tag.Named<Item> ORES_MOONSTONE = forgeTag("ores/moonstone");
        public static final Tag.Named<Item> ORES_SUNSTONE = forgeTag("ores/sunstone");
        public static final Tag.Named<Item> STORAGE_BLOCKS_CHIMERITE = forgeTag("storage_blocks/chimerite");
        public static final Tag.Named<Item> STORAGE_BLOCKS_TOPAZ = forgeTag("storage_blocks/topaz");
        public static final Tag.Named<Item> STORAGE_BLOCKS_VINTEUM = forgeTag("storage_blocks/vinteum");
        public static final Tag.Named<Item> STORAGE_BLOCKS_MOONSTONE = forgeTag("storage_blocks/moonstone");
        public static final Tag.Named<Item> STORAGE_BLOCKS_SUNSTONE = forgeTag("storage_blocks/sunstone");
        public static final Tag.Named<Item> GEMS_CHIMERITE = forgeTag("gems/chimerite");
        public static final Tag.Named<Item> GEMS_TOPAZ = forgeTag("gems/topaz");
        public static final Tag.Named<Item> DUSTS_VINTEUM = forgeTag("dusts/vinteum");
        public static final Tag.Named<Item> GEMS_MOONSTONE = forgeTag("gems/moonstone");
        public static final Tag.Named<Item> GEMS_SUNSTONE = forgeTag("gems/sunstone");
        public static final Tag.Named<Item> DUSTS_ARCANE_COMPOUND = forgeTag("dusts/arcane_compound");
        public static final Tag.Named<Item> DUSTS_ARCANE_ASH = forgeTag("dusts/arcane_ash");
        public static final Tag.Named<Item> DUSTS_PURIFIED_VINTEUM = forgeTag("dusts/purified_vinteum");
        public static final Tag.Named<Item> RUNES = tag("runes");
        public static final Tag.Named<Item> RUNES_COLORLESS = tag("runes/colorless");
        public static final Tag.Named<Item> RUNES_BLACK = tag("runes/black");
        public static final Tag.Named<Item> RUNES_BLUE = tag("runes/blue");
        public static final Tag.Named<Item> RUNES_BROWN = tag("runes/brown");
        public static final Tag.Named<Item> RUNES_CYAN = tag("runes/cyan");
        public static final Tag.Named<Item> RUNES_GRAY = tag("runes/gray");
        public static final Tag.Named<Item> RUNES_GREEN = tag("runes/green");
        public static final Tag.Named<Item> RUNES_LIGHT_BLUE = tag("runes/light_blue");
        public static final Tag.Named<Item> RUNES_LIGHT_GRAY = tag("runes/light_gray");
        public static final Tag.Named<Item> RUNES_LIME = tag("runes/lime");
        public static final Tag.Named<Item> RUNES_MAGENTA = tag("runes/magenta");
        public static final Tag.Named<Item> RUNES_ORANGE = tag("runes/orange");
        public static final Tag.Named<Item> RUNES_PINK = tag("runes/pink");
        public static final Tag.Named<Item> RUNES_PURPLE = tag("runes/purple");
        public static final Tag.Named<Item> RUNES_RED = tag("runes/red");
        public static final Tag.Named<Item> RUNES_WHITE = tag("runes/white");
        public static final Tag.Named<Item> RUNES_YELLOW = tag("runes/yellow");

        private static IOptionalNamedTag<Item> forgeTag(String name) {
            return ItemTags.createOptional(new ResourceLocation(FORGE, name));
        }

        private static IOptionalNamedTag<Item> tag(String name) {
            return ItemTags.createOptional(new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
        }
    }

    /**
     * Holder class for all {@link Block} {@link Tag Tags}
     */
    public static final class Blocks {
        public static final Tag.Named<Block> WITCHWOOD_LOGS = tag("witchwood_logs");
        public static final Tag.Named<Block> ORES_CHIMERITE = forgeTag("ores/chimerite");
        public static final Tag.Named<Block> ORES_TOPAZ = forgeTag("ores/topaz");
        public static final Tag.Named<Block> ORES_VINTEUM = forgeTag("ores/vinteum");
        public static final Tag.Named<Block> ORES_MOONSTONE = forgeTag("ores/moonstone");
        public static final Tag.Named<Block> ORES_SUNSTONE = forgeTag("ores/sunstone");
        public static final Tag.Named<Block> STORAGE_BLOCKS_CHIMERITE = forgeTag("storage_blocks/chimerite");
        public static final Tag.Named<Block> STORAGE_BLOCKS_TOPAZ = forgeTag("storage_blocks/topaz");
        public static final Tag.Named<Block> STORAGE_BLOCKS_VINTEUM = forgeTag("storage_blocks/vinteum");
        public static final Tag.Named<Block> STORAGE_BLOCKS_MOONSTONE = forgeTag("storage_blocks/moonstone");
        public static final Tag.Named<Block> STORAGE_BLOCKS_SUNSTONE = forgeTag("storage_blocks/sunstone");

        private static IOptionalNamedTag<Block> forgeTag(String name) {
            return BlockTags.createOptional(new ResourceLocation(FORGE, name));
        }

        private static IOptionalNamedTag<Block> tag(String name) {
            return BlockTags.createOptional(new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
        }
    }

    /**
     * Holder class for all {@link Fluid} {@link Tag Tags}
     */
    public static final class Fluids {
        private static IOptionalNamedTag<Fluid> forgeTag(String name) {
            return FluidTags.createOptional(new ResourceLocation(FORGE, name));
        }

        private static IOptionalNamedTag<Fluid> tag(String name) {
            return FluidTags.createOptional(new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
        }
    }

    /**
     * Holder class for all {@link EntityType} {@link Tag Tags}
     */
    public static final class EntityTypes {
        private static IOptionalNamedTag<EntityType<?>> forgeTag(String name) {
            return EntityTypeTags.createOptional(new ResourceLocation(FORGE, name));
        }

        private static IOptionalNamedTag<EntityType<?>> tag(String name) {
            return EntityTypeTags.createOptional(new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
        }
    }
}
