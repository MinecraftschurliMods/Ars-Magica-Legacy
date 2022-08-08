package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Shockwave;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class SmashGoal<T extends AbstractBoss> extends AbstractBossGoal<T> {
    private final DamageSource damageSource;

    public SmashGoal(T boss, AbstractBoss.Action action, DamageSource damageSource) {
        super(boss, action, 10, 10);
        this.damageSource = damageSource;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) <= 2;
    }

    @Override
    public void perform() {
        for (LivingEntity e : boss.getLevel().getEntitiesOfClass(LivingEntity.class, boss.getBoundingBox().inflate(4, 2, 4), e -> !(e instanceof AbstractBoss))) {
            e.hurt(damageSource, 8);
            if (!boss.getLevel().isClientSide()) {
                for (int i = -2; i <= 2; i++) {
                    Shockwave entity = Shockwave.create(boss.getLevel());
                    Vec3 movement = boss.getLookAngle().yRot((float) (Math.PI / 36 * i));
                    entity.setDeltaMovement(movement.x(), 0, movement.z());
                    entity.setPos(boss.getX() + movement.x(), boss.getY(), boss.getZ() + movement.z());
                    boss.getLevel().addFreshEntity(entity);
                }
            }
        }
    }
}
