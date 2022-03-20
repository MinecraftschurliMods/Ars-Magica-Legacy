package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class EarthSmashGoal extends Goal {
    private final EarthGuardian earthGuardian;
    private       LivingEntity  target;
    private final float         moveSpeed;
    private       int           cooldown = 0;

    public EarthSmashGoal(EarthGuardian earthGuardian, float moveSpeed) {
        this.earthGuardian = earthGuardian;
        this.moveSpeed = moveSpeed;
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
        if (earthGuardian.getTarget() != null && earthGuardian.distanceToSqr(earthGuardian.getTarget()) > 4D) {
            if (earthGuardian.isOnGround()) {
                return earthGuardian.getNavigation().moveTo(earthGuardian.getTarget(), moveSpeed);
            }
        }
        if (earthGuardian.getTarget() == null || earthGuardian.getTarget().isDeadOrDying() || (earthGuardian.getEarthGuardianAction() == EarthGuardian.EarthGuardianAction.SMASH && earthGuardian.getTicksInAction() > earthGuardian.getEarthGuardianAction().getMaxActionTime())) {
            earthGuardian.setEarthGuardianAction(EarthGuardian.EarthGuardianAction.IDLE);
            cooldown = 100;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        earthGuardian.getLookControl().setLookAt(earthGuardian.getTarget(), 30, 30);
        earthGuardian.getNavigation().moveTo(target, moveSpeed);
        if (earthGuardian.distanceToSqr(target) < 16) {
            if (earthGuardian.getEarthGuardianAction() != EarthGuardian.EarthGuardianAction.SMASH) {
                earthGuardian.setEarthGuardianAction(EarthGuardian.EarthGuardianAction.SMASH);
            }
        }
        if (earthGuardian.getEarthGuardianAction() == EarthGuardian.EarthGuardianAction.SMASH && earthGuardian.getTicksInAction() == 18) {
            if (!earthGuardian.level.isClientSide()) {
                earthGuardian.level.playSound(null, earthGuardian, AMSounds.EARTH_GUARDIAN_HURT.get(), SoundSource.HOSTILE, 1f, 1f);
            }

            List<LivingEntity> nearbyEntities = earthGuardian.level.getEntitiesOfClass(LivingEntity.class, earthGuardian.getBoundingBox().inflate(4, 2, 4));
            for (LivingEntity e : nearbyEntities) {
                if (e != earthGuardian) {
                    e.hurt(DamageSource.mobAttack(earthGuardian), 8); // maybe wrong, hurt(DamageSources.causeDamage(damageType, host, true), 8);
                    if (e instanceof Player) {
                        //AMNetHandler.INSTANCE.sendVelocityAddPacket(host.worldObj, ent, 0, 1.3f, 0);
                    } else {
                        //e.addVelocity(0, 1.4f, 0);
                    }
                }
            }
            if (!earthGuardian.level.isClientSide()) {
                //                for (int i = 0; i < 4; ++i) {
                //                    Shockwave shockwave = new Shockwave(earthGuardian.level);
                //                    shockwave.setPos(earthGuardian.getX(), earthGuardian.getY(), earthGuardian.getZ());
                //                    shockwave.setMoveSpeedAndAngle(0.5f, Mth.wrapDegrees(earthGuardian.getYRot() + (90 * i)));
                //                    earthGuardian.level.addFreshEntity(shockwave);
                //                }
            }
        }
    }
}
