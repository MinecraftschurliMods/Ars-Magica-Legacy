package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

/**
 * The result of a spell cast.
 */
public enum SpellCastResult {
    SUCCESS, NOT_ENOUGH_MANA, BURNED_OUT, MISSING_REAGENTS, CANCELLED, EFFECT_FAILED, SILENCED, NO_PERMISSION;

    /**
     * @return True if this spell cast result represents a failed cast, false otherwise.
     */
    public boolean isFail() {
        return this != SUCCESS;
    }

    /**
     * @return True if this spell cast result represents a successful cast, false otherwise.
     */
    public boolean isSuccess() {
        return this == SUCCESS;
    }

    /**
     * @return True if this spell cast result means that mana should be consumed, burnout should be given and reagents should be consumed, false otherwise.
     */
    public boolean isConsume() {
        return this == SUCCESS;
    }
}
