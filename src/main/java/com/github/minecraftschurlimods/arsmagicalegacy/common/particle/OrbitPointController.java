package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

import net.minecraft.world.phys.Vec3;

public class OrbitPointController extends ParticleController {
    private final Vec3 target;
    private boolean clockwise;
    private double distance;
    private double speed;
    private double angle;
    private boolean currentDistance;
    private double shrinkSpeed = 0;
    private double shrinkDistance = 0;

    public OrbitPointController(AMParticle particle, Vec3 target) {
        super(particle);
        this.target = target;
        clockwise = particle.random().nextBoolean();
        angle = particle.random().nextInt(360);
        distance = 1 + particle.random().nextDouble() * 0.5;
    }

    public OrbitPointController setDistance(double distance) {
        this.distance = distance;
        currentDistance = false;
        return this;
    }

    public OrbitPointController setSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    public OrbitPointController setClockwise(boolean clockwise) {
        this.clockwise = clockwise;
        return this;
    }

    public OrbitPointController setAngle(float angle) {
        this.angle = angle;
        return this;
    }

    public OrbitPointController useCurrentDistance(boolean currentDistance) {
        this.currentDistance = currentDistance;
        return this;
    }

    public OrbitPointController setShrinking(double speed, double targetDistance) {
        shrinkSpeed = speed;
        shrinkDistance = targetDistance;
        return this;
    }

    @Override
    public void tick() {
        angle += clockwise ? speed : -speed;
        while (angle < 0) {
            angle += 360;
        }
        angle %= 360;
        double d;
        if (currentDistance) {
            double dx = target.x - particle.getX();
            double dz = target.z - particle.getZ();
            d = Math.sqrt(dx * dx + dz * dz);
        } else {
            if (shrinkDistance != 0) {
                if (distance <= shrinkDistance) {
                    shrinkDistance = 0;
                } else if (distance < shrinkDistance + shrinkSpeed * 10) {
                    distance -= (distance - shrinkDistance) * shrinkSpeed;
                } else {
                    distance -= shrinkSpeed;
                }
            }
            d = distance;
        }
        particle.setPosition(target.x + Math.cos(angle) * d, particle.getY(), target.z + Math.sin(angle) * d);
    }
}
