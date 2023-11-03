package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMParticleTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.AMParticle;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.FadeOutController;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.FloatUpwardController;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.OrbitEntityController;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.OrbitPointController;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

/**
 * Helper class that holds and registered all the various spell particle spawners.
 */
public final class SpellParticleSpawners {
    /**
     * Should be called during client setup.
     */
    static void init() {
        var helper = ArsMagicaAPI.get().getSpellHelper();
        helper.registerParticleSpawner(AMSpellParts.DROWNING_DAMAGE.get(), SpellParticleSpawners::drowningDamage);
        helper.registerParticleSpawner(AMSpellParts.FIRE_DAMAGE.get(), SpellParticleSpawners::fireDamage);
        helper.registerParticleSpawner(AMSpellParts.FROST_DAMAGE.get(), SpellParticleSpawners::frostDamage);
        helper.registerParticleSpawner(AMSpellParts.LIGHTNING_DAMAGE.get(), SpellParticleSpawners::lightningDamage);
        helper.registerParticleSpawner(AMSpellParts.MAGIC_DAMAGE.get(), SpellParticleSpawners::magicDamage);
        helper.registerParticleSpawner(AMSpellParts.PHYSICAL_DAMAGE.get(), SpellParticleSpawners::physicalDamage);
        helper.registerParticleSpawner(AMSpellParts.ABSORPTION.get(), SpellParticleSpawners::absorption);
        helper.registerParticleSpawner(AMSpellParts.BLINDNESS.get(), SpellParticleSpawners::blindness);
        helper.registerParticleSpawner(AMSpellParts.HASTE.get(), SpellParticleSpawners::haste);
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
        damage(ParticleTypes.ANGRY_VILLAGER, hit, color, 5);
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
            AMParticle particle = particle(hit, AMParticleTypes.SPARKLE.get());
            particle.setY(particle.getY() - 1);
            particle.setLifetime(20);
            particle.setColor(color == -1 ? 0x007fff : color);
            particle.scale(0.2f);
            particle.addRandomOffset(1, 1, 1);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.5).setDistance(0.3 + particle.random().nextDouble() * 0.3));
        }
    }

    private static void blindness(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 15; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.LENS_FLARE.get());
            particle.addRandomOffset(1, 1, 1);
            particle.setLifetime(25 + particle.random().nextInt(10));
            particle.setColor(color == -1 ? 0x000000 : color);
            particle.addController(new OrbitEntityController(particle, ehr.getEntity(), 0.1).setDistance(0.5 + particle.random().nextDouble()));
        }
    }

    private static void haste(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        Vec3 location = hit.getLocation();
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, ParticleTypes.ENCHANT);
            particle.setY(particle.getY() - 1);
            particle.setLifetime(20);
            particle.scale(0.1f);
            particle.addController(new FloatUpwardController(particle, 0, 0.1));
            particle.addController(new OrbitPointController(particle, location).setSpeed(0.1).setDistance(0.3 + particle.random().nextDouble() * 0.3));
            particle.addController(new FadeOutController(particle, 0.05f).killsParticleOnFinish());
            particle.addRandomOffset(1, 1, 1);
            if (color != -1) {
                particle.setColor(color);
            }
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

    private static AMParticle particle(HitResult hit, ParticleOptions options) {
        Vec3 vec = hit.getLocation();
        return new AMParticle(level(), vec.x, hit instanceof EntityHitResult ehr ? ehr.getEntity().getEyeY() : vec.y, vec.z, options);
    }

    private static ClientLevel level() {
        return (ClientLevel) Objects.requireNonNull(ClientHelper.getLocalLevel());
    }
    //endregion
}
