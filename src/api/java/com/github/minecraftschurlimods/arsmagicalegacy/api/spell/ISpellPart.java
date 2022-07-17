package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

/**
 * Base interface for a spell part. A spell part can be a component of type {@link ISpellComponent}, a modifier of type {@link ISpellModifier} or a shape of type {@link ISpellShape}.
 */
public interface ISpellPart {
    ResourceKey<Registry<ISpellPart>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_part"));

    /**
     * @return The type of this spell part.
     */
    SpellPartType getType();

    /**
     * @return The id of this spell part.
     */
    default ResourceLocation getId() {
        return Objects.requireNonNull(ArsMagicaAPI.get().getSpellPartRegistry().getKey(this));
    }

    /**
     * The types of the spell parts.
     */
    enum SpellPartType {
        SHAPE, COMPONENT, MODIFIER
    }
}
