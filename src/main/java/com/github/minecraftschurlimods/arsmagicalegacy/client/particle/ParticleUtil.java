package com.github.minecraftschurlimods.arsmagicalegacy.client.particle;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.ArsMagicaClientApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ControlledParticle;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle.ParticleSpawner;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FallingStar;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ManaVortex;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.SpellEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.SpellShapeEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Whirlwind;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public final class ParticleUtil {
    public static final Identifier ARCANE_COMPENDIUM_CONVERSION = ArsMagicaApi.id("arcane_compendium_conversion");
    public static final Identifier ARCANE_COMPENDIUM_CONVERSION_FINISH = ArsMagicaApi.id("arcane_compendium_conversion_finish");
    private static final Map<SpellEntityKey, ParticleSpawner> SPELL_SHAPE_ENTITY_PARTICLE_SPAWNERS = new HashMap<>();

    private ParticleUtil() {
    }

    public static List<? extends ControlledParticle> spawnParticles(Identifier id, Vec3 position, int color, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        ParticleSpawner spawner = ParticleSpawnerManager.INSTANCE.get(id);
        return spawner != null ? ArsMagicaClientApi.spawnParticles(spawner, position, color, caster, directEntity, hitResult) : List.of();
    }

    public static void spawnArcaneCompendiumConversionParticles(List<BlockPos> from, Vec3 to) {
        for (BlockPos pos : from) {
            spawnParticles(ARCANE_COMPENDIUM_CONVERSION, Vec3.atCenterOf(pos), -1, null, null, null).forEach(particle -> {
                Vec3 distance = to.subtract(particle.getPos());
                Vec3 vec = distance.normalize().scale((distance.length() + 1) / particle.getLifetime());
                particle.setParticleSpeed(vec.x, vec.y, vec.z);
            });
        }
    }

    public static void spawnArcaneCompendiumConversionFinishParticles(Vec3 position) {
        spawnParticles(ARCANE_COMPENDIUM_CONVERSION_FINISH, position, -1, null, null, null);
    }

    public static void spawnFallingStarParticles(FallingStar entity, boolean ground) {
        Vec3 position = entity.position();
        int color = entity.getColor();
        LivingEntity owner = entity.getOwner();
        List<? extends ControlledParticle> list;
        if (ground) {
            list = IntStream.range(0, (int) entity.getDamage())
                .mapToObj(_ -> spawnParticles(FallingStar.GROUND_PARTICLES, position, color, owner, entity, null))
                .flatMap(List::stream)
                .toList();
            int lifetime = (int) entity.getRange();
            for (int i = 0; i < list.size(); i++) {
                ControlledParticle particle = list.get(i);
                particle.setLifetime(lifetime);
                Vec3 speed = Vec3.directionFromRotation(0, (float) i / list.size() * 360).normalize();
                particle.setParticleSpeed(speed.x(), 0, speed.z());
            }
        } else {
            list = spawnParticles(FallingStar.FALL_PARTICLES, position, color, owner, entity, null);
        }
        if (color == -1) {
            list.forEach(particle -> particle.setColor(particle.random().nextInt(0xffffff)));
        }
    }

    public static void spawnManaVortexParticles(ManaVortex entity) {
        int duration = entity.getDuration() - entity.tickCount;
        if (duration > 30) {
            spawnParticles(ManaVortex.PARTICLES, entity.position(), -1, null, entity, null);
        } else if (duration <= 5) {
            spawnParticles(ManaVortex.PARTICLES_DEATH, entity.position(), -1, null, entity, null);
        }
    }

    public static void spawnWhirlwindParticles(Whirlwind entity) {
        spawnParticles(Whirlwind.PARTICLES, entity.position(), -1, null, entity, null);
    }

    public static void spawnSpellEntityParticles(SpellEntity entity, double range, double verticalRange, int color, @Nullable LivingEntity caster) {
        spawnParticles(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()), entity.getEyePosition(), color, caster, entity, null).forEach(particle -> {
            RandomSource random = particle.random();
            particle.setParticleSpeed(Mth.lerp(random.nextDouble(), -range, range), random.nextDouble() * verticalRange, Mth.lerp(random.nextDouble(), -range, range));
        });
    }

    @SuppressWarnings("DataFlowIssue")
    public static void spawnSpellEntityParticles(SpellShapeEntity entity, Spell spell, Vec3 position, int color, @Nullable LivingEntity caster) {
        SpellEntityKey key = new SpellEntityKey(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()), spell.grammar().primaryAffinity(AMClientUtil.level().registryAccess()));
        SPELL_SHAPE_ENTITY_PARTICLE_SPAWNERS.computeIfAbsent(key, _ -> {
            ParticleSpawner spawner = ParticleSpawnerManager.INSTANCE.get(key.id);
            return new ParticleSpawner(AMRegistries.affinities(true).getOrThrow(key.affinity).value().particle(),
                spawner.count(),
                spawner.minLifetime(),
                spawner.maxLifetime(),
                spawner.minOffset(),
                spawner.maxOffset(),
                spawner.minSpeed(),
                spawner.maxSpeed(),
                spawner.gravity(),
                spawner.scale(),
                spawner.color(),
                spawner.alpha(),
                spawner.controllers());
        });
        ArsMagicaClientApi.spawnParticles(SPELL_SHAPE_ENTITY_PARTICLE_SPAWNERS.get(key), position, color, caster, entity, null);
    }

    public static void clearParticleSpawnerCache() {
        SPELL_SHAPE_ENTITY_PARTICLE_SPAWNERS.clear();
    }

    private record SpellEntityKey(Identifier id, ResourceKey<Affinity> affinity) {
    }
}
