package com.github.minecraftschurlimods.arsmagicalegacy.api.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public class ExecuteSpellGoal<T extends Mob & ISpellCasterEntity> extends Goal {
    protected final T caster;
    private final ISpell spell;
    private final int duration;
    private final int cooldown;
    private int castTicks = 0;
    private int cooldownTicks = 0;

    public ExecuteSpellGoal(T caster, ISpell spell, int duration, int cooldown) {
        this.caster = caster;
        this.spell = spell;
        this.duration = duration;
        this.cooldown = cooldown;
    }

    @Override
    public boolean canUse() {
        caster.setIsCastingSpell(true);
        return caster.isCastingSpell() && caster.getTarget() != null && !caster.getTarget().isDeadOrDying();
    }

    @Override
    public boolean canContinueToUse() {
        castTicks++;
        cooldownTicks++;
        return cooldownTicks <= cooldown && caster.isCastingSpell() && caster.getTarget() != null && !caster.getTarget().isDeadOrDying();
    }

    @Override
    public void stop() {
        super.stop();
        caster.setIsCastingSpell(false);
        castTicks = 0;
        cooldownTicks = 0;
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = caster.getTarget();
        if (target == null) return;
        caster.lookAt(target, 30, 30);
        Level level = caster.getLevel();
        if (caster.distanceToSqr(target) > 64) {
            double angle = -Math.atan2(target.getZ() - caster.getZ(), target.getX() - caster.getX());
            caster.getNavigation().moveTo(target.getX() + (Math.cos(angle) * 6), target.getY(), target.getZ() + (Math.sin(angle) * 6), 0.5f);
        } else if (!caster.canAttack(target)) {
            caster.getNavigation().moveTo(target, 0.5f);
        } else if (castTicks == duration) {
            if (!level.isClientSide() && caster instanceof AbstractBoss boss && boss.getAttackSound() != null) {
                level.playSound(null, caster, boss.getAttackSound(), SoundSource.HOSTILE, 1, 1);
            }
            spell.cast(caster, level, 0, false, false);
        }
    }
}
