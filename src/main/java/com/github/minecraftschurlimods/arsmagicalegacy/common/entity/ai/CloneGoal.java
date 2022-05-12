package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WaterGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class CloneGoal extends Goal {
    private final WaterGuardian waterGuardian;
    private int cooldownTicks = 0;

    public CloneGoal(WaterGuardian waterGuardian) {
        this.waterGuardian = waterGuardian;
    }

    @Override
    public boolean canUse() {
        if (waterGuardian.getAction() != WaterGuardian.WaterGuardianAction.IDLE || waterGuardian.getTarget() == null || waterGuardian.getTarget().isDeadOrDying())
            return false;
        waterGuardian.setAction(WaterGuardian.WaterGuardianAction.CLONE);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        cooldownTicks++;
        return waterGuardian.getAction() == WaterGuardian.WaterGuardianAction.CLONE && waterGuardian.getTarget() != null && !waterGuardian.getTarget().isDeadOrDying();
    }

    @Override
    public void stop() {
        super.stop();
        waterGuardian.setAction(WaterGuardian.WaterGuardianAction.IDLE);
        cooldownTicks = 0;
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = waterGuardian.getTarget();
        if (target == null) return;
        waterGuardian.lookAt(target, 30, 30);
        if (!waterGuardian.getLevel().isClientSide() && cooldownTicks >= waterGuardian.getAction().getMaxActionTime()) {
            waterGuardian.setClones(spawnClone(), spawnClone());
            cooldownTicks = 0;
        }
    }

    private WaterGuardian spawnClone() {
        WaterGuardian clone = new WaterGuardian(AMEntities.WATER_GUARDIAN.get(), waterGuardian.getLevel());
        clone.setMaster(waterGuardian);
        clone.setPos(waterGuardian.getX(), waterGuardian.getY(), waterGuardian.getZ());
        waterGuardian.getLevel().addFreshEntity(clone);
        return clone;
    }
}
