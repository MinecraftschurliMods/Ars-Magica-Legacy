package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ShrinkEffect extends AMMobEffect {
    public ShrinkEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x0000dd);
        addAttributeModifier(Attributes.SCALE, "92bae870-53f8-43b9-8f18-a4ebbc36252e", 0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
