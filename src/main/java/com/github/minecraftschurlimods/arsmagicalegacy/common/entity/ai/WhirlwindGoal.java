package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class WhirlwindGoal extends Goal {
    private final AirGuardian airGuardian;
    private int castTicks = 0;

    public WhirlwindGoal(AirGuardian airGuardian) {
        this.airGuardian = airGuardian;
    }

    @Override
    public boolean canUse() {
        airGuardian.setIsCastingSpell(true);
        return airGuardian.isCastingSpell() && airGuardian.getTarget() != null && !airGuardian.getTarget().isDeadOrDying();
    }

    @Override
    public boolean canContinueToUse() {
        castTicks++;
        return airGuardian.isCastingSpell() && airGuardian.getTarget() != null && !airGuardian.getTarget().isDeadOrDying();
    }

    @Override
    public void stop() {
        super.stop();
        airGuardian.setIsCastingSpell(false);
        castTicks = 0;
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = airGuardian.getTarget();
        if (target == null) return;
        airGuardian.lookAt(target, 30, 30);
        if (airGuardian.distanceToSqr(target) > 64) {
            double angle = -Math.atan2(target.getZ() - airGuardian.getZ(), target.getX() - airGuardian.getX());
            airGuardian.getNavigation().moveTo(target.getX() + Math.cos(angle) * 6, target.getY(), target.getZ() + Math.sin(angle) * 6, 0.5f);
        } else if (!airGuardian.canAttack(target)) {
            airGuardian.getNavigation().moveTo(target, 0.5f);
        } else if (castTicks == 10 && !airGuardian.getLevel().isClientSide()) {
//            Whirlwind whirlwind = Whirlwind.create(airGuardian.getLevel());
//            whirlwind.setPos(airGuardian.getX(), airGuardian.getY() + airGuardian.getEyeHeight(), airGuardian.getZ());
//            airGuardian.getLevel().addFreshEntity(whirlwind);
        }
        if (castTicks > 20) {
            stop();
        }
    }
}
