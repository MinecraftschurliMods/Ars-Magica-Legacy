package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExecuteRandomSpellGoal<T extends AbstractBoss> extends ExecuteBossSpellGoal<T> {
    private final List<ISpell> spells;

    public ExecuteRandomSpellGoal(T caster, List<ISpell> spells, int duration) {
        super(caster, null, duration);
        this.spells = spells;
    }

    @Override
    @Nullable
    protected ISpell getSpell(T caster) {
        return spells.get(caster.level().getRandom().nextInt(spells.size()));
    }
}
