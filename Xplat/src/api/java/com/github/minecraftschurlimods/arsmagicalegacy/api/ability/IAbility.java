package com.github.minecraftschurlimods.arsmagicalegacy.api.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.IRegistryEntry;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Base interface for all abilities.
 */
public interface IAbility extends ITranslatable.OfRegistryEntry<IAbility> {
    ResourceKey<Registry<IAbility>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ability"));

    @Override
    default ResourceKey<? extends Registry<? extends IRegistryEntry>> getRegistryKey() {
        return REGISTRY_KEY;
    }
}
