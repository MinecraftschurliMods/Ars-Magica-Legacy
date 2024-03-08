package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.util.NumberedDeferredHolder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.PARTICLE_TYPES;

public interface AMParticleTypes {
    Supplier<SimpleParticleType> NONE_HAND      = register("none_hand");
    Supplier<SimpleParticleType> WATER_HAND     = register("water_hand");
    Supplier<SimpleParticleType> FIRE_HAND      = register("fire_hand");
    Supplier<SimpleParticleType> EARTH_HAND     = register("earth_hand");
    Supplier<SimpleParticleType> AIR_HAND       = register("air_hand");
    Supplier<SimpleParticleType> ICE_HAND       = register("ice_hand");
    Supplier<SimpleParticleType> LIGHTNING_HAND = register("lightning_hand");
    Supplier<SimpleParticleType> NATURE_HAND    = register("nature_hand");
    Supplier<SimpleParticleType> LIFE_HAND      = register("life_hand");
    Supplier<SimpleParticleType> ARCANE_HAND    = register("arcane_hand");
    Supplier<SimpleParticleType> ENDER_HAND     = register("ender_hand");

    Supplier<SimpleParticleType> ARCANE         = register("arcane");
    Supplier<SimpleParticleType> CLOCK          = register("clock");
    Supplier<SimpleParticleType> EMBER          = register("ember");
    Supplier<SimpleParticleType> EXPLOSION      = register("explosion");
    Supplier<SimpleParticleType> GHOST          = register("ghost");
    Supplier<SimpleParticleType> LEAF           = register("leaf");
    Supplier<SimpleParticleType> LENS_FLARE     = register("lens_flare");
    Supplier<SimpleParticleType> LIGHTS         = register("lights");
    Supplier<SimpleParticleType> PLANT          = register("plant");
    Supplier<SimpleParticleType> PULSE          = register("pulse");
    Supplier<SimpleParticleType> ROCK           = register("rock");
    Supplier<SimpleParticleType> ROTATING_RINGS = register("rotating_rings");
    Supplier<SimpleParticleType> STARDUST       = register("stardust");
    Supplier<SimpleParticleType> WATER_BALL     = register("water_ball");
    Supplier<SimpleParticleType> WIND           = register("wind");

    NumberedDeferredHolder<ParticleType<?>, SimpleParticleType> SYMBOLS = new NumberedDeferredHolder<>(PARTICLE_TYPES, 28, "symbols", i -> new SimpleParticleType(false));

    private static Supplier<SimpleParticleType> register(String name) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(false));
    }

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
