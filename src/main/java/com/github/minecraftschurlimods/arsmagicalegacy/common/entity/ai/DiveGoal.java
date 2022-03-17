package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireGuardian;
import net.minecraft.world.entity.ai.goal.Goal;

public class DiveGoal extends Goal {
    private final FireGuardian fireGuardian;
    private int cooldown = 0;

    public DiveGoal(final FireGuardian fireGuardian) {
        this.fireGuardian = fireGuardian;
    }

    @Override
    public boolean canUse() {
        if (fireGuardian.getFireGuardianAction() == FireGuardian.FireGuardianAction.IDLE && fireGuardian.getTarget() != null && cooldown-- <= 0) {
            fireGuardian.setFireGuardianAction(FireGuardian.FireGuardianAction.SPINNING);
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (fireGuardian.getTarget() == null || fireGuardian.getTarget().isDeadOrDying() || fireGuardian.getHitCount() >= 3) {
            this.cooldown = 300;
            fireGuardian.setFireGuardianAction(FireGuardian.FireGuardianAction.IDLE);
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        fireGuardian.getNavigation().moveTo(fireGuardian.getTarget(), 0.75f);
        if (fireGuardian.getTicksInAction() > 40 && fireGuardian.distanceToSqr(fireGuardian.getTarget()) < 64D) {
            fireGuardian.setFireGuardianAction(FireGuardian.FireGuardianAction.SPINNING);
        }
    }
}
