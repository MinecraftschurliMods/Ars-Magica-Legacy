package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats.DAMAGE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats.DURATION;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats.HEALING;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats.RANGE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats.SIZE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats.SPEED;

public class Lunar extends GenericSpellModifier {
    private static final float MULTIPLIER = 1.625f; //1 + 15000 / 24000

    public Lunar() {
        addStatModifier(DAMAGE, (base, modified, spell, caster, target) -> modified + getTimeBasedMultiplier(caster.level.getDayTime()) * 2.5f);
        addStatModifier(DURATION, (base, modified, spell, caster, target) -> modified * getTimeBasedMultiplier(caster.level.getDayTime()) * 5);
        addStatModifier(HEALING, (base, modified, spell, caster, target) -> modified * getTimeBasedMultiplier(caster.level.getDayTime()) * 2);
        addStatModifier(RANGE, (base, modified, spell, caster, target) -> modified + 2 * (caster.level.getDayTime() % 24000 > 13500 && caster.level.getDayTime() % 24000 < 22500 ? 1 + getMoonPhaseMultiplier(caster.level.getMoonPhase()) : 1));
        addStatModifier(SIZE, modifiers.get(RANGE));
        addStatModifier(SPEED, (base, modified, spell, caster, target) -> modified + getTimeBasedMultiplier(caster.level.getDayTime()) * 0.5f);
    }

    private static float getMoonPhaseMultiplier(long phase) {
        return switch ((int) phase) {
            case 0 -> 2f;
            case 1, 7 -> 1.5f;
            case 2, 6 -> 1f;
            case 3, 5 -> 0.5f;
            case 4 -> 0f;
            default -> throw new IllegalStateException("Unexpected value: " + phase);
        };
    }

    private float getTimeBasedMultiplier(long time) {
        time %= 24000;
        if (time < 13500 || time > 22500) return 1;
        float multiplierFromTime = Math.abs(18000 - time);
        return multiplierFromTime * MULTIPLIER / 4500f;
    }
}
