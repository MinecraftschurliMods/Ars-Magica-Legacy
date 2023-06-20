package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import net.minecraft.world.phys.Vec3;

public class EnderRushGoal extends AbstractBossGoal<EnderGuardian> {
    public EnderRushGoal(EnderGuardian boss) {
        super(boss, AbstractBoss.Action.LONG_CAST, 20);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getRandom().nextBoolean();
    }

    @Override
    public void performTick() {
        if (boss.getTarget() == null) return;
        Vec3 a = new Vec3(boss.getX(), boss.getY(), boss.getZ());
        Vec3 b = new Vec3(boss.getTarget().getX(), boss.getTarget().getY(), boss.getTarget().getZ());
        if (a.distanceToSqr(b) > 4) {
            Vec3 movement = boss.position().add(a.subtract(b).normalize().scale(-5));
            boss.moveTo(movement.x(), movement.y(), movement.z());
        } else {
            if (boss.getTarget().hurt(boss.damageSources().mobAttack(boss), 8)) {
                boss.heal(4);
            }
        }
    }
}
