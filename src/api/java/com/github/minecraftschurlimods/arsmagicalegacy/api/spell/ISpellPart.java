package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Base interface for a spell part. A spell part can be a component ({@link ISpellComponent}), a modifier ({@link ISpellModifier}) or a shape ({@link ISpellShape})
 */
public interface ISpellPart extends IForgeRegistryEntry<ISpellPart> {
    /**
     * Get the type for this spell part.
     *
     * @return the {@link SpellPartType} for this spell part
     */
    SpellPartType getType();

    /**
     * The type of the spell part
     */
    enum SpellPartType {
        SHAPE, COMPONENT, MODIFIER
    }
}
