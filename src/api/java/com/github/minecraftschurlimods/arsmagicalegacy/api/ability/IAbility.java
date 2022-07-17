package com.github.minecraftschurlimods.arsmagicalegacy.api.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * Base interface for all abilities.
 */
public interface IAbility extends ITranslatable.WithDescription {
    String ABILITY = "ability";
    ResourceKey<Registry<IAbility>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, ABILITY));

    @Override
    default String getType() {
        return ABILITY;
    }

    @Override
    default ResourceLocation getId() {
        return Objects.requireNonNull(ArsMagicaAPI.get().getAbilityRegistry().getKey(this));
    }
}
