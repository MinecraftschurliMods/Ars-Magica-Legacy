package com.github.minecraftschurlimods.arsmagicalegacy.api.entity;

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
