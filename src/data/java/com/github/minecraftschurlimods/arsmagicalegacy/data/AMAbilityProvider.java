package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AbilityProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAbilities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAffinities;
import net.minecraft.advancements.critereon.MinMaxBounds;

class AMAbilityProvider extends AbilityProvider {
    AMAbilityProvider() {
        super(ArsMagicaAPI.MOD_ID);
    }

    @Override
    public void generate() {
        add(AMAbilities.FIRE_RESISTANCE, new Ability(AMAffinities.FIRE.get(), MinMaxBounds.Doubles.between(0.01, 1)));
        add(AMAbilities.FIRE_PUNCH, new Ability(AMAffinities.FIRE.get(), MinMaxBounds.Doubles.atLeast(1)));
        add(AMAbilities.WATER_DAMAGE_FIRE, new Ability(AMAffinities.FIRE.get(), MinMaxBounds.Doubles.between(0.5, 1)));
        add(AMAbilities.SWIM_SPEED, new Ability(AMAffinities.WATER.get(), MinMaxBounds.Doubles.between(0.01, 1)));
        add(AMAbilities.ENDERMAN_THORNS, new Ability(AMAffinities.WATER.get(), MinMaxBounds.Doubles.atLeast(1)));
        add(AMAbilities.NETHER_DAMAGE_WATER, new Ability(AMAffinities.WATER.get(), MinMaxBounds.Doubles.between(0.5, 1)));
        add(AMAbilities.RESISTANCE, new Ability(AMAffinities.EARTH.get(), MinMaxBounds.Doubles.between(0.01, 1)));
        add(AMAbilities.HASTE, new Ability(AMAffinities.EARTH.get(), MinMaxBounds.Doubles.between(0.01, 1)));
        add(AMAbilities.FALL_DAMAGE, new Ability(AMAffinities.EARTH.get(), MinMaxBounds.Doubles.between(0.5, 1)));
        add(AMAbilities.JUMP_BOOST, new Ability(AMAffinities.AIR.get(), MinMaxBounds.Doubles.between(0.01, 1)));
        add(AMAbilities.FEATHER_FALLING, new Ability(AMAffinities.AIR.get(), MinMaxBounds.Doubles.between(0.01, 1)));
        add(AMAbilities.GRAVITY, new Ability(AMAffinities.AIR.get(), MinMaxBounds.Doubles.between(0.5, 1)));
        add(AMAbilities.FROST_PUNCH, new Ability(AMAffinities.ICE.get(), MinMaxBounds.Doubles.between(0.01, 1)));
        add(AMAbilities.FROST_WALKER, new Ability(AMAffinities.ICE.get(), MinMaxBounds.Doubles.atLeast(1)));
        add(AMAbilities.SLOWNESS, new Ability(AMAffinities.ICE.get(), MinMaxBounds.Doubles.between(0.5, 1)));
        add(AMAbilities.SPEED, new Ability(AMAffinities.LIGHTNING.get(), MinMaxBounds.Doubles.between(0.01, 1)));
        add(AMAbilities.STEP_ASSIST, new Ability(AMAffinities.LIGHTNING.get(), MinMaxBounds.Doubles.atLeast(1)));
        add(AMAbilities.WATER_DAMAGE_LIGHTNING, new Ability(AMAffinities.LIGHTNING.get(), MinMaxBounds.Doubles.between(0.5, 1)));
        add(AMAbilities.THORNS, new Ability(AMAffinities.NATURE.get(), MinMaxBounds.Doubles.between(0.01, 1)));
        add(AMAbilities.SATURATION, new Ability(AMAffinities.NATURE.get(), MinMaxBounds.Doubles.atLeast(1)));
        add(AMAbilities.NETHER_DAMAGE_NATURE, new Ability(AMAffinities.NATURE.get(), MinMaxBounds.Doubles.between(0.5, 1)));
        add(AMAbilities.SMITE, new Ability(AMAffinities.LIFE.get(), MinMaxBounds.Doubles.between(0.01, 1)));
        add(AMAbilities.REGENERATION, new Ability(AMAffinities.LIFE.get(), MinMaxBounds.Doubles.atLeast(1)));
        add(AMAbilities.NAUSEA, new Ability(AMAffinities.LIFE.get(), MinMaxBounds.Doubles.between(0.5, 1)));
        add(AMAbilities.MANA_REDUCTION, new Ability(AMAffinities.ARCANE.get(), MinMaxBounds.Doubles.between(0.01, 1)));
        add(AMAbilities.CLARITY, new Ability(AMAffinities.ARCANE.get(), MinMaxBounds.Doubles.atLeast(1)));
        add(AMAbilities.MAGIC_DAMAGE, new Ability(AMAffinities.ARCANE.get(), MinMaxBounds.Doubles.between(0.5, 1)));
        add(AMAbilities.POISON_RESISTANCE, new Ability(AMAffinities.ENDER.get(), MinMaxBounds.Doubles.between(0.5, 1)));
        add(AMAbilities.NIGHT_VISION, new Ability(AMAffinities.ENDER.get(), MinMaxBounds.Doubles.between(0.5, 1)));
        add(AMAbilities.ENDERMAN_PUMPKIN, new Ability(AMAffinities.ENDER.get(), MinMaxBounds.Doubles.atLeast(1)));
        add(AMAbilities.LIGHT_HEALTH_REDUCTION, new Ability(AMAffinities.ENDER.get(), MinMaxBounds.Doubles.between(0.5, 0.99)));
        add(AMAbilities.WATER_HEALTH_REDUCTION, new Ability(AMAffinities.ENDER.get(), MinMaxBounds.Doubles.between(0.5, 0.99)));
    }
}
