package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ShrinkEffect extends AMMobEffect {
    public ShrinkEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x0000dd);
        addAttributeModifier(AMAttributes.SCALE.get(), "92bae870-53f8-43b9-8f18-a4ebbc36252e", 0, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
        pLivingEntity.refreshDimensions();
    }

    @Override
    public double getAttributeModifierValue(int pAmplifier, AttributeModifier pModifier) {
        return minShrink() / (pAmplifier / alpha() + 1) - 1;
    }

    /**
     * @return The alpha factor for calculating the new size.
     */
    public static double alpha() {
        return 2;
    }

    /**
     * @return The shrink factor for level 0 of the effect.
     */
    public static double minShrink() {
        return 0.75;
    }
}
