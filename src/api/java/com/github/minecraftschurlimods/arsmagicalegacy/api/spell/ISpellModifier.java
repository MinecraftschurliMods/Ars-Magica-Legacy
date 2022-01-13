package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import java.util.Set;

/**
 * Base interface for a spell modifier.
 */
public interface ISpellModifier extends ISpellPart {
    @Override
    default SpellPartType getType() {
        return SpellPartType.MODIFIER;
    }

    ISpellPartStatModifier getStatModifier(ISpellPartStat stat);

    Set<ISpellPartStat> getStatsModified();
}
