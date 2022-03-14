package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class NatureSpinAttackGoal extends Goal {
    private final NatureGuardian natureGuardian;
    private final float moveSpeed;
    private LivingEntity target;
    private int cooldown = 0;
    private final float damage;

    public NatureSpinAttackGoal(NatureGuardian natureGuardian, float moveSpeed, float damage) {
        this.natureGuardian = natureGuardian;
        this.moveSpeed = moveSpeed;
        this.damage = damage;
    }


    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || natureGuardian.getNatureGuardianAction() != NatureGuardian.NatureGuardianAction.IDLE || !natureGuardian.isNatureGuardianActionValid(NatureGuardian.NatureGuardianAction.SPINNING)) {
            return false;
        }
        if (natureGuardian.getTarget() == null || natureGuardian.getTarget().isDeadOrDying() || natureGuardian.getTarget().distanceToSqr(natureGuardian) > 25) {
            return false;
        }
        this.target = natureGuardian.getTarget();
        natureGuardian.setNatureGuardianAction(NatureGuardian.NatureGuardianAction.SPINNING);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (natureGuardian.getTarget() == null || natureGuardian.getTarget().isDeadOrDying() || natureGuardian.getTicksInAction() > NatureGuardian.NatureGuardianAction.SPINNING.getMaxActionTime()) {
            stop();
            return false;
        }
        return true;
    }

    @Override
    public void stop() {
        natureGuardian.setNatureGuardianAction(NatureGuardian.NatureGuardianAction.IDLE);
        cooldown = 150;
        super.stop();
    }

    @Override
    public void tick() {
        super.tick();
        natureGuardian.getLookControl().setLookAt(target, 30, 30);
        natureGuardian.getNavigation().moveTo(target, moveSpeed);
        List<LivingEntity> nearbyEntities = natureGuardian.level.getEntitiesOfClass(LivingEntity.class, this.natureGuardian.getBoundingBox().inflate(2, 2, 2));
        for (LivingEntity e : nearbyEntities) {
            if (e == natureGuardian) {
                natureGuardian.hurt(DamageSource.mobAttack(natureGuardian), damage);
            }
        }

        if (natureGuardian.getTicksInAction() % 50 == 0) {
            if (!natureGuardian.level.isClientSide()) {
                natureGuardian.level.playSound(null, natureGuardian, AMSounds.NATURE_GUARDIAN_ATTACK.get(), SoundSource.HOSTILE, 1f, 1f);
            }
        }
    }
}
