package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireGuardian;

public class FireRainGoal extends AbstractBossGoal<FireGuardian> {
    public FireRainGoal(FireGuardian boss) {
        super(boss, AbstractBoss.Action.LONG_CAST, 20);
    }

    @Override
    public void perform() {
        if (!boss.getLevel().isClientSide()) {
//            FireRain entity = FireRain.create(boss.getLevel());
//            entity.moveTo(boss.getX(), boss.getY() + boss.getEyeHeight(), boss.getZ());
//            entity.setDeltaMovement(boss.getLookAngle());
//            boss.getLevel().addFreshEntity(entity);
        }
    }
}
