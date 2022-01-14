package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStatModifier;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats.*;

public class Lunar extends GenericSpellModifier {

    public Lunar() {
        addStatModifier(DAMAGE, (base, modified, spell, caster, target) -> modified + modifyValueOnTime(caster.level.getDayTime() % 24000, 2.4f));
        addStatModifier(DURATION, (base, modified, spell, caster, target) -> modified * modifyValueOnTime(caster.level.getDayTime() % 24000, 5));
        addStatModifier(HEALING, (base, modified, spell, caster, target) -> modified * modifyValueOnTime(caster.level.getDayTime() % 24000, 2));
        addStatModifier(SIZE, (base, modified, spell, caster, target) -> {
            if (caster.level.getDayTime() % 24000 > 12500 && caster.level.getDayTime() % 24000 < 23500)
                return modified + 3 + ((8 - caster.level.getMoonPhase()) / 2f);
            return modified + 2;
        });
        addStatModifier(RANGE, modifiers.get(SIZE));
    }

    private float modifyValueOnTime(long time, float value) {
        float multiplierFromTime = (float) (Math.sin(((time / 4600f) * (time / 21000f) - 900) * (180 / Math.PI)) * 3) + 1;
        if (multiplierFromTime < 0) {
            multiplierFromTime *= -0.5f;
        }
        return value * multiplierFromTime;
    }
}
