package com.github.minecraftschurlimods.arsmagicalegacy.api.util;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

/**
 * Interface representing an object that is registered in a registry.
 */
public interface IRegistryEntry {
    /**
     * Gets the registry name of this object.
     *
     * @return the registry name
     */
    default ResourceLocation getRegistryName() {
        ResourceKey<? extends Registry<? extends IRegistryEntry>> registryKey = getRegistryKey();
        Optional<? extends Registry<IRegistryEntry>> registry = RegistryAccess.BUILTIN.get().registry(registryKey);
        if (registry.isEmpty()) throw new IllegalStateException("Registry " + registryKey + " not found");
        ResourceLocation registryName = registry.get().getKey(this);
        if (registryName == null) throw new IllegalStateException("Object not registered in registry " + registryKey);
        return registryName;
    }

    /**
     * Gets the key of the registry of this object.
     *
     * @return the registry key
     */
    ResourceKey<? extends Registry<? extends IRegistryEntry>> getRegistryKey();
}
