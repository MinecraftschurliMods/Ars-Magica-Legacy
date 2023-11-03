package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

public abstract class ParticleController {
    protected final AMParticle particle;
    protected boolean stopOtherControllers = false;
    protected boolean killOnFinish = false;
    private boolean first = true;
    private boolean finished = false;

    public ParticleController(AMParticle particle) {
        this.particle = particle;
    }

    public ParticleController stopsOtherControllers() {
        stopOtherControllers = true;
        return this;
    }

    public ParticleController killsParticleOnFinish() {
        killOnFinish = true;
        return this;
    }

    public abstract void tick();

    public void tickFirst() {
        tick();
    }

    public void finish() {
        finished = true;
        if (killOnFinish && particle.isAlive()) {
            particle.remove();
        }
    }

    public boolean isFinished() {
        return finished;
    }

    void baseTick() {
        if (!particle.isAlive() || finished) return;
        if (first) {
            tickFirst();
            first = false;
        } else {
            tick();
        }
    }
}
