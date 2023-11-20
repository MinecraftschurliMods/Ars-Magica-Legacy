package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ArcToEntityController extends ParticleController {
    private final Entity target;
    private final double speed;
    private final Vec3 start;
    private double percent = 0;

    public ArcToEntityController(AMParticle particle, Vec3 start, Entity target, double speed) {
        super(particle);
        this.target = target;
        this.speed = speed;
        this.start = start;
    }

    public ArcToEntityController(AMParticle particle, Entity target, double speed) {
        this(particle, new Vec3(particle.getX(), particle.getY(), particle.getZ()), target, speed);
    }

    @Override
    public void tick() {
        percent += speed;
        if (percent >= 1) {
            finish();
            return;
        }
        Vec3 offset = new Vec3(particle.random().nextDouble() * 10 - 5, 0, particle.random().nextDouble() * 10 - 5);
        Vec3 control1 = new Vec3(start.x + (target.getX() - start.x) / 3, start.y + (target.getY() - start.y) / 3, start.z + (target.getZ() - start.z) / 3).add(offset);
        Vec3 control2 = new Vec3(start.x + (target.getX() - start.x) / 3 * 2, start.y + (target.getY() - start.y) / 3 * 2, start.z + (target.getZ() - start.z) / 3 * 2).add(offset);
        Vec3 bezier = AMUtil.bezier(start, control1, control2, target.position(), percent);
        particle.setPos(bezier.x, bezier.y, bezier.z);
    }
}
