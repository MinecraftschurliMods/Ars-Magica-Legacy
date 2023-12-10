package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

import net.minecraft.world.entity.Entity;

public class OrbitEntityController extends ParticleController {
    private final Entity target;
    private final double speed;
    private final boolean clockwise;
    private double angle;
    private double distance;

    public OrbitEntityController(AMParticle particle, Entity target, double speed) {
        super(particle);
        this.target = target;
        this.speed = speed;
        clockwise = particle.random().nextBoolean();
        angle = particle.random().nextInt(360);
        distance = 1 + particle.random().nextDouble() * 0.5;
    }

    public OrbitEntityController setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    @Override
    public void tick() {
        if (target == null || !target.isAlive()) {
            finish();
            return;
        }
        angle += clockwise ? speed : -speed;
        while (angle < 0) {
            angle += 360;
        }
        angle %= 360;
        particle.setPosition(target.getX() + Math.cos(angle) * distance, particle.getY(), target.getZ() + Math.sin(angle) * distance);
    }
}
