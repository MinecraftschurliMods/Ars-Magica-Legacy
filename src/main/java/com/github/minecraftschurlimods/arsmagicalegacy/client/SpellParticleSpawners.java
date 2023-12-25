package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Blizzard;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FallingStar;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireRain;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ManaVortex;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Projectile;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Wall;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Wave;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Zone;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMParticleTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.AMParticle;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.ApproachEntityController;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.ArcToEntityController;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.ChangeSizeController;
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
import net.minecraft.world.entity.MobType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;
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
        helper.registerParticleSpawner(AMSpellParts.ENDER_INTERVENTION.get(),  SpellParticleSpawners::enderIntervention);
        helper.registerParticleSpawner(AMSpellParts.FLING.get(),               SpellParticleSpawners::fling);
        helper.registerParticleSpawner(AMSpellParts.FORGE.get(),               SpellParticleSpawners::forge);
        helper.registerParticleSpawner(AMSpellParts.GROW.get(),                SpellParticleSpawners::grow);
        helper.registerParticleSpawner(AMSpellParts.HARVEST.get(),             SpellParticleSpawners::harvest);
        helper.registerParticleSpawner(AMSpellParts.HEAL.get(),                SpellParticleSpawners::heal);
        helper.registerParticleSpawner(AMSpellParts.IGNITION.get(),            SpellParticleSpawners::ignition);
        helper.registerParticleSpawner(AMSpellParts.KNOCKBACK.get(),           SpellParticleSpawners::knockback);
        helper.registerParticleSpawner(AMSpellParts.LIFE_DRAIN.get(),          SpellParticleSpawners::lifeDrain);
        helper.registerParticleSpawner(AMSpellParts.LIFE_TAP.get(),            SpellParticleSpawners::lifeTap);
        helper.registerParticleSpawner(AMSpellParts.LIGHT.get(),               SpellParticleSpawners::light);
        helper.registerParticleSpawner(AMSpellParts.MANA_BLAST.get(),          SpellParticleSpawners::manaBlast);
        helper.registerParticleSpawner(AMSpellParts.MANA_DRAIN.get(),          SpellParticleSpawners::manaDrain);
        helper.registerParticleSpawner(AMSpellParts.MELT_ARMOR.get(),          SpellParticleSpawners::meltArmor);
        helper.registerParticleSpawner(AMSpellParts.PLANT.get(),               SpellParticleSpawners::plant);
        helper.registerParticleSpawner(AMSpellParts.PLOW.get(),                SpellParticleSpawners::plow);
        helper.registerParticleSpawner(AMSpellParts.RECALL.get(),              SpellParticleSpawners::recall);
        helper.registerParticleSpawner(AMSpellParts.REPEL.get(),               SpellParticleSpawners::repel);
        helper.registerParticleSpawner(AMSpellParts.TRANSPLACE.get(),          SpellParticleSpawners::transplace);
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
        particles(25, hit, AMParticleTypes.STARDUST, color == -1 ? 0x007fff : color, 20, particle -> {
            particle.setY(particle.getY() - 1);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.5).setDistance(0.3 + random.nextDouble() * 0.3));
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void blindness(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(15, hit, AMParticleTypes.LENS_FLARE, color == -1 ? 0x000000 : color, 25 + random.nextInt(10), particle -> {
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1).setDistance(random.nextDouble() + 0.5));
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void haste(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(25, hit, AMParticleTypes.LIGHTS, color, 20, particle -> {
            particle.setY(particle.getY() - 1);
            particle.scale(0.1f);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitPointController(particle, hit.getLocation(), 0.1).setDistance(0.3 + random.nextDouble() * 0.3));
            particle.addController(new FadeOutController(particle, 0.05f).killsParticleOnFinish());
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void invisibility(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(25, hit, AMParticleTypes.EMBER, color, 20, particle -> {
            particle.setY(particle.getY() - 1);
            particle.scale(0.1f);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitPointController(particle, hit.getLocation(), 0.5).setDistance(0.3 + random.nextDouble() * 0.3));
            particle.addController(new FadeOutController(particle, 0.05f).killsParticleOnFinish());
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void jumpBoost(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(15, hit, AMParticleTypes.WIND, color, 15, particle -> {
            particle.setY(particle.getY() - 1);
            particle.setSpeed(random.nextDouble() * 0.1 - 0.05, random.nextDouble() * 0.2, random.nextDouble() * 0.1 - 0.05);
            particle.setGravity(1);
            particle.scale(0.1f);
            particle.addRandomOffset(1, 0.5, 1);
        });
    }

    private static void levitation(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(15, hit, AMParticleTypes.EMBER, color == -1 ? 0x333399 : color, 40, particle -> {
            particle.scale(0.1f);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1 + random.nextDouble() * 0.1).setDistance(0.4 + random.nextDouble() * 0.4));
            particle.addRandomOffset(1, 0.5, 1);
        });
    }

    private static void nightVision(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(8, hit, AMParticleTypes.LIGHTS, color == -1 ? 0x337f33 : color, 30, particle -> {
            particle.scale(0.1f);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1 + random.nextDouble() * 0.1).setDistance(0.4 + random.nextDouble() * 0.4));
            particle.addRandomOffset(1, 0.5, 1);
        });
    }

    private static void regeneration(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, AMParticleTypes.STARDUST, color == -1 ? 0x19ffcc : color, 20, particle -> {
            particle.setY(particle.getY() - 1);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.5).setDistance(0.3 + random.nextDouble() * 0.3));
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void slowness(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, AMParticleTypes.STARDUST, color, 20, particle -> {
            particle.setY(particle.getY() + 1);
            particle.addController(new FloatUpwardController(particle, 0, -0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.2).setDistance(0.3 + random.nextDouble() * 0.3));
            particle.addRandomOffset(1, 2, 1);
        });
    }

    private static void slowFalling(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, AMParticleTypes.WIND, color, 20, particle -> {
            particle.setY(particle.getY() - 1);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.5).setDistance(0.3 + random.nextDouble() * 0.3));
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void waterBreathing(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, AMParticleTypes.WATER_BALL, color, 20, particle -> {
            particle.setY(particle.getY() - 1);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.5).setDistance(0.3 + random.nextDouble() * 0.3));
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void agility(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(15, hit, AMParticleTypes.STARDUST, color, 25 + random.nextInt(10), particle -> {
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1).setDistance(random.nextDouble() + 0.5));
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void astralDistortion(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(10, hit, AMParticleTypes.PULSE, color == -1 ? 0xb233e5 : color, 25 + random.nextInt(10), particle -> {
            particle.addRandomOffset(5, 4, 5);
            particle.addController(new FloatUpwardController(particle, 0.2, 0));
        });
    }

    private static void entangle(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, AMParticleTypes.PLANT, color, 20, particle -> {
            particle.scale(0.1f);
            particle.addRandomOffset(1, 2, 1);
            particle.addController(new ApproachEntityController(particle, ehr.getEntity(), 0.15, 0.4));
        });
    }

    private static void flight(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(15, hit, random.nextBoolean() ? AMParticleTypes.WIND : AMParticleTypes.EMBER, color, 20, particle -> {
            particle.scale(0.1f);
            particle.addRandomOffset(1, 0.5, 1);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.2 + random.nextDouble() * 0.2));
        });
    }

    private static void frost(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(5, hit, ParticleTypes.SNOWFLAKE, color, 10, particle -> {
            particle.setSpeed(random.nextDouble() * 0.2 - 0.1, 0.3, random.nextDouble() * 0.2 - 0.1);
            particle.scale(0.1f);
            particle.setGravity(1);
            particle.addRandomOffset(1, 0.5, 1);
        });
    }

    private static void fury(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(10, hit, AMParticleTypes.PULSE, color == -1 ? 0xff0000 : color, 10, particle -> {
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.15).setDistance(random.nextDouble() + 1));
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void gravityWell(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, AMParticleTypes.PULSE, color, 20, particle -> {
            particle.scale(0.01f);
            particle.addController(new LeaveTrailController(particle, AMParticleTypes.PULSE.get()).setColor(color == -1 ? 0xb233e5 : color).setLifetime(5).addController(p -> new FloatUpwardController(p, 0, -0.3)));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.2));
            particle.addController(new FadeOutController(particle, 0.05f).killsParticleOnFinish());
            particle.addRandomOffset(1, 2, 1);
        });
    }

    private static void reflect(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(25, hit, AMParticleTypes.LENS_FLARE, color, 20, particle -> {
            particle.addRandomOffset(1, 2, 1);
            if (color == -1) {
                particle.setColor(0.5f + random.nextFloat() * 0.5f, 0.1f, 0.5f + random.nextFloat() * 0.5f);
            }
        });
    }

    private static void shield(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, AMParticleTypes.SYMBOLS.random(random), color, 10, particle -> {
            particle.setY(particle.getY() - 1);
            particle.scale(0.1f);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.2).setDistance(1));
        });
    }

    private static void swiftSwim(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, AMParticleTypes.WATER_BALL, color == -1 ? 0x0000ff : color, 20, particle -> {
            particle.scale(0.1f);
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new MoveInDirectionController(particle, ehr.getEntity().getYHeadRot() + 90, ehr.getEntity().getXRot(), 0.1 + random.nextDouble() * 0.5));
            particle.addRandomOffset(1, 2, 1);
        });
    }

    private static void temporalAnchor(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, AMParticleTypes.CLOCK, color, 40, particle -> {
            particle.scale(0.1f);
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1 + random.nextDouble() * 0.1));
            particle.addRandomOffset(1, 2, 1);
        });
    }

    private static void trueSight(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, AMParticleTypes.STARDUST, color, 40, particle -> {
            particle.setY(particle.getY() - 1);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1).setDistance(1));
            particle.addRandomOffset(1, 1, 1);
            if (color == -1 && random.nextBoolean()) {
                particle.setColor(0xb219b2);
            }
        });
    }

    private static void wateryGrave(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, AMParticleTypes.WATER_BALL, color, 20, particle -> {
            particle.scale(0.01f);
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new LeaveTrailController(particle, AMParticleTypes.WATER_BALL.get()).setColor(color == -1 ? 0xffffff : color).setLifetime(5).addController(p -> new FloatUpwardController(p, 0, -0.3)));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.2));
            particle.addRandomOffset(1, 2, 1);
        });
    }

    private static void attract(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(5, hit, AMParticleTypes.ARCANE, color == -1 ? 0xcc4cb2 : color, 20, particle -> particle.addRandomOffset(0.5, 0.5, 0.5));
    }

    private static void banishRain(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(25, hit, AMParticleTypes.WATER_BALL, color, 25 + random.nextInt(10), particle -> {
            particle.addController(new FloatUpwardController(particle, 0, 0.5));
            particle.addRandomOffset(5, 4, 5);
        });
    }

    private static void blink(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, AMParticleTypes.STARDUST, color, 20, particle -> {
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new MoveInDirectionController(particle, ehr.getEntity().getYHeadRot() + 90, ehr.getEntity().getXRot(), 0.1 + random.nextDouble() * 0.5));
            particle.addRandomOffset(1, 2, 1);
        });
    }

    private static void charm(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(10, hit, ParticleTypes.HEART, color, 20, particle -> {
            particle.addController(new FloatUpwardController(particle, 0, 0.1 + random.nextDouble() * 0.05));
            particle.addRandomOffset(1, 2, 1);
        });
    }

    private static void createWater(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(15, hit, ParticleTypes.BUBBLE, color, 10, particle -> {
            particle.setSpeed(random.nextDouble() - 0.5, random.nextDouble() - 0.5, random.nextDouble() - 0.5);
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void disarm(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, ParticleTypes.ELECTRIC_SPARK, color == -1 ? random.nextBoolean() ? 0xb2b219 : 0x19b219 : color, 40, particle -> {
            particle.setGravity(1);
            particle.scale(0.1f);
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new MoveInDirectionController(particle, ehr.getEntity().getYHeadRot() + 90, ehr.getEntity().getXRot(), 0.1 + random.nextDouble() * 0.5));
            particle.addRandomOffset(1, 2, 1);
        });
    }

    private static void dispel(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, ParticleTypes.ELECTRIC_SPARK, color, 20, particle -> {
            particle.scale(0.1f);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1 + random.nextDouble() * 0.1));
            particle.addRandomOffset(1, 2, 1);
            if (color != -1 && random.nextBoolean()) {
                particle.setColor(0xb219b2);
            }
        });
    }

    private static void divineIntervention(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(100, hit, AMParticleTypes.ARCANE, color, 25 + random.nextInt(10), particle -> {
            particle.setY(particle.getY() - 1);
            particle.addRandomOffset(1, 1, 1);
            if (random.nextBoolean()) {
                particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1).setDistance(0.5 + random.nextDouble()));
            } else {
                particle.addController(new OrbitPointController(particle, ehr.getLocation(), 0.1).setDistance(0.5 + random.nextDouble()));
            }
        });
    }

    private static void drought(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(25, hit, AMParticleTypes.EMBER, color == -1 ? 0xe5cc7f : color, 40, particle -> {
            particle.setY(particle.getY() + 1);
            particle.setGravity(1);
            particle.scale(0.1f);
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addRandomOffset(1, 0, 1);
        });
    }

    private static void enderIntervention(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(100, hit, AMParticleTypes.GHOST, color == -1 ? 0xb23333 : color, 25 + random.nextInt(10), particle -> {
            particle.setY(particle.getY() - 1);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addRandomOffset(1, 2, 1);
        });
    }

    private static void fling(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(25, hit, AMParticleTypes.WIND, color, 20, particle -> {
            particle.addController(new FloatUpwardController(particle, 0, 0.3 + random.nextDouble() * 0.3));
            particle.addRandomOffset(1, 2, 1);
        });
    }

    private static void forge(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        AMParticle particle = particle(hit, AMParticleTypes.LIGHTS.get(), color == -1 ? 0xb26633 : color, 20);
        particle.setAlpha(0.1f);
        particle.scale(0.3f);
    }

    private static void grow(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(25, hit, AMParticleTypes.PLANT, color, 20, particle -> {
            particle.setY(particle.getY() + 1);
            particle.scale(0.1f);
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitPointController(particle, hit.getLocation().add(0.5, 0.5, 0.5), 0.1).setDistance(0.3 + random.nextDouble() * 0.3));
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void harvest(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(25, hit, AMParticleTypes.PLANT, color == -1 ? 0xb23319 : color, 20, particle -> {
            particle.setY(particle.getY() + 1);
            particle.setGravity(1);
            particle.scale(0.1f);
            particle.addController(new FloatUpwardController(particle, 0, 0.3));
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void heal(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        Entity target = ehr.getEntity();
        if (target instanceof LivingEntity living && living.getMobType() == MobType.UNDEAD) {
            particles(25, hit, AMParticleTypes.SYMBOLS.random(random), color == -1 ? 0xff3333 : color, null, particle -> {
                particle.setY(particle.getY() - 1);
                particle.scale(0.1f);
                particle.addController(new FloatUpwardController(particle, 0, -0.01));
                particle.addController(new FadeOutController(particle, 0.02f));
                particle.addRandomOffset(1, 1, 1);
            });
        } else {
            particles(25, hit, AMParticleTypes.STARDUST, color == -1 ? 0x19ff19 : color, 20, particle -> {
                particle.setY(particle.getY() - 1);
                particle.addController(new FloatUpwardController(particle, 0, 0.1));
                particle.addController(new OrbitEntityController(particle, target, 0.5).setDistance(0.3 + random.nextDouble() * 0.3));
                particle.addRandomOffset(1, 1, 1);
            });
        }
    }

    private static void ignition(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(25, hit, AMParticleTypes.EXPLOSION, color, 5, particle -> {
            particle.setSpeed(random.nextDouble() * 0.2 - 0.1, 0.3, random.nextDouble() * 0.2 - 0.1);
            particle.setGravity(1);
            particle.scale(0.1f);
            particle.addRandomOffset(1, 0.5, 1);
        });
    }

    private static void knockback(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(25, hit, AMParticleTypes.STARDUST, color, 20, particle -> {
            particle.addController(new FadeOutController(particle, 0.05f));
            particle.addController(new MoveInDirectionController(particle, caster.getYHeadRot() + 90, 0, 0.1 + random.nextDouble() * 0.5));
            particle.addRandomOffset(1, 2, 1);
        });
    }

    private static void lifeDrain(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(15, hit, AMParticleTypes.EMBER, color == -1 ? 0xff3333 : color, -1, particle -> {
            particle.setAlpha(0.5f);
            particle.addController(new ArcToEntityController(particle, caster, 0.05));
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void lifeTap(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, ParticleTypes.ELECTRIC_SPARK, color == -1 ? random.nextBoolean() ? 0x66197f : 0x197f19 : color, 15, particle -> {
            particle.scale(0.1f);
            particle.addController(new ApproachEntityController(particle, ehr.getEntity(), 0.1, 0.1));
            particle.addRandomOffset(2, 0.5, 2);
        });
    }

    private static void light(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(5, hit, ParticleTypes.ELECTRIC_SPARK, color == -1 ? 0x9933cc : color, 20, particle -> {
            particle.setSpeed(random.nextDouble() * 0.2 - 0.1, random.nextDouble() * 0.2, random.nextDouble() * 0.2 - 0.1);
            particle.addRandomOffset(1, 0.5, 1);
            if (hit instanceof BlockHitResult) {
                particle.setY(particle.getY() + 1);
            }
        });
    }

    private static void manaBlast(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(100, hit, ParticleTypes.ELECTRIC_SPARK, color == -1 ? 0x9900e5 : color, 10, particle -> {
            particle.addController(new ApproachEntityController(particle, ehr.getEntity(), 0.15, 0.1));
            particle.addController(new FadeOutController(particle, 0.1f));
            particle.addRandomOffset(1, 0.5, 1);
        });
    }

    private static void manaDrain(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(15, hit, AMParticleTypes.STARDUST, color == -1 ? 0x0066ff : color, -1, particle -> {
            particle.setAlpha(0.5f);
            particle.addController(new ArcToEntityController(particle, caster, 0.05));
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void meltArmor(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        AMParticle particle = particle(hit, AMParticleTypes.LIGHTS.get(), color == -1 ? 0xb26633 : color, 20);
        particle.setAlpha(0.1f);
        particle.scale(0.3f);
    }

    private static void plant(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(15, hit, AMParticleTypes.PLANT, color, 20, particle -> {
            particle.setSpeed(random.nextDouble() * 0.2 - 0.1, 0.2, random.nextDouble() * 0.2 - 0.1);
            particle.setGravity(1);
            particle.scale(0.1f);
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void plow(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        particles(10, hit, AMParticleTypes.ROCK, color, 20, particle -> {
            particle.setY(particle.getY() + 1);
            particle.setSpeed(random.nextDouble() * 0.2 - 0.1, 0.2, random.nextDouble() * 0.2 - 0.1);
            particle.setGravity(1);
            particle.scale(0.05f);
            particle.addRandomOffset(1, 1, 1);
        });
    }

    private static void recall(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        particles(25, hit, AMParticleTypes.ARCANE, color, 20, particle -> {
            particle.setY(particle.getY() - 1);
            particle.addController(new ApproachEntityController(particle, ehr.getEntity(), 0.3, 0.1));
            particle.addRandomOffset(3, 2, 3);
        });
    }

    private static void repel(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        AMParticle particle = particle(hit, AMParticleTypes.STARDUST.get(), color, 20);
        particle.addController(new FadeOutController(particle, 0.05f));
    }

    private static void transplace(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        Entity target = ehr.getEntity();
        particles(15, new EntityHitResult(caster), ParticleTypes.ELECTRIC_SPARK, color == -1 ? 0xff0000 : color, 40, particle -> {
            particle.addController(new ArcToEntityController(particle, caster.getEyePosition(), target, 0.05));
            particle.addRandomOffset(1, 1, 1);
        });
        particles(15, new EntityHitResult(caster), ParticleTypes.ELECTRIC_SPARK, color == -1 ? 0x0000ff : 0xffffff - color, 40, particle -> {
            particle.addController(new ArcToEntityController(particle, target.getEyePosition(), caster, 0.05));
            particle.addRandomOffset(1, 1, 1);
        });
    }

    //region helpers
    private static void damage(ParticleOptions options, HitResult hit, RandomSource random, int color, int amount) {
        particles(amount, hit, options, color, 5, particle -> {
            particle.setSpeed(random.nextDouble() * 0.2 - 0.1, random.nextDouble() * 0.2, random.nextDouble() * 0.2 - 0.1);
            particle.setGravity(1);
            particle.scale(0.1f);
            particle.addRandomOffset(1, 0.5, 1);
        });
    }

    private static AMParticle particle(HitResult hit, ParticleOptions options, int color, @Nullable Integer lifetime) {
        Vec3 vec = hit.getLocation();
        double x = vec.x, y = vec.y, z = vec.z;
        if (hit instanceof BlockHitResult bhr) {
            x = bhr.getBlockPos().getX() + 0.5;
            z = bhr.getBlockPos().getZ() + 0.5;
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

    private static void particles(int count, HitResult hit, Supplier<? extends ParticleOptions> options, int color, @Nullable Integer lifetime, Consumer<AMParticle> extraOptions) {
        particles(count, hit, options.get(), color, lifetime, extraOptions);
    }

    private static void particles(int count, HitResult hit, ParticleOptions options, int color, @Nullable Integer lifetime, Consumer<AMParticle> extraOptions) {
        Vec3 vec = hit.getLocation();
        double x = vec.x, y = vec.y, z = vec.z;
        if (hit instanceof BlockHitResult bhr) {
            x = bhr.getBlockPos().getX() + 0.5;
            z = bhr.getBlockPos().getZ() + 0.5;
        } else if (hit instanceof EntityHitResult e) {
            y += e.getEntity().getEyeHeight();
        }
        AMParticle.bulkCreate(count, (ClientLevel) Objects.requireNonNull(ClientHelper.getLocalLevel()), x, y, z, options).forEach(particle -> {
            particle.setNoGravity();
            particle.scale(0.2f);
            if (color != -1) {
                particle.setColor(color);
            }
            if (lifetime != null) {
                particle.setLifetime(lifetime);
            }
            extraOptions.accept(particle);
        });
    }
    //endregion

    //region non-component
    public static void handleReceivedPacket(int i) {
        Entity entity = Objects.requireNonNull(ClientHelper.getLocalLevel()).getEntity(i);
        if (entity == null) {
            ArsMagicaLegacy.LOGGER.trace("Tried to spawn particles for entity id {}, but the entity was null. It probably wasn't synced yet", i);
        } else if (entity instanceof Blizzard blizzard) {
            blizzard(blizzard);
        } else if (entity instanceof FallingStar fallingStar) {
            fallingStar(fallingStar);
        } else if (entity instanceof FireRain fireRain) {
            fireRain(fireRain);
        } else if (entity instanceof ManaVortex manaVortex) {
            manaVortex(manaVortex);
        } else if (entity instanceof Projectile projectile) {
            projectile(projectile);
        } else if (entity instanceof Wall wall) {
            wall(wall);
        } else if (entity instanceof Wave wave) {
            wave(wave);
        } else if (entity instanceof Zone zone) {
            zone(zone);
        }
    }

    private static void blizzard(Blizzard blizzard) {
        float radius = blizzard.getRadius();
        RandomSource random = blizzard.level().getRandom();
        int color = blizzard.getColor();
        AMParticle.bulkCreate(100, (ClientLevel) Objects.requireNonNull(ClientHelper.getLocalLevel()), blizzard.getX(), blizzard.getY() + 10, blizzard.getZ(), ParticleTypes.SNOWFLAKE).forEach(particle -> {
            particle.setAlpha(0.6f);
            particle.setSpeed(random.nextDouble() * 0.2 - 0.1, random.nextDouble() * 0.1 - 0.05, random.nextDouble() * 0.2 - 0.1);
            particle.setLifetime(40);
            particle.setGravity(1);
            particle.scale(0.1f);
            particle.addRandomOffset(radius * 2, 1, radius * 2);
            if (color != -1) {
                particle.setColor(color);
            }
        });
    }

    private static void fallingStar(FallingStar fallingStar) {
        ClientLevel level = (ClientLevel) Objects.requireNonNull(ClientHelper.getLocalLevel());
        RandomSource random = level.getRandom();
        int color = fallingStar.getColor();
        if (fallingStar.hasImpacted()) {
            float damage = fallingStar.getDamage();
            float radius = fallingStar.getRadius();
            EntityHitResult hit = new EntityHitResult(fallingStar);
            for (int i = 0; i < damage * 24; i++) {
                Vec3 speed = Vec3.directionFromRotation(0, i / 24f / damage * 360f).normalize();
                AMParticle particle = particle(hit, AMParticleTypes.EMBER.get(), -1, (int) (damage * radius));
                particle.setSpeed(speed.x * 0.5, speed.y * 0.5, speed.z * 0.5);
                if (color == -1) {
                    float f = random.nextFloat();
                    particle.setColor(f * 0.24f, f * 0.58f, f * 0.71f);
                } else {
                    particle.setColor(color);
                }
                particle.addRandomOffset(0.5, 0.5, 0.5);
            }
        } else {
            Vec3 movement = fallingStar.getDeltaMovement();
            for (float i = 0; i < Math.abs(movement.y); i++) {
                AMParticle particle = new AMParticle(level, fallingStar.getX() + movement.x * i, fallingStar.getY() + movement.y * i, fallingStar.getZ() + movement.z * i, AMParticleTypes.EMBER.get());
                particle.setLifetime(5);
                if (color == -1) {
                    float f = random.nextFloat();
                    particle.setColor(f * 0.24f, f * 0.58f, f * 0.71f);
                } else {
                    particle.setColor(color);
                }
                particle.addController(new ChangeSizeController(particle, 0.5f, 0.05f, 20));
            }
        }
    }

    private static void fireRain(FireRain fireRain) {
        float radius = fireRain.getRadius();
        RandomSource random = fireRain.level().getRandom();
        int color = fireRain.getColor();
        AMParticle.bulkCreate(100, (ClientLevel) Objects.requireNonNull(ClientHelper.getLocalLevel()), fireRain.getX(), fireRain.getY() + 10, fireRain.getZ(), AMParticleTypes.EXPLOSION.get()).forEach(particle -> {
            particle.setSpeed(random.nextDouble() * 0.2 - 0.1, random.nextDouble() * 0.1 - 0.05, random.nextDouble() * 0.2 - 0.1);
            particle.setLifetime(40);
            particle.setGravity(1);
            particle.addRandomOffset(radius * 2, 1, radius * 2);
            if (color != -1) {
                particle.setColor(color);
            }
        });
    }

    private static void manaVortex(ManaVortex manaVortex) {
        int duration = manaVortex.getDuration();
        RandomSource random = manaVortex.level().getRandom();
        if (duration - manaVortex.tickCount > 30) {
            AMParticle particle = particle(new EntityHitResult(manaVortex), AMParticleTypes.EMBER.get(), 0x3d3dcc, 10 + manaVortex.level().getRandom().nextInt(10));
            particle.addRandomOffset(0.2, 0.2, 0.2);
        }
        if (duration - manaVortex.tickCount <= 10) {
            particles(72, new EntityHitResult(manaVortex), AMParticleTypes.EMBER, 0x3d3dcc, 20, particle -> {
                particle.setSpeed(random.nextDouble() * 0.2 - 0.1, random.nextDouble() * 0.2, random.nextDouble() * 0.2 - 0.1);
                particle.addController(new FadeOutController(particle, 0.02f));
                particle.addRandomOffset(0.2, 0.2, 0.2);
            });
        }
    }

    private static void projectile(Projectile projectile) {
        int color = projectile.getColor();
        AMParticle particle = particle(new EntityHitResult(projectile), Objects.requireNonNull(projectile.getSpell().primaryAffinity().getParticle()), -1, 5);
        particle.scale(0.05f);
        particle.addController(new FloatUpwardController(particle, 0.05, 0));
        particle.addController(new FadeOutController(particle, 0.2f));
        particle.addRandomOffset(0.1, 0.1, 0.1);
        if (color != -1) {
            particle.setColor(color);
        }
    }

    private static void wallOrWave(ISpell spell, RandomSource random, Vec3 position, float radius, float rotation, int color) {
        ClientLevel level = (ClientLevel) Objects.requireNonNull(ClientHelper.getLocalLevel());
        ParticleOptions options = Objects.requireNonNull(spell.primaryAffinity().getParticle());
        double posX = position.x, posY = position.y, posZ = position.z;
        double cos = Math.cos(Math.toRadians(rotation)) * radius;
        double sin = Math.sin(Math.toRadians(rotation)) * radius;
        Vec3 a = new Vec3(posX - cos, posY, posZ - sin);
        Vec3 b = new Vec3(posX + cos, posY, posZ + sin);
        double minX = posX - radius;
        double minY = posY - 1;
        double minZ = posZ - radius;
        double maxX = posX + radius;
        double maxY = posY + 3;
        double maxZ = posZ + radius;
        for (double x = minX; x <= maxX; x += 0.25) {
            for (double y = minY; y <= maxY; y += 0.25) {
                for (double z = minZ; z <= maxZ; z += 0.25) {
                    double newX = x + random.nextDouble() * 0.2 - 0.1;
                    double newZ = z + random.nextDouble() * 0.2 - 0.1;
                    if (newX > minX && newX < maxX && newZ > minZ && newZ < maxZ) {
                        Vec3 newVec = new Vec3(newX, posY, newZ);
                        if (newVec.distanceTo(a) < 0.5 || newVec.distanceTo(b) < 0.5 || newVec.distanceTo(position) < 0.5) {
                            AMParticle particle = new AMParticle(level, newX, y, newZ, options);
                            particle.setNoGravity();
                            particle.setLifetime(5);
                            particle.scale(0.15f);
                            particle.addController(new FloatUpwardController(particle, 0, 0.07));
                            particle.addRandomOffset(1, 1, 1);
                            if (color != -1) {
                                particle.setColor(color);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void wall(Wall wall) {
        wallOrWave(wall.getSpell(), wall.level().getRandom(), wall.position(), wall.getRadius(), wall.getYRot(), wall.getColor());
    }

    private static void wave(Wave wave) {
        wallOrWave(wave.getSpell(), wave.level().getRandom(), wave.position(), wave.getRadius(), wave.getYRot(), wave.getColor());
    }

    private static void zone(Zone zone) {
        ClientLevel level = (ClientLevel) Objects.requireNonNull(ClientHelper.getLocalLevel());
        ParticleOptions options = Objects.requireNonNull(zone.getSpell().primaryAffinity().getParticle());
        Vec3 position = zone.position();
        double x = position.x, y = zone.getEyeY(), z = position.z;
        float radius = zone.getRadius();
        int color = zone.getColor();
        for (int i = 0; i < 30; i++) {
            AMParticle particle = new AMParticle(level, x, y, z, options);
            particle.setNoGravity();
            particle.setLifetime(20);
            particle.scale(0.15f);
            particle.addController(new FloatUpwardController(particle, 0, 0.07));
            particle.addRandomOffset(radius, 1, radius);
            if (color != -1) {
                particle.setColor(color);
            }
        }
    }
    //endregion
}
