package com.github.minecraftschurli.arsmagicalegacy.api.skill;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

/**
 * Interface representing an occulus tab manager.
 */
public interface IOcculusTabManager {
    /**
     * Get the {@link IOcculusTab} for the id or empty.
     *
     * @param id the id of the requested {@link IOcculusTab}
     * @return an {@link Optional} of the requested {@link IOcculusTab} or {@link Optional#empty()} if it is not loaded
     */
    Optional<IOcculusTab> getOptional(ResourceLocation id);

    /**
     * Get the {@link IOcculusTab} for the id or null.
     *
     * @param id the id of the requested {@link IOcculusTab}
     * @return the requested {@link IOcculusTab} or null if it is not loaded
     */
    @Nullable
    IOcculusTab getNullable(ResourceLocation id);

    /**
     * Get the {@link IOcculusTab} for the id or throw.
     *
     * @param id the id of the requested {@link IOcculusTab}
     * @return the requested {@link IOcculusTab}
     */
    IOcculusTab get(ResourceLocation id);

    /**
     * Get an unmodifiable collection of all tabs.
     *
     * @return the collection of all tabs
     */
    Collection<IOcculusTab> getTabs();

    /**
     * Get the {@link IOcculusTab} by index.
     *
     * @param index the index to fetch
     * @return the {@link IOcculusTab} at the index
     * @throws ArrayIndexOutOfBoundsException if the index is out of bounds
     */
    IOcculusTab getByIndex(int index) throws ArrayIndexOutOfBoundsException;
}
