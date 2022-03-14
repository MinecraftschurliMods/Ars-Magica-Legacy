package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WaterGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import net.minecraft.world.entity.ai.goal.Goal;

public class CloneGoal extends Goal {
    private final WaterGuardian waterGuardian;
    private int cooldown = 0;

    public CloneGoal(WaterGuardian waterGuardian) {
        this.waterGuardian = waterGuardian;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || waterGuardian.getWaterGuardianAction() != WaterGuardian.WaterGuardianAction.IDLE || !waterGuardian.isWaterGuardianActionValid(WaterGuardian.WaterGuardianAction.CLONE)) {
            return false;
        }
        return waterGuardian.getTarget() != null && !waterGuardian.getTarget().isDeadOrDying();
    }

    @Override
    public boolean canContinueToUse() {
        if (waterGuardian.getWaterGuardianAction() == WaterGuardian.WaterGuardianAction.CLONE && waterGuardian.getTicksInAction() > waterGuardian.getWaterGuardianAction().getMaxActionTime()) {
            waterGuardian.setWaterGuardianAction(WaterGuardian.WaterGuardianAction.IDLE);
            cooldown = 200;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        if (waterGuardian.getWaterGuardianAction() != WaterGuardian.WaterGuardianAction.CLONE) {
            waterGuardian.setWaterGuardianAction(WaterGuardian.WaterGuardianAction.CLONE);
        }

        if (!waterGuardian.level.isClientSide() && waterGuardian.getWaterGuardianAction() == WaterGuardian.WaterGuardianAction.CLONE && waterGuardian.getTicksInAction() == 30) {
            WaterGuardian clone1 = spawnClone();
            WaterGuardian clone2 = spawnClone();

            waterGuardian.setClones(clone1, clone2);
        }
    }

    private WaterGuardian spawnClone() {
        WaterGuardian clone = new WaterGuardian(AMEntities.WATER_GUARDIAN.get(), waterGuardian.level);
        clone.setMaster(waterGuardian);
        clone.setPos(waterGuardian.getX(), waterGuardian.getY(), waterGuardian.getZ());
        waterGuardian.level.addFreshEntity(clone);
        return clone;
    }
}
