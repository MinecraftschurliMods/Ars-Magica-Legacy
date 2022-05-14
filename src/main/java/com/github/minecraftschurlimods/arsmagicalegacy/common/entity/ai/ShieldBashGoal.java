package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class ShieldBashGoal extends AbstractBossGoal<NatureGuardian> {
    public ShieldBashGoal(NatureGuardian boss) {
        super(boss, NatureGuardian.NatureGuardianAction.SHIELD_BASH);
    }

    @Override
    public void perform() {
        for (LivingEntity e : boss.getLevel().getEntitiesOfClass(LivingEntity.class, boss.getBoundingBox().expandTowards(Math.cos(boss.getYRot()) * 2, 0, Math.sin(boss.getYRot()) * 2).inflate(2.5, 2, 2.5), e -> !(e instanceof AbstractBoss))) {
            double angle = Math.atan2(e.getX() - boss.getX(), e.getZ() - boss.getZ());
            e.setDeltaMovement(4 * Math.cos(angle), 0.325, 4 * Math.sin(angle));
            e.hurt(DamageSource.mobAttack(boss), 2);
        }
    }
}
