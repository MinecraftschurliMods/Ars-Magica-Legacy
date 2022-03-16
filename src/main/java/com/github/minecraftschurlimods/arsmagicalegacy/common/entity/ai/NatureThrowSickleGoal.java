package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class NatureThrowSickleGoal extends Goal {
    private final NatureGuardian natureGuardian;
    private final float          moveSpeed;
    private       LivingEntity   target;
    private       int            cooldown = 0;

    public NatureThrowSickleGoal(NatureGuardian natureGuardian, float moveSpeed) {
        this.natureGuardian = natureGuardian;
        this.moveSpeed = moveSpeed;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || natureGuardian.getNatureGuardianAction() != NatureGuardian.NatureGuardianAction.IDLE) {
            return false;
        }
        if (natureGuardian.getTarget() == null || natureGuardian.isDeadOrDying()) {
            return false;
        }
        this.target = natureGuardian.getTarget();
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (natureGuardian.getTarget() == null || natureGuardian.getTarget().isDeadOrDying() || (natureGuardian.getNatureGuardianAction() == NatureGuardian.NatureGuardianAction.THROWING_SICKLE && natureGuardian.getTicksInAction() > natureGuardian.getNatureGuardianAction().getMaxActionTime())) {
            natureGuardian.setNatureGuardianAction((NatureGuardian.NatureGuardianAction.IDLE));
            cooldown = 50;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        natureGuardian.getLookControl().setLookAt(target, 30, 30);
        if (natureGuardian.distanceToSqr(target) > 100) {
            natureGuardian.getNavigation().moveTo(target, moveSpeed);
        } else {
            natureGuardian.getNavigation().recomputePath(); // is clearPathEntity() --> recomputePath
            if (natureGuardian.getNatureGuardianAction() != NatureGuardian.NatureGuardianAction.THROWING_SICKLE) {
                natureGuardian.setNatureGuardianAction(NatureGuardian.NatureGuardianAction.THROWING_SICKLE);
            }

            if (natureGuardian.getTicksInAction() == 12) {
                natureGuardian.lookAt(target, 100, 100);
                if (!natureGuardian.level.isClientSide()) {
//                    ThrownSickle projectile = new ThrownSickle(natureGuardian.level, natureGuardian, 2.0f);
//                    projectile.setThrowingEntity(natureGuardian);
//                    projectile.setProjectileSpeed(2.0);
//                    natureGuardian.level.addFreshEntity(projectile);
                }
            }
        }
    }
}
