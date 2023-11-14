package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;

@FunctionalInterface
public interface ISpellParticleSpawner {
    /**
     * Spawns spell particles. Only called on the client.
     *
     * @param spell The spell that spawned the particles.
     * @param hit   The hit result to use.
     * @param color The color to use. May be -1, which indicates that no color should be used.
     */
    void spawnParticles(ISpell spell, LivingEntity caster, HitResult hit, RandomSource random, int color);
}
