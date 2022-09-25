package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Objects;

// Special handling is needed as calling addAttributeModifier(AMAtrributes.MANA_REGEN.get(), ...) causes errors during registration
public class ManaRegenEffect extends AMMobEffect {
    private static final java.util.UUID UUID = java.util.UUID.fromString("648D7064-6A88-4F59-8ABE-C2C23999D7A9");

    public ManaRegenEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x2222aa);
    }

    @Override
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
        AttributeInstance attribute = Objects.requireNonNull(entity.getAttributes().getInstance(AMAttributes.MANA_REGEN.get()));
        if (attribute.getModifier(UUID) != null) {
            attribute.removeModifier(UUID);
        }
        attribute.addTransientModifier(new AttributeModifier(UUID, "mana_regen_mob_effect", (effect.getAmplifier() + 1) * 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        Objects.requireNonNull(entity.getAttributes().getInstance(AMAttributes.MANA_REGEN.get())).removeModifier(UUID);
    }
}
