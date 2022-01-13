package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.DefaultSpellPartStatModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStatModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.Set;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats.*;

/**
 *
 */
public class Lunar extends AbstractModifier {
    @SuppressWarnings("unchecked")
    private static final Set<ISpellPartStat> STATS = (Set<ISpellPartStat>) (Object) EnumSet.of(RANGE, SIZE, DAMAGE, DURATION, HEALING);

    @Override
    public ISpellPartStatModifier getStatModifier(ISpellPartStat stat) {;
        if (!(stat instanceof SpellPartStats)) return DefaultSpellPartStatModifier.NOOP;
        return switch ((SpellPartStats) stat) {
            case DAMAGE -> (base, modified, spell, caster, target) -> modified + modifyValueOnTime(caster.level.getDayTime() % 24000, 2.4f);
            case DURATION -> (base, modified, spell, caster, target) -> modified * modifyValueOnTime(caster.level.getDayTime() % 24000, 5);
            case HEALING -> (base, modified, spell, caster, target) -> modified * modifyValueOnTime(caster.level.getDayTime() % 24000, 2);
            case SIZE, RANGE -> (base, modified, spell, caster, target) -> {
                if (caster.level.getDayTime() % 24000 > 12500 && caster.level.getDayTime() % 24000 < 23500)
                    return modified + 3 + ((8 - caster.level.getMoonPhase()) / 2f);
                return modified + 2;
            };
            default -> DefaultSpellPartStatModifier.NOOP;
        };
    }

    @Override
    public Set<ISpellPartStat> getStatsModified() {
        return STATS;
    }

    private float modifyValueOnTime(long time, float value) {
        float multiplierFromTime = (float) (Math.sin(((time / 4600f) * (time / 21000f) - 900) * (180 / 3.14159265358979)) * 3) + 1;
        if (multiplierFromTime < 0) multiplierFromTime *= -0.5f;
        return value * multiplierFromTime;
    }
}
