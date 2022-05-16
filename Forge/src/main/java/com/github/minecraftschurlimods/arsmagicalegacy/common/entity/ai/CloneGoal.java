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
        if (cooldown-- > 0 || waterGuardian.getAction() != WaterGuardian.WaterGuardianAction.IDLE || !waterGuardian.isWaterGuardianActionValid(WaterGuardian.WaterGuardianAction.CLONE)) {
            return false;
        }
        return waterGuardian.getTarget() != null && !waterGuardian.getTarget().isDeadOrDying();
    }

    @Override
    public boolean canContinueToUse() {
        if (waterGuardian.getAction() == WaterGuardian.WaterGuardianAction.CLONE && waterGuardian.getTicksInAction() > waterGuardian.getAction().getMaxActionTime()) {
            waterGuardian.setAction(WaterGuardian.WaterGuardianAction.IDLE);
            cooldown = 200;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        if (waterGuardian.getAction() != WaterGuardian.WaterGuardianAction.CLONE) {
            waterGuardian.setAction(WaterGuardian.WaterGuardianAction.CLONE);
        }
        if (!waterGuardian.getLevel().isClientSide() && waterGuardian.getAction() == WaterGuardian.WaterGuardianAction.CLONE && waterGuardian.getTicksInAction() == 30) {
            waterGuardian.setClones(spawnClone(), spawnClone());
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
