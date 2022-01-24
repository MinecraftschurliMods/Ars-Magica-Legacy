package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Base interface for a spell part. A spell part can be a component, a modifier or a shape.
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
