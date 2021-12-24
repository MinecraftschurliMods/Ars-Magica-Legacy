package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class AMMobEffect extends MobEffect {
    public AMMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    /**
     * Called when the given entity gets the effect added.
     *
     * @param entity The entity that gets the effect added.
     * @param effect The MobEffectInstance that gets added.
     */
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
    }

    /**
     * Called when the given entity loses the effect (expiry or removal).
     *
     * @param entity The entity that loses the effect.
     * @param effect The MobEffectInstance that gets removed.
     */
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
    }
}
