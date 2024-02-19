package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import net.minecraft.world.entity.LivingEntity;

public class SpinGoal<T extends AbstractBoss> extends AbstractBossGoal<T> {
    public SpinGoal(T boss) {
        super(boss, AbstractBoss.Action.SPIN, 20);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) <= 2;
    }

    @Override
    public void performTick() {
        for (LivingEntity e : boss.level().getEntitiesOfClass(LivingEntity.class, boss.getBoundingBox().inflate(2, 2, 2), e -> !(e instanceof AbstractBoss))) {
            e.hurt(boss.damageSources().mobAttack(boss), 4);
        }
    }
}
