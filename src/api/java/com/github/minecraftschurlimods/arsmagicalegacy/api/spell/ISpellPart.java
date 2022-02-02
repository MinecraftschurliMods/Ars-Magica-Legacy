package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Base interface for a spell part. A spell part can be a component of type {@link ISpellComponent}, a modifier of type {@link ISpellModifier} or a shape of type {@link ISpellShape}.
 */
public interface ISpellPart extends IForgeRegistryEntry<ISpellPart> {
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
