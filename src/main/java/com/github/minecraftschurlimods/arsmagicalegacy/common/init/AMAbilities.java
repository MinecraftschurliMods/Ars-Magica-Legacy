package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.AttributeAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.BurnoutCostModifierAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.DamageModifierAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.EffectAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.EffectResistanceAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.EndermanPumpkinAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.ExtraDamageAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.FirePunchAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.FrostPunchAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.FrostWalkerAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.JumpBoostAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.KillEffectAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.LightHealthModifierAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.NetherDamageAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.SpellCastEffectAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.ThornsAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.WaterDamageAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.WaterHealthModifierAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.AbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMAbilities {
    DeferredRegister<MapCodec<? extends AbilityEffect>> ABILITY_EFFECTS = DeferredRegister.create(AMRegistries.Keys.ABILITY_EFFECT, ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<AttributeAbilityEffect>>           ATTRIBUTE_EFFECT             = ABILITY_EFFECTS.register("attribute",             () -> AttributeAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<DamageModifierAbilityEffect>>      DAMAGE_MODIFIER_EFFECT       = ABILITY_EFFECTS.register("damage_modifier",       () -> DamageModifierAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<EndermanPumpkinAbilityEffect>>     ENDERMAN_PUMPKIN_EFFECT      = ABILITY_EFFECTS.register("enderman_pumpkin",      () -> EndermanPumpkinAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<EffectAbilityEffect>>              EFFECT_EFFECT                = ABILITY_EFFECTS.register("effect",                () -> EffectAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<EffectResistanceAbilityEffect>>    EFFECT_RESISTANCE_EFFECT     = ABILITY_EFFECTS.register("effect_resistance",     () -> EffectResistanceAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<ExtraDamageAbilityEffect>>         EXTRA_DAMAGE_EFFECT          = ABILITY_EFFECTS.register("extra_damage",          () -> ExtraDamageAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<FirePunchAbilityEffect>>           FIRE_PUNCH_EFFECT            = ABILITY_EFFECTS.register("fire_punch",            () -> FirePunchAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<FrostPunchAbilityEffect>>          FROST_PUNCH_EFFECT           = ABILITY_EFFECTS.register("frost_punch",           () -> FrostPunchAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<FrostWalkerAbilityEffect>>         FROST_WALKER_EFFECT          = ABILITY_EFFECTS.register("frost_walker",          () -> FrostWalkerAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<JumpBoostAbilityEffect>>           JUMP_BOOST_EFFECT            = ABILITY_EFFECTS.register("jump_boost",            () -> JumpBoostAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<KillEffectAbilityEffect>>          KILL_EFFECT_EFFECT           = ABILITY_EFFECTS.register("kill_effect",           () -> KillEffectAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<LightHealthModifierAbilityEffect>> LIGHT_HEALTH_MODIFIER_EFFECT = ABILITY_EFFECTS.register("light_health_modifier", () -> LightHealthModifierAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<BurnoutCostModifierAbilityEffect>> BURNOUT_COST_MODIFIER_EFFECT = ABILITY_EFFECTS.register("burnout_cost_modifier", () -> BurnoutCostModifierAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<NetherDamageAbilityEffect>>        NETHER_DAMAGE_EFFECT         = ABILITY_EFFECTS.register("nether_damage",         () -> NetherDamageAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<SpellCastEffectAbilityEffect>>     SPELL_CAST_EFFECT_EFFECT     = ABILITY_EFFECTS.register("spell_cast_effect",     () -> SpellCastEffectAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<ThornsAbilityEffect>>              THORNS_EFFECT                = ABILITY_EFFECTS.register("thorns",                () -> ThornsAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<WaterDamageAbilityEffect>>         WATER_DAMAGE_EFFECT          = ABILITY_EFFECTS.register("water_damage",          () -> WaterDamageAbilityEffect.CODEC);
    DeferredHolder<MapCodec<? extends AbilityEffect>, MapCodec<WaterHealthModifierAbilityEffect>> WATER_HEALTH_MODIFIER_EFFECT = ABILITY_EFFECTS.register("water_health_modifier", () -> WaterHealthModifierAbilityEffect.CODEC);

    ResourceKey<Ability> SWIM_SPEED             = key("swim_speed");
    ResourceKey<Ability> ENDER_THORNS           = key("ender_thorns");
    ResourceKey<Ability> NETHER_DAMAGE_WATER    = key("nether_damage_water");
    ResourceKey<Ability> FIRE_RESISTANCE        = key("fire_resistance");
    ResourceKey<Ability> FIRE_PUNCH             = key("fire_punch");
    ResourceKey<Ability> WATER_DAMAGE_FIRE      = key("water_damage_fire");
    ResourceKey<Ability> RESISTANCE             = key("resistance");
    ResourceKey<Ability> HASTE                  = key("haste");
    ResourceKey<Ability> FALL_DAMAGE            = key("fall_damage");
    ResourceKey<Ability> JUMP_BOOST             = key("jump_boost");
    ResourceKey<Ability> FEATHER_FALLING        = key("feather_falling");
    ResourceKey<Ability> GRAVITY                = key("gravity");
    ResourceKey<Ability> FROST_PUNCH            = key("frost_punch");
    ResourceKey<Ability> FROST_WALKER           = key("frost_walker");
    ResourceKey<Ability> SLOWNESS               = key("slowness");
    ResourceKey<Ability> SPEED                  = key("speed");
    ResourceKey<Ability> STEP_ASSIST            = key("step_assist");
    ResourceKey<Ability> WATER_DAMAGE_LIGHTNING = key("water_damage_lightning");
    ResourceKey<Ability> THORNS                 = key("thorns");
    ResourceKey<Ability> SATURATION             = key("saturation");
    ResourceKey<Ability> NETHER_DAMAGE_NATURE   = key("nether_damage_nature");
    ResourceKey<Ability> SMITE                  = key("smite");
    ResourceKey<Ability> REGENERATION           = key("regeneration");
    ResourceKey<Ability> NAUSEA                 = key("nausea");
    ResourceKey<Ability> BURNOUT_REDUCTION      = key("burnout_reduction");
    ResourceKey<Ability> CLARITY                = key("clarity");
    ResourceKey<Ability> MAGIC_DAMAGE           = key("magic_damage");
    ResourceKey<Ability> POISON_RESISTANCE      = key("poison_resistance");
    ResourceKey<Ability> NIGHT_VISION           = key("night_vision");
    ResourceKey<Ability> ENDERMAN_PUMPKIN       = key("enderman_pumpkin");
    ResourceKey<Ability> LIGHT_HEALTH_REDUCTION = key("light_health_reduction");
    ResourceKey<Ability> WATER_HEALTH_REDUCTION = key("water_health_reduction");
    // @formatter:on

    private static ResourceKey<Ability> key(String name) {
        return ResourceKey.create(AMRegistries.Keys.ABILITY, ArsMagicaApi.id(name));
    }
}
