package at.minecraftschurli.mods.arsmagicalegacy.api.client.particle;

import at.minecraftschurli.mods.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellStat;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/// Represents a particle spawner. A particle spawner is looked up by id in [ArsMagicaClientApi#spawnParticles(ParticleSpawner, Vec3, int, LivingEntity, Entity, HitResult)] and used to spawn particles.
///
/// @param particle    The [ParticleOptions] to use.
/// @param count       The amount of particles to spawn.
/// @param minLifetime The min lifetime of the particles. Particles will be given a random lifetime between minLifetime and maxLifetime.
/// @param maxLifetime The max lifetime of the particles. Particles will be given a random lifetime between minLifetime and maxLifetime.
/// @param minOffset   The min offset of the particles. Particles will be randomly offset between minOffset and maxOffset.
/// @param maxOffset   The max offset of the particles. Particles will be randomly offset between minOffset and maxOffset.
/// @param minSpeed    The min speed of the particles. Particles will be given a random speed between minSpeed and maxSpeed.
/// @param maxSpeed    The max speed of the particles. Particles will be given a random speed between minSpeed and maxSpeed.
/// @param gravity     The gravity of the particles.
/// @param scale       The scale of the particles.
/// @param color       The color of the particles. May be overridden by [SpellStat#COLOR].
/// @param alpha       The alpha of the particles.
/// @param controllers A list of [ParticleController]s to add [ParticleControllerInstance]s of to the particles.
public record ParticleSpawner(
    ParticleOptions particle,
    int count,
    int minLifetime,
    int maxLifetime,
    Vec3 minOffset,
    Vec3 maxOffset,
    Vec3 minSpeed,
    Vec3 maxSpeed,
    float gravity,
    float scale,
    int color,
    float alpha,
    List<ParticleController> controllers
) {
    public static final Codec<ParticleSpawner> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ParticleTypes.CODEC.fieldOf("particle").forGetter(ParticleSpawner::particle),
        Codec.INT.fieldOf("count").forGetter(ParticleSpawner::count),
        Codec.INT.fieldOf("min_lifetime").forGetter(ParticleSpawner::minLifetime),
        Codec.INT.fieldOf("max_lifetime").forGetter(ParticleSpawner::maxLifetime),
        Vec3.CODEC.optionalFieldOf("min_offset", Vec3.ZERO).forGetter(ParticleSpawner::minOffset),
        Vec3.CODEC.optionalFieldOf("max_offset", Vec3.ZERO).forGetter(ParticleSpawner::maxOffset),
        Vec3.CODEC.optionalFieldOf("min_speed", Vec3.ZERO).forGetter(ParticleSpawner::minSpeed),
        Vec3.CODEC.optionalFieldOf("max_speed", Vec3.ZERO).forGetter(ParticleSpawner::maxSpeed),
        Codec.FLOAT.optionalFieldOf("gravity", 0f).forGetter(ParticleSpawner::gravity),
        Codec.FLOAT.optionalFieldOf("scale", 1f).forGetter(ParticleSpawner::scale),
        Codec.INT.optionalFieldOf("color", -1).forGetter(ParticleSpawner::color),
        Codec.FLOAT.optionalFieldOf("alpha", 1f).forGetter(ParticleSpawner::alpha),
        ParticleController.CODEC.listOf().optionalFieldOf("controllers", List.of()).forGetter(ParticleSpawner::controllers)
    ).apply(inst, ParticleSpawner::new));
}
