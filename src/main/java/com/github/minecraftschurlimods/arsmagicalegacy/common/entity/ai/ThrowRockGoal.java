package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ThrownRock;

public class ThrowRockGoal extends AbstractBossGoal<EarthGuardian> {
    public ThrowRockGoal(EarthGuardian boss) {
        super(boss, EarthGuardian.EarthGuardianAction.THROWING);
    }

    @Override
    public void perform() {
        if (!boss.getLevel().isClientSide()) {
            ThrownRock entity = ThrownRock.create(boss.getLevel());
            entity.moveTo(boss.getEyePosition().add(boss.getLookAngle()));
            entity.setDeltaMovement(boss.getLookAngle());
            entity.setOwner(boss);
            boss.getLevel().addFreshEntity(entity);
        }
    }
}
