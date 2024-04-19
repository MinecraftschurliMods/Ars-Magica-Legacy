package com.github.minecraftschurlimods.arsmagicalegacy.common.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public class ColoredDeferredHolder<B, T extends B> {
    private final Map<DyeColor, DeferredHolder<B, T>> map = new EnumMap<>(DyeColor.class);

    public ColoredDeferredHolder(DeferredRegister<B> register, String suffix, Function<DyeColor, ? extends T> creator) {
        for (DyeColor color : DyeColor.values()) {
            map.put(color, register.register(color.getName() + "_" + suffix, () -> creator.apply(color)));
        }
    }

    /**
     * @param color The color to get the rune item's registry object for.
     * @return The registry object of the rune item of the given color.
     */
    public DeferredHolder<B, T> registryObject(DyeColor color) {
        return map.get(color);
    }

    /**
     * @param color The color to get the registry object's value for.
     * @return The registry object's value of the given color.
     */
    public T get(DyeColor color) {
        return map.get(color).get();
    }

    /**
     * @param color The color to get the registry object's id for.
     * @return The registry object's id of the given color.
     */
    public ResourceLocation getId(DyeColor color) {
        return map.get(color).getId();
    }
}
