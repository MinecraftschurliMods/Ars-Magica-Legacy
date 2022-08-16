package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AbilityBuilder;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AbilityProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAbilities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAffinities;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

class AMAbilityProvider extends AbilityProvider {
    AMAbilityProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(ArsMagicaAPI.MOD_ID, generator, existingFileHelper);
    }

    @Override
    protected void createAbilities(Consumer<AbilityBuilder> consumer) {
        createAbility(AMAbilities.FIRE_RESISTANCE,        AMAffinities.FIRE.get(),      MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        createAbility(AMAbilities.FIRE_PUNCH,             AMAffinities.FIRE.get(),      MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        createAbility(AMAbilities.WATER_DAMAGE_FIRE,      AMAffinities.FIRE.get(),      MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        createAbility(AMAbilities.SWIM_SPEED,             AMAffinities.WATER.get(),     MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        createAbility(AMAbilities.ENDERMAN_THORNS,        AMAffinities.WATER.get(),     MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        createAbility(AMAbilities.NETHER_DAMAGE_WATER,    AMAffinities.WATER.get(),     MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        createAbility(AMAbilities.RESISTANCE,             AMAffinities.EARTH.get(),     MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        createAbility(AMAbilities.HASTE,                  AMAffinities.EARTH.get(),     MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        createAbility(AMAbilities.FALL_DAMAGE,            AMAffinities.EARTH.get(),     MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        createAbility(AMAbilities.JUMP_BOOST,             AMAffinities.AIR.get(),       MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        createAbility(AMAbilities.FEATHER_FALLING,        AMAffinities.AIR.get(),       MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        createAbility(AMAbilities.GRAVITY,                AMAffinities.AIR.get(),       MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        createAbility(AMAbilities.FROST_PUNCH,            AMAffinities.ICE.get(),       MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        createAbility(AMAbilities.FROST_WALKER,           AMAffinities.ICE.get(),       MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        createAbility(AMAbilities.SLOWNESS,               AMAffinities.ICE.get(),       MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        createAbility(AMAbilities.SPEED,                  AMAffinities.LIGHTNING.get(), MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        createAbility(AMAbilities.STEP_ASSIST,            AMAffinities.LIGHTNING.get(), MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        createAbility(AMAbilities.WATER_DAMAGE_LIGHTNING, AMAffinities.LIGHTNING.get(), MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        createAbility(AMAbilities.THORNS,                 AMAffinities.NATURE.get(),    MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        createAbility(AMAbilities.SATURATION,             AMAffinities.NATURE.get(),    MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        createAbility(AMAbilities.NETHER_DAMAGE_NATURE,   AMAffinities.NATURE.get(),    MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        createAbility(AMAbilities.SMITE,                  AMAffinities.LIFE.get(),      MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        createAbility(AMAbilities.REGENERATION,           AMAffinities.LIFE.get(),      MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        createAbility(AMAbilities.NAUSEA,                 AMAffinities.LIFE.get(),      MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        createAbility(AMAbilities.MANA_REDUCTION,         AMAffinities.ARCANE.get(),    MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        createAbility(AMAbilities.CLARITY,                AMAffinities.ARCANE.get(),    MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        createAbility(AMAbilities.MAGIC_DAMAGE,           AMAffinities.ARCANE.get(),    MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        createAbility(AMAbilities.POISON_RESISTANCE,      AMAffinities.ENDER.get(),     MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        createAbility(AMAbilities.NIGHT_VISION,           AMAffinities.ENDER.get(),     MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        createAbility(AMAbilities.ENDERMAN_PUMPKIN,       AMAffinities.ENDER.get(),     MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        createAbility(AMAbilities.LIGHT_HEALTH_REDUCTION, AMAffinities.ENDER.get(),     MinMaxBounds.Doubles.between(0.5, 0.99)).build(consumer);
        createAbility(AMAbilities.WATER_HEALTH_REDUCTION, AMAffinities.ENDER.get(),     MinMaxBounds.Doubles.between(0.5, 0.99)).build(consumer);
    }
}
