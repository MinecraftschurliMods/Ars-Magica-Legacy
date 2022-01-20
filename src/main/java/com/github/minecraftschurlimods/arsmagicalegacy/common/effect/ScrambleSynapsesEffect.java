package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ScrambleSynapsesEffect extends AMMobEffect {
    public ScrambleSynapsesEffect() {
        super(MobEffectCategory.HARMFUL, 0x306600);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity.getLevel().getRandom().nextInt(80) < 10) {
            Direction direction = Direction.from2DDataValue(pLivingEntity.getLevel().getRandom().nextInt(4));
            pLivingEntity.setDeltaMovement(pLivingEntity.getDeltaMovement().add(direction.getStepX() / 2f, direction.getStepY() / 2f, direction.getStepZ() / 2f));
        }
    }
}
