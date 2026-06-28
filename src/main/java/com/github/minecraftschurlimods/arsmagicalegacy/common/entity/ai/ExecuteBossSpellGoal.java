package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import net.minecraft.sounds.SoundEvent;
import org.jspecify.annotations.Nullable;

public class ExecuteBossSpellGoal<T extends AbstractBoss> extends ExecuteSpellGoal<T> {
    public ExecuteBossSpellGoal(T caster, @Nullable Spell spell, int duration) {
        super(caster, spell, duration);
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && caster.getTicksInAction() <= duration * 2;
    }

    @Override
    @Nullable
    protected SoundEvent getAttackSound() {
        return caster.getAttackSound();
    }
}
