package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class NatureStrikeAttackGoal extends Goal {
    private final NatureGuardian natureGuardian;
    private final float          moveSpeed;
    private       LivingEntity   target;
    private       int            cooldown = 0;
    private final float          damage;

    public NatureStrikeAttackGoal(NatureGuardian natureGuardian, float moveSpeed, float damage) {
        this.natureGuardian = natureGuardian;
        this.moveSpeed = moveSpeed;
        this.damage = damage;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || natureGuardian.getNatureGuardianAction() != NatureGuardian.NatureGuardianAction.IDLE) {
            return false;
        }
        if (natureGuardian.getTarget() == null || natureGuardian.getTarget().isDeadOrDying()) {
            return false;
        }
        if (natureGuardian.getTarget() != null && natureGuardian.distanceToSqr(natureGuardian.getTarget()) > 4D) {
            if (!natureGuardian.getNavigation().moveTo(natureGuardian.getTarget(), moveSpeed)) {
                return false;
            }
        }
        this.target = natureGuardian.getTarget();
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
        natureGuardian.getNavigation().moveTo(target, moveSpeed);
        if (natureGuardian.distanceToSqr(target) < 16) {
            if (natureGuardian.getNatureGuardianAction() != NatureGuardian.NatureGuardianAction.STRIKE) {
                natureGuardian.setNatureGuardianAction(NatureGuardian.NatureGuardianAction.STRIKE);
            }
        }

        if (natureGuardian.getNatureGuardianAction() == NatureGuardian.NatureGuardianAction.STRIKE && natureGuardian.getTicksInAction() > 12) {
            if (!natureGuardian.level.isClientSide()) {
                natureGuardian.level.playSound(null, natureGuardian, AMSounds.EARTH_GUARDIAN_HURT.get(), SoundSource.HOSTILE, 1f, 1f);
            }

            double offsetX = Math.cos(natureGuardian.getYRot()) * 2;
            double offsetZ = Math.sin(natureGuardian.getYRot()) * 2;
            List<LivingEntity> nearbyEntities = natureGuardian.level.getEntitiesOfClass(LivingEntity.class, natureGuardian.getBoundingBox().expandTowards(offsetX, 0, offsetZ).inflate(2.5, 2, 2.5));  // is offset() --> expandTowards
            for (LivingEntity e : nearbyEntities) {
                if (e != natureGuardian) {
                    e.hurt(DamageSource.mobAttack(natureGuardian), damage);  // maybe wrong, hurt(DamageSources.causeDamage(damageType, host, true), 8);
                }
            }
        }
    }
}
