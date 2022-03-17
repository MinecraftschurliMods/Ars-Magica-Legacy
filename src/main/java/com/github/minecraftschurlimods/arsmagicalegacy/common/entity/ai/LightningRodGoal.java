package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LightningGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.mojang.math.Vector3f;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class LightningRodGoal extends Goal {
    private final LightningGuardian lightningGuardian;
    private       int               cooldown  = 0;
    private       Vector3f          startPos;
    private       boolean           hasThrown = false;
    private       boolean           hasBolted = false;
    private       LivingEntity      target;

    public LightningRodGoal(LightningGuardian lightningGuardian) {
        this.lightningGuardian = lightningGuardian;
    }

    @Override
    public boolean canUse() {
        if (lightningGuardian.getHealth() > lightningGuardian.getMaxHealth() * 0.75f || lightningGuardian.getTarget() == null || !lightningGuardian.getSensing().hasLineOfSight(lightningGuardian.getTarget())) {
            return false;
        }
        return cooldown-- <= 0;
    }

    @Override
    public void stop() {
        startPos = null;
        hasThrown = false;
        hasBolted = false;

        if (target != null) {
            //EntityExtension.For(target).setDisableGravity(false);
        }
        target = null;
    }

    @Override
    public void tick() {
        float factor = (lightningGuardian.getHealth() / lightningGuardian.getMaxHealth()) + 0.1f;
        cooldown = (int) (500 * factor);
        int ticks = lightningGuardian.getTicksInAction();
        if (ticks <= 25) {
            target = lightningGuardian.getTarget();
            if (target != null) {
                startPos = new Vector3f((float) target.getX(), (float) target.getY(), (float) target.getZ());  // maybe wrong
            }
        } else {
            if (target == null || target.isDeadOrDying() || startPos == null) {
                stop();
                return;
            }
            lightningGuardian.getLookControl().setLookAt(target, 30, 30);
            if (ticks > 85 && ticks <= 150) {
                if (!lightningGuardian.level.isClientSide() && ticks % 20 == 0) {
                    lightningGuardian.level.playSound(null, lightningGuardian, AMSounds.LIGHTNING_GUARDIAN_LIGHTNING_ROD.get(), SoundSource.HOSTILE, 1.0f, lightningGuardian.getRandom().nextFloat() * 0.5f + 0.5f);
                }
            }

            if (ticks > 25 && ticks <= 85) {
                lightningGuardian.teleportTo(startPos.x(), startPos.y() + ((ticks - 25) * 0.1), startPos.z());
                //EntityExtension.For(target).setDisableGravity(true);
                if (!lightningGuardian.level.isClientSide() && ticks == 30) {
                    lightningGuardian.level.playSound(null, lightningGuardian, AMSounds.LIGHTNING_GUARDIAN_LIGHTNING_ROD.get(), SoundSource.HOSTILE, 1.0f, lightningGuardian.getRandom().nextFloat() * 0.5f + 0.5f);
                }
            } else if (ticks > 85 && ticks <= 105) {
                lightningGuardian.teleportTo(startPos.x(), startPos.y() + 6, startPos.z());
            } else if (ticks > 105 && ticks <= 150) {
                lightningGuardian.teleportTo(startPos.x(), startPos.y() + 6, startPos.z());
                if (ticks > 115) {
                    target.hurt(DamageSource.LIGHTNING_BOLT, 3);
                }
                // Particle
                if (!lightningGuardian.level.isClientSide() && ticks % 20 == 0) {
                    lightningGuardian.level.playSound(null, lightningGuardian, AMSounds.LIGHTNING_GUARDIAN_AMBIENT.get(), SoundSource.HOSTILE, 1.0f, lightningGuardian.getRandom().nextFloat() * 0.5f + 0.5f);  // is LIGHTNING_GUARDIAN_IDLE --> LIGHTNING_GUARDIAN_AMBIENT
                }
            } else if (ticks > 150 && ticks <= 158 && !hasThrown) {
                target.push(0, -3, 0);
                target.fallDistance = 5;
                //EntityExtension.For(target).setDisableGravity(false);
                hasThrown = true;
            } else if (ticks > 165 && !hasBolted) {
                hasBolted = true;
                LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, lightningGuardian.level);  // maybe wrong
                bolt.setPos(target.getX(), target.getY(), target.getZ());
                lightningGuardian.level.addFreshEntity(bolt);  // is addWeatherEffect --> addFreshEntity
            }
        }
    }
}
