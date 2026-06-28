package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ScrambleSynapsesEffect extends MobEffect {
    public ScrambleSynapsesEffect() {
        super(MobEffectCategory.HARMFUL, 0x306600);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity livingEntity, int amplifier) {
        if (!level.isClientSide() && level.getRandom().nextInt(80) < 10) {
            Direction direction = Direction.from2DDataValue(level.getRandom().nextInt(4));
            livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(direction.getStepX() / 2f, direction.getStepY() / 2f, direction.getStepZ() / 2f));
            livingEntity.hurtMarked = true;
        }
        return super.applyEffectTick(level, livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
