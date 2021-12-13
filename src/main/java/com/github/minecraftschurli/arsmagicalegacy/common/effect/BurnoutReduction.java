package com.github.minecraftschurli.arsmagicalegacy.common.effect;

import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

/**
 * This class is required so that it doesn't cause classloading issues in {@link AMBlocks} and {@link AMItems}
 */
public class BurnoutReduction extends AMMobEffect {
    public BurnoutReduction() {
        super(MobEffectCategory.BENEFICIAL, 0xcc0000);
        addAttributeModifier(new RangedAttribute("burnout_regen", 0.2, 0, Short.MAX_VALUE).setSyncable(false), "4D02B930-DF3D-441E-898D-36A38689E485", 0.3, AttributeModifier.Operation.ADDITION);
    }
}
