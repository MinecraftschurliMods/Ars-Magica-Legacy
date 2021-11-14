package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.common.effect.AMMobEffect;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.MOB_EFFECTS;

@NonExtendable
public interface AMMobEffects {
    RegistryObject<MobEffect> AGILITY           = MOB_EFFECTS.register("agility",           () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xade000));
    RegistryObject<MobEffect> ASTRAL_DISTORTION = MOB_EFFECTS.register("astral_distortion", () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0x6c0000));
    RegistryObject<MobEffect> BURNOUT_REDUCTION = MOB_EFFECTS.register("burnout_reduction", () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xcc0000));
    RegistryObject<MobEffect> CLARITY           = MOB_EFFECTS.register("clarity",           () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xbbffff));
    RegistryObject<MobEffect> ENTANGLE          = MOB_EFFECTS.register("entangle",          () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x009300));
    RegistryObject<MobEffect> FLIGHT            = MOB_EFFECTS.register("flight",            () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xc6dada));
    RegistryObject<MobEffect> FROST             = MOB_EFFECTS.register("frost",             () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0x1fffdd));
    RegistryObject<MobEffect> FURY              = MOB_EFFECTS.register("fury",              () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0xff8033));
    RegistryObject<MobEffect> GRAVITY_WELL      = MOB_EFFECTS.register("gravity_well",      () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0xa400ff));
    RegistryObject<MobEffect> ILLUMINATION      = MOB_EFFECTS.register("illumination",      () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xffffbe));
    RegistryObject<MobEffect> INSTANT_MANA      = MOB_EFFECTS.register("instant_mana",      () -> new InstantenousMobEffect(MobEffectCategory.BENEFICIAL, 0x00ffff));
    RegistryObject<MobEffect> MAGIC_SHIELD      = MOB_EFFECTS.register("magic_shield",      () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xd780ff));
    RegistryObject<MobEffect> MANA_BOOST        = MOB_EFFECTS.register("mana_boost",        () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x0093ff));
    RegistryObject<MobEffect> MANA_REGEN        = MOB_EFFECTS.register("mana_regen",        () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x2222aa).addAttributeModifier(AMAttributes.MANA_REGEN.get(), "648D7064-6A88-4F59-8ABE-C2C23999D7A9", 0.3, AttributeModifier.Operation.ADDITION));
    RegistryObject<MobEffect> REFLECT           = MOB_EFFECTS.register("reflect",           () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xadffff));
    RegistryObject<MobEffect> SCRAMBLE_SYNAPSES = MOB_EFFECTS.register("scramble_synapses", () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0x306600));
    RegistryObject<MobEffect> SHIELD            = MOB_EFFECTS.register("shield",            () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xc4c4c4));
    RegistryObject<MobEffect> SHRINK            = MOB_EFFECTS.register("shrink",            () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x0000dd));
    RegistryObject<MobEffect> SILENCE           = MOB_EFFECTS.register("silence",           () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0xc1c1ff));
    RegistryObject<MobEffect> SWIFT_SWIM        = MOB_EFFECTS.register("swift_swim",        () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x3b3bff));
    RegistryObject<MobEffect> TEMPORAL_ANCHOR   = MOB_EFFECTS.register("temporal_anchor",   () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xa2a2a2));
    RegistryObject<MobEffect> TRUE_SIGHT        = MOB_EFFECTS.register("true_sight",        () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xc400ff));
    RegistryObject<MobEffect> WATERY_GRAVE      = MOB_EFFECTS.register("watery_grave",      () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0x0000a2));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }
}
