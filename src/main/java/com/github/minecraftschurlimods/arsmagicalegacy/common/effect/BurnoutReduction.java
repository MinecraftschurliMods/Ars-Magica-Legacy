package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class BurnoutReduction extends AMMobEffect {
    public BurnoutReduction() {
        super(MobEffectCategory.BENEFICIAL, 0xcc0000);
        addAttributeModifier(AMAttributes.BURNOUT_REGEN.get(), "4D02B930-DF3D-441E-898D-36A38689E485", 0.3, AttributeModifier.Operation.ADDITION);
    }
}
