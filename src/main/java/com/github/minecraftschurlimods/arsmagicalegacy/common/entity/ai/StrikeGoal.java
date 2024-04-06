package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import net.minecraft.world.entity.LivingEntity;

public class StrikeGoal<T extends AbstractBoss> extends AbstractBossGoal<T> {
    public StrikeGoal(T boss) {
        super(boss, AbstractBoss.Action.STRIKE, 5, 15);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) <= 2;
    }

    @Override
    public void perform() {
        for (LivingEntity e : boss.getLevel().getEntitiesOfClass(LivingEntity.class, boss.getBoundingBox().expandTowards(Math.cos(boss.getYRot()) * 2, 0, Math.sin(boss.getYRot()) * 2).inflate(2.5, 2, 2.5), e -> !(e instanceof AbstractBoss))) {
            e.hurt(boss.damageSources().mobAttack(boss), 6);
        }
    }
}
