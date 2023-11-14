package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;

public class ApproachEntityController extends ParticleController {
    private final Entity target;
    private final double speed;
    private final double distance;

    public ApproachEntityController(AMParticle particle, Entity target, double speed, double distance) {
        super(particle);
        this.target = target;
        this.speed = speed;
        this.distance = distance;
    }

    @Override
    public void tick() {
        if (target == null || target instanceof LivingEntity living && living.isDeadOrDying() || particle.distanceToSq(target.position()) <= distance * distance) {
            finish();
            return;
        }
        double dx = target.getX() - particle.getX();
        double dz = target.getZ() - particle.getZ();
        double angle = Math.atan2(dz, dx);
        double dy;
        if (target instanceof LivingEntity living) {
            dy = particle.getY() - living.getEyeY();
        } else if (target instanceof ItemEntity) {
            dy = particle.getY() - target.getY();
        } else {
            dy = particle.getY() - (target.getY() + target.getBbHeight() / 2);
        }
        particle.setPosition(particle.getX() + speed * Math.cos(angle), particle.getY() - speed * Math.sin(-Math.atan2(dy, Math.sqrt(dx * dx + dz * dz))), particle.getZ() + speed * Math.sin(angle));
    }
}
