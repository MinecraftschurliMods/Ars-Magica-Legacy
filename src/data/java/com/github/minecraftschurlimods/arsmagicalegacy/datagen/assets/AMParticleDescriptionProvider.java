package com.github.minecraftschurlimods.arsmagicalegacy.datagen.assets;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMParticles;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.data.ParticleDescriptionProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class AMParticleDescriptionProvider extends ParticleDescriptionProvider {
    public AMParticleDescriptionProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void addDescriptions() {
        spriteSet(AMParticles.NONE_HAND, 15);
        spriteSet(AMParticles.WATER_HAND, 30);
        spriteSet(AMParticles.FIRE_HAND, 25);
        spriteSet(AMParticles.EARTH_HAND, 18);
        spriteSet(AMParticles.AIR_HAND, 25);
        spriteSet(AMParticles.ICE_HAND, 30);
        spriteSet(AMParticles.LIGHTNING_HAND, 20);
        spriteSet(AMParticles.NATURE_HAND, 30);
        spriteSet(AMParticles.LIFE_HAND, 25);
        spriteSet(AMParticles.ARCANE_HAND, 28);
        spriteSet(AMParticles.ENDER_HAND, 30);
        spriteSet(AMParticles.ARCANE, 8);
        sprite(AMParticles.CLOCK);
        sprite(AMParticles.EMBER);
        spriteSet(AMParticles.EXPLOSION, 24);
        sprite(AMParticles.GHOST);
        sprite(AMParticles.LEAF);
        spriteSet(AMParticles.LENS_FLARE, 13);
        spriteSet(AMParticles.LIGHTS, 8);
        spriteSet(AMParticles.PLANT, 13);
        spriteSet(AMParticles.PULSE, 24);
        spriteSet(AMParticles.ROCK, 16);
        spriteSet(AMParticles.ROTATING_RINGS, 60);
        sprite(AMParticles.STARDUST);
        sprite(AMParticles.WATER_BALL);
        spriteSet(AMParticles.WIND, 10);
        spriteSet(AMParticles.SYMBOLS, 28);
    }

    private void spriteSet(DeferredHolder<ParticleType<?>, SimpleParticleType> particle, int textureCount) {
        spriteSet(particle.get(), particle.getId(), textureCount, false);
    }

    private void sprite(DeferredHolder<ParticleType<?>, SimpleParticleType> particle) {
        spriteSet(particle.get(), particle.getId());
    }
}
