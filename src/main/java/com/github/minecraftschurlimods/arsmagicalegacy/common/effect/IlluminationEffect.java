package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;

public class IlluminationEffect extends AMMobEffect {
    public IlluminationEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xffffbe);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity.getLevel().getBrightness(LightLayer.BLOCK, pLivingEntity.blockPosition()) == 0) {
            pLivingEntity.getLevel().setBlock(pLivingEntity.blockPosition(), Blocks.LIGHT.defaultBlockState(), 3);
        }
    }
}
