package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMParticleTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.AMParticle;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.ApproachEntityController;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.ApproachPointController;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.FadeOutController;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.FloatUpwardController;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.LeaveTrailController;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.MoveInDirectionController;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.OrbitEntityController;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.OrbitPointController;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Helper class that holds and registered all the various spell particle spawners.
 */
public final class SpellParticleSpawners {
    /**
     * Should be called during client setup.
     */
    @Internal
    static void init() {
        var helper = ArsMagicaAPI.get().getSpellHelper();
        helper.registerParticleSpawner(AMSpellParts.DROWNING_DAMAGE.get(),     SpellParticleSpawners::drowningDamage);
        helper.registerParticleSpawner(AMSpellParts.FIRE_DAMAGE.get(),         SpellParticleSpawners::fireDamage);
        helper.registerParticleSpawner(AMSpellParts.FROST_DAMAGE.get(),        SpellParticleSpawners::frostDamage);
        helper.registerParticleSpawner(AMSpellParts.LIGHTNING_DAMAGE.get(),    SpellParticleSpawners::lightningDamage);
        helper.registerParticleSpawner(AMSpellParts.MAGIC_DAMAGE.get(),        SpellParticleSpawners::magicDamage);
        helper.registerParticleSpawner(AMSpellParts.PHYSICAL_DAMAGE.get(),     SpellParticleSpawners::physicalDamage);
        helper.registerParticleSpawner(AMSpellParts.ABSORPTION.get(),          SpellParticleSpawners::absorption);
        helper.registerParticleSpawner(AMSpellParts.BLINDNESS.get(),           SpellParticleSpawners::blindness);
        helper.registerParticleSpawner(AMSpellParts.HASTE.get(),               SpellParticleSpawners::haste);
        helper.registerParticleSpawner(AMSpellParts.INVISIBILITY.get(),        SpellParticleSpawners::invisibility);
        helper.registerParticleSpawner(AMSpellParts.JUMP_BOOST.get(),          SpellParticleSpawners::jumpBoost);
        helper.registerParticleSpawner(AMSpellParts.LEVITATION.get(),          SpellParticleSpawners::levitation);
        helper.registerParticleSpawner(AMSpellParts.NIGHT_VISION.get(),        SpellParticleSpawners::nightVision);
        helper.registerParticleSpawner(AMSpellParts.REGENERATION.get(),        SpellParticleSpawners::regeneration);
        helper.registerParticleSpawner(AMSpellParts.SLOWNESS.get(),            SpellParticleSpawners::slowness);
        helper.registerParticleSpawner(AMSpellParts.SLOW_FALLING.get(),        SpellParticleSpawners::slowFalling);
        helper.registerParticleSpawner(AMSpellParts.WATER_BREATHING.get(),     SpellParticleSpawners::waterBreathing);
        helper.registerParticleSpawner(AMSpellParts.AGILITY.get(),             SpellParticleSpawners::agility);
        helper.registerParticleSpawner(AMSpellParts.ASTRAL_DISTORTION.get(),   SpellParticleSpawners::astralDistortion);
        helper.registerParticleSpawner(AMSpellParts.ENTANGLE.get(),            SpellParticleSpawners::entangle);
        helper.registerParticleSpawner(AMSpellParts.FLIGHT.get(),              SpellParticleSpawners::flight);
        helper.registerParticleSpawner(AMSpellParts.FROST.get(),               SpellParticleSpawners::frost);
        helper.registerParticleSpawner(AMSpellParts.FURY.get(),                SpellParticleSpawners::fury);
        helper.registerParticleSpawner(AMSpellParts.GRAVITY_WELL.get(),        SpellParticleSpawners::gravityWell);
        helper.registerParticleSpawner(AMSpellParts.REFLECT.get(),             SpellParticleSpawners::reflect);
        helper.registerParticleSpawner(AMSpellParts.SHIELD.get(),              SpellParticleSpawners::shield);
        helper.registerParticleSpawner(AMSpellParts.SWIFT_SWIM.get(),          SpellParticleSpawners::swiftSwim);
        helper.registerParticleSpawner(AMSpellParts.TEMPORAL_ANCHOR.get(),     SpellParticleSpawners::temporalAnchor);
        helper.registerParticleSpawner(AMSpellParts.TRUE_SIGHT.get(),          SpellParticleSpawners::trueSight);
        helper.registerParticleSpawner(AMSpellParts.WATERY_GRAVE.get(),        SpellParticleSpawners::wateryGrave);
        helper.registerParticleSpawner(AMSpellParts.ATTRACT.get(),             SpellParticleSpawners::attract);
        helper.registerParticleSpawner(AMSpellParts.BANISH_RAIN.get(),         SpellParticleSpawners::banishRain);
        helper.registerParticleSpawner(AMSpellParts.BLINK.get(),               SpellParticleSpawners::blink);
        helper.registerParticleSpawner(AMSpellParts.CHARM.get(),               SpellParticleSpawners::charm);
        helper.registerParticleSpawner(AMSpellParts.CREATE_WATER.get(),        SpellParticleSpawners::createWater);
        helper.registerParticleSpawner(AMSpellParts.DISARM.get(),              SpellParticleSpawners::disarm);
        helper.registerParticleSpawner(AMSpellParts.DISPEL.get(),              SpellParticleSpawners::dispel);
        helper.registerParticleSpawner(AMSpellParts.DIVINE_INTERVENTION.get(), SpellParticleSpawners::divineIntervention);
        helper.registerParticleSpawner(AMSpellParts.DROUGHT.get(),             SpellParticleSpawners::drought);
    }

    private static void drowningDamage(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        damage(ParticleTypes.BUBBLE, hit, random, color, 25);
    }

    private static void fireDamage(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        damage(AMParticleTypes.EXPLOSION.get(), hit, random, color, 5);
    }

    private static void frostDamage(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        damage(ParticleTypes.SNOWFLAKE, hit, random, color, 25);
    }

    private static void lightningDamage(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        damage(ParticleTypes.ELECTRIC_SPARK, hit, random, color, 5);
    }

    private static void magicDamage(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        damage(AMParticleTypes.ARCANE.get(), hit, random, color, 5);
    }

    private static void physicalDamage(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        damage(AMParticleTypes.EMBER.get(), hit, random, color == -1 ? 0xcc3333 : color, 5);
    }

    private static void absorption(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.STARDUST, color == -1 ? 0x007fff : color, 20);
            particle.setY(particle.getY() - 1);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.5).setDistance(0.3 + random.nextDouble() * 0.3));
            particle.addRandomOffset(1, 1, 1);
        }
    }

    private static void blindness(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 15; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.LENS_FLARE, color == -1 ? 0x000000 : color, 25 + random.nextInt(10));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1).setDistance(random.nextDouble() + 0.5));
            particle.addRandomOffset(1, 1, 1);
        }
    }

    private static void haste(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.LIGHTS, color, 20);
            particle.setY(particle.getY() - 1);
            particle.scale(0.1f);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitPointController(particle, hit.getLocation(), 0.1).setDistance(0.3 + random.nextDouble() * 0.3));
            particle.addController(new FadeOutController(particle, 0.05f).killsParticleOnFinish());
            particle.addRandomOffset(1, 1, 1);
        }
    }

    private static void invisibility(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.EMBER, color, 20);
            particle.setY(particle.getY() - 1);
            particle.scale(0.1f);
            particle.addRandomOffset(1, 1, 1);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitPointController(particle, hit.getLocation(), 0.5).setDistance(0.3 + random.nextDouble() * 0.3));
            particle.addController(new FadeOutController(particle, 0.05f).killsParticleOnFinish());
        }
    }

    private static void jumpBoost(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        for (int i = 0; i < 15; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.WIND, color, 15);
            particle.setY(particle.getY() - 1);
            particle.setSpeed(random.nextDouble() * 0.1 - 0.05, random.nextDouble() * 0.2, random.nextDouble() * 0.1 - 0.05);
            particle.setGravity(1);
            particle.scale(0.1f);
            particle.addRandomOffset(1, 0.5, 1);
        }
    }

    private static void levitation(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 15; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.EMBER, color == -1 ? 0x333399 : color, 40);
            particle.scale(0.1f);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1 + random.nextDouble() * 0.1).setDistance(0.4 + random.nextDouble() * 0.4));
            particle.addRandomOffset(1, 0.5, 1);
        }
    }

    private static void nightVision(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 8; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.LIGHTS, color == -1 ? 0x337f33 : color, 30);
            particle.scale(0.1f);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1 + random.nextDouble() * 0.1).setDistance(0.4 + random.nextDouble() * 0.4));
            particle.addRandomOffset(1, 0.5, 1);
        }
    }

    private static void regeneration(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.STARDUST, color == -1 ? 0x19ffcc : color, 20);
            particle.setY(particle.getY() - 1);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.5).setDistance(0.3 + random.nextDouble() * 0.3));
            particle.addRandomOffset(1, 1, 1);
        }
    }

    private static void slowness(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.STARDUST, color, 20);
            particle.setY(particle.getY() + 1);
            particle.addController(new FloatUpwardController(particle, 0, -0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.2).setDistance(0.3 + random.nextDouble() * 0.3));
            particle.addRandomOffset(1, 2, 1);
        }
    }

    private static void slowFalling(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.WIND, color, 20);
            particle.setY(particle.getY() - 1);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.5).setDistance(0.3 + random.nextDouble() * 0.3));
            particle.addRandomOffset(1, 1, 1);
        }
    }

    private static void waterBreathing(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.WATER_BALL, color, 20);
            particle.setY(particle.getY() - 1);
            particle.setAlpha(0.2f);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.5).setDistance(0.3 + random.nextDouble() * 0.3));
            particle.addRandomOffset(1, 1, 1);
        }
    }

    private static void agility(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 15; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.STARDUST, color, 25 + random.nextInt(10));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1).setDistance(random.nextDouble() + 0.5));
        }
    }

    private static void astralDistortion(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        for (int i = 0; i < 10; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.PULSE, color == -1 ? 0xb233e5 : color, 25 + random.nextInt(10));
            particle.addRandomOffset(5, 4, 5);
            particle.addController(new FloatUpwardController(particle, 0.2, 0));
        }
    }

    private static void entangle(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.PLANT, color, 20);
            particle.scale(0.1f);
            particle.addRandomOffset(1, 2, 1);
            particle.addController(new ApproachEntityController(particle, ehr.getEntity(), 0.15, 0.4));
        }
    }

    private static void flight(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 15; i++) {
            AMParticle particle = particle(hit, caster.getLevel().random.nextBoolean() ? AMParticleTypes.WIND : AMParticleTypes.EMBER, color, 20);
            particle.scale(0.1f);
            particle.addRandomOffset(1, 0.5, 1);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.2 + random.nextDouble() * 0.2));
        }
    }

    private static void frost(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        for (int i = 0; i < 5; i++) {
            AMParticle particle = particle(hit, ParticleTypes.SNOWFLAKE, color, 10);
            particle.setSpeed(random.nextDouble() * 0.2 - 0.1, 0.3, random.nextDouble() * 0.2 - 0.1);
            particle.scale(0.1f);
            particle.setGravity(1);
            particle.addRandomOffset(1, 0.5, 1);
        }
    }

    private static void fury(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 10; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.PULSE, color == -1 ? 0xff0000 : color, 10);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.15).setDistance(random.nextDouble() + 1));
            particle.addRandomOffset(1, 1, 1);
        }
    }

    private static void gravityWell(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.PULSE, color, 20);
            particle.scale(0.01f);
            particle.addController(new LeaveTrailController(particle, AMParticleTypes.PULSE.get()).setColor(color == -1 ? 0xb233e5 : color).setLifetime(5).addController(p -> new FloatUpwardController(p, 0, -0.3)));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.2));
            particle.addController(new FadeOutController(particle, 0.05f).killsParticleOnFinish());
            particle.addRandomOffset(1, 2, 1);
        }
    }

    private static void reflect(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.LENS_FLARE, color, 20);
            particle.addRandomOffset(1, 2, 1);
            if (color == -1) {
                particle.setColor(0.5f + random.nextFloat() * 0.5f, 0.1f, 0.5f + random.nextFloat() * 0.5f);
            }
        }
    }

    private static void shield(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.LIGHTS, color, 10); // TODO symbols particle type
            particle.setY(particle.getY() - 1);
            particle.scale(0.1f);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.2).setDistance(1));
        }
    }

    private static void swiftSwim(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        Entity target = ehr.getEntity();
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.WATER_BALL, color == -1 ? 0xff0000 : color, 20);
            particle.scale(0.1f);
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new MoveInDirectionController(particle, (target instanceof LivingEntity living ? living.getYHeadRot() : target.getYRot()) + 90, target.getXRot(), 0.1 + random.nextDouble() * 0.5));
            particle.addRandomOffset(1, 2, 1);
        }
    }

    private static void temporalAnchor(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.CLOCK, color, 40);
            particle.scale(0.1f);
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1 + random.nextDouble() * 0.1));
            particle.addRandomOffset(1, 2, 1);
        }
    }

    private static void trueSight(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.STARDUST, color, 40);
            particle.setY(particle.getY() - 1);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1).setDistance(1));
            particle.addRandomOffset(1, 1, 1);
            if (color == -1 && random.nextBoolean()) {
                particle.setColor(0xb219b2);
            }
        }
    }

    private static void wateryGrave(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.WATER_BALL, color, 20);
            particle.scale(0.01f);
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new LeaveTrailController(particle, AMParticleTypes.WATER_BALL.get()).setColor(color == -1 ? 0xffffff : color).setLifetime(5).addController(p -> new FloatUpwardController(p, 0, -0.3)));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.2));
            particle.addRandomOffset(1, 2, 1);
        }
    }

    private static void attract(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        Vec3 location = hit.getLocation();
        AMParticle particle = particle(hit, AMParticleTypes.ARCANE, color == -1 ? 0xcc4cb2 : color);
        particle.addController(new ApproachPointController(particle, location.x, location.y, location.z, 0.025f, 0.025f));
        particle.addRandomOffset(1, 1, 1);
    }

    private static void banishRain(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.WATER_BALL, color, 25 + random.nextInt(10));
            particle.addController(new FloatUpwardController(particle, 0, 0.5));
            particle.addRandomOffset(5, 4, 5);
        }
    }

    private static void blink(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        Entity target = ehr.getEntity();
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.STARDUST, color, 20);
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new MoveInDirectionController(particle, (target instanceof LivingEntity living ? living.getYHeadRot() : target.getYRot()) + 90, target.getXRot(), 0.1 + random.nextDouble() * 0.5));
            particle.addRandomOffset(1, 2, 1);
        }
    }

    private static void charm(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        for (int i = 0; i < 10; i++) {
            AMParticle particle = particle(hit, ParticleTypes.HEART, color, 20);
            particle.addController(new FloatUpwardController(particle, 0, 0.1 + random.nextDouble() * 0.05));
            particle.addRandomOffset(1, 2, 1);
        }
    }

    private static void createWater(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        for (int i = 0; i < 15; i++) {
            AMParticle particle = particle(hit, ParticleTypes.SPLASH, color);
            particle.setSpeed(random.nextDouble() - 0.5, random.nextDouble() - 0.5, random.nextDouble() - 0.5);
            particle.addRandomOffset(1, 1, 1);
        }
    }

    private static void disarm(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        Entity target = ehr.getEntity();
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, ParticleTypes.ELECTRIC_SPARK, color, 40);
            particle.setGravity(1);
            particle.scale(0.1f);
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new MoveInDirectionController(particle, (target instanceof LivingEntity living ? living.getYHeadRot() : target.getYRot()) + 90, target.getXRot(), 0.1 + random.nextDouble() * 0.5));
            particle.addRandomOffset(1, 2, 1);
            if (color != -1) {
                particle.setColor(random.nextBoolean() ? 0xb2b219 : 0x19b219);
            }
        }
    }

    private static void dispel(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, ParticleTypes.ELECTRIC_SPARK, color, 20);
            particle.scale(0.1f);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1 + random.nextDouble() * 0.1));
            particle.addRandomOffset(1, 2, 1);
            if (color != -1 && random.nextBoolean()) {
                particle.setColor(0xb219b2);
            }
        }
    }

    private static void divineIntervention(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 100; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.ARCANE, color, 25 + random.nextInt(10));
            particle.setY(particle.getY() - 1);
            particle.addRandomOffset(1, 1, 1);
            if (random.nextBoolean()) {
                particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1).setDistance(0.5 + random.nextDouble()));
            } else {
                particle.addController(new OrbitPointController(particle, ehr.getLocation(), 0.1).setDistance(0.5 + random.nextDouble()));
            }
        }
    }

    private static void drought(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.EMBER, color == -1 ? 0xe5cc7f : color, 40);
            particle.setY(particle.getY() + 1);
            particle.setGravity(1);
            particle.scale(0.1f);
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addRandomOffset(1, 0, 1);
        }
    }

    //region helpers
    private static void damage(ParticleOptions options, HitResult hit, RandomSource random, int color, int amount) {
        for (int i = 0; i < amount; i++) {
            AMParticle particle = particle(hit, options, color, 5);
            particle.setSpeed(random.nextDouble() * 0.2 - 0.1, random.nextDouble() * 0.2, random.nextDouble() * 0.2 - 0.1);
            particle.setGravity(1);
            particle.scale(0.1f);
            particle.addRandomOffset(1, 0.5, 1);
        }
    }

    private static AMParticle particle(HitResult hit, Supplier<? extends ParticleOptions> options, int color) {
        return particle(hit, options, color, -1);
    }

    private static AMParticle particle(HitResult hit, ParticleOptions options, int color) {
        return particle(hit, options, color, -1);
    }

    private static AMParticle particle(HitResult hit, Supplier<? extends ParticleOptions> options, int color, @Nullable Integer lifetime) {
        return particle(hit, options.get(), color, lifetime);
    }

    private static AMParticle particle(HitResult hit, ParticleOptions options, int color, @Nullable Integer lifetime) {
        Vec3 vec = hit.getLocation();
        double x = vec.x, y = vec.y, z = vec.z;
        if (hit instanceof BlockHitResult) {
            x += 0.5;
            z += 0.5;
        } else if (hit instanceof EntityHitResult e) {
            y += e.getEntity().getEyeHeight();
        }
        AMParticle particle = new AMParticle((ClientLevel) Objects.requireNonNull(ClientHelper.getLocalLevel()), x, y, z, options);
        particle.setNoGravity();
        particle.scale(0.2f);
        if (color != -1) {
            particle.setColor(color);
        }
        if (lifetime != null) {
            particle.setLifetime(lifetime);
        }
        return particle;
    }
    //endregion
}
