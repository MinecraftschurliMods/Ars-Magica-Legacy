package com.github.minecraftschurlimods.arsmagicalegacy.api.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;

/**
 * Base interface for all abilities.
 */
public interface IAbility extends ITranslatable.OfRegistryEntry<IAbility> {
    @Override
    default String getType() {
        return "ability";
    }
}
