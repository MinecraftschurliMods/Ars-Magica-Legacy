package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AbilityProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAbilities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAffinities;
import com.google.gson.JsonElement;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

class AMAbilityProvider extends AbilityProvider {
    AMAbilityProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(ArsMagicaAPI.MOD_ID, generator, existingFileHelper, registryOps);
    }

    @Override
    protected void generate(Consumer<Builder> consumer) {
        builder(AMAbilities.FIRE_RESISTANCE,        AMAffinities.FIRE.get(),      MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.FIRE_PUNCH,             AMAffinities.FIRE.get(),      MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.WATER_DAMAGE_FIRE,      AMAffinities.FIRE.get(),      MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.SWIM_SPEED,             AMAffinities.WATER.get(),     MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.ENDERMAN_THORNS,        AMAffinities.WATER.get(),     MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.NETHER_DAMAGE_WATER,    AMAffinities.WATER.get(),     MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.RESISTANCE,             AMAffinities.EARTH.get(),     MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.HASTE,                  AMAffinities.EARTH.get(),     MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.FALL_DAMAGE,            AMAffinities.EARTH.get(),     MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.JUMP_BOOST,             AMAffinities.AIR.get(),       MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.FEATHER_FALLING,        AMAffinities.AIR.get(),       MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.GRAVITY,                AMAffinities.AIR.get(),       MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.FROST_PUNCH,            AMAffinities.ICE.get(),       MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.FROST_WALKER,           AMAffinities.ICE.get(),       MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.SLOWNESS,               AMAffinities.ICE.get(),       MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.SPEED,                  AMAffinities.LIGHTNING.get(), MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.STEP_ASSIST,            AMAffinities.LIGHTNING.get(), MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.WATER_DAMAGE_LIGHTNING, AMAffinities.LIGHTNING.get(), MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.THORNS,                 AMAffinities.NATURE.get(),    MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.SATURATION,             AMAffinities.NATURE.get(),    MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.NETHER_DAMAGE_NATURE,   AMAffinities.NATURE.get(),    MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.SMITE,                  AMAffinities.LIFE.get(),      MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.REGENERATION,           AMAffinities.LIFE.get(),      MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.NAUSEA,                 AMAffinities.LIFE.get(),      MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.MANA_REDUCTION,         AMAffinities.ARCANE.get(),    MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.CLARITY,                AMAffinities.ARCANE.get(),    MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.MAGIC_DAMAGE,           AMAffinities.ARCANE.get(),    MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.POISON_RESISTANCE,      AMAffinities.ENDER.get(),     MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.NIGHT_VISION,           AMAffinities.ENDER.get(),     MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.ENDERMAN_PUMPKIN,       AMAffinities.ENDER.get(),     MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.LIGHT_HEALTH_REDUCTION, AMAffinities.ENDER.get(),     MinMaxBounds.Doubles.between(0.5, 0.99)).build(consumer);
        builder(AMAbilities.WATER_HEALTH_REDUCTION, AMAffinities.ENDER.get(),     MinMaxBounds.Doubles.between(0.5, 0.99)).build(consumer);
    }
}
