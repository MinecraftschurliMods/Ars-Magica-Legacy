package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class SpinGoal<T extends AbstractBoss> extends AbstractBossGoal<T> {
    private final DamageSource damageSource;

    public SpinGoal(T boss, AbstractBoss.Action action, DamageSource damageSource) {
        super(boss, action);
        this.damageSource = damageSource;
    }

    @Override
    public void tick() {
        super.tick();
        boss.getLevel().broadcastEntityEvent(boss, (byte) 56);
    }

    @Override
    public void stop() {
        super.stop();
        boss.getLevel().broadcastEntityEvent(boss, (byte) 57);
    }

    @Override
    public void performTick() {
        for (LivingEntity e : boss.getLevel().getEntitiesOfClass(LivingEntity.class, boss.getBoundingBox().inflate(2, 2, 2), e -> !(e instanceof AbstractBoss))) {
            e.hurt(damageSource, 4);
        }
    }
}
