package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AbilityProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAbilities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAffinities;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;

import java.util.function.Consumer;

class AMAbilityProvider extends AbilityProvider {
    AMAbilityProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void generate(Consumer<Builder> consumer) {
        builder(AMAbilities.FIRE_RESISTANCE.get(),        AMAffinities.FIRE.get(),      MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.FIRE_PUNCH.get(),             AMAffinities.FIRE.get(),      MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.WATER_DAMAGE_FIRE.get(),      AMAffinities.FIRE.get(),      MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.SWIM_SPEED.get(),             AMAffinities.WATER.get(),     MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.ENDERMAN_THORNS.get(),        AMAffinities.WATER.get(),     MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.NETHER_DAMAGE_WATER.get(),    AMAffinities.WATER.get(),     MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.RESISTANCE.get(),             AMAffinities.EARTH.get(),     MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.HASTE.get(),                  AMAffinities.EARTH.get(),     MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.FALL_DAMAGE.get(),            AMAffinities.EARTH.get(),     MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.JUMP_BOOST.get(),             AMAffinities.AIR.get(),       MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.FEATHER_FALLING.get(),        AMAffinities.AIR.get(),       MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.GRAVITY.get(),                AMAffinities.AIR.get(),       MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.FROST_PUNCH.get(),            AMAffinities.ICE.get(),       MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.FROST_WALKER.get(),           AMAffinities.ICE.get(),       MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.SLOWNESS.get(),               AMAffinities.ICE.get(),       MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.SPEED.get(),                  AMAffinities.LIGHTNING.get(), MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.STEP_ASSIST.get(),            AMAffinities.LIGHTNING.get(), MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.WATER_DAMAGE_LIGHTNING.get(), AMAffinities.LIGHTNING.get(), MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.THORNS.get(),                 AMAffinities.NATURE.get(),    MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.SATURATION.get(),             AMAffinities.NATURE.get(),    MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.NETHER_DAMAGE_NATURE.get(),   AMAffinities.NATURE.get(),    MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.SMITE.get(),                  AMAffinities.LIFE.get(),      MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.REGENERATION.get(),           AMAffinities.LIFE.get(),      MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.NAUSEA.get(),                 AMAffinities.LIFE.get(),      MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.MANA_REDUCTION.get(),         AMAffinities.ARCANE.get(),    MinMaxBounds.Doubles.between(0.01, 1)).build(consumer);
        builder(AMAbilities.CLARITY.get(),                AMAffinities.ARCANE.get(),    MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.MAGIC_DAMAGE.get(),           AMAffinities.ARCANE.get(),    MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.POISON_RESISTANCE.get(),      AMAffinities.ENDER.get(),     MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.NIGHT_VISION.get(),           AMAffinities.ENDER.get(),     MinMaxBounds.Doubles.between(0.5, 1)).build(consumer);
        builder(AMAbilities.ENDERMAN_PUMPKIN.get(),       AMAffinities.ENDER.get(),     MinMaxBounds.Doubles.atLeast(1)).build(consumer);
        builder(AMAbilities.LIGHT_HEALTH_REDUCTION.get(), AMAffinities.ENDER.get(),     MinMaxBounds.Doubles.between(0.5, 0.99)).build(consumer);
        builder(AMAbilities.WATER_HEALTH_REDUCTION.get(), AMAffinities.ENDER.get(),     MinMaxBounds.Doubles.between(0.5, 0.99)).build(consumer);
    }
}
