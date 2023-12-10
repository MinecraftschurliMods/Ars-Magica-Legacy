package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.util.NumberedRegistryObject;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.PARTICLE_TYPES;

public interface AMParticleTypes {
    RegistryObject<SimpleParticleType> NONE_HAND      = register("none_hand");
    RegistryObject<SimpleParticleType> WATER_HAND     = register("water_hand");
    RegistryObject<SimpleParticleType> FIRE_HAND      = register("fire_hand");
    RegistryObject<SimpleParticleType> EARTH_HAND     = register("earth_hand");
    RegistryObject<SimpleParticleType> AIR_HAND       = register("air_hand");
    RegistryObject<SimpleParticleType> ICE_HAND       = register("ice_hand");
    RegistryObject<SimpleParticleType> LIGHTNING_HAND = register("lightning_hand");
    RegistryObject<SimpleParticleType> NATURE_HAND    = register("nature_hand");
    RegistryObject<SimpleParticleType> LIFE_HAND      = register("life_hand");
    RegistryObject<SimpleParticleType> ARCANE_HAND    = register("arcane_hand");
    RegistryObject<SimpleParticleType> ENDER_HAND     = register("ender_hand");

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

    NumberedRegistryObject<ParticleType<?>, SimpleParticleType> SYMBOLS = new NumberedRegistryObject<>(PARTICLE_TYPES, 28, "symbols", i -> new SimpleParticleType(false));

    private static RegistryObject<SimpleParticleType> register(String name) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(false));
    }

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
