package com.github.minecraftschurlimods.arsmagicalegacy.api.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * Base interface for all abilities.
 */
public interface IAbility extends ITranslatable.WithDescription {
    @Override
    default String getType() {
        return "ability";
    }

    @Override
    default ResourceLocation getId() {
        return Objects.requireNonNull(ArsMagicaAPI.get().getAbilityRegistry().getKey(this));
    }
}
