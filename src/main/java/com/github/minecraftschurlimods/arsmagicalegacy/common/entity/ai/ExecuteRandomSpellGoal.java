package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellCasterEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExecuteRandomSpellGoal<T extends Mob & ISpellCasterEntity> extends ExecuteSpellGoal<T> {
    private final List<ISpell> spells;

    public ExecuteRandomSpellGoal(T caster, List<ISpell> spells, int duration) {
        super(caster, null, duration);
        this.spells = spells;
    }

    @Override
    @Nullable
    protected ISpell getSpell(T caster) {
        return spells.get(caster.getLevel().getRandom().nextInt(spells.size()));
    }
}
