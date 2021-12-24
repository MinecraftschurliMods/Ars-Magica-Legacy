package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FrostEffect extends AMMobEffect {
    public FrostEffect() {
        super(MobEffectCategory.HARMFUL, 0x1fffdd);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity.canFreeze()) {
            pLivingEntity.setIsInPowderSnow(true);
        }
    }
}
