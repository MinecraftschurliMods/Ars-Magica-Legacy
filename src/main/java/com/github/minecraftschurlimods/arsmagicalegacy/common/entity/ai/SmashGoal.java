package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Shockwave;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class SmashGoal<T extends AbstractBoss> extends AbstractBossGoal<T> {
    public SmashGoal(T boss) {
        super(boss, AbstractBoss.Action.SMASH, 5, 15);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) > 2 && boss.distanceTo(boss.getTarget()) <= 4;
    }

    @Override
    public void perform() {
        for (LivingEntity e : boss.getLevel().getEntitiesOfClass(LivingEntity.class, boss.getBoundingBox().inflate(4, 2, 4), e -> !(e instanceof AbstractBoss))) {
            e.hurt(DamageSource.mobAttack(boss), 4);
        }
        if (!boss.getLevel().isClientSide()) {
            for (int i = -20; i <= 20; i++) {
                Shockwave entity = Shockwave.create(boss.getLevel());
                Vec3 movement = boss.getLookAngle().yRot((float) (Math.PI / 180 * i));
                entity.setDeltaMovement(movement.x(), 0, movement.z());
                entity.setPos(boss.getX() + movement.x(), boss.getY(), boss.getZ() + movement.z());
                boss.getLevel().addFreshEntity(entity);
            }
        }
    }
}
