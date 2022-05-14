package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import net.minecraft.world.phys.Vec3;

public class ShadowstepGoal extends AbstractBossGoal<EnderGuardian> {
    public ShadowstepGoal(EnderGuardian boss) {
        super(boss, EnderGuardian.EnderGuardianAction.LONG_CASTING, 30);
    }

    @Override
    public void perform() {
        if (boss.getTarget() != null) {
            Vec3 facing = boss.getTarget().getViewVector(1);
            double x = boss.getTarget().getX() - facing.x() * 3;
            double y = boss.getTarget().getY();
            double z = boss.getTarget().getX() - facing.z() * 3;
            boss.setPos(x, y, z);
            boss.xOld = x;
            boss.yOld = y;
            boss.zOld = z;
        }
    }
}
