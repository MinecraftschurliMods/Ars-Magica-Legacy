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
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }

    /**
     * Called when the given entity gets the effect.
     *
     * @param entity The entity to call this on.
     * @param effect The effect instance the entity received.
     */
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
    }

    /**
     * Called when the given entity loses the effect.
     *
     * @param entity The entity to call this on.
     * @param effect The effect instance the entity got removed.
     */
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
    }
}
