package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class EarthStrikeAttackGoal extends Goal {
    private final EarthGuardian earthGuardian;
    private final float         moveSpeed;
    private       LivingEntity  target;
    private       int           cooldown = 0;
    private final float         damage;

    public EarthStrikeAttackGoal(EarthGuardian earthGuardian, float moveSpeed, float damage) {
        this.earthGuardian = earthGuardian;
        this.moveSpeed = moveSpeed;
        this.damage = damage;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || earthGuardian.getEarthGuardianAction() != EarthGuardian.EarthGuardianAction.IDLE) {
            return false;
        }
        if (earthGuardian.getTarget() == null || earthGuardian.getTarget().isDeadOrDying()) {
            return false;
        }
        if (earthGuardian.getTarget() != null && earthGuardian.distanceToSqr(earthGuardian.getTarget()) > 4D) {
            if (!earthGuardian.getNavigation().moveTo(earthGuardian.getTarget(), moveSpeed)) {
                return false;
            }
        }
        this.target = earthGuardian.getTarget();
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
        earthGuardian.getNavigation().moveTo(target, moveSpeed);
        if (earthGuardian.distanceToSqr(target) < 16) {
            if (earthGuardian.getEarthGuardianAction() != EarthGuardian.EarthGuardianAction.STRIKE) {
                earthGuardian.setEarthGuardianAction(EarthGuardian.EarthGuardianAction.STRIKE);
            }
        }

        if (earthGuardian.getEarthGuardianAction() == EarthGuardian.EarthGuardianAction.STRIKE && earthGuardian.getTicksInAction() > 12) {
            if (!earthGuardian.level.isClientSide()) {
                earthGuardian.level.playSound(null, earthGuardian, AMSounds.EARTH_GUARDIAN_HURT.get(), SoundSource.HOSTILE, 1f, 1f);
            }

            double offsetX = Math.cos(earthGuardian.getYRot()) * 2;
            double offsetZ = Math.sin(earthGuardian.getYRot()) * 2;
            List<LivingEntity> nearbyEntities = earthGuardian.level.getEntitiesOfClass(LivingEntity.class, earthGuardian.getBoundingBox().expandTowards(offsetX, 0, offsetZ).inflate(2.5, 2, 2.5));  // is offset() --> expandTowards
            for (LivingEntity e : nearbyEntities) {
                if (e != earthGuardian) {
                    e.hurt(DamageSource.mobAttack(earthGuardian), damage);  // maybe wrong, hurt(DamageSources.causeDamage(damageType, host, true), damage);
                }
            }
        }
    }
}
