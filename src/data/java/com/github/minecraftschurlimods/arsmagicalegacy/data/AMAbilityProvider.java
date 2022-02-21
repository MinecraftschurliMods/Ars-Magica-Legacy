package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AbilityBuilder;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AbilityProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.Range;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAbilities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAffinities;
import net.minecraft.data.DataGenerator;

import java.util.function.Consumer;

class AMAbilityProvider extends AbilityProvider {
    AMAbilityProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void createAbilities(Consumer<AbilityBuilder> consumer) {
        createAbility(AMAbilities.FIRE_RESISTANCE.get().getId(),        AMAffinities.FIRE.get(),      Range.ofUpperBound(1))    .build(consumer);
        createAbility(AMAbilities.FIRE_PUNCH.get().getId(),             AMAffinities.FIRE.get(),      Range.ofLowerBound(1))    .build(consumer);
        createAbility(AMAbilities.WATER_DAMAGE_FIRE.get().getId(),      AMAffinities.FIRE.get(),      Range.ofBounds(0.5, 1))   .build(consumer);
        createAbility(AMAbilities.DEPTH_STRIDER.get().getId(),          AMAffinities.WATER.get(),     Range.ofUpperBound(1))    .build(consumer);
        createAbility(AMAbilities.ENDERMAN_THORNS.get().getId(),        AMAffinities.WATER.get(),     Range.ofLowerBound(1))    .build(consumer);
        createAbility(AMAbilities.NETHER_DAMAGE_WATER.get().getId(),    AMAffinities.WATER.get(),     Range.ofBounds(0.5, 1))   .build(consumer);
        createAbility(AMAbilities.RESISTANCE.get().getId(),             AMAffinities.EARTH.get(),     Range.ofUpperBound(1))    .build(consumer);
        createAbility(AMAbilities.HASTE.get().getId(),                  AMAffinities.EARTH.get(),     Range.ofUpperBound(1))    .build(consumer);
        createAbility(AMAbilities.FALL_DAMAGE.get().getId(),            AMAffinities.EARTH.get(),     Range.ofBounds(0.5, 1))   .build(consumer);
        createAbility(AMAbilities.JUMP_BOOST.get().getId(),             AMAffinities.AIR.get(),       Range.ofUpperBound(1))    .build(consumer);
        createAbility(AMAbilities.FEATHER_FALLING.get().getId(),        AMAffinities.AIR.get(),       Range.ofUpperBound(1))    .build(consumer);
        createAbility(AMAbilities.GRAVITY.get().getId(),                AMAffinities.AIR.get(),       Range.ofBounds(0.5, 1))   .build(consumer);
        createAbility(AMAbilities.FROST_PUNCH.get().getId(),            AMAffinities.ICE.get(),       Range.ofUpperBound(1))    .build(consumer);
        createAbility(AMAbilities.FROST_WALKER.get().getId(),           AMAffinities.ICE.get(),       Range.ofLowerBound(1))    .build(consumer);
        createAbility(AMAbilities.SLOWNESS.get().getId(),               AMAffinities.ICE.get(),       Range.ofBounds(0.5, 1))   .build(consumer);
        createAbility(AMAbilities.SPEED.get().getId(),                  AMAffinities.LIGHTNING.get(), Range.ofUpperBound(1))    .build(consumer);
        createAbility(AMAbilities.STEP_ASSIST.get().getId(),            AMAffinities.LIGHTNING.get(), Range.ofLowerBound(1))    .build(consumer);
        createAbility(AMAbilities.WATER_DAMAGE_LIGHTNING.get().getId(), AMAffinities.LIGHTNING.get(), Range.ofBounds(0.5, 1))   .build(consumer);
        createAbility(AMAbilities.THORNS.get().getId(),                 AMAffinities.NATURE.get(),    Range.ofUpperBound(1))    .build(consumer);
        createAbility(AMAbilities.SATURATION.get().getId(),             AMAffinities.NATURE.get(),    Range.ofLowerBound(1))    .build(consumer);
        createAbility(AMAbilities.NETHER_DAMAGE_NATURE.get().getId(),   AMAffinities.NATURE.get(),    Range.ofBounds(0.5, 1))   .build(consumer);
        createAbility(AMAbilities.SMITE.get().getId(),                  AMAffinities.LIFE.get(),      Range.ofUpperBound(1))    .build(consumer);
        createAbility(AMAbilities.REGENERATION.get().getId(),           AMAffinities.LIFE.get(),      Range.ofLowerBound(1))    .build(consumer);
        createAbility(AMAbilities.NAUSEA.get().getId(),                 AMAffinities.LIFE.get(),      Range.ofBounds(0.5, 1))   .build(consumer);
        createAbility(AMAbilities.MANA_REDUCTION.get().getId(),         AMAffinities.ARCANE.get(),    Range.ofUpperBound(1))    .build(consumer);
        createAbility(AMAbilities.CLARITY.get().getId(),                AMAffinities.ARCANE.get(),    Range.ofLowerBound(1))    .build(consumer);
        createAbility(AMAbilities.MAGIC_DAMAGE.get().getId(),           AMAffinities.ARCANE.get(),    Range.ofBounds(0.5, 1))   .build(consumer);
        createAbility(AMAbilities.POISON_RESISTANCE.get().getId(),      AMAffinities.ENDER.get(),     Range.ofBounds(0.5, 1))   .build(consumer);
        createAbility(AMAbilities.NIGHT_VISION.get().getId(),           AMAffinities.ENDER.get(),     Range.ofBounds(0.5, 1))   .build(consumer);
        createAbility(AMAbilities.ENDERMAN_PUMPKIN.get().getId(),       AMAffinities.ENDER.get(),     Range.ofLowerBound(1))    .build(consumer);
        createAbility(AMAbilities.LIGHT_HEALTH_REDUCTION.get().getId(), AMAffinities.ENDER.get(),     Range.ofBounds(0.5, 0.99)).build(consumer);
        createAbility(AMAbilities.WATER_HEALTH_REDUCTION.get().getId(), AMAffinities.ENDER.get(),     Range.ofBounds(0.5, 0.99)).build(consumer);
    }
}
