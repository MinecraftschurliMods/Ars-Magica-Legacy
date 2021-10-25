package com.github.minecraftschurli.arsmagicalegacy.common.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 */
public class ColoredRegistryObject<B extends IForgeRegistryEntry<B>, T extends B> {
    Map<DyeColor, RegistryObject<T>> map = new EnumMap<>(DyeColor.class);

    ColoredRegistryObject(DeferredRegister<B> register, String suffix, Function<DyeColor, ? extends T> creator) {
        for (DyeColor color : DyeColor.values()) {
            map.put(color, register.register(color.getName() + "_" + suffix, () -> creator.apply(color)));
        }
    }

    public RegistryObject<T> registryObject(DyeColor color) {
        return map.get(color);
    }

    public T get(DyeColor color) {
        return map.get(color).get();
    }

    public ResourceLocation getId(DyeColor color) {
        return map.get(color).getId();
    }
}
