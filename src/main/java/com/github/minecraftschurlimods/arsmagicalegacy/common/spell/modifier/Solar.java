package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats.DAMAGE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats.DURATION;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats.HEALING;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats.RANGE;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats.SIZE;

public class Solar extends GenericSpellModifier {
    private static final float MULTIPLIER = 1.375f; //1 + 9000 / 24000

    public Solar() {
        addStatModifier(DAMAGE, (base, modified, spell, caster, target) -> modified + getTimeBasedMultiplier(caster.level.getDayTime()) * 2.5f);
        addStatModifier(DURATION, (base, modified, spell, caster, target) -> modified * getTimeBasedMultiplier(caster.level.getDayTime()) * 5);
        addStatModifier(HEALING, (base, modified, spell, caster, target) -> modified * getTimeBasedMultiplier(caster.level.getDayTime()) * 2);
        addStatModifier(SIZE, (base, modified, spell, caster, target) -> modified + 1.5f * (caster.level.getDayTime() % 24000 < 13500 || caster.level.getDayTime() % 24000 > 22500 ? 1 + getMoonPhaseMultiplier(caster.level.getMoonPhase()) : 1));
        addStatModifier(RANGE, modifiers.get(SIZE));
    }

    /**
     * @param phase the moon phase
     * @return a multiplier value. 2 is new moon, 1.5 is first quarter, 1 is full moon, 0.5 is last quarter, 0 is full moon
     */
    private static float getMoonPhaseMultiplier(long phase) {
        return switch ((int) phase) {
            case 4 -> 2f;
            case 3, 5 -> 1.5f;
            case 2, 6 -> 1f;
            case 1, 7 -> 0.5f;
            case 0 -> 0f;
            default -> throw new IllegalStateException("Unexpected value: " + phase);
        };
    }

    /**
     * Returns the value of the multiplier, amplified by the world time. The closer the time is to noon, the higher this value is.
     * Returns 1 if it is night.
     *
     * @param time the world time.
     * @return the value of the multiplier, as described above
     */
    private float getTimeBasedMultiplier(long time) {
        time %= 24000;
        if (time > 13500 && time < 22500) return 1;
        float multiplierFromTime = Math.abs(6000 - time);
        return multiplierFromTime * MULTIPLIER / 7500f;
    }
}
