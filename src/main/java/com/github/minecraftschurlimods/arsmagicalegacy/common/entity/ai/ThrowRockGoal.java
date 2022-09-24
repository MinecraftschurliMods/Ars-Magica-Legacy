package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ThrownRock;

public class ThrowRockGoal extends AbstractBossGoal<EarthGuardian> {
    public ThrowRockGoal(EarthGuardian boss) {
        super(boss, AbstractBoss.Action.THROW, 20);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) > 4;
    }

    @Override
    public void tick() {
        super.tick();
        boss.getLevel().broadcastEntityEvent(boss, (byte) 56);
    }

    @Override
    public void stop() {
        super.stop();
        boss.getLevel().broadcastEntityEvent(boss, (byte) 57);
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
