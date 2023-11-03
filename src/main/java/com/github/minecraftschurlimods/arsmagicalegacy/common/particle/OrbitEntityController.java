package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

import net.minecraft.world.entity.Entity;

public class OrbitEntityController extends ParticleController {
    private final Entity target;
    private final double speed;
    private final boolean clockwise;
    private double angle;
    private double distance;
    private double offset;
    private double targetY;
    private boolean ignoreY;
    private Double orbitY = null;

    public OrbitEntityController(AMParticle particle, boolean stopOtherControllers, boolean killParticleOnFinish, Entity target, double speed) {
        super(particle, stopOtherControllers, killParticleOnFinish);
        this.target = target;
        this.speed = speed;
        clockwise = particle.random().nextBoolean();
        angle = particle.random().nextInt(360);
        distance = 1 + particle.random().nextDouble() * 0.5;
        offset = particle.getY() - target.getEyeY();
        newTarget();
    }

    public OrbitEntityController setOrbitY(double orbitY) {
        this.orbitY = orbitY;
        return this;
    }

    public OrbitEntityController setIgnoreY(boolean ignoreY) {
        this.ignoreY = ignoreY;
        return this;
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
        if (Math.abs(targetY - offset) < 0.1) {
            newTarget();
        }
        if (targetY < offset) {
            offset -= speed / 4;
        } else if (targetY > offset) {
            offset += speed / 4;
        }
        angle += clockwise ? speed : -speed;
        while (angle < 0) {
            angle += 360;
        }
        angle %= 360;
        double posY = particle.getY();
        if (!ignoreY) {
            posY = orbitY != null ? target.getEyeY() + orbitY : target.getY() - target.getEyeHeight() + offset;
        }
        particle.setPosition(target.getX() + Math.cos(angle) * distance, posY, target.getZ() + Math.sin(angle) * distance);
    }

    private void newTarget() {
        targetY = target != null ? particle.random().nextDouble() * target.getBbHeight() : 0;
    }
}
