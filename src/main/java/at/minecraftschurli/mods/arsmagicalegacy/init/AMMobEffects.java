package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.effect.AMMobEffect;
import at.minecraftschurli.mods.arsmagicalegacy.effect.EntangleEffect;
import at.minecraftschurli.mods.arsmagicalegacy.effect.FlightEffect;
import at.minecraftschurli.mods.arsmagicalegacy.effect.FuryEffect;
import at.minecraftschurli.mods.arsmagicalegacy.effect.IlluminationEffect;
import at.minecraftschurli.mods.arsmagicalegacy.effect.InstantManaEffect;
import at.minecraftschurli.mods.arsmagicalegacy.effect.ScrambleSynapsesEffect;
import at.minecraftschurli.mods.arsmagicalegacy.effect.TemporalAnchorEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMMobEffects {
    DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<MobEffect, AMMobEffect>            ASTRAL_DISTORTION = MOB_EFFECTS.register("astral_distortion", () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0x6c0000));
    DeferredHolder<MobEffect, MobEffect>              BURNOUT_REDUCTION = MOB_EFFECTS.register("burnout_reduction", id -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xcc0000).addAttributeModifier(AMAttributes.BURNOUT_REGENERATION, id.withPrefix("effect."), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    DeferredHolder<MobEffect, AMMobEffect>            CLARITY           = MOB_EFFECTS.register("clarity",           () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xbbffff));
    DeferredHolder<MobEffect, EntangleEffect>         ENTANGLE          = MOB_EFFECTS.register("entangle",          EntangleEffect::new);
    DeferredHolder<MobEffect, FlightEffect>           FLIGHT            = MOB_EFFECTS.register("flight",            FlightEffect::new);
    DeferredHolder<MobEffect, FuryEffect>             FURY              = MOB_EFFECTS.register("fury",              FuryEffect::new);
    DeferredHolder<MobEffect, MobEffect>              GRAVITY_WELL      = MOB_EFFECTS.register("gravity_well",      id -> new AMMobEffect(MobEffectCategory.HARMFUL, 0xa400ff).addAttributeModifier(Attributes.GRAVITY, id.withPrefix("effect."), 2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    DeferredHolder<MobEffect, IlluminationEffect>     ILLUMINATION      = MOB_EFFECTS.register("illumination",      IlluminationEffect::new);
    DeferredHolder<MobEffect, InstantManaEffect>      INSTANT_MANA      = MOB_EFFECTS.register("instant_mana",      InstantManaEffect::new);
    DeferredHolder<MobEffect, MobEffect>              MANA_BOOST        = MOB_EFFECTS.register("mana_boost",        id -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x0093ff).addAttributeModifier(AMAttributes.MAX_MANA, id.withPrefix("effect."), 250, AttributeModifier.Operation.ADD_VALUE));
    DeferredHolder<MobEffect, MobEffect>              MANA_REGENERATION = MOB_EFFECTS.register("mana_regeneration", id -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x2222aa).addAttributeModifier(AMAttributes.MANA_REGENERATION, id.withPrefix("effect."), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    DeferredHolder<MobEffect, AMMobEffect>            REFLECT           = MOB_EFFECTS.register("reflect",           () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xadffff));
    DeferredHolder<MobEffect, ScrambleSynapsesEffect> SCRAMBLE_SYNAPSES = MOB_EFFECTS.register("scramble_synapses", ScrambleSynapsesEffect::new);
    DeferredHolder<MobEffect, MobEffect>              SHRINK            = MOB_EFFECTS.register("shrink",            id -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x0000dd).addAttributeModifier(Attributes.SCALE, id.withPrefix("effect."), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, amplifier -> -(amplifier + 1.) / (amplifier + 2)));
    DeferredHolder<MobEffect, AMMobEffect>            SILENCE           = MOB_EFFECTS.register("silence",           () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0xc1c1ff));
    DeferredHolder<MobEffect, MobEffect>              SWIFT_SWIM        = MOB_EFFECTS.register("swift_swim",        id -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0x3b3bff).addAttributeModifier(NeoForgeMod.SWIM_SPEED, id.withPrefix("effect."), 1.33f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    DeferredHolder<MobEffect, TemporalAnchorEffect>   TEMPORAL_ANCHOR   = MOB_EFFECTS.register("temporal_anchor",   TemporalAnchorEffect::new);
    DeferredHolder<MobEffect, AMMobEffect>            TRUE_SIGHT        = MOB_EFFECTS.register("true_sight",        () -> new AMMobEffect(MobEffectCategory.BENEFICIAL, 0xc400ff));
    DeferredHolder<MobEffect, AMMobEffect>            WATERY_GRAVE      = MOB_EFFECTS.register("watery_grave",      () -> new AMMobEffect(MobEffectCategory.HARMFUL, 0x0000a2));
    // @formatter:on

    DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<Potion, Potion> LESSER_MANA    = POTIONS.register("lesser_mana",    id -> new Potion(id.getPath(), new MobEffectInstance(INSTANT_MANA, 1, 0), new MobEffectInstance(MANA_REGENERATION, 600, 0)));
    DeferredHolder<Potion, Potion> STANDARD_MANA  = POTIONS.register("standard_mana",  id -> new Potion(id.getPath(), new MobEffectInstance(INSTANT_MANA, 1, 1), new MobEffectInstance(MANA_REGENERATION, 1200, 1)));
    DeferredHolder<Potion, Potion> GREATER_MANA   = POTIONS.register("greater_mana",   id -> new Potion(id.getPath(), new MobEffectInstance(INSTANT_MANA, 1, 2), new MobEffectInstance(MANA_REGENERATION, 1800, 2)));
    DeferredHolder<Potion, Potion> EPIC_MANA      = POTIONS.register("epic_mana",      id -> new Potion(id.getPath(), new MobEffectInstance(INSTANT_MANA, 1, 3), new MobEffectInstance(MANA_REGENERATION, 1800, 2), new MobEffectInstance(MANA_BOOST,  600, 0)));
    DeferredHolder<Potion, Potion> LEGENDARY_MANA = POTIONS.register("legendary_mana", id -> new Potion(id.getPath(), new MobEffectInstance(INSTANT_MANA, 1, 4), new MobEffectInstance(MANA_REGENERATION, 1800, 2), new MobEffectInstance(MANA_BOOST, 1200, 1)));
    DeferredHolder<Potion, Potion> INFUSED_MANA   = POTIONS.register("infused_mana",   id -> new Potion(id.getPath(), new MobEffectInstance(INSTANT_MANA, 1, 9)));
    // @formatter:on
}
