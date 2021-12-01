package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.common.effect.AMMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.MOB_EFFECTS;

@NonExtendable
public interface AMMobEffects {
    RegistryObject<MobEffect> MANA_REGEN = MOB_EFFECTS.register("mana_regen", () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x2222AA).addAttributeModifier(AMAttributes.MANA_REGEN.get(), "648D7064-6A88-4F59-8ABE-C2C23999D7A9", 0.3, AttributeModifier.Operation.ADDITION));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }
}
