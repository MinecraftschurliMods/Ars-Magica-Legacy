package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.AMMobEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.EntangleEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.FlightEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.FrostEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.FuryEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.IlluminationEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.InstantManaEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.ScrambleSynapsesEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.ShrinkEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.TemporalAnchorEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.MOB_EFFECTS;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.POTIONS;

@NonExtendable
public interface AMMobEffects {
    RegistryObject<MobEffect> AGILITY           = MOB_EFFECTS.register("agility",           () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xade000).addAttributeModifier(ForgeMod.STEP_HEIGHT_ADDITION.get(), "C1105901-7F9E-4811-81F4-D8801749333E", 0.4, AttributeModifier.Operation.ADDITION));
    RegistryObject<MobEffect> ASTRAL_DISTORTION = MOB_EFFECTS.register("astral_distortion", () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0x6c0000));
    RegistryObject<MobEffect> BURNOUT_REDUCTION = MOB_EFFECTS.register("burnout_reduction", () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xcc0000).addAttributeModifier(AMAttributes.BURNOUT_REGEN.get(), "4D02B930-DF3D-441E-898D-36A38689E485", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL));
    RegistryObject<MobEffect> CLARITY           = MOB_EFFECTS.register("clarity",           () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xbbffff));
    RegistryObject<MobEffect> ENTANGLE          = MOB_EFFECTS.register("entangle",          EntangleEffect::new);
    RegistryObject<MobEffect> FLIGHT            = MOB_EFFECTS.register("flight",            FlightEffect::new);
    RegistryObject<MobEffect> FROST             = MOB_EFFECTS.register("frost",             FrostEffect::new);
    RegistryObject<MobEffect> FURY              = MOB_EFFECTS.register("fury",              FuryEffect::new);
    RegistryObject<MobEffect> GRAVITY_WELL      = MOB_EFFECTS.register("gravity_well",      () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0xa400ff).addAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), "CC5AF142-2BD2-4215-B836-2605AED11727", 2, AttributeModifier.Operation.MULTIPLY_BASE));
    RegistryObject<MobEffect> ILLUMINATION      = MOB_EFFECTS.register("illumination",      IlluminationEffect::new);
    RegistryObject<MobEffect> INSTANT_MANA      = MOB_EFFECTS.register("instant_mana",      InstantManaEffect::new);
    RegistryObject<MobEffect> MAGIC_SHIELD      = MOB_EFFECTS.register("magic_shield",      () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xd780ff));
    RegistryObject<MobEffect> MANA_BOOST        = MOB_EFFECTS.register("mana_boost",        () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x0093ff).addAttributeModifier(AMAttributes.MAX_MANA.get(), "88812AE6-E2A3-4FC3-9A52-E0040DF399A9", 250, AttributeModifier.Operation.ADDITION));
    RegistryObject<MobEffect> MANA_REGEN        = MOB_EFFECTS.register("mana_regen",        () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x2222aa).addAttributeModifier(AMAttributes.MANA_REGEN.get(), "648D7064-6A88-4F59-8ABE-C2C23999D7A9", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL));
    RegistryObject<MobEffect> REFLECT           = MOB_EFFECTS.register("reflect",           () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xadffff));
    RegistryObject<MobEffect> SCRAMBLE_SYNAPSES = MOB_EFFECTS.register("scramble_synapses", ScrambleSynapsesEffect::new);
    RegistryObject<MobEffect> SHIELD            = MOB_EFFECTS.register("shield",            () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xc4c4c4).addAttributeModifier(Attributes.ARMOR, "F323F5EB-9C66-4142-BCFA-B7855F16D534", 2, AttributeModifier.Operation.ADDITION));
    RegistryObject<MobEffect> SHRINK            = MOB_EFFECTS.register("shrink",            ShrinkEffect::new);
    RegistryObject<MobEffect> SILENCE           = MOB_EFFECTS.register("silence",           () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0xc1c1ff));
    RegistryObject<MobEffect> SWIFT_SWIM        = MOB_EFFECTS.register("swift_swim",        () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x3b3bff).addAttributeModifier(ForgeMod.SWIM_SPEED.get(), "A5B6CF2A-2F7C-51EF-9022-7C3E7D5E6AAC", 1.33f, AttributeModifier.Operation.MULTIPLY_BASE));
    RegistryObject<MobEffect> TEMPORAL_ANCHOR   = MOB_EFFECTS.register("temporal_anchor",   TemporalAnchorEffect::new);
    RegistryObject<MobEffect> TRUE_SIGHT        = MOB_EFFECTS.register("true_sight",        () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xc400ff));
    RegistryObject<MobEffect> WATERY_GRAVE      = MOB_EFFECTS.register("watery_grave",      () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0x0000a2));

    RegistryObject<Potion> LESSER_MANA    = POTIONS.register("lesser_mana",    () -> new Potion(new MobEffectInstance(INSTANT_MANA.get(), 1, 0), new MobEffectInstance(MANA_REGEN.get(),  600, 0)));
    RegistryObject<Potion> STANDARD_MANA  = POTIONS.register("standard_mana",  () -> new Potion(new MobEffectInstance(INSTANT_MANA.get(), 1, 1), new MobEffectInstance(MANA_REGEN.get(), 1200, 1)));
    RegistryObject<Potion> GREATER_MANA   = POTIONS.register("greater_mana",   () -> new Potion(new MobEffectInstance(INSTANT_MANA.get(), 1, 2), new MobEffectInstance(MANA_REGEN.get(), 1800, 2)));
    RegistryObject<Potion> EPIC_MANA      = POTIONS.register("epic_mana",      () -> new Potion(new MobEffectInstance(INSTANT_MANA.get(), 1, 3), new MobEffectInstance(MANA_REGEN.get(), 1800, 2), new MobEffectInstance(MANA_BOOST.get(),  600, 0)));
    RegistryObject<Potion> LEGENDARY_MANA = POTIONS.register("legendary_mana", () -> new Potion(new MobEffectInstance(INSTANT_MANA.get(), 1, 4), new MobEffectInstance(MANA_REGEN.get(), 1800, 2), new MobEffectInstance(MANA_BOOST.get(), 1200, 1)));
    RegistryObject<Potion> INFUSED_MANA   = POTIONS.register("infused_mana",   () -> new Potion(new MobEffectInstance(INSTANT_MANA.get(), 1, 9)));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
