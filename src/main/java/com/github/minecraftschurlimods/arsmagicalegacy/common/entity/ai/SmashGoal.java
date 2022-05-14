package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class SmashGoal<T extends AbstractBoss> extends AbstractBossGoal<T> {
    private final DamageSource damageSource;

    public SmashGoal(T boss, AbstractBoss.Action action, DamageSource damageSource) {
        super(boss, action);
        this.damageSource = damageSource;
    }

    @Override
    public void perform() {
        for (LivingEntity e : boss.getLevel().getEntitiesOfClass(LivingEntity.class, boss.getBoundingBox().inflate(4, 2, 4), e -> !(e instanceof AbstractBoss))) {
            e.hurt(damageSource, 8);
            e.setDeltaMovement(e.getDeltaMovement().add(0, 1.4f, 0));
            if (!boss.getLevel().isClientSide()) {
                for (int i = 0; i < 4; i++) {
//                    Shockwave entity = new Shockwave(boss.getLevel());
//                    entity.setPos(boss.getX(), boss.getY() + boss.getEyeHeight(), boss.getZ());
//                    entity.setDeltaMovement(boss.getLookAngle());
//                    boss.getLevel().addFreshEntity(entity);
                }
            }
        }
    }
}
