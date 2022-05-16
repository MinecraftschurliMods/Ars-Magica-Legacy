package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class NatureThrowSickleGoal extends Goal {
    private final NatureGuardian natureGuardian;
    private LivingEntity target;
    private int cooldown = 0;

    public NatureThrowSickleGoal(NatureGuardian natureGuardian) {
        this.natureGuardian = natureGuardian;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || natureGuardian.getAction() != NatureGuardian.NatureGuardianAction.IDLE || natureGuardian.getTarget() == null || natureGuardian.isDeadOrDying())
            return false;
        target = natureGuardian.getTarget();
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (natureGuardian.getTarget() == null || natureGuardian.getTarget().isDeadOrDying() || (natureGuardian.getAction() == NatureGuardian.NatureGuardianAction.THROWING_SICKLE && natureGuardian.getTicksInAction() > natureGuardian.getAction().getMaxActionTime())) {
            natureGuardian.setAction((NatureGuardian.NatureGuardianAction.IDLE));
            cooldown = 50;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        natureGuardian.getLookControl().setLookAt(target, 30, 30);
        if (natureGuardian.distanceToSqr(target) > 100) {
            natureGuardian.getNavigation().moveTo(target, 0.5f);
        } else {
            natureGuardian.getNavigation().recomputePath();//is clearPathEntity() -> recomputePath
            if (natureGuardian.getAction() != NatureGuardian.NatureGuardianAction.THROWING_SICKLE) {
                natureGuardian.setAction(NatureGuardian.NatureGuardianAction.THROWING_SICKLE);
            }
            if (natureGuardian.getTicksInAction() == 12) {
                natureGuardian.lookAt(target, 100, 100);
                if (!natureGuardian.getLevel().isClientSide()) {
//                    ThrownSickle projectile = new ThrownSickle(natureGuardian.getLevel(), natureGuardian, 2.0f);
//                    projectile.setThrowingEntity(natureGuardian);
//                    projectile.setProjectileSpeed(2.0);
//                    natureGuardian.getLevel().addFreshEntity(projectile);
                }
            }
        }
    }
}
