package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class ThrowRockGoal extends Goal {
    private final EarthGuardian earthGuardian;
    private final float moveSpeed;
    private LivingEntity target;
    private int cooldown = 0;

    public ThrowRockGoal(EarthGuardian earthGuardian, float moveSpeed) {
        this.earthGuardian = earthGuardian;
        this.moveSpeed = moveSpeed;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || earthGuardian.getEarthGuardianAction() != EarthGuardian.EarthGuardianAction.IDLE) return false;
        if (earthGuardian.getTarget() == null || earthGuardian.getTarget().isDeadOrDying()) return false;
        this.target = earthGuardian.getTarget();
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (earthGuardian.getTarget() == null || earthGuardian.getTarget().isDeadOrDying() || (earthGuardian.getEarthGuardianAction() == EarthGuardian.EarthGuardianAction.THROWING_ROCK && earthGuardian.getTicksInAction() > earthGuardian.getEarthGuardianAction().getMaxActionTime())) {
            earthGuardian.setEarthGuardianAction(EarthGuardian.EarthGuardianAction.IDLE);
            cooldown = 50;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        earthGuardian.getLookControl().setLookAt(target, 30, 30);
        if (earthGuardian.distanceToSqr(target) > 100) {
            earthGuardian.getNavigation().moveTo(target, moveSpeed);
        } else {
            earthGuardian.getNavigation().recomputePath();  // is clearPathEntity() --> recomputePath()
            if (earthGuardian.getEarthGuardianAction() != EarthGuardian.EarthGuardianAction.THROWING_ROCK) {
                earthGuardian.setEarthGuardianAction(EarthGuardian.EarthGuardianAction.THROWING_ROCK);
            }
            if (earthGuardian.getTicksInAction() == 27) {
                if (!earthGuardian.level.isClientSide()) {
                    earthGuardian.level.playSound(null, earthGuardian, AMSounds.EARTH_GUARDIAN_HURT.get(), SoundSource.HOSTILE, 1.0f, 1.0f);
                }
                earthGuardian.lookAt(target, 180, 180);
                if (!earthGuardian.level.isClientSide()) {
//                    ThrowRock projectile = new ThrowRock(earthGuardian.level, earthGuardian, 2.0f);
//                    earthGuardian.level.addFreshEntity(projectile);
                }
            }
        }

    }
}
