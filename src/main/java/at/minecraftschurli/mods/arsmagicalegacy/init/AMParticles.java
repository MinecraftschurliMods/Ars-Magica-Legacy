package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMParticles {
    DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE, ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<ParticleType<?>, SimpleParticleType> NONE_HAND      = register("none_hand");
    DeferredHolder<ParticleType<?>, SimpleParticleType> WATER_HAND     = register("water_hand");
    DeferredHolder<ParticleType<?>, SimpleParticleType> FIRE_HAND      = register("fire_hand");
    DeferredHolder<ParticleType<?>, SimpleParticleType> EARTH_HAND     = register("earth_hand");
    DeferredHolder<ParticleType<?>, SimpleParticleType> AIR_HAND       = register("air_hand");
    DeferredHolder<ParticleType<?>, SimpleParticleType> ICE_HAND       = register("ice_hand");
    DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHTNING_HAND = register("lightning_hand");
    DeferredHolder<ParticleType<?>, SimpleParticleType> NATURE_HAND    = register("nature_hand");
    DeferredHolder<ParticleType<?>, SimpleParticleType> LIFE_HAND      = register("life_hand");
    DeferredHolder<ParticleType<?>, SimpleParticleType> ARCANE_HAND    = register("arcane_hand");
    DeferredHolder<ParticleType<?>, SimpleParticleType> ENDER_HAND     = register("ender_hand");
    DeferredHolder<ParticleType<?>, SimpleParticleType> ARCANE         = register("arcane");
    DeferredHolder<ParticleType<?>, SimpleParticleType> CLOCK          = register("clock");
    DeferredHolder<ParticleType<?>, SimpleParticleType> EMBER          = register("ember");
    DeferredHolder<ParticleType<?>, SimpleParticleType> EXPLOSION      = register("explosion");
    DeferredHolder<ParticleType<?>, SimpleParticleType> GHOST          = register("ghost");
    DeferredHolder<ParticleType<?>, SimpleParticleType> LEAF           = register("leaf");
    DeferredHolder<ParticleType<?>, SimpleParticleType> LENS_FLARE     = register("lens_flare");
    DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHTS         = register("lights");
    DeferredHolder<ParticleType<?>, SimpleParticleType> PLANT          = register("plant");
    DeferredHolder<ParticleType<?>, SimpleParticleType> PULSE          = register("pulse");
    DeferredHolder<ParticleType<?>, SimpleParticleType> ROCK           = register("rock");
    DeferredHolder<ParticleType<?>, SimpleParticleType> ROTATING_RINGS = register("rotating_rings");
    DeferredHolder<ParticleType<?>, SimpleParticleType> STARDUST       = register("stardust");
    DeferredHolder<ParticleType<?>, SimpleParticleType> WATER_BALL     = register("water_ball");
    DeferredHolder<ParticleType<?>, SimpleParticleType> WIND           = register("wind");
    DeferredHolder<ParticleType<?>, SimpleParticleType> SYMBOLS        = register("symbols");
    // @formatter:on

    private static DeferredHolder<ParticleType<?>, SimpleParticleType> register(String name) {
        return PARTICLES.register(name, () -> new SimpleParticleType(false));
    }
}
