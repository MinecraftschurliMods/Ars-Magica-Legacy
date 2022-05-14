package com.github.minecraftschurlimods.arsmagicalegacy.api.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractBossGoal<T extends AbstractBoss> extends Goal {
    public final T boss;
    public final AbstractBoss.Action action;
    protected final int maxCooldownTicks;
    protected int cooldownTicks = 0;

    public AbstractBossGoal(T boss, AbstractBoss.Action action) {
        this(boss, action, action.getMaxActionTime());
    }

    public AbstractBossGoal(T boss, AbstractBoss.Action action, int maxCooldownTicks) {
        this.boss = boss;
        this.action = action;
        this.maxCooldownTicks = maxCooldownTicks;
    }

    @Override
    public boolean canUse() {
        if (!boss.isIdle() || boss.getTarget() == null || boss.getTarget().isDeadOrDying()) return false;
        boss.setAction(action);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        cooldownTicks++;
        return boss.getAction() == action && boss.getTarget() != null && !boss.getTarget().isDeadOrDying();
    }

    @Override
    public void stop() {
        super.stop();
        boss.setIdle();
        cooldownTicks = 0;
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = boss.getTarget();
        if (target == null) return;
        boss.lookAt(target, 30, 30);
        if (boss.distanceToSqr(target) > 64) {
            double angle = -Math.atan2(target.getZ() - boss.getZ(), target.getX() - boss.getX());
            boss.getNavigation().moveTo(target.getX() + Math.cos(angle) * 6, target.getY(), target.getZ() + Math.sin(angle) * 6, 0.5f);
        } else if (!boss.canAttack(target)) {
            boss.getNavigation().moveTo(target, 0.5f);
        } else if (cooldownTicks >= maxCooldownTicks) {
            SoundEvent sound = getAttackSound();
            if (sound != null) {
                boss.getLevel().playSound(null, boss, sound, SoundSource.HOSTILE, 1f, 0.5f + boss.getLevel().getRandom().nextFloat() * 0.5f);
            }
            perform();
            cooldownTicks = 0;
        }
    }

    /**
     * @return The attack sound to play for this goal.
     */
    @Nullable
    protected SoundEvent getAttackSound() {
        return boss.getAttackSound();
    }

    /**
     * Performs the goal's effect.
     */
    public abstract void perform();
}
