package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireGuardian;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class FlamethrowerGoal extends Goal {
    private final FireGuardian fireGuardian;
    private int cooldown = 0;

    public FlamethrowerGoal(FireGuardian fireGuardian) {
        this.fireGuardian = fireGuardian;
    }

    @Override
    public boolean canUse() {
        return fireGuardian.getFireGuardianAction() == FireGuardian.FireGuardianAction.IDLE && fireGuardian.getTarget() != null && cooldown-- <= 0;
    }

    @Override
    public boolean canContinueToUse() {
        if (fireGuardian.getTarget() == null || fireGuardian.getTarget().isDeadOrDying() || fireGuardian.getTicksInAction() > 80) {
            cooldown = 40;
            fireGuardian.setFireGuardianAction(FireGuardian.FireGuardianAction.IDLE);
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = fireGuardian.getTarget();
        if (target == null) return;
        if (fireGuardian.distanceToSqr(target) < 64) {
            if (fireGuardian.getFireGuardianAction() != FireGuardian.FireGuardianAction.LONG_CASTING) {
                fireGuardian.setFireGuardianAction(FireGuardian.FireGuardianAction.LONG_CASTING);
            }
            fireGuardian.getLookControl().setLookAt(target, 10, 10);
            fireGuardian.getNavigation().recomputePath(); // is clearPathEntity() --> recomputePath()
        } else {
            double angle = -Math.atan2(target.getZ() - fireGuardian.getZ(), target.getX() - fireGuardian.getX());
            double newZ = target.getZ() + (Math.sin(angle) * 4);
            double newX = target.getX() + (Math.cos(angle) * 4);
            fireGuardian.getNavigation().moveTo(newX, target.getY(), newZ, 0.5f);
        }
    }
}
