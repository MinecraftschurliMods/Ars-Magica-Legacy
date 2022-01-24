package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class ManaRegenEffect extends AMMobEffect {
    public ManaRegenEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x2222aa);
        addAttributeModifier(new RangedAttribute("mana_regen", 0.1, 0, Short.MAX_VALUE).setSyncable(false), "648D7064-6A88-4F59-8ABE-C2C23999D7A9", 0.3, AttributeModifier.Operation.ADDITION);
    }
}
