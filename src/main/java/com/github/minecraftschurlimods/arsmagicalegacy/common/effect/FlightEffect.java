package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class FlightEffect extends AMMobEffect {
    public FlightEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xc6dada);
    }

    @Override
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
        if (entity instanceof ServerPlayer && !((ServerPlayer) entity).isCreative()) {
            ((ServerPlayer) entity).getAbilities().mayfly = true;
            ((ServerPlayer) entity).onUpdateAbilities();
        }
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        if (entity instanceof ServerPlayer && !((ServerPlayer) entity).isCreative()) {
            ((ServerPlayer) entity).getAbilities().mayfly = false;
            ((ServerPlayer) entity).getAbilities().flying = false;
            entity.fallDistance = 0;
            ((ServerPlayer) entity).onUpdateAbilities();
        }
    }
}
