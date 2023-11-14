package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

public class MoveInDirectionController extends ParticleController {
    private final double x, y, z;

    public MoveInDirectionController(AMParticle particle, double yaw, double pitch, double speed) {
        super(particle);
        x = Math.cos(Math.toRadians(yaw)) * speed;
        y = -Math.sin(Math.toRadians(pitch)) * speed;
        z = Math.sin(Math.toRadians(yaw)) * speed;
    }

    @Override
    public void tick() {
        particle.setPosition(particle.getX() + x, particle.getY() + y, particle.getZ() + z);
    }
}
