package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMParticleTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.AMParticle;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.ApproachEntityController;
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
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.Objects;

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
        helper.registerParticleSpawner(AMSpellParts.DROWNING_DAMAGE.get(),   SpellParticleSpawners::drowningDamage);
        helper.registerParticleSpawner(AMSpellParts.FIRE_DAMAGE.get(),       SpellParticleSpawners::fireDamage);
        helper.registerParticleSpawner(AMSpellParts.FROST_DAMAGE.get(),      SpellParticleSpawners::frostDamage);
        helper.registerParticleSpawner(AMSpellParts.LIGHTNING_DAMAGE.get(),  SpellParticleSpawners::lightningDamage);
        helper.registerParticleSpawner(AMSpellParts.MAGIC_DAMAGE.get(),      SpellParticleSpawners::magicDamage);
        helper.registerParticleSpawner(AMSpellParts.PHYSICAL_DAMAGE.get(),   SpellParticleSpawners::physicalDamage);
        helper.registerParticleSpawner(AMSpellParts.ABSORPTION.get(),        SpellParticleSpawners::absorption);
        helper.registerParticleSpawner(AMSpellParts.BLINDNESS.get(),         SpellParticleSpawners::blindness);
        helper.registerParticleSpawner(AMSpellParts.HASTE.get(),             SpellParticleSpawners::haste);
        helper.registerParticleSpawner(AMSpellParts.INVISIBILITY.get(),      SpellParticleSpawners::invisibility);
        helper.registerParticleSpawner(AMSpellParts.JUMP_BOOST.get(),        SpellParticleSpawners::jumpBoost);
        helper.registerParticleSpawner(AMSpellParts.LEVITATION.get(),        SpellParticleSpawners::levitation);
        helper.registerParticleSpawner(AMSpellParts.NIGHT_VISION.get(),      SpellParticleSpawners::nightVision);
        helper.registerParticleSpawner(AMSpellParts.REGENERATION.get(),      SpellParticleSpawners::regeneration);
        helper.registerParticleSpawner(AMSpellParts.SLOWNESS.get(),          SpellParticleSpawners::slowness);
        helper.registerParticleSpawner(AMSpellParts.SLOW_FALLING.get(),      SpellParticleSpawners::slowFalling);
        helper.registerParticleSpawner(AMSpellParts.WATER_BREATHING.get(),   SpellParticleSpawners::waterBreathing);
        helper.registerParticleSpawner(AMSpellParts.AGILITY.get(),           SpellParticleSpawners::agility);
        helper.registerParticleSpawner(AMSpellParts.ASTRAL_DISTORTION.get(), SpellParticleSpawners::astralDistortion);
        helper.registerParticleSpawner(AMSpellParts.ENTANGLE.get(),          SpellParticleSpawners::entangle);
        helper.registerParticleSpawner(AMSpellParts.FLIGHT.get(),            SpellParticleSpawners::flight);
        helper.registerParticleSpawner(AMSpellParts.FROST.get(),             SpellParticleSpawners::frost);
        helper.registerParticleSpawner(AMSpellParts.FURY.get(),              SpellParticleSpawners::fury);
        helper.registerParticleSpawner(AMSpellParts.GRAVITY_WELL.get(),      SpellParticleSpawners::gravityWell);
        helper.registerParticleSpawner(AMSpellParts.REFLECT.get(),           SpellParticleSpawners::reflect);
        helper.registerParticleSpawner(AMSpellParts.SHIELD.get(),            SpellParticleSpawners::shield);
        helper.registerParticleSpawner(AMSpellParts.SWIFT_SWIM.get(),        SpellParticleSpawners::swiftSwim);
        helper.registerParticleSpawner(AMSpellParts.TEMPORAL_ANCHOR.get(),   SpellParticleSpawners::temporalAnchor);
        helper.registerParticleSpawner(AMSpellParts.TRUE_SIGHT.get(),        SpellParticleSpawners::trueSight);
        helper.registerParticleSpawner(AMSpellParts.WATERY_GRAVE.get(),      SpellParticleSpawners::wateryGrave);
    }

    private static void drowningDamage(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        damage(ParticleTypes.BUBBLE, hit, color, 25);
    }

    private static void fireDamage(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        damage(AMParticleTypes.EXPLOSION.get(), hit, color, 5);
    }

    private static void frostDamage(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        damage(ParticleTypes.SNOWFLAKE, hit, color, 25);
    }

    private static void lightningDamage(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        damage(ParticleTypes.ELECTRIC_SPARK, hit, color, 5);
    }

    private static void magicDamage(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        damage(AMParticleTypes.ARCANE.get(), hit, color, 5);
    }

    private static void physicalDamage(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        damage(AMParticleTypes.EMBER.get(), hit, color == -1 ? 0xcc3333 : color, 5);
    }

    private static void absorption(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.STARDUST.get(), color == -1 ? 0x007fff : color);
            particle.setY(particle.getY() - 1);
            particle.setLifetime(20);
            particle.scale(0.2f);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.5).setDistance(0.3 + particle.random().nextDouble() * 0.3));
            particle.addRandomOffset(1, 1, 1);
        }
    }

    private static void blindness(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 15; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.LENS_FLARE.get(), color == -1 ? 0x000000 : color);
            particle.setLifetime(25 + particle.random().nextInt(10));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1).setDistance(0.5 + particle.random().nextDouble()));
            particle.addRandomOffset(1, 1, 1);
        }
    }

    private static void haste(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.LIGHTS.get(), color);
            particle.setY(particle.getY() - 1);
            particle.setLifetime(20);
            particle.scale(0.1f);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitPointController(particle, hit.getLocation()).setSpeed(0.1).setDistance(0.3 + particle.random().nextDouble() * 0.3));
            particle.addController(new FadeOutController(particle, 0.05f).killsParticleOnFinish());
            particle.addRandomOffset(1, 1, 1);
        }
    }

    private static void invisibility(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.EMBER.get(), color);
            particle.setY(particle.getY() - 1);
            particle.setLifetime(20);
            particle.scale(0.1f);
            particle.addRandomOffset(1, 1, 1);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitPointController(particle, hit.getLocation()).setSpeed(0.5).setDistance(0.3 + particle.random().nextDouble() * 0.3));
            particle.addController(new FadeOutController(particle, 0.05f).killsParticleOnFinish());
        }
    }

    private static void jumpBoost(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        for (int i = 0; i < 15; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.WIND.get(), color);
            particle.setY(particle.getY() - 1);
            RandomSource random = particle.random();
            particle.setSpeed(random.nextDouble() * 0.1 - 0.05, random.nextDouble() * 0.2, random.nextDouble() * 0.1 - 0.05);
            particle.setLifetime(15);
            particle.setNoGravity();
            particle.scale(0.1f);
            particle.addRandomOffset(1, 0.5, 1);
        }
    }

    private static void levitation(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 15; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.EMBER.get(), color == -1 ? 0x333399 : color);
            particle.setLifetime(40);
            particle.scale(0.1f);
            RandomSource random = particle.random();
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1 + random.nextDouble() * 0.1).setDistance(0.4 + random.nextDouble() * 0.4));
            particle.addRandomOffset(1, 0.5, 1);
        }
    }

    private static void nightVision(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 8; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.LIGHTS.get(), color == -1 ? 0x337f33 : color);
            particle.setLifetime(30);
            particle.scale(0.1f);
            RandomSource random = particle.random();
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1 + random.nextDouble() * 0.1).setDistance(0.4 + random.nextDouble() * 0.4));
            particle.addRandomOffset(1, 0.5, 1);
        }
    }

    private static void regeneration(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.STARDUST.get(), color == -1 ? 0x19ffcc : color);
            particle.setY(particle.getY() - 1);
            particle.setLifetime(20);
            particle.scale(0.2f);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.5).setDistance(0.3 + particle.random().nextDouble() * 0.3));
            particle.addRandomOffset(1, 1, 1);
        }
    }

    private static void slowness(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.STARDUST.get(), color);
            particle.setY(particle.getY() + 1);
            particle.setLifetime(20);
            particle.scale(0.2f);
            particle.addController(new FloatUpwardController(particle, 0, -0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.2).setDistance(0.3 + particle.random().nextDouble() * 0.3));
            particle.addRandomOffset(1, 2, 1);
        }
    }

    private static void slowFalling(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.WIND.get(), color);
            particle.setY(particle.getY() - 1);
            particle.setLifetime(20);
            particle.scale(0.2f);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.5).setDistance(0.3 + particle.random().nextDouble() * 0.3));
            particle.addRandomOffset(1, 1, 1);
        }
    }

    private static void waterBreathing(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.WATER_BALL.get(), color);
            particle.setY(particle.getY() - 1);
            particle.setLifetime(20);
            particle.setAlpha(0.2f);
            particle.scale(0.2f);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.5).setDistance(0.3 + particle.random().nextDouble() * 0.3));
            particle.addRandomOffset(1, 1, 1);
        }
    }

    private static void agility(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 15; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.STARDUST.get(), color);
            RandomSource random = particle.random();
            particle.setLifetime(25 + random.nextInt(10));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1).setDistance(0.5 + random.nextDouble()));
        }
    }

    private static void astralDistortion(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        for (int i = 0; i < 10; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.PULSE.get(), color == -1 ? 0xb233e5 : color);
            particle.setLifetime(25 + particle.random().nextInt(10));
            particle.addRandomOffset(5, 4, 5);
            particle.addController(new FloatUpwardController(particle, 0.2, 0));
        }
    }

    private static void entangle(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.PLANT.get(), color);
            particle.setLifetime(20);
            particle.scale(0.1f);
            particle.addRandomOffset(1, 2, 1);
            particle.addController(new ApproachEntityController(particle, ehr.getEntity(), 0.15, 0.4));
        }
    }

    private static void flight(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 15; i++) {
            AMParticle particle = particle(hit, caster.getLevel().random.nextBoolean() ? AMParticleTypes.WIND.get() : AMParticleTypes.EMBER.get(), color);
            particle.setLifetime(20);
            particle.scale(0.1f);
            particle.addRandomOffset(1, 0.5, 1);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.2 + particle.random().nextDouble() * 0.2));
        }
    }

    private static void frost(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        for (int i = 0; i < 5; i++) {
            AMParticle particle = particle(hit, ParticleTypes.SNOWFLAKE, color);
            RandomSource random = particle.random();
            particle.setSpeed(random.nextDouble() * 0.2 - 0.1, 0.3, random.nextDouble() * 0.2 - 0.1);
            particle.setLifetime(10);
            particle.scale(0.1f);
            particle.setNoGravity();
            particle.addRandomOffset(1, 0.5, 1);
        }
    }

    private static void fury(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 10; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.PULSE.get(), color == -1 ? 0xff0000 : color);
            particle.setLifetime(10);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.15).setDistance(particle.random().nextDouble() + 1));
            particle.addRandomOffset(1, 1, 1);
        }
    }

    private static void gravityWell(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.PULSE.get(), color);
            particle.setLifetime(20);
            particle.scale(0.01f);
            particle.addController(new LeaveTrailController(particle, AMParticleTypes.PULSE.get()).setColor(color == -1 ? 0xb233e5 : color).setLifetime(5).addController(p -> new FloatUpwardController(p, 0, -0.3)));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.2));
            particle.addController(new FadeOutController(particle, 0.05f).killsParticleOnFinish());
            particle.addRandomOffset(1, 2, 1);
        }
    }

    private static void reflect(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.LENS_FLARE.get(), color);
            particle.setLifetime(20);
            particle.scale(0.2f);
            particle.addRandomOffset(1, 2, 1);
            if (color == -1) {
                RandomSource random = particle.random();
                particle.setColor(0.5f + random.nextFloat() * 0.5f, 0.1f, 0.5f + random.nextFloat() * 0.5f);
            }
        }
    }

    private static void shield(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.LIGHTS.get(), color); // TODO symbols particle type
            particle.setY(particle.getY() - 1);
            particle.setLifetime(10);
            particle.scale(0.1f);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.2).setDistance(1));
        }
    }

    private static void swiftSwim(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        Entity target = ehr.getEntity();
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.WATER_BALL.get(), color == -1 ? 0xff0000 : color);
            particle.setLifetime(20);
            particle.scale(0.1f);
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new MoveInDirectionController(particle, target instanceof LivingEntity living ? living.getYHeadRot() : target.getYRot(), target.getXRot(), 0.1 + particle.random().nextDouble() * 0.5));
            particle.addRandomOffset(1, 2, 1);
        }
    }

    private static void temporalAnchor(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.CLOCK.get(), color);
            particle.setLifetime(40);
            particle.scale(0.1f);
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1 + particle.random().nextDouble() * 0.1));
            particle.addRandomOffset(1, 2, 1);
        }
    }

    private static void trueSight(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.STARDUST.get(), color);
            particle.setY(particle.getY() - 1);
            particle.setLifetime(40);
            particle.scale(0.2f);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1).setDistance(1));
            particle.addRandomOffset(1, 1, 1);
            if (color == -1 && particle.random().nextBoolean()) {
                particle.setColor(0xb219b2);
            }
        }
    }

    private static void wateryGrave(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.WATER_BALL.get(), color);
            particle.setLifetime(20);
            particle.scale(0.01f);
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new LeaveTrailController(particle, AMParticleTypes.WATER_BALL.get()).setColor(color == -1 ? 0xffffff : color).setLifetime(5).addController(p -> new FloatUpwardController(p, 0, -0.3)));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.2));
            particle.addRandomOffset(1, 2, 1);
        }
    }

    //region helpers
    private static void damage(ParticleOptions options, HitResult hit, int color, int amount) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        Vec3 vec = hit.getLocation();
        for (int i = 0; i < amount; i++) {
            AMParticle particle = new AMParticle(level(), vec.x, ehr.getEntity().getEyeY(), vec.z, options);
            RandomSource random = particle.random();
            particle.setSpeed(random.nextDouble() * 0.2 - 0.1, random.nextDouble() * 0.2, random.nextDouble() * 0.2 - 0.1);
            particle.setLifetime(5);
            particle.setNoGravity();
            particle.scale(0.1f);
            particle.addRandomOffset(1, 0.5, 1);
            if (color != -1) {
                particle.setColor(color);
            }
        }
    }

    private static AMParticle particle(HitResult hit, ParticleOptions options, int color) {
        Vec3 vec = hit.getLocation();
        AMParticle particle = new AMParticle(level(), vec.x, hit instanceof EntityHitResult ehr ? ehr.getEntity().getEyeY() : vec.y, vec.z, options);
        if (color != -1) {
            particle.setColor(color);
        }
        return particle;
    }

    private static ClientLevel level() {
        return (ClientLevel) Objects.requireNonNull(ClientHelper.getLocalLevel());
    }
    //endregion
}
