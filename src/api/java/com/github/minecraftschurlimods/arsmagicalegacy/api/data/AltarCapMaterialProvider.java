package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractDatapackRegistryProvider;

public abstract class AltarCapMaterialProvider extends AbstractDatapackRegistryProvider<AltarCapMaterial> {
    protected AltarCapMaterialProvider(String namespace) {
        super(AltarCapMaterial.REGISTRY_KEY, namespace);
    }
}
