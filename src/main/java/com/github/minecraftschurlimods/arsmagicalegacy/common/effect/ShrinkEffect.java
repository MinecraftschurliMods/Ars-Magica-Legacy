package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ShrinkEffect extends AMMobEffect {
    public ShrinkEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x0000dd);
    }

    @Override
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
        if (entity instanceof Player player) {

        }
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        if (entity instanceof Player player) {

        }
    }
}
