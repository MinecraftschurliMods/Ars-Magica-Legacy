package com.github.minecraftschurli.arsmagicalegacy.common.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

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
