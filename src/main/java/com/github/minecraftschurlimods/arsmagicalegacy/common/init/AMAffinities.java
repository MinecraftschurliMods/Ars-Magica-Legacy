package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.AFFINITIES;

@NonExtendable
public interface AMAffinities {
    Holder<Affinity> NONE      = AFFINITIES.register("none",      () -> Affinity.builder().setColor(0)       .setDirectOpposite(Affinity.NONE)     .setCastSound(AMSounds.CAST_NONE)     .setParticle(AMParticleTypes.NONE_HAND).build());
    Holder<Affinity> WATER     = AFFINITIES.register("water",     () -> Affinity.builder().setColor(0x0b5cef).setDirectOpposite(Affinity.FIRE)     .setCastSound(AMSounds.CAST_WATER)    .setLoopSound(AMSounds.LOOP_WATER)    .addMajorOpposites(Affinity.LIGHTNING, Affinity.EARTH, Affinity.ARCANE, Affinity.ENDER).addMinorOpposites(Affinity.AIR, Affinity.ICE)         .setParticle(AMParticleTypes.WATER_HAND)    .build());
    Holder<Affinity> FIRE      = AFFINITIES.register("fire",      () -> Affinity.builder().setColor(0xef260b).setDirectOpposite(Affinity.WATER)    .setCastSound(AMSounds.CAST_FIRE)     .setLoopSound(AMSounds.LOOP_FIRE)     .addMajorOpposites(Affinity.AIR, Affinity.ICE, Affinity.NATURE, Affinity.LIFE)         .addMinorOpposites(Affinity.EARTH, Affinity.LIGHTNING) .setParticle(AMParticleTypes.FIRE_HAND)     .build());
    Holder<Affinity> EARTH     = AFFINITIES.register("earth",     () -> Affinity.builder().setColor(0x61330b).setDirectOpposite(Affinity.AIR)      .setCastSound(AMSounds.CAST_EARTH)    .setLoopSound(AMSounds.LOOP_EARTH)    .addMajorOpposites(Affinity.WATER, Affinity.ARCANE, Affinity.LIFE, Affinity.LIGHTNING) .addMinorOpposites(Affinity.NATURE, Affinity.FIRE)     .setParticle(AMParticleTypes.EARTH_HAND)    .build());
    Holder<Affinity> AIR       = AFFINITIES.register("air",       () -> Affinity.builder().setColor(0x777777).setDirectOpposite(Affinity.EARTH)    .setCastSound(AMSounds.CAST_AIR)      .setLoopSound(AMSounds.LOOP_AIR)      .addMajorOpposites(Affinity.NATURE, Affinity.FIRE, Affinity.ICE, Affinity.ENDER)       .addMinorOpposites(Affinity.WATER, Affinity.ARCANE)    .setParticle(AMParticleTypes.AIR_HAND)      .build());
    Holder<Affinity> ICE       = AFFINITIES.register("ice",       () -> Affinity.builder().setColor(0xd3e8fc).setDirectOpposite(Affinity.LIGHTNING).setCastSound(AMSounds.CAST_ICE)      .setLoopSound(AMSounds.LOOP_ICE)      .addMajorOpposites(Affinity.LIFE, Affinity.FIRE, Affinity.AIR, Affinity.ARCANE)        .addMinorOpposites(Affinity.WATER, Affinity.ENDER)     .setParticle(AMParticleTypes.ICE_HAND)      .build());
    Holder<Affinity> LIGHTNING = AFFINITIES.register("lightning", () -> Affinity.builder().setColor(0xdece19).setDirectOpposite(Affinity.ICE)      .setCastSound(AMSounds.CAST_LIGHTNING).setLoopSound(AMSounds.LOOP_LIGHTNING).addMajorOpposites(Affinity.WATER, Affinity.ENDER, Affinity.NATURE, Affinity.EARTH)    .addMinorOpposites(Affinity.LIFE, Affinity.FIRE)       .setParticle(AMParticleTypes.LIGHTNING_HAND).build());
    Holder<Affinity> NATURE    = AFFINITIES.register("nature",    () -> Affinity.builder().setColor(0x228718).setDirectOpposite(Affinity.ARCANE)   .setCastSound(AMSounds.CAST_NATURE)   .setLoopSound(AMSounds.LOOP_NATURE)   .addMajorOpposites(Affinity.AIR, Affinity.ENDER, Affinity.LIGHTNING, Affinity.FIRE)    .addMinorOpposites(Affinity.LIFE, Affinity.EARTH)      .setParticle(AMParticleTypes.NATURE_HAND)   .build());
    Holder<Affinity> LIFE      = AFFINITIES.register("life",      () -> Affinity.builder().setColor(0x34e122).setDirectOpposite(Affinity.ENDER)    .setCastSound(AMSounds.CAST_LIFE)     .setLoopSound(AMSounds.LOOP_LIFE)     .addMajorOpposites(Affinity.ARCANE, Affinity.ICE, Affinity.FIRE, Affinity.EARTH)       .addMinorOpposites(Affinity.NATURE, Affinity.LIGHTNING).setParticle(AMParticleTypes.LIFE_HAND)     .build());
    Holder<Affinity> ARCANE    = AFFINITIES.register("arcane",    () -> Affinity.builder().setColor(0xb935cd).setDirectOpposite(Affinity.NATURE)   .setCastSound(AMSounds.CAST_ARCANE)   .setLoopSound(AMSounds.LOOP_ARCANE)   .addMajorOpposites(Affinity.LIFE, Affinity.EARTH, Affinity.WATER, Affinity.ICE)        .addMinorOpposites(Affinity.AIR, Affinity.ENDER)       .setParticle(AMParticleTypes.ARCANE_HAND)   .build());
    Holder<Affinity> ENDER     = AFFINITIES.register("ender",     () -> Affinity.builder().setColor(0x3f043d).setDirectOpposite(Affinity.LIFE)     .setCastSound(AMSounds.CAST_ENDER)    .setLoopSound(AMSounds.LOOP_ENDER)    .addMajorOpposites(Affinity.NATURE, Affinity.LIGHTNING, Affinity.WATER, Affinity.AIR)  .addMinorOpposites(Affinity.ARCANE, Affinity.ICE)      .setParticle(AMParticleTypes.ENDER_HAND)    .build());

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
