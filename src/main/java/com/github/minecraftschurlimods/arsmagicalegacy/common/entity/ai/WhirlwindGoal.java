package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Whirlwind;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;

import java.util.Objects;

public class WhirlwindGoal extends AbstractBossGoal<AirGuardian> {
    public WhirlwindGoal(AirGuardian boss) {
        super(boss, AbstractBoss.Action.LONG_CAST, 40);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) > 4;
    }

    @Override
    public void perform() {
        if (!boss.getLevel().isClientSide()) {
            Whirlwind entity = Objects.requireNonNull(AMEntities.WHIRLWIND.get().create(boss.getLevel()));
            entity.moveTo(boss.getX(), boss.getY() + boss.getEyeHeight(), boss.getZ());
            entity.setDeltaMovement(boss.getLookAngle());
            boss.getLevel().addFreshEntity(entity);
        }
    }
}
