package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class NatureStrikeAttackGoal extends Goal {
    private final NatureGuardian natureGuardian;
    private LivingEntity target;
    private int cooldown = 0;

    public NatureStrikeAttackGoal(NatureGuardian natureGuardian) {
        this.natureGuardian = natureGuardian;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || natureGuardian.getNatureGuardianAction() != NatureGuardian.NatureGuardianAction.IDLE || natureGuardian.getTarget() == null || natureGuardian.getTarget().isDeadOrDying() || natureGuardian.getTarget() != null && natureGuardian.distanceToSqr(natureGuardian.getTarget()) > 4D && !natureGuardian.getNavigation().moveTo(natureGuardian.getTarget(), 0.5f))
            return false;
        target = natureGuardian.getTarget();
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (natureGuardian.getTarget() == null || natureGuardian.getTarget().isDeadOrDying() || (natureGuardian.getNatureGuardianAction() == NatureGuardian.NatureGuardianAction.STRIKE && natureGuardian.getTicksInAction() > natureGuardian.getNatureGuardianAction().getMaxActionTime())) {
            natureGuardian.setNatureGuardianAction(NatureGuardian.NatureGuardianAction.IDLE);
            cooldown = 5;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        natureGuardian.getLookControl().setLookAt(target, 30, 30);
        natureGuardian.getNavigation().moveTo(target, 0.5f);
        if (natureGuardian.distanceToSqr(target) < 16) {
            if (natureGuardian.getNatureGuardianAction() != NatureGuardian.NatureGuardianAction.STRIKE) {
                natureGuardian.setNatureGuardianAction(NatureGuardian.NatureGuardianAction.STRIKE);
            }
        }
        if (natureGuardian.getNatureGuardianAction() == NatureGuardian.NatureGuardianAction.STRIKE && natureGuardian.getTicksInAction() > 12) {
            if (!natureGuardian.getLevel().isClientSide()) {
                natureGuardian.getLevel().playSound(null, natureGuardian, AMSounds.EARTH_GUARDIAN_HURT.get(), SoundSource.HOSTILE, 1f, 1f);
            }
            double offsetX = Math.cos(natureGuardian.getYRot()) * 2;
            double offsetZ = Math.sin(natureGuardian.getYRot()) * 2;
            for (LivingEntity e : natureGuardian.getLevel().getEntitiesOfClass(LivingEntity.class, natureGuardian.getBoundingBox().expandTowards(offsetX, 0, offsetZ).inflate(2.5, 2, 2.5))) {
                if (e != natureGuardian) {
                    e.hurt(DamageSource.mobAttack(natureGuardian), 4f);
                }
            }
        }
    }
}
