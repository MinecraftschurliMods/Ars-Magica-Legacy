package com.github.minecraftschurlimods.arsmagicalegacy.common.particle;

import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ArcToPointController extends ParticleController {
    private final double speed;
    private final Vec3 start;
    private final Vec3 end;
    private final Vec3 control1;
    private final Vec3 control2;
    private double percent = 0;

    public ArcToPointController(AMParticle particle, Vec3 start, Vec3 end, double speed) {
        super(particle);
        this.speed = speed;
        this.start = start;
        this.end = end;
        Vec3 offset = new Vec3(particle.random().nextDouble() * 10 - 5, 0, particle.random().nextDouble() * 10 - 5);
        control1 = new Vec3(start.x + (end.x - start.x) / 3, start.y + (end.y - start.y) / 3, start.z + (end.z - start.z) / 3).add(offset);
        control2 = new Vec3(start.x + (end.x - start.x) / 3 * 2, start.y + (end.y - start.y) / 3 * 2, start.z + (end.z - start.z) / 3 * 2).add(offset);
    }

    public ArcToPointController(AMParticle particle, Vec3 end, double speed) {
        this(particle, new Vec3(particle.getX(), particle.getY(), particle.getZ()), end, speed);
    }

    @Override
    public void tick() {
        percent += speed;
        if (percent >= 1) {
            finish();
            return;
        }
        Vec3 bezier = AMUtil.bezier(start, control1, control2, end, percent);
        particle.setPos(bezier.x, bezier.y, bezier.z);
    }
}
