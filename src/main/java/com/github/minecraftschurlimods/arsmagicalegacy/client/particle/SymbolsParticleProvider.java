package com.github.minecraftschurlimods.arsmagicalegacy.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class SymbolsParticleProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprites;

    public SymbolsParticleProvider(SpriteSet sprites) {
        this.sprites = sprites;
    }

    @Override
    public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
        return new SymbolsParticle(level, x, y, z, sprites.get(random));
    }

    private static class SymbolsParticle extends SingleQuadParticle {
        private SymbolsParticle(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
            super(level, x, y, z, sprite);
        }

        @Override
        protected Layer getLayer() {
            return Layer.TRANSLUCENT;
        }
    }
}
