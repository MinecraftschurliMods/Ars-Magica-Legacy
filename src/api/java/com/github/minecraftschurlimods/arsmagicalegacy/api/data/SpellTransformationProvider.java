package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellTransformation;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractDatapackRegistryProvider;

public abstract class SpellTransformationProvider extends AbstractDatapackRegistryProvider<SpellTransformation> {
    protected SpellTransformationProvider(String namespace) {
        super(SpellTransformation.REGISTRY_KEY, namespace);
    }
}
