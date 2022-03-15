package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireGuardian;
import net.minecraft.world.entity.ai.goal.Goal;

public class FlameThrowerGoal extends Goal {
    private final FireGuardian fireGuardian;
    private       int          cooldown = 0;

    public FlameThrowerGoal(FireGuardian fireGuardian) {
        this.fireGuardian = fireGuardian;
    }

    @Override
    public boolean canUse() {
        return fireGuardian.getFireGuardianAction() == FireGuardian.FireGuardianAction.IDLE && fireGuardian.getTarget() != null && cooldown-- <= 0;
    }

    @Override
    public boolean canContinueToUse() {
        if (fireGuardian.getTarget() == null || fireGuardian.getTarget().isDeadOrDying() || fireGuardian.getTicksInAction() > 80) {
            this.cooldown = 40;
            fireGuardian.setFireGuardianAction(FireGuardian.FireGuardianAction.IDLE);
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        if (fireGuardian.distanceToSqr(fireGuardian.getTarget()) < 64) {
            if (fireGuardian.getFireGuardianAction() != FireGuardian.FireGuardianAction.LONG_CASTING) {
                fireGuardian.setFireGuardianAction(FireGuardian.FireGuardianAction.LONG_CASTING);
            }
            fireGuardian.getLookControl().setLookAt(fireGuardian.getTarget(), 10, 10);
            fireGuardian.getNavigation().recomputePath(); // is clearPathEntity() --> recomputePath()
        } else {
            double deltaZ = fireGuardian.getTarget().getZ() - fireGuardian.getZ();
            double deltaX = fireGuardian.getTarget().getX() - fireGuardian.getX();
            double angle = -Math.atan2(deltaZ, deltaX);

            double newZ = fireGuardian.getTarget().getZ() + (Math.sin(angle) * 4);
            double newX = fireGuardian.getTarget().getX() + (Math.cos(angle) * 4);
            fireGuardian.getNavigation().moveTo(newX, fireGuardian.getTarget().getY(), newZ, 0.5f);
        }
    }
}
