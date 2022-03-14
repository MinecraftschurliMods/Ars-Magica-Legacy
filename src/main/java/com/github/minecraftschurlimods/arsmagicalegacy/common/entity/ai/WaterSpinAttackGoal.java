package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WaterGuardian;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class WaterSpinAttackGoal extends Goal {
    private final WaterGuardian waterGuardian;
    private final float moveSpeed;
    private LivingEntity target;
    private int cooldown = 0;
    private final float damage;

    public WaterSpinAttackGoal(WaterGuardian waterGuardian, float moveSpeed, float damage) {
        this.waterGuardian = waterGuardian;
        this.moveSpeed = moveSpeed;
        this.damage = damage;
    }


    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || waterGuardian.getWaterGuardianAction() != WaterGuardian.WaterGuardianAction.IDLE || !waterGuardian.isWaterGuardianActionValid(WaterGuardian.WaterGuardianAction.SPINNING)) {
            return false;
        }
        if (waterGuardian.getTarget() == null || waterGuardian.getTarget().isDeadOrDying() || waterGuardian.getTarget().distanceToSqr(waterGuardian) > 25) {
            return false;
        }
        this.target = waterGuardian.getTarget();
        waterGuardian.setWaterGuardianAction(WaterGuardian.WaterGuardianAction.SPINNING);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (waterGuardian.getTarget() == null || waterGuardian.getTarget().isDeadOrDying() || waterGuardian.getTicksInAction() > WaterGuardian.WaterGuardianAction.SPINNING.getMaxActionTime()) {
            stop();
            return false;
        }
        return true;
    }

    @Override
    public void stop() {
        waterGuardian.setWaterGuardianAction(WaterGuardian.WaterGuardianAction.IDLE);
        cooldown = 150;
        super.stop();
    }

    @Override
    public void tick() {
        super.tick();
        waterGuardian.getLookControl().setLookAt(target, 30, 30);
        waterGuardian.getNavigation().moveTo(target, moveSpeed);
        List<LivingEntity> nearbyEntities = waterGuardian.level.getEntitiesOfClass(LivingEntity.class, this.waterGuardian.getBoundingBox().inflate(2, 2, 2));
        for (LivingEntity e : nearbyEntities) {
            if (e == waterGuardian) {
                waterGuardian.hurt(DamageSource.mobAttack(waterGuardian), damage);
            }
        }
    }
}
