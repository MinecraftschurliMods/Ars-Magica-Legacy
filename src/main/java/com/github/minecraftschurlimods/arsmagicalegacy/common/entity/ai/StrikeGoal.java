package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class StrikeGoal<T extends AbstractBoss> extends AbstractBossGoal<T> {
    private final DamageSource damageSource;

    public StrikeGoal(T boss, AbstractBoss.Action action, DamageSource damageSource) {
        super(boss, action);
        this.damageSource = damageSource;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) <= 2;
    }

    @Override
    public void perform() {
        for (LivingEntity e : boss.getLevel().getEntitiesOfClass(LivingEntity.class, boss.getBoundingBox().expandTowards(Math.cos(boss.getYRot()) * 2, 0, Math.sin(boss.getYRot()) * 2).inflate(2.5, 2, 2.5), e -> !(e instanceof AbstractBoss))) {
            e.hurt(damageSource, 4);
        }
    }
}
