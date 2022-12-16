package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.ObeliskFuel;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractDatapackRegistryProvider;

public abstract class ObeliskFuelProvider extends AbstractDatapackRegistryProvider<ObeliskFuel> {
    protected ObeliskFuelProvider(String namespace) {
        super(ObeliskFuel.REGISTRY_KEY, namespace);
    }
}
