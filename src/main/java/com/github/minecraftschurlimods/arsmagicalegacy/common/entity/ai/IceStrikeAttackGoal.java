package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardian;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class IceStrikeAttackGoal extends Goal {
    private final IceGuardian iceGuardian;
    private LivingEntity target;
    private int cooldown = 0;

    public IceStrikeAttackGoal(IceGuardian iceGuardian) {
        this.iceGuardian = iceGuardian;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || iceGuardian.getAction() != IceGuardian.IceGuardianAction.IDLE || iceGuardian.getTarget() == null || iceGuardian.getTarget().isDeadOrDying() || iceGuardian.getTarget() != null && iceGuardian.distanceToSqr(iceGuardian.getTarget()) > 4D && !iceGuardian.getNavigation().moveTo(iceGuardian.getTarget(), 0.5f))
            return false;
        target = iceGuardian.getTarget();
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (iceGuardian.getTarget() == null || iceGuardian.getTarget().isDeadOrDying() || (iceGuardian.getAction() == IceGuardian.IceGuardianAction.STRIKE && iceGuardian.getTicksInAction() > iceGuardian.getAction().getMaxActionTime())) {
            iceGuardian.setAction(IceGuardian.IceGuardianAction.IDLE);
            cooldown = 5;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        iceGuardian.getLookControl().setLookAt(target, 30, 30);
        iceGuardian.getNavigation().moveTo(target, 0.5f);
        if (iceGuardian.distanceToSqr(target) < 16) {
            if (iceGuardian.getAction() != IceGuardian.IceGuardianAction.STRIKE) {
                iceGuardian.setAction(IceGuardian.IceGuardianAction.STRIKE);
            }
        }
        if (iceGuardian.getAction() == IceGuardian.IceGuardianAction.STRIKE && iceGuardian.getTicksInAction() > 12) {
            double offsetX = Math.cos(iceGuardian.getYRot()) * 2;
            double offsetZ = Math.sin(iceGuardian.getYRot()) * 2;
            for (LivingEntity e : iceGuardian.getLevel().getEntitiesOfClass(LivingEntity.class, iceGuardian.getBoundingBox().expandTowards(offsetX, 0, offsetZ).inflate(2.5, 2, 2.5))) {
                if (e != iceGuardian) {
                    e.hurt(DamageSource.FREEZE, 6f);
                }
            }
        }
    }
}
