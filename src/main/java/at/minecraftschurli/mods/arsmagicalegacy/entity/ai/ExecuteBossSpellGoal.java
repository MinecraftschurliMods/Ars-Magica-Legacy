package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
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
