package com.github.minecraftschurli.arsmagicalegacy.api.spell;

public enum SpellCastResult {
    SUCCESS, NOT_ENOUGH_MANA, BURNED_OUT, MISSING_REAGENTS, CANCELLED, FAIL;

    public boolean isFail() {
        return this != SUCCESS;
    }

    public boolean isSuccess() {
        return this == SUCCESS;
    }

    public boolean isConsume() {
        return this == SUCCESS || this == FAIL;
    }
}
