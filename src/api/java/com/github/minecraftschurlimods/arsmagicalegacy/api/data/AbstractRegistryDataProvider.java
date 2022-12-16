package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.easydatagenlib.api.AbstractDatapackRegistryProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public abstract class AbstractRegistryDataProvider<T> extends AbstractDatapackRegistryProvider<T> {
    public AbstractRegistryDataProvider(ResourceKey<? extends Registry<T>> registryKey, String namespace) {
        super(registryKey, namespace);
    }
}
