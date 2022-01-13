package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class ShrinkEffect extends AMMobEffect {
    public ShrinkEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x0000dd);
    }

    @Override
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
    }
}
