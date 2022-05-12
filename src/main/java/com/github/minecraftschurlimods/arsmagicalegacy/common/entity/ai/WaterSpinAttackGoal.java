package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WaterGuardian;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class WaterSpinAttackGoal extends Goal {
    private final WaterGuardian waterGuardian;
    private int cooldownTicks = 0;

    public WaterSpinAttackGoal(WaterGuardian waterGuardian) {
        this.waterGuardian = waterGuardian;
    }

    @Override
    public boolean canUse() {
        if (waterGuardian.getAction() != WaterGuardian.WaterGuardianAction.IDLE || waterGuardian.getTarget() == null || waterGuardian.getTarget().isDeadOrDying())
            return false;
        waterGuardian.setAction(WaterGuardian.WaterGuardianAction.SPINNING);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        cooldownTicks++;
        return waterGuardian.getAction() == WaterGuardian.WaterGuardianAction.SPINNING && waterGuardian.getTarget() != null && !waterGuardian.getTarget().isDeadOrDying();
    }

    @Override
    public void stop() {
        super.stop();
        waterGuardian.setAction(WaterGuardian.WaterGuardianAction.IDLE);
        cooldownTicks = 0;
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = waterGuardian.getTarget();
        if (target == null) return;
        waterGuardian.lookAt(target, 30, 30);
        if (waterGuardian.distanceToSqr(target) > 64) {
            double angle = -Math.atan2(target.getZ() - waterGuardian.getZ(), target.getX() - waterGuardian.getX());
            waterGuardian.getNavigation().moveTo(target.getX() + Math.cos(angle) * 6, target.getY(), target.getZ() + Math.sin(angle) * 6, 0.5f);
        } else if (!waterGuardian.canAttack(target)) {
            waterGuardian.getNavigation().moveTo(target, 0.5f);
        } else if (cooldownTicks >= waterGuardian.getAction().getMaxActionTime()) {
            for (LivingEntity e : waterGuardian.getLevel().getEntitiesOfClass(LivingEntity.class, waterGuardian.getBoundingBox().inflate(2, 2, 2))) {
                if (!(e instanceof WaterGuardian)) {
                    waterGuardian.hurt(DamageSource.mobAttack(waterGuardian), 4f);
                }
            }
            cooldownTicks = 0;
        }
    }
}
