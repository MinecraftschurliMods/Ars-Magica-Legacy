package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class LaunchArmGoal extends Goal {
    private final IceGuardian  iceGuardian;
    private final float        moveSpeed;
    private       LivingEntity target;
    private       int          cooldown = 0;

    public LaunchArmGoal(IceGuardian iceGuardian, float moveSpeed) {
        this.iceGuardian = iceGuardian;
        this.moveSpeed = moveSpeed;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || iceGuardian.getTarget() == null) {
            return false;
        }
        if (iceGuardian.getTarget().distanceToSqr(iceGuardian) > 16D) {
            target = iceGuardian.getTarget();
            return true;
        }
        List<LivingEntity> nearbyEntities = iceGuardian.level.getEntitiesOfClass(LivingEntity.class, iceGuardian.getBoundingBox().inflate(20, 20, 20));
        if (nearbyEntities.size() > 0) {
            for (LivingEntity e : nearbyEntities) {
                if (!(e instanceof Player) && (!((Player) e).isCreative())) {  // maybe wrong
                    if (e.distanceToSqr(iceGuardian) > 49D) {
                        target = e;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (target == null || target.isDeadOrDying() || iceGuardian.distanceToSqr(target) < 49D || iceGuardian.distanceToSqr(target) > 225D || (iceGuardian.getIceGuardianAction() == IceGuardian.IceGuardianAction.LAUNCHING && iceGuardian.getTicksInAction() > iceGuardian.getIceGuardianAction().getMaxActionTime())) {
            iceGuardian.setIceGuardianAction(IceGuardian.IceGuardianAction.IDLE);
            target = null;
            if (!iceGuardian.hasLeftArm() && !iceGuardian.hasRightArm()) {
                cooldown = 20;
            }
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        iceGuardian.getLookControl().setLookAt(target, 30, 30);
        if (iceGuardian.distanceToSqr(target) > 144 && iceGuardian.getIceGuardianAction() == IceGuardian.IceGuardianAction.IDLE) {
            iceGuardian.getNavigation().moveTo(target, moveSpeed);
        } else {
            iceGuardian.getNavigation().recomputePath(); // is clearPathEntity() --> recomputePath()
            if (iceGuardian.getIceGuardianAction() != IceGuardian.IceGuardianAction.LAUNCHING) {
                iceGuardian.setIceGuardianAction(IceGuardian.IceGuardianAction.LAUNCHING);
            }
            if (iceGuardian.getTicksInAction() == 14) {
                iceGuardian.lookAt(target, 180, 180);
                if (!iceGuardian.level.isClientSide()) {
                    iceGuardian.level.playSound(null, iceGuardian, AMSounds.ICE_GUARDIAN_LAUNCH_ARM.get(), SoundSource.HOSTILE, 1.0f, 1.0f);

                    //                    IceGuardianArm projectile = new IceGuardianArm(iceGuardian.level, iceGuardian, 1.25f);
                    //                    projectile.setThrowingEntity(iceGuardian);
                    //                    projectile.setProjectileSpeed(2.0);
                    //                    iceGuardian.level.addFreshEntity(projectile);
                }
            }
        }
    }
}
