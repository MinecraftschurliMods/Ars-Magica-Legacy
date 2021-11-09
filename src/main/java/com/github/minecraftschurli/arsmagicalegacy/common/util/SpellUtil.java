package com.github.minecraftschurli.arsmagicalegacy.common.util;

import com.github.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;

public final class SpellUtil {
    private SpellUtil() {}

    public static SpellCastResult castSucceeded(boolean cast) {
        return cast ? SpellCastResult.SUCCESS : SpellCastResult.FAIL;
    }

}
