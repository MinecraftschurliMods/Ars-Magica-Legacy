package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Whirlwind;

public class WhirlwindGoal extends AbstractBossGoal<AirGuardian> {
    public WhirlwindGoal(AirGuardian boss) {
        super(boss, AirGuardian.AirGuardianAction.SPINNING);
    }

    @Override
    public void perform() {
        if (!boss.getLevel().isClientSide()) {
            Whirlwind entity = Whirlwind.create(boss.getLevel());
            entity.moveTo(boss.getX(), boss.getY() + boss.getEyeHeight(), boss.getZ());
            entity.setDeltaMovement(boss.getLookAngle());
            boss.getLevel().addFreshEntity(entity);
        }
    }
}
