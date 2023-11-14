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
        double deltaX = target.getX() - particle.getX();
        double deltaZ = target.getZ() - particle.getZ();
        double angle = Math.atan2(deltaZ, deltaX);
        double deltaY;
        if (target instanceof LivingEntity living) {
            deltaY = particle.getY() - living.getEyeY();
        } else if (target instanceof ItemEntity) {
            deltaY = particle.getY() - target.getY();
        } else {
            deltaY = particle.getY() - (target.getY() + target.getBbHeight() / 2);
        }
        particle.setPosition(particle.getX() + speed * Math.cos(angle), particle.getY() - speed * Math.sin(-Math.atan2(deltaY, Math.sqrt(deltaX * deltaX + deltaZ * deltaZ))), particle.getZ() + speed * Math.sin(angle));
    }
}
