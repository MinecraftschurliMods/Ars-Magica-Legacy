package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.DefaultSpellPartStatModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStatModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;

import java.util.EnumSet;
import java.util.Set;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats.*;

public class Solar extends AbstractModifier {
    @SuppressWarnings("unchecked")
    private static final Set<ISpellPartStat> STATS = (Set<ISpellPartStat>) (Object) EnumSet.of(RANGE, SIZE, DAMAGE, DURATION, HEALING);

    @Override
    public ISpellPartStatModifier getStatModifier(ISpellPartStat stat) {
        if (!(stat instanceof SpellPartStats)) return DefaultSpellPartStatModifier.NOOP;
        return switch ((SpellPartStats) stat) {
            case DAMAGE -> (base, modified, spell, caster, target) -> modified + modifyValueOnTime(caster.level.getDayTime() % 24000, 2.4f);
            case DURATION -> (base, modified, spell, caster, target) -> modified * modifyValueOnTime(caster.level.getDayTime() % 24000, 5);
            case HEALING -> (base, modified, spell, caster, target) -> modified * modifyValueOnTime(caster.level.getDayTime() % 24000, 2);
            case SIZE, RANGE -> (base, modified, spell, caster, target) -> modified + 2;
            default -> DefaultSpellPartStatModifier.NOOP;
        };
    }

    @Override
    public Set<ISpellPartStat> getStatsModified() {
        return STATS;
    }

    private float modifyValueOnTime(long time, float value) {
        float multiplierFromTime = (float) (Math.cos(((time / 3800f) * (time / 24000f) - 13000f) * (180f / Math.PI)) * 1.5f) + 1;
        if (multiplierFromTime < 0) {
            multiplierFromTime *= -0.5f;
        }
        return value * multiplierFromTime;
    }
}
