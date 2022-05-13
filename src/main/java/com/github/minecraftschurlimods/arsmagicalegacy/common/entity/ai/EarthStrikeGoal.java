package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class EarthStrikeGoal extends Goal {
    private final EarthGuardian earthGuardian;
    private int cooldownTicks = 0;

    public EarthStrikeGoal(EarthGuardian earthGuardian) {
        this.earthGuardian = earthGuardian;
    }

    @Override
    public boolean canUse() {
        if (earthGuardian.getAction() != EarthGuardian.EarthGuardianAction.IDLE || earthGuardian.getTarget() == null || earthGuardian.getTarget().isDeadOrDying())
            return false;
        earthGuardian.setAction(EarthGuardian.EarthGuardianAction.STRIKE);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        cooldownTicks++;
        return earthGuardian.getAction() == EarthGuardian.EarthGuardianAction.STRIKE && earthGuardian.getTarget() != null && !earthGuardian.getTarget().isDeadOrDying();
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
                earthGuardian.getLevel().playSound(null, earthGuardian, AMSounds.EARTH_GUARDIAN_HURT.get(), SoundSource.HOSTILE, 1f, 1f);
            }
            for (LivingEntity e : earthGuardian.getLevel().getEntitiesOfClass(LivingEntity.class, earthGuardian.getBoundingBox().expandTowards(Math.cos(earthGuardian.getYRot()) * 2, 0, Math.sin(earthGuardian.getYRot()) * 2).inflate(2.5, 2, 2.5))) {
                if (!(e instanceof EarthGuardian)) {
                    e.hurt(DamageSource.mobAttack(earthGuardian), 4);
                }
            }
        }
    }
}
