package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractBossGoal<T extends AbstractBoss> extends Goal {
    public final T boss;
    public final AbstractBoss.Action action;
    protected final int duration;
    protected final int cooldown;
    protected int ticks = 0;

    public AbstractBossGoal(T boss, AbstractBoss.Action action, int duration) {
        this(boss, action, duration, 0);
    }

    public AbstractBossGoal(T boss, AbstractBoss.Action action, int duration, int cooldown) {
        this.boss = boss;
        this.action = action;
        this.duration = duration;
        this.cooldown = cooldown;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canUse() {
        return boss.getAction() == AbstractBoss.Action.IDLE && boss.getTarget() != null && !boss.getTarget().isDeadOrDying() && boss.canAttack(boss.getTarget()) && boss.level().getRandom().nextBoolean();
    }

    @Override
    public boolean canContinueToUse() {
        return boss.getAction() == action && boss.getTicksInAction() <= duration * 2;
    }

    @Override
    public void start() {
        super.start();
        boss.setAction(action);
    }

    @Override
    public void stop() {
        super.stop();
        boss.setAction(AbstractBoss.Action.IDLE);
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
        }
        ticks++;
        performTick();
        if (ticks == duration) {
            SoundEvent sound = getAttackSound();
            if (sound != null) {
                boss.level().playSound(null, boss, sound, SoundSource.HOSTILE, 1f, 0.5f + boss.level().getRandom().nextFloat());
            }
            perform();
        }
        if (ticks >= duration + cooldown) {
            ticks = 0;
            stop();
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
     * Performs the goal's final effect.
     */
    public void perform() {
    }

    /**
     * Performs the goal's ticking effect.
     */
    public void performTick() {
    }
}
