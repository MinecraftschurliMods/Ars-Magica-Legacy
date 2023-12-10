package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

import net.minecraft.util.Mth;

public class ChangeSizeController extends ParticleController {
    private final float from;
    private final float to;
    private final int duration;

    public ChangeSizeController(AMParticle particle, float from, float to, int duration) {
        super(particle);
        particle.scale(from);
        this.from = from;
        this.to = to;
        this.duration = duration;
    }

    @Override
    public void tick() {
        particle.scale(Mth.lerp(Mth.clamp((float) tickCount / duration, 0, 1), from, to));
    }
}
