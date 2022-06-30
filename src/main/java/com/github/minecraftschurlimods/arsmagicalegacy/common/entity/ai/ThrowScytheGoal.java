package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureScythe;

public class ThrowScytheGoal extends AbstractBossGoal<NatureGuardian> {
    public ThrowScytheGoal(NatureGuardian boss) {
        super(boss, NatureGuardian.NatureGuardianAction.THROWING);
    }

    @Override
    public void perform() {
        if (!boss.getLevel().isClientSide()) {
            NatureScythe entity = NatureScythe.create(boss.getLevel());
            entity.moveTo(boss.getX(), boss.getY() + boss.getEyeHeight(), boss.getZ());
            entity.setDeltaMovement(boss.getLookAngle());
            boss.getLevel().addFreshEntity(entity);
        }
    }
}
