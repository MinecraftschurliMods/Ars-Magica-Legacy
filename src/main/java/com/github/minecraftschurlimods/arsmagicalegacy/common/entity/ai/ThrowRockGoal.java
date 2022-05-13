package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class ThrowRockGoal extends Goal {
    private final EarthGuardian earthGuardian;
    private int cooldownTicks = 0;

    public ThrowRockGoal(EarthGuardian earthGuardian) {
        this.earthGuardian = earthGuardian;
    }

    @Override
    public boolean canUse() {
        if (earthGuardian.getAction() != EarthGuardian.EarthGuardianAction.IDLE || earthGuardian.getTarget() == null || earthGuardian.getTarget().isDeadOrDying())
            return false;
        earthGuardian.setAction(EarthGuardian.EarthGuardianAction.THROWING_ROCK);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        cooldownTicks++;
        return earthGuardian.getAction() == EarthGuardian.EarthGuardianAction.THROWING_ROCK && earthGuardian.getTarget() != null && !earthGuardian.getTarget().isDeadOrDying();
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = earthGuardian.getTarget();
        if (target == null) return;
        earthGuardian.lookAt(target, 30, 30);
        if (earthGuardian.distanceToSqr(target) > 64) {
            double angle = -Math.atan2(target.getZ() - earthGuardian.getZ(), target.getX() - earthGuardian.getX());
            earthGuardian.getNavigation().moveTo(target.getX() + Math.cos(angle) * 6, target.getY(), target.getZ() + Math.sin(angle) * 6, 0.5f);
        } else if (!earthGuardian.canAttack(target)) {
            earthGuardian.getNavigation().moveTo(target, 0.5f);
        } else if (cooldownTicks >= earthGuardian.getAction().getMaxActionTime()) {
            if (!earthGuardian.getLevel().isClientSide()) {
                earthGuardian.getLevel().playSound(null, earthGuardian, AMSounds.EARTH_GUARDIAN_HURT.get(), SoundSource.HOSTILE, 1.0f, 1.0f);
            }
            earthGuardian.lookAt(target, 180, 180);
            if (!earthGuardian.getLevel().isClientSide()) {
//                ThrowRock projectile = new ThrowRock(earthGuardian.getLevel(), earthGuardian, 2.0f);
//                earthGuardian.getLevel().addFreshEntity(projectile);
            }
        }

    }
}
