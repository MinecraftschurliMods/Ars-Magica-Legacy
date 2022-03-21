package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LightningGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

public class LightningBoltGoal extends Goal {
    private final LightningGuardian lightningGuardian;
    private int cooldown = 0;

    public LightningBoltGoal(LightningGuardian lightningGuardian) {
        this.lightningGuardian = lightningGuardian;
    }

    @Override
    public boolean canUse() {
        return lightningGuardian.getTarget() != null && lightningGuardian.getSensing().hasLineOfSight(lightningGuardian.getTarget()) && cooldown-- <= 0;
    }

    @Override
    public void stop() {
        cooldown = 3;
    }

    @Override
    public void tick() {
        if (lightningGuardian.getTarget() != null) {
            lightningGuardian.getLookControl().setLookAt(lightningGuardian.getTarget(), 30, 30);
            if (lightningGuardian.getTicksInAction() == 7) {
                //doStrike()
                if (lightningGuardian.getTarget() != null && lightningGuardian.getSensing().hasLineOfSight(lightningGuardian.getTarget())) {
                    if (lightningGuardian.distanceToSqr(lightningGuardian.getTarget()) > 400) {
                        lightningGuardian.getNavigation().moveTo(lightningGuardian.getTarget(), 0.5f);
                        return;
                    } else {
                        lightningGuardian.getNavigation().recomputePath();//is clearPathEntity -> recomputePath()
                        if (lightningGuardian.getRandom().nextDouble() > 0.2f) {
                            // Particle
                            lightningGuardian.getTarget().hurt(DamageSource.LIGHTNING_BOLT, 3);
                            if (lightningGuardian.getTarget() instanceof Player player) {
                                if (player.getAbilities().flying) {
                                    player.getAbilities().flying = false;
                                }
//                                if (player.canRide()) {//should be isRiding()
//                                    player.unRide();
//                                }
                            }
                        }
                    }
                }
                if (!lightningGuardian.getLevel().isClientSide()) {
                    lightningGuardian.getLevel().playSound(null, lightningGuardian, AMSounds.LIGHTNING_GUARDIAN_ATTACK.get(), SoundSource.HOSTILE, 1.0f, (float) (0.5 + lightningGuardian.getRandom().nextDouble() * 0.5f));
                }
            }
        }
    }
}
