package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import net.minecraft.world.phys.Vec3;

public class ShadowstepGoal extends AbstractBossGoal<EnderGuardian> {
    public ShadowstepGoal(EnderGuardian boss) {
        super(boss, AbstractBoss.Action.LONG_CAST, 20);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getRandom().nextBoolean();
    }

    @Override
    public void perform() {
        if (boss.getTarget() != null) {
            Vec3 facing = boss.getTarget().getViewVector(1).normalize();
            boss.moveTo(boss.getTarget().getX() - facing.x() * 3, boss.getTarget().getY(), boss.getTarget().getX() - facing.z() * 3);
        }
    }
}
