package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class EnderRushGoal extends Goal {
    private final EnderGuardian enderGuardian;
    private int cooldown = 0;

    public EnderRushGoal(EnderGuardian enderGuardian) {
        this.enderGuardian = enderGuardian;
    }

    @Override
    public boolean canUse() {
        return enderGuardian.getTarget() != null && cooldown-- <= 0;
    }

    @Override
    public void stop() {
        cooldown = 60;
    }

    @Override
    public void tick() {
        if (enderGuardian.getTarget() != null) {
            enderGuardian.getLookControl().setLookAt(enderGuardian.getTarget(), 30, 30);
        }
        if (enderGuardian.getTicksInAction() >= 18 && enderGuardian.getTicksInAction() <= 30 && enderGuardian.getTarget() != null) {
            Vec3 a = new Vec3(enderGuardian.getX(), enderGuardian.getY(), enderGuardian.getZ());
            Vec3 b = new Vec3(enderGuardian.getTarget().getX(), enderGuardian.getTarget().getY(), enderGuardian.getTarget().getZ());
            if (a.distanceToSqr(b) > 4) {
                //Vec3 movement = MathUtilities.GetMovementVectorBetweenPoints(a, b);
                Vec3 movement = new Vec3(1, 1, 1);
                float speed = -5f;
                enderGuardian.moveTo(movement.x * speed, movement.y * speed, movement.z * speed);
            } else {
                enderGuardian.getLevel().playSound(null, enderGuardian, AMSounds.ENDER_GUARDIAN_ATTACK.get(), SoundSource.HOSTILE, 1.0f, (float) (0.5 + enderGuardian.getRandom().nextDouble() * 0.5f));
                if (enderGuardian.getTarget().hurt(DamageSource.mobAttack(enderGuardian), 15) && enderGuardian.getTarget().getHealth() <= 0) {
                    enderGuardian.heal(200);
                }
            }
        }
    }
}
