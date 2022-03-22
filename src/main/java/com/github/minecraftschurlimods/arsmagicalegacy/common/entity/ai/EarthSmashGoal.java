package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class EarthSmashGoal extends Goal {
    private final EarthGuardian earthGuardian;
    private LivingEntity target;
    private int cooldown = 0;

    public EarthSmashGoal(EarthGuardian earthGuardian) {
        this.earthGuardian = earthGuardian;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || earthGuardian.getAction() != EarthGuardian.EarthGuardianAction.IDLE || earthGuardian.getTarget() == null || earthGuardian.getTarget().isDeadOrDying() || earthGuardian.getTarget() != null && earthGuardian.distanceToSqr(earthGuardian.getTarget()) > 4D && !earthGuardian.getNavigation().moveTo(earthGuardian.getTarget(), 0.5f))
            return false;
        target = earthGuardian.getTarget();
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (earthGuardian.getTarget() != null && earthGuardian.distanceToSqr(earthGuardian.getTarget()) > 4D) {
            if (earthGuardian.isOnGround()) {
                return earthGuardian.getNavigation().moveTo(earthGuardian.getTarget(), 0.5f);
            }
        }
        if (earthGuardian.getTarget() == null || earthGuardian.getTarget().isDeadOrDying() || (earthGuardian.getAction() == EarthGuardian.EarthGuardianAction.SMASH && earthGuardian.getTicksInAction() > earthGuardian.getAction().getMaxActionTime())) {
            earthGuardian.setAction(EarthGuardian.EarthGuardianAction.IDLE);
            cooldown = 100;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        if (earthGuardian.getTarget() != null) {
            earthGuardian.getLookControl().setLookAt(earthGuardian.getTarget(), 30, 30);
        }
        earthGuardian.getNavigation().moveTo(target, 0.5f);
        if (earthGuardian.distanceToSqr(target) < 16) {
            if (earthGuardian.getAction() != EarthGuardian.EarthGuardianAction.SMASH) {
                earthGuardian.setAction(EarthGuardian.EarthGuardianAction.SMASH);
            }
        }
        if (earthGuardian.getAction() == EarthGuardian.EarthGuardianAction.SMASH && earthGuardian.getTicksInAction() == 18) {
            if (!earthGuardian.getLevel().isClientSide()) {
                earthGuardian.getLevel().playSound(null, earthGuardian, AMSounds.EARTH_GUARDIAN_HURT.get(), SoundSource.HOSTILE, 1f, 1f);
            }
            for (LivingEntity e : earthGuardian.getLevel().getEntitiesOfClass(LivingEntity.class, earthGuardian.getBoundingBox().inflate(4, 2, 4))) {
                if (e != earthGuardian) {
                    e.hurt(DamageSource.mobAttack(earthGuardian), 8);
                    e.setDeltaMovement(e.getDeltaMovement().add(0, 1.4f, 0));
                }
            }
//            if (!earthGuardian.getLevel().isClientSide()) {
//                for (int i = 0; i < 4; ++i) {
//                    Shockwave shockwave = new Shockwave(earthGuardian.getLevel());
//                    shockwave.setPos(earthGuardian.getX(), earthGuardian.getY(), earthGuardian.getZ());
//                    shockwave.setMoveSpeedAndAngle(0.5f, Mth.wrapDegrees(earthGuardian.getYRot() + (90 * i)));
//                    earthGuardian.getLevel().addFreshEntity(shockwave);
//                }
//            }
        }
    }
}
