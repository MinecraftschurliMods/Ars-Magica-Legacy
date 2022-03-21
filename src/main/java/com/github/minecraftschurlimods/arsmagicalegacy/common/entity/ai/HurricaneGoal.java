package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class HurricaneGoal extends Goal {
    private final AirGuardian airGuardian;
    private LivingEntity target;
    private int cooldown = 0;

    public HurricaneGoal(AirGuardian airGuardian) {
        this.airGuardian = airGuardian;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || airGuardian.getAirGuardianAction() != AirGuardian.AirGuardianAction.IDLE) return false;
        if (airGuardian.getTarget() == null || airGuardian.getTarget().isDeadOrDying() || airGuardian.getTarget().distanceToSqr(airGuardian) > 25)
            return false;
        target = airGuardian.getTarget();
        return true;
    }

    @Override
    public boolean canContinueToUse() {
//        if (airGuardian.getHitCount() >= 10) {
//            airGuardian.setAirGuardianAction(AirGuardian.AirGuardianAction.IDLE);
//            cooldown = 20;
//            return false;
//        }
        if (airGuardian.getTarget() == null || airGuardian.getTarget().isDeadOrDying() || airGuardian.getTicksInAction() > AirGuardian.AirGuardianAction.SPINNING.getMaxActionTime()) {
            if (!airGuardian.getLevel().isClientSide()) {
                int y = (int) (airGuardian.getY() + 1);
                for (int x = -1; x <= 1; ++x) {
                    for (int z = -1; z <= 1; ++z) {
                        BlockPos pos = new BlockPos(airGuardian.getX() + x, y, airGuardian.getZ() + z);
                        while (!airGuardian.getLevel().canSeeSky(pos) && airGuardian.getLevel().getBlockState(pos).getBlock() != Blocks.BEDROCK) {
                            if (Math.abs(y - airGuardian.getY()) > 10) break;
                            airGuardian.getLevel().destroyBlock(new BlockPos(airGuardian.getX() + x, y++, airGuardian.getZ() + z), true);
                        }
                        y = (int) (airGuardian.getY() + 2);
                    }
                }
            }
            for (LivingEntity e : airGuardian.getLevel().getEntitiesOfClass(LivingEntity.class, airGuardian.getBoundingBox().inflate(2, 2, 2))) {
                if (e != airGuardian) {
                    //AMVector3 movement = MathUtilities.GetMovementVectorBetweenPoints(new AMVector3(host), new AMVector3(ent));
                    Vec3 movement = new Vec3(1, 1, 1); // wrong
                    float factor = 2.5f;
                    double x = -(movement.x() * factor);
                    double y = 2.5f;
                    double z = -(movement.z() * factor);
                    e.hurt(DamageSource.MAGIC, 12);
                    e.push(x, y, z);
                    e.fallDistance = 0f;
                }
            }
            airGuardian.setAirGuardianAction(AirGuardian.AirGuardianAction.IDLE);
            cooldown = 20;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        airGuardian.getLookControl().setLookAt(target, 30, 30);
        for (LivingEntity e : airGuardian.getLevel().getEntitiesOfClass(LivingEntity.class, airGuardian.getBoundingBox().inflate(6, 3, 6))) {
            if (e != airGuardian) {
//                if (!airGuardian.getLevel().isClientSide() && e instanceof Whirlwind) {
//                    e.remove(Entity.RemovalReason.KILLED);  // should be removeAfterChangingDimensions()
//                    continue;
//                }
//                AMVector3 movement = MathUtilities.GetMovementVectorBetweenPoints(new AMVector3(host).add(new AMVector3(0, host.getEyeHeight(), 0)), new AMVector3(ent));
                Vec3 movement = new Vec3(1, 1, 1); // wrong
                float factor = 0.18f;
                double x = (movement.x() * factor);
                double y = (movement.y() * factor);
                double z = (movement.z() * factor);
                double dX = e.getDeltaMovement().x();
                double dY = e.getDeltaMovement().y();
                double dZ = e.getDeltaMovement().z();
                e.push(x, y, z);
                if (Math.abs(dX) > Math.abs(x * 2)) {
                    e.setDeltaMovement(x, dY, dZ);
                }
                if (Math.abs(dY) > Math.abs(y * 2)) {
                    e.setDeltaMovement(dX, y, dZ);
                }
                if (Math.abs(dZ) > Math.abs(z * 2)) {
                    e.setDeltaMovement(dX, dY, z);
                }
                e.fallDistance = 0f;
            }
        }
    }
}
