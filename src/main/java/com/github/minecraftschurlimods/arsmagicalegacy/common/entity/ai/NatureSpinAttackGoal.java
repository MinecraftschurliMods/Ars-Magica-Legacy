package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class NatureSpinAttackGoal extends Goal {
    private final NatureGuardian natureGuardian;
    private LivingEntity target;
    private int cooldown = 0;

    public NatureSpinAttackGoal(NatureGuardian natureGuardian) {
        this.natureGuardian = natureGuardian;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || natureGuardian.getAction() != NatureGuardian.NatureGuardianAction.IDLE || !natureGuardian.isNatureGuardianActionValid(NatureGuardian.NatureGuardianAction.SPINNING) || natureGuardian.getTarget() == null || natureGuardian.getTarget().isDeadOrDying() || natureGuardian.getTarget().distanceToSqr(natureGuardian) > 25) {
            return false;
        }
        target = natureGuardian.getTarget();
        natureGuardian.setAction(NatureGuardian.NatureGuardianAction.SPINNING);
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
        natureGuardian.setAction(NatureGuardian.NatureGuardianAction.IDLE);
        cooldown = 150;
        super.stop();
    }

    @Override
    public void tick() {
        super.tick();
        natureGuardian.getLookControl().setLookAt(target, 30, 30);
        natureGuardian.getNavigation().moveTo(target, 0.5f);
        for (LivingEntity e : natureGuardian.getLevel().getEntitiesOfClass(LivingEntity.class, this.natureGuardian.getBoundingBox().inflate(2, 2, 2))) {
            if (e == natureGuardian) {
                natureGuardian.hurt(DamageSource.mobAttack(natureGuardian), 8f);
            }
        }
        if (natureGuardian.getTicksInAction() % 50 == 0) {
            if (!natureGuardian.getLevel().isClientSide()) {
                natureGuardian.getLevel().playSound(null, natureGuardian, AMSounds.NATURE_GUARDIAN_ATTACK.get(), SoundSource.HOSTILE, 1f, 1f);
            }
        }
    }
}
