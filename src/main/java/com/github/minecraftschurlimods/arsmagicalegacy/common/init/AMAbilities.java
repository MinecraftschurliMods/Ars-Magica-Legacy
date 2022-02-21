package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.AbstractAbility;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface AMAbilities {
    RegistryObject<AbstractAbility> FIRE_RESISTANCE        = AMRegistries.ABILITIES.register("fire_resistance",        () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> FIRE_PUNCH             = AMRegistries.ABILITIES.register("fire_punch",             () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> WATER_DAMAGE_FIRE      = AMRegistries.ABILITIES.register("water_damage_fire",      () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> DEPTH_STRIDER          = AMRegistries.ABILITIES.register("depth_strider",          () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> ENDERMAN_THORNS        = AMRegistries.ABILITIES.register("enderman_thorns",        () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> NETHER_DAMAGE_WATER    = AMRegistries.ABILITIES.register("nether_damage_water",    () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> RESISTANCE             = AMRegistries.ABILITIES.register("resistance",             () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> HASTE                  = AMRegistries.ABILITIES.register("haste",                  () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> FALL_DAMAGE            = AMRegistries.ABILITIES.register("fall_damage",            () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> JUMP_BOOST             = AMRegistries.ABILITIES.register("jump_boost",             () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> FEATHER_FALLING        = AMRegistries.ABILITIES.register("feather_falling",        () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> GRAVITY                = AMRegistries.ABILITIES.register("gravity",                () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> FROST_WALKER           = AMRegistries.ABILITIES.register("frost_walker",           () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> FROST_PUNCH            = AMRegistries.ABILITIES.register("frost_punch",            () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> SLOWNESS               = AMRegistries.ABILITIES.register("slowness",               () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> SPEED                  = AMRegistries.ABILITIES.register("speed",                  () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> STEP_ASSIST            = AMRegistries.ABILITIES.register("step_assist",            () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> WATER_DAMAGE_LIGHTNING = AMRegistries.ABILITIES.register("water_damage_lightning", () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> THORNS                 = AMRegistries.ABILITIES.register("thorns",                 () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> SATURATION             = AMRegistries.ABILITIES.register("saturation",             () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> NETHER_DAMAGE_NATURE   = AMRegistries.ABILITIES.register("nether_damage_nature",   () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> SMITE                  = AMRegistries.ABILITIES.register("smite",                  () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> REGENERATION           = AMRegistries.ABILITIES.register("regeneration",           () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> NAUSEA                 = AMRegistries.ABILITIES.register("nausea",                 () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> MANA_REDUCTION         = AMRegistries.ABILITIES.register("mana_reduction",         () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> CLARITY                = AMRegistries.ABILITIES.register("clarity",                () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> MAGIC_DAMAGE           = AMRegistries.ABILITIES.register("magic_damage",           () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> POISON_RESISTANCE      = AMRegistries.ABILITIES.register("poison_resistance",      () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> NIGHT_VISION           = AMRegistries.ABILITIES.register("night_vision",           () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> ENDERMAN_PUMPKIN       = AMRegistries.ABILITIES.register("enderman_pumpkin",       () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> LIGHT_HEALTH_REDUCTION = AMRegistries.ABILITIES.register("light_health_reduction", () -> new AbstractAbility() {});
    RegistryObject<AbstractAbility> WATER_HEALTH_REDUCTION = AMRegistries.ABILITIES.register("water_health_reduction", () -> new AbstractAbility() {});

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
