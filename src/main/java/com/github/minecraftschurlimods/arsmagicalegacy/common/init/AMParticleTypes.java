package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.PARTICLE_TYPES;

public interface AMParticleTypes {
    RegistryObject<SimpleParticleType> ARCANE         = register("arcane");
    RegistryObject<SimpleParticleType> CLOCK          = register("clock");
    RegistryObject<SimpleParticleType> EMBER          = register("ember");
    RegistryObject<SimpleParticleType> EXPLOSION      = register("explosion");
    RegistryObject<SimpleParticleType> GHOST          = register("ghost");
    RegistryObject<SimpleParticleType> LEAF           = register("leaf");
    RegistryObject<SimpleParticleType> LENS_FLARE     = register("lens_flare");
    RegistryObject<SimpleParticleType> LIGHTS         = register("lights");
    RegistryObject<SimpleParticleType> PLANT          = register("plant");
    RegistryObject<SimpleParticleType> PULSE          = register("pulse");
    RegistryObject<SimpleParticleType> ROCK           = register("rock");
    RegistryObject<SimpleParticleType> ROTATING_RINGS = register("rotating_rings");
    RegistryObject<SimpleParticleType> STARDUST       = register("stardust");
    RegistryObject<SimpleParticleType> WATER_BALL     = register("water_ball");
    RegistryObject<SimpleParticleType> WIND           = register("wind");

    private static RegistryObject<SimpleParticleType> register(String name) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(false));
    }

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
