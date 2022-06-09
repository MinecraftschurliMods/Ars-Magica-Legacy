package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbility;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ABILITIES;

@NonExtendable
public interface AMAbilities {
    RegistryObject<IAbility> FIRE_RESISTANCE        = register("fire_resistance");
    RegistryObject<IAbility> FIRE_PUNCH             = register("fire_punch");
    RegistryObject<IAbility> WATER_DAMAGE_FIRE      = register("water_damage_fire");
    RegistryObject<IAbility> SWIM_SPEED             = register("swim_speed");
    RegistryObject<IAbility> ENDERMAN_THORNS        = register("enderman_thorns");
    RegistryObject<IAbility> NETHER_DAMAGE_WATER    = register("nether_damage_water");
    RegistryObject<IAbility> RESISTANCE             = register("resistance");
    RegistryObject<IAbility> HASTE                  = register("haste");
    RegistryObject<IAbility> FALL_DAMAGE            = register("fall_damage");
    RegistryObject<IAbility> JUMP_BOOST             = register("jump_boost");
    RegistryObject<IAbility> FEATHER_FALLING        = register("feather_falling");
    RegistryObject<IAbility> GRAVITY                = register("gravity");
    RegistryObject<IAbility> FROST_PUNCH            = register("frost_punch");
    RegistryObject<IAbility> FROST_WALKER           = register("frost_walker");
    RegistryObject<IAbility> SLOWNESS               = register("slowness");
    RegistryObject<IAbility> SPEED                  = register("speed");
    RegistryObject<IAbility> STEP_ASSIST            = register("step_assist");
    RegistryObject<IAbility> WATER_DAMAGE_LIGHTNING = register("water_damage_lightning");
    RegistryObject<IAbility> THORNS                 = register("thorns");
    RegistryObject<IAbility> SATURATION             = register("saturation");
    RegistryObject<IAbility> NETHER_DAMAGE_NATURE   = register("nether_damage_nature");
    RegistryObject<IAbility> SMITE                  = register("smite");
    RegistryObject<IAbility> REGENERATION           = register("regeneration");
    RegistryObject<IAbility> NAUSEA                 = register("nausea");
    RegistryObject<IAbility> MANA_REDUCTION         = register("mana_reduction");
    RegistryObject<IAbility> CLARITY                = register("clarity");
    RegistryObject<IAbility> MAGIC_DAMAGE           = register("magic_damage");
    RegistryObject<IAbility> POISON_RESISTANCE      = register("poison_resistance");
    RegistryObject<IAbility> NIGHT_VISION           = register("night_vision");
    RegistryObject<IAbility> ENDERMAN_PUMPKIN       = register("enderman_pumpkin");
    RegistryObject<IAbility> LIGHT_HEALTH_REDUCTION = register("light_health_reduction");
    RegistryObject<IAbility> WATER_HEALTH_REDUCTION = register("water_health_reduction");

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}

    private static RegistryObject<IAbility> register(String name) {
        return ABILITIES.register(name, AMAbilities::create);
    }

    private static IAbility create() {
        return new IAbility() {};
    }
}
