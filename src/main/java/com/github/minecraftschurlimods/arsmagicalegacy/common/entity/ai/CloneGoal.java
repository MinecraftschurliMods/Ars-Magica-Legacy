package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WaterGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;

public class CloneGoal extends AbstractBossGoal<WaterGuardian> {
    public CloneGoal(WaterGuardian boss) {
        super(boss, AbstractBoss.Action.LONG_CAST, 20);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) > 2;
    }

    @Override
    public void perform() {
        boss.setClones(spawnClone(), spawnClone());
    }

    private WaterGuardian spawnClone() {
        WaterGuardian clone = new WaterGuardian(AMEntities.WATER_GUARDIAN.get(), boss.getLevel());
        clone.setMaster(boss);
        clone.setPos(boss.getX(), boss.getY(), boss.getZ());
        boss.getLevel().addFreshEntity(clone);
        return clone;
    }
}
