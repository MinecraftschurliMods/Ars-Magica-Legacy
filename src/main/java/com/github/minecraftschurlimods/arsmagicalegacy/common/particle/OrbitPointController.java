package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.world.phys.Vec3;

public class OrbitPointController extends ParticleController {
    private final Vec3 target;
    private final double speed;
    private final boolean clockwise;
    private double distance;
    private double angle;
    private boolean currentDistance;

    public OrbitPointController(AMParticle particle, Vec3 target, double speed) {
        super(particle);
        this.target = target;
        this.speed = speed;
        clockwise = particle.random().nextBoolean();
        angle = particle.random().nextInt(360);
        distance = 1 + particle.random().nextDouble() * 0.5;
    }

    public OrbitPointController setDistance(double distance) {
        this.distance = distance;
        currentDistance = false;
        return this;
    }

    @Override
    public void tick() {
        angle += clockwise ? speed : -speed;
        angle = AMUtil.wrap(angle, 360);
        double dx = target.x - particle.getX();
        double dz = target.z - particle.getZ();
        double d = Math.sqrt(dx * dx + dz * dz);
        if (!currentDistance) {
            d = Math.min(d, distance);
            if (d == distance) {
                currentDistance = true;
            }
        }
        particle.setPosition(target.x + Math.cos(angle) * d, particle.getY(), target.z + Math.sin(angle) * d);
    }
}
