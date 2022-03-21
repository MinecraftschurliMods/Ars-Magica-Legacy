package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class EarthStrikeAttackGoal extends Goal {
    private final EarthGuardian earthGuardian;
    private LivingEntity target;
    private int cooldown = 0;

    public EarthStrikeAttackGoal(EarthGuardian earthGuardian) {
        this.earthGuardian = earthGuardian;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || earthGuardian.getEarthGuardianAction() != EarthGuardian.EarthGuardianAction.IDLE || earthGuardian.getTarget() == null || earthGuardian.getTarget().isDeadOrDying() || earthGuardian.getTarget() != null && earthGuardian.distanceToSqr(earthGuardian.getTarget()) > 4D && !earthGuardian.getNavigation().moveTo(earthGuardian.getTarget(), 0.5f))
            return false;
        target = earthGuardian.getTarget();
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (earthGuardian.getTarget() == null || earthGuardian.getTarget().isDeadOrDying() || (earthGuardian.getEarthGuardianAction() == EarthGuardian.EarthGuardianAction.STRIKE && earthGuardian.getTicksInAction() > earthGuardian.getEarthGuardianAction().getMaxActionTime())) {
            earthGuardian.setEarthGuardianAction(EarthGuardian.EarthGuardianAction.IDLE);
            cooldown = 5;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        earthGuardian.getLookControl().setLookAt(target, 30, 30);
        earthGuardian.getNavigation().moveTo(target, 0.5f);
        if (earthGuardian.distanceToSqr(target) < 16) {
            if (earthGuardian.getEarthGuardianAction() != EarthGuardian.EarthGuardianAction.STRIKE) {
                earthGuardian.setEarthGuardianAction(EarthGuardian.EarthGuardianAction.STRIKE);
            }
        }
        if (earthGuardian.getEarthGuardianAction() == EarthGuardian.EarthGuardianAction.STRIKE && earthGuardian.getTicksInAction() > 12) {
            if (!earthGuardian.getLevel().isClientSide()) {
                earthGuardian.getLevel().playSound(null, earthGuardian, AMSounds.EARTH_GUARDIAN_HURT.get(), SoundSource.HOSTILE, 1f, 1f);
            }
            double offsetX = Math.cos(earthGuardian.getYRot()) * 2;
            double offsetZ = Math.sin(earthGuardian.getYRot()) * 2;
            for (LivingEntity e : earthGuardian.getLevel().getEntitiesOfClass(LivingEntity.class, earthGuardian.getBoundingBox().expandTowards(offsetX, 0, offsetZ).inflate(2.5, 2, 2.5))) {
                if (e != earthGuardian) {
                    e.hurt(DamageSource.mobAttack(earthGuardian), 4f);
                }
            }
        }
    }
}
