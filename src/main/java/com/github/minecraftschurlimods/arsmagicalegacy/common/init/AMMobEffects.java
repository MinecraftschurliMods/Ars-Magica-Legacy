package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.AMMobEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.EntangleEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.FlightEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.FrostEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.FuryEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.IlluminationEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.InstantManaEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.ScrambleSynapsesEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.TemporalAnchorEffect;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.MOB_EFFECTS;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.POTIONS;

@NonExtendable
public interface AMMobEffects {
    Holder<MobEffect> AGILITY           = MOB_EFFECTS.register("agility",           id -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xade000).addAttributeModifier(Attributes.STEP_HEIGHT, id.withPrefix("effect."), 0.4, AttributeModifier.Operation.ADD_VALUE));
    Holder<MobEffect> ASTRAL_DISTORTION = MOB_EFFECTS.register("astral_distortion", () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0x6c0000));
    Holder<MobEffect> BURNOUT_REDUCTION = MOB_EFFECTS.register("burnout_reduction", id -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xcc0000).addAttributeModifier(AMAttributes.BURNOUT_REGEN, id.withPrefix("effect."), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    Holder<MobEffect> CLARITY           = MOB_EFFECTS.register("clarity",           () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xbbffff));
    Holder<MobEffect> ENTANGLE          = MOB_EFFECTS.register("entangle",          EntangleEffect::new);
    Holder<MobEffect> FLIGHT            = MOB_EFFECTS.register("flight",            id -> new FlightEffect().addAttributeModifier(NeoForgeMod.CREATIVE_FLIGHT, id.withPrefix("effect."), 1, AttributeModifier.Operation.ADD_VALUE));
    Holder<MobEffect> FROST             = MOB_EFFECTS.register("frost",             FrostEffect::new);
    Holder<MobEffect> FURY              = MOB_EFFECTS.register("fury",              FuryEffect::new);
    Holder<MobEffect> GRAVITY_WELL      = MOB_EFFECTS.register("gravity_well",      id -> new AMMobEffect(MobEffectCategory.HARMFUL, 0xa400ff).addAttributeModifier(Attributes.GRAVITY, id.withPrefix("effect."), 2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    Holder<MobEffect> ILLUMINATION      = MOB_EFFECTS.register("illumination",      IlluminationEffect::new);
    Holder<MobEffect> INSTANT_MANA      = MOB_EFFECTS.register("instant_mana",      InstantManaEffect::new);
    Holder<MobEffect> MAGIC_SHIELD      = MOB_EFFECTS.register("magic_shield",      () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xd780ff));
    Holder<MobEffect> MANA_BOOST        = MOB_EFFECTS.register("mana_boost",        id -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x0093ff).addAttributeModifier(AMAttributes.MAX_MANA, id.withPrefix("effect."), 250, AttributeModifier.Operation.ADD_VALUE));
    Holder<MobEffect> MANA_REGEN        = MOB_EFFECTS.register("mana_regen",        id -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x2222aa).addAttributeModifier(AMAttributes.MANA_REGEN, id.withPrefix("effect."), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    Holder<MobEffect> REFLECT           = MOB_EFFECTS.register("reflect",           () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xadffff));
    Holder<MobEffect> SCRAMBLE_SYNAPSES = MOB_EFFECTS.register("scramble_synapses", ScrambleSynapsesEffect::new);
    Holder<MobEffect> SHIELD            = MOB_EFFECTS.register("shield",            id -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xc4c4c4).addAttributeModifier(Attributes.ARMOR, id.withPrefix("effect."), 2, AttributeModifier.Operation.ADD_VALUE));
    Holder<MobEffect> SHRINK            = MOB_EFFECTS.register("shrink",            id -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x0000dd).addAttributeModifier(Attributes.SCALE, id.withPrefix("effect."), -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    Holder<MobEffect> SILENCE           = MOB_EFFECTS.register("silence",           () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0xc1c1ff));
    Holder<MobEffect> SWIFT_SWIM        = MOB_EFFECTS.register("swift_swim",        id -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x3b3bff).addAttributeModifier(NeoForgeMod.SWIM_SPEED, id.withPrefix("effect."), 1.33f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    Holder<MobEffect> TEMPORAL_ANCHOR   = MOB_EFFECTS.register("temporal_anchor",   TemporalAnchorEffect::new);
    Holder<MobEffect> TRUE_SIGHT        = MOB_EFFECTS.register("true_sight",        () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xc400ff));
    Holder<MobEffect> WATERY_GRAVE      = MOB_EFFECTS.register("watery_grave",      () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0x0000a2));

    Holder<Potion> LESSER_MANA    = POTIONS.register("lesser_mana",    () -> new Potion(new MobEffectInstance(INSTANT_MANA, 1, 0), new MobEffectInstance(MANA_REGEN,  600, 0)));
    Holder<Potion> STANDARD_MANA  = POTIONS.register("standard_mana",  () -> new Potion(new MobEffectInstance(INSTANT_MANA, 1, 1), new MobEffectInstance(MANA_REGEN, 1200, 1)));
    Holder<Potion> GREATER_MANA   = POTIONS.register("greater_mana",   () -> new Potion(new MobEffectInstance(INSTANT_MANA, 1, 2), new MobEffectInstance(MANA_REGEN, 1800, 2)));
    Holder<Potion> EPIC_MANA      = POTIONS.register("epic_mana",      () -> new Potion(new MobEffectInstance(INSTANT_MANA, 1, 3), new MobEffectInstance(MANA_REGEN, 1800, 2), new MobEffectInstance(MANA_BOOST,  600, 0)));
    Holder<Potion> LEGENDARY_MANA = POTIONS.register("legendary_mana", () -> new Potion(new MobEffectInstance(INSTANT_MANA, 1, 4), new MobEffectInstance(MANA_REGEN, 1800, 2), new MobEffectInstance(MANA_BOOST, 1200, 1)));
    Holder<Potion> INFUSED_MANA   = POTIONS.register("infused_mana",   () -> new Potion(new MobEffectInstance(INSTANT_MANA, 1, 9)));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
