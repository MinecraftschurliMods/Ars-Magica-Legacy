package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.AbstractAbility;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ABILITIES;

@NonExtendable
public interface AMAbilities {
    RegistryObject<AbstractAbility> FIRE_RESISTANCE        = ABILITIES.register("fire_resistance",        () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> FIRE_PUNCH             = ABILITIES.register("fire_punch",             () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> WATER_DAMAGE_FIRE      = ABILITIES.register("water_damage_fire",      () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> SWIM_SPEED             = ABILITIES.register("swim_speed",             () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> ENDERMAN_THORNS        = ABILITIES.register("enderman_thorns",        () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> NETHER_DAMAGE_WATER    = ABILITIES.register("nether_damage_water",    () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> RESISTANCE             = ABILITIES.register("resistance",             () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> HASTE                  = ABILITIES.register("haste",                  () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> FALL_DAMAGE            = ABILITIES.register("fall_damage",            () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> JUMP_BOOST             = ABILITIES.register("jump_boost",             () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> FEATHER_FALLING        = ABILITIES.register("feather_falling",        () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> GRAVITY                = ABILITIES.register("gravity",                () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> FROST_PUNCH            = ABILITIES.register("frost_punch",            () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> FROST_WALKER           = ABILITIES.register("frost_walker",           () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> SLOWNESS               = ABILITIES.register("slowness",               () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> SPEED                  = ABILITIES.register("speed",                  () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> STEP_ASSIST            = ABILITIES.register("step_assist",            () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> WATER_DAMAGE_LIGHTNING = ABILITIES.register("water_damage_lightning", () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> THORNS                 = ABILITIES.register("thorns",                 () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> SATURATION             = ABILITIES.register("saturation",             () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> NETHER_DAMAGE_NATURE   = ABILITIES.register("nether_damage_nature",   () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> SMITE                  = ABILITIES.register("smite",                  () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> REGENERATION           = ABILITIES.register("regeneration",           () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> NAUSEA                 = ABILITIES.register("nausea",                 () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> MANA_REDUCTION         = ABILITIES.register("mana_reduction",         () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> CLARITY                = ABILITIES.register("clarity",                () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> MAGIC_DAMAGE           = ABILITIES.register("magic_damage",           () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> POISON_RESISTANCE      = ABILITIES.register("poison_resistance",      () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> NIGHT_VISION           = ABILITIES.register("night_vision",           () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> ENDERMAN_PUMPKIN       = ABILITIES.register("enderman_pumpkin",       () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> LIGHT_HEALTH_REDUCTION = ABILITIES.register("light_health_reduction", () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> WATER_HEALTH_REDUCTION = ABILITIES.register("water_health_reduction", () -> new AbstractAbility() {});

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
