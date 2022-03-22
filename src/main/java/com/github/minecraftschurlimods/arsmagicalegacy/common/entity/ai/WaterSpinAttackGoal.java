package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WaterGuardian;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class WaterSpinAttackGoal extends Goal {
    private final WaterGuardian waterGuardian;
    private LivingEntity target;
    private int cooldown = 0;

    public WaterSpinAttackGoal(WaterGuardian waterGuardian) {
        this.waterGuardian = waterGuardian;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || waterGuardian.getAction() != WaterGuardian.WaterGuardianAction.IDLE || !waterGuardian.isWaterGuardianActionValid(WaterGuardian.WaterGuardianAction.SPINNING) || waterGuardian.getTarget() == null || waterGuardian.getTarget().isDeadOrDying() || waterGuardian.getTarget().distanceToSqr(waterGuardian) > 25)
            return false;
        target = waterGuardian.getTarget();
        waterGuardian.setAction(WaterGuardian.WaterGuardianAction.SPINNING);
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
        waterGuardian.setAction(WaterGuardian.WaterGuardianAction.IDLE);
        cooldown = 150;
        super.stop();
    }

    @Override
    public void tick() {
        super.tick();
        waterGuardian.getLookControl().setLookAt(target, 30, 30);
        waterGuardian.getNavigation().moveTo(target, 0.5f);
        for (LivingEntity e : waterGuardian.getLevel().getEntitiesOfClass(LivingEntity.class, waterGuardian.getBoundingBox().inflate(2, 2, 2))) {
            if (e == waterGuardian) {
                waterGuardian.hurt(DamageSource.mobAttack(waterGuardian), 4f);
            }
        }
    }
}
