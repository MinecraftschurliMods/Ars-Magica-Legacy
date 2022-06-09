package com.github.minecraftschurlimods.arsmagicalegacy.api.occulus;

import com.github.minecraftschurlimods.arsmagicalegacy.api.util.IDataManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface representing an occulus tab manager.
 */
public interface IOcculusTabManager extends IDataManager<IOcculusTab> {
    /**
     * @param id The id of the occulus tab to get.
     * @return An optional of the occulus tab, or an empty optional if the occulus tab is not loaded.
     */
    Optional<IOcculusTab> getOptional(ResourceLocation id);

    /**
     * @param id The id of the occulus tab to get.
     * @return The occulus tab, or null if the occulus tab is not loaded.
     */
    @Nullable
    IOcculusTab getNullable(ResourceLocation id);

    /**
     * @param id The id of the occulus tab to get.
     * @return The occulus tab. Throws an exception if the occulus tab is not loaded.
     */
    IOcculusTab get(ResourceLocation id);

    /**
     * @return An unmodifiable collection of all tabs.
     */
    Collection<IOcculusTab> getTabs();

    /**
     * @param index The index to get the occulus tab for.
     * @return The occulus tab at the given index.
     * @throws ArrayIndexOutOfBoundsException If no occulus tab exists by that index.
     */
    IOcculusTab getByIndex(int index) throws ArrayIndexOutOfBoundsException;
}
