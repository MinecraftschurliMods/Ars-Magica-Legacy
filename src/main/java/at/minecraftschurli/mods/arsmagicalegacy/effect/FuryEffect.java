package at.minecraftschurli.mods.arsmagicalegacy.effect;

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
        entity.addEffect(new MobEffectInstance(MobEffects.STRENGTH, effect.getDuration(), effect.getAmplifier()));
        entity.addEffect(new MobEffectInstance(MobEffects.HASTE, effect.getDuration(), effect.getAmplifier()));
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, effect.getDuration(), effect.getAmplifier()));
        entity.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, effect.getDuration(), effect.getAmplifier()));
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        entity.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 200, effect.getAmplifier()));
        entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, effect.getAmplifier()));
    }
}
