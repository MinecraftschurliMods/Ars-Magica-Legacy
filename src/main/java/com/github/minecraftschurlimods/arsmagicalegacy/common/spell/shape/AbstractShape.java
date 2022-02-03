package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellShape;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Set;

public abstract class AbstractShape extends ForgeRegistryEntry<ISpellPart> implements ISpellShape {
    private final Set<ISpellPartStat> stats;

    public AbstractShape(ISpellPartStat... stats) {
        this.stats = Set.of(stats);
    }

    @Override
    public Set<ISpellPartStat> getStatsUsed() {
        return stats;
    }
}
