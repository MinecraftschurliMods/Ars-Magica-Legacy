package com.github.minecraftschurlimods.arsmagicalegacy.common.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public class ColoredRegistryObject<B extends IForgeRegistryEntry<B>, T extends B> {
    private final Map<DyeColor, RegistryObject<T>> map = new EnumMap<>(DyeColor.class);

    /**
     * Creates a new colored registry object group.
     *
     * @param register The registry to use.
     * @param suffix   The suffix of the items, without the underscore.
     * @param creator  The item creator.
     */
    public ColoredRegistryObject(DeferredRegister<B> register, String suffix, Function<DyeColor, ? extends T> creator) {
        for (DyeColor color : DyeColor.values()) {
            map.put(color, register.register(color.getName() + "_" + suffix, () -> creator.apply(color)));
        }
    }

    /**
     * Returns the registry object for the given color.
     *
     * @param color The color to get the registry object for.
     * @return The registry object for the given color.
     */
    public RegistryObject<T> registryObject(DyeColor color) {
        return map.get(color);
    }

    /**
     * Returns the registry object content for the given color.
     *
     * @param color The color to get the registry object content for.
     * @return The registry object content for the given color.
     */
    public T get(DyeColor color) {
        return map.get(color).get();
    }

    /**
     * Returns the registry object id for the given color.
     *
     * @param color The color to get the registry object id for.
     * @return The registry object id for the given color.
     */
    public ResourceLocation getId(DyeColor color) {
        return map.get(color).getId();
    }
}
