package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WaterGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class EarthSmashGoal extends Goal {
    private final EarthGuardian earthGuardian;
    private int cooldownTicks = 0;

    public EarthSmashGoal(EarthGuardian earthGuardian) {
        this.earthGuardian = earthGuardian;
    }

    @Override
    public boolean canUse() {
        if (earthGuardian.getAction() != EarthGuardian.EarthGuardianAction.IDLE || earthGuardian.getTarget() == null || earthGuardian.getTarget().isDeadOrDying())
            return false;
        earthGuardian.setAction(EarthGuardian.EarthGuardianAction.SMASH);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        cooldownTicks++;
        return earthGuardian.getAction() == EarthGuardian.EarthGuardianAction.SMASH && earthGuardian.getTarget() != null && !earthGuardian.getTarget().isDeadOrDying();
    }

    @Override
    public void stop() {
        super.stop();
        earthGuardian.setAction(EarthGuardian.EarthGuardianAction.IDLE);
        cooldownTicks = 0;
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = earthGuardian.getTarget();
        if (target == null) return;
        earthGuardian.lookAt(target, 30, 30);
        if (earthGuardian.distanceToSqr(target) > 64) {
            double angle = -Math.atan2(target.getZ() - earthGuardian.getZ(), target.getX() - earthGuardian.getX());
            earthGuardian.getNavigation().moveTo(target.getX() + Math.cos(angle) * 6, target.getY(), target.getZ() + Math.sin(angle) * 6, 0.5f);
        } else if (!earthGuardian.canAttack(target)) {
            earthGuardian.getNavigation().moveTo(target, 0.5f);
        } else if (cooldownTicks >= earthGuardian.getAction().getMaxActionTime()) {
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
