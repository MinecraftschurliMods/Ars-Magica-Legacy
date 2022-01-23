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
     * Get the value for the id or empty.
     *
     * @param id the id of the requested value
     * @return an {@link Optional} of the requested value or {@link Optional#empty()} if it is not loaded
     */
    Optional<T> getOptional(ResourceLocation id);

    /**
     * Get the value for the id or throw.
     *
     * @param id the id of the requested value
     * @return the requested value
     */
    T get(ResourceLocation id);

    /**
     * Get the value for the id or null.
     *
     * @param id the id of the requested value
     * @return the requested value or null if it is not loaded
     */
    T getNullable(ResourceLocation id);
}
