package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class ShieldBashGoal extends Goal {
    private final NatureGuardian natureGuardian;
    private LivingEntity target;
    private int cooldown = 0;

    public ShieldBashGoal(NatureGuardian natureGuardian) {
        this.natureGuardian = natureGuardian;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || natureGuardian.getNatureGuardianAction() != NatureGuardian.NatureGuardianAction.IDLE || natureGuardian.isNatureGuardianActionValid(NatureGuardian.NatureGuardianAction.SHIELD_BASH) || natureGuardian.getTarget() == null || natureGuardian.getTarget().isDeadOrDying())
            return false;
        target = natureGuardian.getTarget();
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (natureGuardian.getTarget() == null || natureGuardian.getTarget().isDeadOrDying() || (natureGuardian.getNatureGuardianAction() == NatureGuardian.NatureGuardianAction.SHIELD_BASH && natureGuardian.getTicksInAction() > natureGuardian.getNatureGuardianAction().getMaxActionTime())) {
            natureGuardian.setNatureGuardianAction(NatureGuardian.NatureGuardianAction.IDLE);
            cooldown = 10;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        natureGuardian.getLookControl().setLookAt(target, 30, 30);
        natureGuardian.getNavigation().moveTo(target, 0.75f);
        if (natureGuardian.distanceToSqr(target) < 16) {
            if (natureGuardian.getNatureGuardianAction() != NatureGuardian.NatureGuardianAction.SHIELD_BASH) {
                natureGuardian.setNatureGuardianAction(NatureGuardian.NatureGuardianAction.SHIELD_BASH);
            }
        }
        if (natureGuardian.getNatureGuardianAction() == NatureGuardian.NatureGuardianAction.SHIELD_BASH && natureGuardian.getTicksInAction() > 12) {
            if (!natureGuardian.getLevel().isClientSide()) {
                natureGuardian.getLevel().playSound(null, natureGuardian, AMSounds.NATURE_GUARDIAN_HURT.get(), SoundSource.HOSTILE, 1f, 1f);
            }
            double offsetX = Math.cos(natureGuardian.getYRot()) * 2;
            double offsetZ = Math.sin(natureGuardian.getYRot()) * 2;
            for (LivingEntity e : natureGuardian.getLevel().getEntitiesOfClass(LivingEntity.class, natureGuardian.getBoundingBox().expandTowards(offsetX, 0, offsetZ).inflate(2.5, 2, 2.5))) {
                if (e != natureGuardian) {
                    double speed = 4;
                    double vertSpeed = 0.325;
                    double deltaX = e.getX() - natureGuardian.getX();
                    double deltaZ = e.getZ() - natureGuardian.getZ();
                    double angle = Math.atan2(deltaZ, deltaX);
                    e.setDeltaMovement(speed * Math.cos(angle), vertSpeed, speed * Math.sin(angle));
                    e.hurt(DamageSource.mobAttack(natureGuardian), 2);
                }
            }
        }
    }
}
