package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class ShadowstepGoal extends Goal {
    private final EnderGuardian enderGuardian;
    private int cooldown = 0;

    public ShadowstepGoal(EnderGuardian enderGuardian) {
        this.enderGuardian = enderGuardian;
    }

    @Override
    public boolean canUse() {
        return enderGuardian.getTarget() != null && cooldown-- <= 0;
    }

    @Override
    public void stop() {
        cooldown = 30;
        if (enderGuardian.getTarget() != null) {
            Vec3 facing = enderGuardian.getTarget().getViewVector(1.0f);
            double x = enderGuardian.getTarget().getX() - facing.x() * 3;
            double y = enderGuardian.getTarget().getY();
            double z = enderGuardian.getTarget().getX() - facing.z() * 3;
            enderGuardian.setPos(x, y, z);
            enderGuardian.xOld = x;
            enderGuardian.yOld = y;
            enderGuardian.zOld = z;
            enderGuardian.getLevel().playSound(null, enderGuardian, AMSounds.ENDER_GUARDIAN_ATTACK.get(), SoundSource.HOSTILE, 1.0f, enderGuardian.getRandom().nextFloat() * 0.5f + 0.5f);
        }
    }

    @Override
    public void tick() {
        if (enderGuardian.getTarget() != null) {
            enderGuardian.getLookControl().setLookAt(enderGuardian.getTarget(), 30, 30);
        }
    }
}
