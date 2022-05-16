package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireGuardian;
import net.minecraft.world.entity.ai.goal.Goal;

public class FireRainGoal extends Goal {
    private final FireGuardian fireGuardian;
    private int cooldown = 0;
    private int boltTicks = 0;

    public FireRainGoal(FireGuardian fireGuardian) {
        this.fireGuardian = fireGuardian;
    }

    @Override
    public boolean canUse() {
        if (fireGuardian.getAction() == FireGuardian.FireGuardianAction.IDLE && fireGuardian.getTarget() != null && cooldown-- <= 0) {
            fireGuardian.setAction(FireGuardian.FireGuardianAction.CASTING);
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.cooldown <= 0;
    }

    @Override
    public void stop() {
        fireGuardian.setAction(FireGuardian.FireGuardianAction.IDLE);
        cooldown = 150;
        boltTicks = 0;
    }

    @Override
    public void tick() {
        if (fireGuardian.getAction() != FireGuardian.FireGuardianAction.CASTING) {
            fireGuardian.setAction(FireGuardian.FireGuardianAction.CASTING);
        }
        boltTicks++;
        if (boltTicks == 12) {
            if (!fireGuardian.getLevel().isClientSide()) {
//                FireRain fire = FireRain.create(fireGuardian.getLevel());
//                fire.setPos(fireGuardian.getX(), fireGuardian.getY(), fireGuardian.getZ());
//                fire.setTicksToExist(300);
//                fire.setRadius(10);
//                fire.setCasterAndStack(host, null);
//                fireGuardian.getLevel().addFreshEntity(fire);
            }
        }
        if (boltTicks >= 23) {
            stop();
        }
    }
}
