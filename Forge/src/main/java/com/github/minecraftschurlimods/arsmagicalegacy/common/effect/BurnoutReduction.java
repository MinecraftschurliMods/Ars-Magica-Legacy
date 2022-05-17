package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class BurnoutReduction extends AMMobEffect {
    public BurnoutReduction() {
        super(MobEffectCategory.BENEFICIAL, 0xcc0000);
        String uuid = "4D02B930-DF3D-441E-898D-36A38689E485";
        if (AMAttributes.BURNOUT_REGEN.isPresent()) {
            addAttributeModifier(AMAttributes.BURNOUT_REGEN.get(), uuid, 0.3, AttributeModifier.Operation.ADDITION);
        } else {
            addAttributeModifier(new RangedAttribute("burnout_regen", 0.2, 0, Short.MAX_VALUE).setSyncable(false), uuid, 0.3, AttributeModifier.Operation.ADDITION);
        }
    }
}
