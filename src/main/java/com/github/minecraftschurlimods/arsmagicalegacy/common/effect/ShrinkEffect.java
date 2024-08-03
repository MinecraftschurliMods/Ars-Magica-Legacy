package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ShrinkEffect extends AMMobEffect {
    public ShrinkEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x0000dd);
        addAttributeModifier(Attributes.SCALE, ArsMagicaAPI.resource("shrink"), 0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
