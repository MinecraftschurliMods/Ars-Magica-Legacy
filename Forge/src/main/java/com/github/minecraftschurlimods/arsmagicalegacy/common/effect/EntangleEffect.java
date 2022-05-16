package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class EntangleEffect extends AMMobEffect {
    public EntangleEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x009300);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.setDeltaMovement(Vec3.ZERO);
    }
}
