package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.IRegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Base interface for a spell part. A spell part can be a component of type {@link ISpellComponent}, a modifier of type {@link ISpellModifier} or a shape of type {@link ISpellShape}.
 */
public interface ISpellPart extends IRegistryEntry {
    ResourceKey<Registry<ISpellPart>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_part"));

    @Override
    default ResourceKey<Registry<ISpellPart>> getRegistryKey() {
        return REGISTRY_KEY;
    }

    /**
     * @return The type of this spell part.
     */
    SpellPartType getType();

    /**
     * The types of the spell parts.
     */
    enum SpellPartType {
        SHAPE, COMPONENT, MODIFIER
    }
}
