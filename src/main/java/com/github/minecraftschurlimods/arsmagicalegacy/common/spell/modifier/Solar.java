package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.DefaultSpellPartStatModifier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats.*;

public class Solar extends GenericSpellModifier {

    public Solar() {
        addStatModifier(DAMAGE, (base, modified, spell, caster, target) ->modified + modifyValueOnTime(caster.level.getDayTime() % 24000, 2.4f));
        addStatModifier(DURATION, (base, modified, spell, caster, target) -> modified * modifyValueOnTime(caster.level.getDayTime() % 24000, 5));
        addStatModifier(HEALING, (base, modified, spell, caster, target) -> modified * modifyValueOnTime(caster.level.getDayTime() % 24000, 2));
        addStatModifier(SIZE, DefaultSpellPartStatModifier.add(2));
        addStatModifier(RANGE, modifiers.get(SIZE));
    }

    private float modifyValueOnTime(long time, float value) {
        float multiplierFromTime = (float) (Math.cos(((time / 3800f) * (time / 24000f) - 13000f) * (180f / Math.PI)) * 1.5f) + 1;
        if (multiplierFromTime < 0) {
            multiplierFromTime *= -0.5f;
        }
        return value * multiplierFromTime;
    }
}
