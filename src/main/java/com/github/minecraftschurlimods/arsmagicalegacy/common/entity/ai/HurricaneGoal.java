package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Whirlwind;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class HurricaneGoal extends Goal {
    private final AirGuardian airGuardian;
    private int castTicks = 0;

    public HurricaneGoal(AirGuardian airGuardian) {
        this.airGuardian = airGuardian;
    }

    @Override
    public boolean canUse() {
        if (airGuardian.getAction() != AirGuardian.AirGuardianAction.IDLE) return false;
        airGuardian.setAction(AirGuardian.AirGuardianAction.SPINNING);
        LivingEntity entity = airGuardian.getTarget();
        return entity != null && !entity.isDeadOrDying() && !(entity.distanceToSqr(airGuardian) > 25);
    }

    @Override
    public boolean canContinueToUse() {
        castTicks++;
        return airGuardian.getTarget() != null && !airGuardian.getTarget().isDeadOrDying() && airGuardian.getTicksInAction() <= AirGuardian.AirGuardianAction.SPINNING.getMaxActionTime();
    }

    @Override
    public void stop() {
        super.stop();
        airGuardian.setAction(AirGuardian.AirGuardianAction.IDLE);
        castTicks = 0;
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = airGuardian.getTarget();
        if (target == null) return;
        airGuardian.lookAt(target, 30, 30);
        Level level = airGuardian.getLevel();
        if (!level.isClientSide()) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    int y = (int) (airGuardian.getY() + 1);
                    BlockPos pos = new BlockPos((int) (airGuardian.getX() + x), y, (int) (airGuardian.getZ() + z));
                    while (!level.canSeeSky(pos) && level.getBlockState(pos).getDestroySpeed(level, pos) != -1) {
                        if (Math.abs(y - airGuardian.getY()) > 10) break;
                        level.destroyBlock(new BlockPos(pos.getX(), y++, pos.getZ()), true);
                    }
                }
            }
        }
        if (castTicks >= 20) {
            for (Entity e : level.getEntitiesOfClass(Entity.class, airGuardian.getBoundingBox().inflate(6, 3, 6), e -> e != airGuardian)) {
                if (!level.isClientSide() && e instanceof Whirlwind) {
                    e.remove(Entity.RemovalReason.KILLED);
                    continue;
                }
                Vec3 movement = airGuardian.getEyePosition().subtract(e.position()).normalize();
                double x = -movement.x() * 2.5;
                double y = 2.5;
                double z = -movement.z() * 2.5;
                if (e instanceof LivingEntity living) {
                    living.hurt(DamageSource.MAGIC, 12);
                }
                e.push(x, y, z);
                e.fallDistance = 0f;
            }
            castTicks = 0;
        }
    }
}
