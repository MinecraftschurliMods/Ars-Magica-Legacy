package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStatModifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GenericSpellModifier extends AbstractModifier {
    protected Map<ISpellPartStat, ISpellPartStatModifier> modifiers = new HashMap<>();

    @Override
    public ISpellPartStatModifier getStatModifier(ISpellPartStat stat) {
        return modifiers.get(stat);
    }

    @Override
    public Set<ISpellPartStat> getStatsModified() {
        return modifiers.keySet();
    }

    public GenericSpellModifier addStatModifier(ISpellPartStat stat, ISpellPartStatModifier modifier) {
        modifiers.put(stat, modifier);
        return this;
    }
}
