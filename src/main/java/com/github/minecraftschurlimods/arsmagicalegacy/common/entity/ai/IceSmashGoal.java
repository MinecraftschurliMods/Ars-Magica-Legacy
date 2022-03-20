package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class IceSmashGoal extends Goal {
    private final IceGuardian  iceGuardian;
    private       LivingEntity target;
    private final float        moveSpeed;
    private       int          cooldown = 0;

    public IceSmashGoal(IceGuardian iceGuardian, float moveSpeed) {
        this.iceGuardian = iceGuardian;
        this.moveSpeed = moveSpeed;
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
        if (iceGuardian.getTarget() != null && iceGuardian.distanceToSqr(iceGuardian.getTarget()) > 4D) {
            if (iceGuardian.isOnGround()) {
                return iceGuardian.getNavigation().moveTo(iceGuardian.getTarget(), moveSpeed);
            }
        }
        if (iceGuardian.getTarget() == null || iceGuardian.getTarget().isDeadOrDying() || (iceGuardian.getIceGuardianAction() == IceGuardian.IceGuardianAction.SMASH && iceGuardian.getTicksInAction() > iceGuardian.getIceGuardianAction().getMaxActionTime())) {
            iceGuardian.setIceGuardianAction(IceGuardian.IceGuardianAction.IDLE);
            cooldown = 100;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        iceGuardian.getLookControl().setLookAt(iceGuardian.getTarget(), 30, 30);
        iceGuardian.getNavigation().moveTo(target, moveSpeed);
        if (iceGuardian.distanceToSqr(target) < 16) {
            if (iceGuardian.getIceGuardianAction() != IceGuardian.IceGuardianAction.SMASH) {
                iceGuardian.setIceGuardianAction(IceGuardian.IceGuardianAction.SMASH);
            }
        }
        if (iceGuardian.getIceGuardianAction() == IceGuardian.IceGuardianAction.SMASH && iceGuardian.getTicksInAction() == 18) {
            if (!iceGuardian.level.isClientSide()) {
                iceGuardian.level.playSound(null, iceGuardian, AMSounds.EARTH_GUARDIAN_HURT.get(), SoundSource.HOSTILE, 1f, 1f);
            }

            List<LivingEntity> nearbyEntities = iceGuardian.level.getEntitiesOfClass(LivingEntity.class, iceGuardian.getBoundingBox().inflate(4, 2, 4));
            for (LivingEntity e : nearbyEntities) {
                if (e != iceGuardian) {
                    e.hurt(DamageSource.FREEZE, 8);  // maybe wrong
                    if (e instanceof Player) {
                        //AMNetHandler.INSTANCE.sendVelocityAddPacket(host.worldObj, ent, 0, 1.3f, 0);
                    } else {
                        //e.addVelocity(0, 1.4f, 0);
                    }
                }
            }
            if (!iceGuardian.level.isClientSide()) {
                //                for (int i = 0; i < 4; ++i) {
                //                    Shockwave shockwave = new Shockwave(iceGuardian.level);
                //                    shockwave.setPos(iceGuardian.getX(), iceGuardian.getY(), iceGuardian.getZ());
                //                    shockwave.setMoveSpeedAndAngle(0.5f, Mth.wrapDegrees(iceGuardian.getYRot() + (90 * i)));
                //                    iceGuardian.level.addFreshEntity(shockwave);
                //                }
            }
        }
    }
}
