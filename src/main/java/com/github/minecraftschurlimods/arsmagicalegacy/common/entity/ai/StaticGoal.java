package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LightningGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class StaticGoal extends AbstractBossGoal<LightningGuardian> {
    public StaticGoal(LightningGuardian boss) {
        super(boss, AbstractBoss.Action.SPIN, 20);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.LIGHTNING_GUARDIAN_STATIC.get();
    }

    @Override
    public void perform() {
        for (LivingEntity e : boss.getLevel().getEntitiesOfClass(LivingEntity.class, boss.getBoundingBox().inflate(8, 3, 8), e -> !(e instanceof AbstractBoss))) {
            e.hurt(DamageSource.LIGHTNING_BOLT, 8);
        }
    }
}
