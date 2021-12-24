package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class FuryEffect extends AMMobEffect {
    public FuryEffect() {
        super(MobEffectCategory.HARMFUL, 0xff8033);
    }

    @Override
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, effect.getDuration(), effect.getAmplifier()));
        entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, effect.getDuration(), effect.getAmplifier()));
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, effect.getDuration(), effect.getAmplifier()));
        entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, effect.getDuration(), effect.getAmplifier()));
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, effect.getAmplifier()));
        entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, effect.getAmplifier()));
    }
}
