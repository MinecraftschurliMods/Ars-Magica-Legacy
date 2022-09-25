package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Objects;

// Special handling is needed as calling addAttributeModifier(AMAtrributes.BURNOUT_REGEN.get(), ...) causes errors during registration
public class BurnoutReduction extends AMMobEffect {
    private static final java.util.UUID UUID = java.util.UUID.fromString("4D02B930-DF3D-441E-898D-36A38689E485");

    public BurnoutReduction() {
        super(MobEffectCategory.BENEFICIAL, 0xcc0000);
    }

    @Override
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
        AttributeInstance attribute = Objects.requireNonNull(entity.getAttributes().getInstance(AMAttributes.BURNOUT_REGEN.get()));
        if (attribute.getModifier(UUID) != null) {
            attribute.removeModifier(UUID);
        }
        attribute.addTransientModifier(new AttributeModifier(UUID, "burnout_reduction_mob_effect", (effect.getAmplifier() + 1) * 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        Objects.requireNonNull(entity.getAttributes().getInstance(AMAttributes.BURNOUT_REGEN.get())).removeModifier(UUID);
    }
}
