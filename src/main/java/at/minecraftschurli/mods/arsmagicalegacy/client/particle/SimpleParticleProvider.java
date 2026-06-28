package at.minecraftschurli.mods.arsmagicalegacy.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class SimpleParticleProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprites;

    public SimpleParticleProvider(SpriteSet sprites) {
        this.sprites = sprites;
    }

    @Override
    public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
        return new SimpleParticle(level, x, y, z, sprites);
    }

    private static class SimpleParticle extends SimpleAnimatedParticle {
        private SimpleParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
            super(level, x, y, z, sprites, 0);
            setSpriteFromAge(sprites);
        }
    }
}
