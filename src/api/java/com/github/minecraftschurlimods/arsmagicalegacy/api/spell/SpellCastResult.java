package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

/**
 * The result of a spell cast.
 */
public enum SpellCastResult {
    SUCCESS, NOT_ENOUGH_MANA, BURNED_OUT, MISSING_REAGENTS, CANCELLED, EFFECT_FAILED, SILENCED, NO_PERMISSION;

    /**
     * @return true, if this spellcast result represents a failed cast
     */
    public boolean isFail() {
        return this != SUCCESS;
    }

    /**
     * @return true, if this spellcast result represents a successful cast
     */
    public boolean isSuccess() {
        return this == SUCCESS;
    }

    /**
     * @return true, if this spellcast result represents status that should consume (consume mana, give burnout and consume reagents)
     */
    public boolean isConsume() {
        return this == SUCCESS;
    }
}
