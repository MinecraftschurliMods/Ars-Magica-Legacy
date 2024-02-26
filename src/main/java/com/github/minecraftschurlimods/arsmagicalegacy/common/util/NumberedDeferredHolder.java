package com.github.minecraftschurlimods.arsmagicalegacy.common.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collections;
import java.util.Map;
import java.util.function.IntFunction;

public class NumberedDeferredHolder<B, T extends B> {
    private final Map<Integer, DeferredHolder<B, T>> map = new Int2ObjectOpenHashMap<>();

    public NumberedDeferredHolder(DeferredRegister<B> register, int count, String prefix, IntFunction<? extends T> creator) {
        for (int i = 0; i < count; i++) {
            final int j = i; // I hate Java sometimes; it cries about effectively final in the lambda so this is needed
            map.put(i, register.register(prefix + "_" + i, () -> creator.apply(j)));
        }
    }

    /**
     * @param i The index of the registry object to get.
     * @return The i-th registry object.
     */
    public DeferredHolder<B, T> registryObject(int i) {
        return map.get(i);
    }

    /**
     * @param i The index of the registry object value to get.
     * @return The i-th registry object value.
     */
    public T get(int i) {
        return map.get(i).get();
    }

    /**
     * @param i The index of the registry object id to get.
     * @return The i-th registry object id.
     */
    public ResourceLocation getId(int i) {
        return map.get(i).getId();
    }

    /**
     * @param random The random source to use.
     * @return A random registry object.
     */
    public DeferredHolder<B, T> randomRegistryObject(RandomSource random) {
        return map.get(random.nextInt(map.size()));
    }

    /**
     * @param random The random source to use.
     * @return A random registry object value.
     */
    public T random(RandomSource random) {
        return map.get(random.nextInt(map.size())).get();
    }

    /**
     * @param random The random source to use.
     * @return A random registry object id.
     */
    public ResourceLocation randomId(RandomSource random) {
        return map.get(random.nextInt(map.size())).getId();
    }

    /**
     * @return An unmodifiable view of all values this registry object wraps.
     */
    public Map<Integer, DeferredHolder<B, T>> values() {
        return Collections.unmodifiableMap(map);
    }
}
