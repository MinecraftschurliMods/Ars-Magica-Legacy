package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Set;

public abstract class AbstractComponent extends ForgeRegistryEntry<ISpellPart> implements ISpellComponent {
    private final Set<ISpellPartStat> stats;

    protected AbstractComponent(ISpellPartStat... stats) {
        this.stats = Set.of(stats);
    }

    @Override
    public Set<ISpellPartStat> getStatsUsed() {
        return stats;
    }
}
