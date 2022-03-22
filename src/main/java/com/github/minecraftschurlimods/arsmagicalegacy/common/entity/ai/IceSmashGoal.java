package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class IceSmashGoal extends Goal {
    private final IceGuardian iceGuardian;
    private LivingEntity target;
    private int cooldown = 0;

    public IceSmashGoal(IceGuardian iceGuardian) {
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
        if (iceGuardian.getTarget() != null && iceGuardian.distanceToSqr(iceGuardian.getTarget()) > 4D) {
            if (iceGuardian.isOnGround()) return iceGuardian.getNavigation().moveTo(iceGuardian.getTarget(), 0.5f);
        }
        if (iceGuardian.getTarget() == null || iceGuardian.getTarget().isDeadOrDying() || (iceGuardian.getAction() == IceGuardian.IceGuardianAction.SMASH && iceGuardian.getTicksInAction() > iceGuardian.getAction().getMaxActionTime())) {
            iceGuardian.setAction(IceGuardian.IceGuardianAction.IDLE);
            cooldown = 100;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        iceGuardian.getLookControl().setLookAt(target, 30, 30);
        iceGuardian.getNavigation().moveTo(target, 0.5f);
        if (iceGuardian.distanceToSqr(target) < 16) {
            if (iceGuardian.getAction() != IceGuardian.IceGuardianAction.SMASH) {
                iceGuardian.setAction(IceGuardian.IceGuardianAction.SMASH);
            }
        }
        if (iceGuardian.getAction() == IceGuardian.IceGuardianAction.SMASH && iceGuardian.getTicksInAction() == 18) {
            if (!iceGuardian.getLevel().isClientSide()) {
                iceGuardian.getLevel().playSound(null, iceGuardian, AMSounds.EARTH_GUARDIAN_HURT.get(), SoundSource.HOSTILE, 1f, 1f);
            }
            for (LivingEntity e : iceGuardian.getLevel().getEntitiesOfClass(LivingEntity.class, iceGuardian.getBoundingBox().inflate(4, 2, 4))) {
                if (e != iceGuardian) {
                    e.hurt(DamageSource.FREEZE, 8);
                    e.setDeltaMovement(e.getDeltaMovement().add(0, 1.4f, 0));
                }
            }
            if (!iceGuardian.getLevel().isClientSide()) {
//                for (int i = 0; i < 4; ++i) {
//                    Shockwave shockwave = new Shockwave(iceGuardian.getLevel());
//                    shockwave.setPos(iceGuardian.getX(), iceGuardian.getY(), iceGuardian.getZ());
//                    shockwave.setMoveSpeedAndAngle(0.5f, Mth.wrapDegrees(iceGuardian.getYRot() + (90 * i)));
//                    iceGuardian.getLevel().addFreshEntity(shockwave);
//                }
            }
        }
    }
}
