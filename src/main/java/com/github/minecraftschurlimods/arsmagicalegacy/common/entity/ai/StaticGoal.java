package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LightningGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class StaticGoal extends Goal {
    private final LightningGuardian lightningGuardian;
    private int cooldown = 0;

    public StaticGoal(LightningGuardian lightningGuardian) {
        this.lightningGuardian = lightningGuardian;
    }

    @Override
    public boolean canUse() {
        if (lightningGuardian.getTarget() == null || lightningGuardian.distanceToSqr(lightningGuardian.getTarget()) > 64D || !lightningGuardian.getSensing().hasLineOfSight(lightningGuardian.getTarget())) {
            return false;
        }
        return cooldown-- <= 0;
    }

    @Override
    public void stop() {
        cooldown = 50;
        doStrike();
    }

    @Override
    public void tick() {
        if (lightningGuardian.getTarget() != null) {
            lightningGuardian.getLookControl().setLookAt(lightningGuardian.getTarget(), 10, 10);
            if (lightningGuardian.getTicksInAction() == 20 && !lightningGuardian.level.isClientSide()) {
                lightningGuardian.level.playSound(null, lightningGuardian, AMSounds.LIGHTNING_GUARDIAN_STATIC.get(), SoundSource.HOSTILE, 1.0f, lightningGuardian.getRandom().nextFloat() * 0.5f + 0.5f);
            }
            if (lightningGuardian.getTicksInAction() > 66 && lightningGuardian.getTicksInAction() % 15 == 0 && lightningGuardian.getSensing().hasLineOfSight(lightningGuardian.getTarget())) {
                doStrike();
            }
        }
    }

    private void doStrike() {
        List<LivingEntity> nearbyEntities = lightningGuardian.level.getEntitiesOfClass(LivingEntity.class, lightningGuardian.getBoundingBox().inflate(8, 3, 8));
        for (LivingEntity e : nearbyEntities) {
            if (e != lightningGuardian) {
                e.hurt(DamageSource.LIGHTNING_BOLT, 8);
            }
        }
    }
}
