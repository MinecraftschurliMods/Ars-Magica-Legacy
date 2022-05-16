package com.github.minecraftschurlimods.arsmagicalegacy.api.util;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

/**
 * Interface representing a data manager.
 */
public interface IDataManager<T> extends Map<ResourceLocation, T> {
    /**
     * @param id The id of the requested value.
     * @return An optional of the requested value, or an empty optional if it is not loaded.
     */
    Optional<T> getOptional(ResourceLocation id);

    /**
     * @param id The id of the requested value.
     * @return The requested value. Throws an exception if it is not loaded.
     */
    T get(ResourceLocation id);

    /**
     * @param id The id of the requested value.
     * @return An optional of the requested value, or null if it is not loaded.
     */
    @Nullable T getNullable(ResourceLocation id);
}
