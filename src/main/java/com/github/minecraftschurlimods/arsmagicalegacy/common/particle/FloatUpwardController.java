package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

public class FloatUpwardController extends ParticleController {
    private final double jitter;
    private final double halfJitter;
    private final double speed;

    public FloatUpwardController(AMParticle particle, boolean stopOtherControllers, boolean killParticleOnFinish, double jitter, double speed) {
        super(particle, stopOtherControllers, killParticleOnFinish);
        this.jitter = jitter;
        this.halfJitter = jitter / 2;
        this.speed = speed;
    }

    @Override
    public void tick() {
        if (particle.getY() > particle.level().getMaxBuildHeight()) {
            finish();
            return;
        }
        particle.move(particle.random().nextDouble() * jitter - halfJitter, speed, particle.random().nextDouble() * jitter - halfJitter);
    }
}
