package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardian;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class IceStrikeAttackGoal extends Goal {
    private final IceGuardian iceGuardian;
    private final float          moveSpeed;
    private       LivingEntity   target;
    private       int            cooldown = 0;
    private final float          damage;

    public IceStrikeAttackGoal(IceGuardian iceGuardian, float moveSpeed, float damage) {
        this.iceGuardian = iceGuardian;
        this.moveSpeed = moveSpeed;
        this.damage = damage;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || iceGuardian.getIceGuardianAction() != IceGuardian.IceGuardianAction.IDLE) {
            return false;
        }
        if (iceGuardian.getTarget() == null || iceGuardian.getTarget().isDeadOrDying()) {
            return false;
        }
        if (iceGuardian.getTarget() != null && iceGuardian.distanceToSqr(iceGuardian.getTarget()) > 4D) {
            if (!iceGuardian.getNavigation().moveTo(iceGuardian.getTarget(), moveSpeed)) {
                return false;
            }
        }
        this.target = iceGuardian.getTarget();
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (iceGuardian.getTarget() == null || iceGuardian.getTarget().isDeadOrDying() || (iceGuardian.getIceGuardianAction() == IceGuardian.IceGuardianAction.STRIKE && iceGuardian.getTicksInAction() > iceGuardian.getIceGuardianAction().getMaxActionTime())) {
            iceGuardian.setIceGuardianAction(IceGuardian.IceGuardianAction.IDLE);
            cooldown = 5;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        iceGuardian.getLookControl().setLookAt(target, 30, 30);
        iceGuardian.getNavigation().moveTo(target, moveSpeed);
        if (iceGuardian.distanceToSqr(target) < 16) {
            if (iceGuardian.getIceGuardianAction() != IceGuardian.IceGuardianAction.STRIKE) {
                iceGuardian.setIceGuardianAction(IceGuardian.IceGuardianAction.STRIKE);
            }
        }

        if (iceGuardian.getIceGuardianAction() == IceGuardian.IceGuardianAction.STRIKE && iceGuardian.getTicksInAction() > 12) {
            if (!iceGuardian.level.isClientSide()) {
                //iceGuardian.level.playSound(null, iceGuardian, AMSounds.ICE_GUARDIAN_HURT.get(), SoundSource.HOSTILE, 1f, 1f);
            }

            double offsetX = Math.cos(iceGuardian.getYRot()) * 2;
            double offsetZ = Math.sin(iceGuardian.getYRot()) * 2;
            List<LivingEntity> nearbyEntities = iceGuardian.level.getEntitiesOfClass(LivingEntity.class, iceGuardian.getBoundingBox().expandTowards(offsetX, 0, offsetZ).inflate(2.5, 2, 2.5));  // is offset() --> expandTowards
            for (LivingEntity e : nearbyEntities) {
                if (e != iceGuardian) {
                    e.hurt(DamageSource.FREEZE, damage);  // maybe wrong, hurt(DamageSources.causeDamage(damageType, host, true), damage);
                }
            }
        }
    }
}
