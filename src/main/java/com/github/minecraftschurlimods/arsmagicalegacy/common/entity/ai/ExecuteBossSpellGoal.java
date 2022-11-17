package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;

public class ExecuteBossSpellGoal<T extends AbstractBoss> extends ExecuteSpellGoal<T> {
    public ExecuteBossSpellGoal(T caster, @Nullable ISpell spell, int duration) {
        super(caster, spell, duration);
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && caster.getTicksInAction() <= duration * 2;
    }

    @Override
    protected SoundEvent getAttackSound() {
        return caster.getAttackSound();
    }
}
