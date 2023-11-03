package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

public class FadeOutController extends ParticleController {
    private final float speed;

    public FadeOutController(AMParticle particle, float speed) {
        super(particle);
        this.speed = speed;
    }

    public FadeOutController(AMParticle particle) {
        this(particle, 0.01f);
    }

    @Override
    public void tick() {
        float alpha = particle.getAlpha();
        if (alpha <= 0) {
            finish();
            return;
        }
        particle.setAlpha(alpha - speed);
    }
}
