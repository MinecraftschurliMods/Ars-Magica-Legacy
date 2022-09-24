package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

/**
 * Marks a class as a spell casting entity. Has methods to determine if a spell can be cast, if a spell is cast, and to set whether a spell is currently being cast.
 */
public interface ISpellCasterEntity {
    /**
     * @return Whether this entity can currently cast a spell or not.
     */
    boolean canCastSpell();

    /**
     * @return Whether this entity is currently casting a spell or not.
     */
    boolean isCastingSpell();

    /**
     * Sets whether this entity is currently casting a spell or not.
     *
     * @param isCastingSpell Whether this entity is currently casting a spell or not.
     */
    void setIsCastingSpell(boolean isCastingSpell);
}
