package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Ability;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractDatapackRegistryProvider;

public abstract class AbilityProvider extends AbstractDatapackRegistryProvider<Ability> {
    protected AbilityProvider(String namespace) {
        super(Ability.REGISTRY_KEY, namespace);
    }
}
