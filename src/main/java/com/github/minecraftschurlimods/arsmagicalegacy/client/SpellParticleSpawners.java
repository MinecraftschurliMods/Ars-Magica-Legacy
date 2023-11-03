package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMParticleTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.AMParticle;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.FloatUpwardController;
import com.github.minecraftschurlimods.arsmagicalegacy.common.particle.OrbitEntityController;
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
    }

    private static void drowningDamage(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        damage(ParticleTypes.BUBBLE, hit, color);
    }

    private static void fireDamage(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        damage(AMParticleTypes.EXPLOSION.get(), hit, color);
    }

    private static void frostDamage(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        damage(ParticleTypes.SNOWFLAKE, hit, color);
    }

    private static void lightningDamage(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        damage(ParticleTypes.CLOUD, hit, color);
    }

    private static void magicDamage(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        damage(AMParticleTypes.ARCANE.get(), hit, color);
    }

    private static void physicalDamage(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        damage(AMParticleTypes.EMBER.get(), hit, color);
    }

    private static void absorption(ISpell spell, LivingEntity caster, HitResult hit, int color) {
        if (!(hit instanceof EntityHitResult ehr)) return;
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, AMParticleTypes.SPARKLE.get());
            particle.setLifetime(20);
            particle.scale(0.2f);
            particle.addRandomOffset(1, 1, 1);
            particle.addController(new FloatUpwardController(particle, false, false, 0, 0.1));
            particle.addController(new OrbitEntityController(particle, false, false, ehr.getEntity(), 0.5).setIgnoreY(true).setDistance(0.3 * particle.random().nextDouble() * 0.3));
            particle.setColor(color == -1 ? 0x007fff : color);
        }
    }

    //region helpers
    private static void damage(ParticleOptions options, HitResult hit, int color) {
        for (int i = 0; i < 25; i++) {
            AMParticle particle = particle(hit, options);
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
