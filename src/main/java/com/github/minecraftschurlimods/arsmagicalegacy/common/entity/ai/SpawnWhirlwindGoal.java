package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;

public class SpawnWhirlwindGoal extends Goal {
    private final AirGuardian airGuardian;
    private       int         cooldown  = 0;
    private       boolean     hasCasted = false;
    private       int         castTicks = 0;
    private       float       moveSpeed = 0;

    public SpawnWhirlwindGoal(AirGuardian airGuardian, float moveSpeed) {
        this.airGuardian = airGuardian;
        this.moveSpeed = moveSpeed;
    }

    @Override
    public boolean canUse() {
        cooldown--;
        if (airGuardian.getAirGuardianAction() == AirGuardian.AirGuardianAction.IDLE && airGuardian.getTarget() != null && cooldown <= 0) {
            hasCasted = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return !hasCasted && airGuardian.getTarget() != null && !airGuardian.getTarget().isDeadOrDying();
    }

    @Override
    public void stop() {
        airGuardian.setAirGuardianAction(AirGuardian.AirGuardianAction.IDLE);
        cooldown = 10;
        hasCasted = true;
        castTicks = 0;
    }

    @Override
    public void tick() {
        airGuardian.getLookControl().setLookAt(airGuardian.getTarget(), 30, 30);
        if (airGuardian.distanceToSqr(airGuardian.getTarget()) > 64) {
            double deltaX = airGuardian.getTarget().getX() - airGuardian.getX();
            double deltaZ = airGuardian.getTarget().getZ() - airGuardian.getZ();
            double angle = -Math.atan2(deltaZ, deltaX);

            double newX = airGuardian.getTarget().getX() + (Math.cos(angle) * 6);
            double newZ = airGuardian.getTarget().getZ() + (Math.sin(angle) * 6);

            airGuardian.getNavigation().moveTo(newX, airGuardian.getTarget().getY(), newZ, 0.5f);
        } else if (!airGuardian.canBeSeenByAnyone()) {  // should be canSee(airGuardian.getTarget())
            airGuardian.getNavigation().moveTo(airGuardian.getTarget(), 0.5f);
        } else {
            if (airGuardian.getAirGuardianAction() != AirGuardian.AirGuardianAction.CASTING) {
                airGuardian.setAirGuardianAction(AirGuardian.AirGuardianAction.CASTING);
            }
            castTicks++;
            if (castTicks == 12 && !airGuardian.level.isClientSide()) {
                airGuardian.level.playSound(null, airGuardian, AMSounds.CAST_AIR.get(), SoundSource.HOSTILE, 1.0f, 1.0f); // is there a AIR_GUARDIAN_ATTACK?
                //                EntityWhirlwind whirlwind = new EntityWhirlwind(airGuardian.level);
                //                whirlwind.setPos(airGuardian.getX(), airGuardian.getY() + airGuardian.getEyeHeight(), airGuardian.getZ());
                //                airGuardian.level.addFreshEntity(whirlwind);
            }

        }
        if (castTicks >= 23) {
            stop();
        }
    }
}
