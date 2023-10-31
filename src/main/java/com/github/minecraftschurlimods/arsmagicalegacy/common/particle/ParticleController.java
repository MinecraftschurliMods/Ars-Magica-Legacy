package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

public abstract class ParticleController {
    protected final AMParticle particle;
    protected final int priority;
    protected final boolean stopOtherControllers;
    protected final boolean killOnFinish;
    private boolean first = true;
    private boolean finished = false;

    public ParticleController(AMParticle particle, int priority, boolean stopOtherControllers, boolean killParticleOnFinish) {
        this.particle = particle;
        this.priority = priority;
        this.stopOtherControllers = stopOtherControllers;
        this.killOnFinish = killParticleOnFinish;
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
