package com.github.minecraftschurli.arsmagicalegacy.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

/**
 * @author Minecraftschurli
 * @version 2021-08-04
 */
public final class Tags {
    private static final String FORGE = "forge";

    private Tags() {}

    /**
     * Holder class for all {@link Item} {@link Tag Tags}
     */
    public static final class Items {
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
