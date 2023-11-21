package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

import net.minecraft.core.particles.ParticleOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LeaveTrailController extends ParticleController {
    private final List<Function<AMParticle, ParticleController>> controllers = new ArrayList<>();
    private final ParticleOptions particleOptions;
    private int color = 0xffffff;
    private int lifetime = -1;
    private int spawnTicks = 0;

    public LeaveTrailController(AMParticle particle, ParticleOptions particleOptions) {
        super(particle);
        this.particleOptions = particleOptions;
    }

    public LeaveTrailController setColor(int color) {
        this.color = color;
        return this;
    }

    public LeaveTrailController setLifetime(int lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    public LeaveTrailController addController(Function<AMParticle, ParticleController> controller) {
        controllers.add(controller);
        return this;
    }

    @Override
    public void tick() {
        spawnTicks++;
        if (spawnTicks == 2) {
            spawnTicks = 0;
            AMParticle p = new AMParticle(particle.level(), particle.getX(), particle.getY(), particle.getZ(), particleOptions);
            p.setLifetime(lifetime);
            p.setColor(color);
            for (Function<AMParticle, ParticleController> controller : controllers) {
                p.addController(controller.apply(p));
            }
        }
    }
}
