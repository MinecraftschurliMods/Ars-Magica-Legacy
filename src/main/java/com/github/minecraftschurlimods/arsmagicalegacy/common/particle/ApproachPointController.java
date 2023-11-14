package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

public class ApproachPointController extends ParticleController {
    private final double targetX, targetY, targetZ;
    private final double speed;
    private final double distance;

    public ApproachPointController(AMParticle particle, double targetX, double targetY, double targetZ, double speed, double distance) {
        super(particle);
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.speed = speed;
        this.distance = distance;
    }

    @Override
    public void tick() {
        double x = particle.getX();
        double y = particle.getY();
        double z = particle.getZ();
        double dx = targetX - x;
        double dy = targetY - y;
        double dz = targetZ - z;
        double distanceToTarget = dx * dx + dy * dy + dz * dz;
        if (distanceToTarget <= distance * distance) {
            finish();
            return;
        }
        if (Math.abs(dx) > distance || Math.abs(dz) > distance) {
            double angle = Math.atan2(dz, dx);
            x = particle.getX() + speed * Math.cos(angle);
            z = particle.getZ() + speed * Math.sin(angle);
        }
        particle.setPosition(x, y, z);
    }
}
