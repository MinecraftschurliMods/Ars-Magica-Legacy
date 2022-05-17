package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @param <T> The type of the object.
 */
public interface RegistryObject<T> extends Supplier<T> {
    /**
     * @return the {@link ResourceKey} for this registry object.
     */
    ResourceKey<T> getResourceKey();

    /**
     * @return the {@link ResourceLocation id} for this registry object.
     */
    ResourceLocation getId();

    /**
     * @return the actual registered object.
     */
    @Override
    T get();

    /**
     * @return the {@link Holder} for this registry object.
     */
    Holder<T> asHolder();

    /**
     * Lazy map this registry object to the target type using the given mapping function.
     *
     * @param mappingFunction the mapping function
     * @return the supplier representing the lazily mapped object
     * @param <U> The target type.
     */
    default <U> Supplier<U> lazyMap(Function<T, U> mappingFunction) {
        return () -> mappingFunction.apply(get());
    }

    /**
     * Map this registry object to the target type using the given mapping function.
     *
     * @param mappingFunction the mapping function
     * @return the optional representing the mapped object
     * @param <U>
     */
    default <U> Optional<U> map(Function<T, U> mappingFunction) {
        return Optional.ofNullable(mappingFunction.apply(get()));
    }

    /**
     * @return whether this registry object is present.
     */
    boolean isPresent();
}
