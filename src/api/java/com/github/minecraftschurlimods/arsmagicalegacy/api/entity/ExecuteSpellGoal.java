package com.github.minecraftschurlimods.arsmagicalegacy.api.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ExecuteSpellGoal<T extends Mob & ISpellCasterEntity> extends Goal {
    protected final T caster;
    protected final ISpell spell;
    protected final int duration;
    protected int ticks = 0;

    public ExecuteSpellGoal(T caster, ISpell spell, int duration, int cooldown) {
        this.caster = caster;
        this.spell = spell;
        this.duration = duration;
    }

    @Override
    public boolean canUse() {
        return !caster.isCastingSpell() && caster.getTarget() != null && !caster.getTarget().isDeadOrDying() && caster.getRandom().nextBoolean();
    }

    @Override
    public boolean canContinueToUse() {
        return caster.isCastingSpell() && caster.getTarget() != null && !caster.getTarget().isDeadOrDying();
    }

    @Override
    public void stop() {
        super.stop();
        caster.setIsCastingSpell(false);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = caster.getTarget();
        if (target == null) return;
        caster.lookAt(target, 30, 30);
        if (caster.distanceToSqr(target) > 64) {
            double angle = -Math.atan2(target.getZ() - caster.getZ(), target.getX() - caster.getX());
            caster.getNavigation().moveTo(target.getX() + (Math.cos(angle) * 6), target.getY(), target.getZ() + (Math.sin(angle) * 6), 0.5f);
        } else if (!caster.canAttack(target)) {
            caster.getNavigation().moveTo(target, 0.5f);
        } else {
            caster.setIsCastingSpell(true);
            ticks++;
            if (ticks >= duration) {
                SoundEvent sound = getAttackSound();
                if (sound != null) {
                    caster.getLevel().playSound(null, caster, sound, SoundSource.HOSTILE, 1f, 0.5f + caster.getLevel().getRandom().nextFloat());
                }
                spell.cast(caster, caster.getLevel(), 0, false, false);
                ticks = 0;
                stop();
            }
        }
    }

    /**
     * @return The attack sound to play for this goal.
     */
    @Nullable
    protected SoundEvent getAttackSound() {
        return caster instanceof AbstractBoss boss ? boss.getAttackSound() : null;
    }
}
