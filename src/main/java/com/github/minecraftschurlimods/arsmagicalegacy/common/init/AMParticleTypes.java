package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.PARTICLE_TYPES;

public interface AMParticleTypes {
    RegistryObject<SimpleParticleType> ARCANE         = PARTICLE_TYPES.register("arcane",         () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> CLOCK          = PARTICLE_TYPES.register("clock",          () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> EMBER          = PARTICLE_TYPES.register("ember",          () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> EXPLOSION      = PARTICLE_TYPES.register("explosion",      () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> GHOST          = PARTICLE_TYPES.register("ghost",          () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> HEART          = PARTICLE_TYPES.register("heart",          () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> LEAF           = PARTICLE_TYPES.register("leaf",           () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> LENS_FLARE     = PARTICLE_TYPES.register("lens_flare",     () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> LIGHTS         = PARTICLE_TYPES.register("lights",         () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> PLANT          = PARTICLE_TYPES.register("plant",          () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> PULSE          = PARTICLE_TYPES.register("pulse",          () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> ROCK           = PARTICLE_TYPES.register("rock",           () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> ROTATING_RINGS = PARTICLE_TYPES.register("rotating_rings", () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> SMOKE          = PARTICLE_TYPES.register("smoke",          () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> SPARKLE        = PARTICLE_TYPES.register("sparkle",        () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> STARDUST       = PARTICLE_TYPES.register("stardust",       () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> WATER_BALL     = PARTICLE_TYPES.register("water_ball",     () -> new SimpleParticleType(false));
    RegistryObject<SimpleParticleType> WIND           = PARTICLE_TYPES.register("wind",           () -> new SimpleParticleType(false));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
