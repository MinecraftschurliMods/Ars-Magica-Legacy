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
    private int spawnDelay = 2;
    private int spawnTicks = 0;
    private boolean gravity = false;
    private double offsetX, offsetY, offsetZ;

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

    public LeaveTrailController setSpawnDelay(int spawnDelay) {
        this.spawnDelay = spawnDelay;
        return this;
    }

    public LeaveTrailController setOffset(double x, double y, double z) {
        offsetX = x;
        offsetY = y;
        offsetZ = z;
        return this;
    }

    public LeaveTrailController enableGravity() {
        this.gravity = true;
        return this;
    }

    public LeaveTrailController addController(Function<AMParticle, ParticleController> controller) {
        controllers.add(controller);
        return this;
    }

    @Override
    public void tick() {
        spawnTicks++;
        if (spawnTicks == spawnDelay) {
            spawnTicks = 0;
            AMParticle p = new AMParticle(particle.level(), particle.getX(), particle.getY(), particle.getZ(), particleOptions);
            p.setLifetime(lifetime);
            p.setColor(color);
            p.addRandomOffset(offsetX, offsetY, offsetZ);
            if (gravity) {
                p.setGravity(1);
            }
            for (Function<AMParticle, ParticleController> controller : controllers) {
                p.addController(controller.apply(p));
            }
        }
    }
}
