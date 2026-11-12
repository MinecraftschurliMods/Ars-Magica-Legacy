package at.minecraftschurli.mods.arsmagicalegacy.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FallingLeavesParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class LeafParticleProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprites;

    public LeafParticleProvider(SpriteSet sprites) {
        this.sprites = sprites;
    }

    @Override
    public Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random) {
        return new FallingLeavesParticle(level, x, y, z, sprites.get(random), 0.25f, 10, true, false, 2, 0);
    }
}
