package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.Vec3;

public class EnderRushGoal extends AbstractBossGoal<EnderGuardian> {
    public EnderRushGoal(EnderGuardian boss) {
        super(boss, EnderGuardian.EnderGuardianAction.LONG_CASTING, 30);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getRandom().nextBoolean();
    }

    @Override
    public void tick() {
        super.tick();
        if (ticks >= 20 && ticks <= 30) {
            perform();
        }
    }

    @Override
    public void perform() {
        if (boss.getTarget() == null) return;
        Vec3 a = new Vec3(boss.getX(), boss.getY(), boss.getZ());
        Vec3 b = new Vec3(boss.getTarget().getX(), boss.getTarget().getY(), boss.getTarget().getZ());
        if (a.distanceToSqr(b) > 4) {
            Vec3 movement = a.subtract(b).normalize().scale(-5);
            boss.moveTo(movement.x(), movement.y(), movement.z());
        } else {
            if (boss.getTarget().hurt(DamageSource.mobAttack(boss), 15) && boss.getTarget().getHealth() <= 0) {
                boss.heal(200);
            }
        }
    }
}
