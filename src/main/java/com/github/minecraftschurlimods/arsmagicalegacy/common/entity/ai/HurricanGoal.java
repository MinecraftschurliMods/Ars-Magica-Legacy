package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import com.ibm.icu.math.MathContext;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.MathUtil;

import java.util.Iterator;
import java.util.List;

public class HurricanGoal extends Goal {
    private final AirGuardian  airGuardian;
    private       LivingEntity target;
    private final float        moveSpeed;
    private       int          cooldown = 0;

    public HurricanGoal(AirGuardian airGuardian, float moveSpeed) {
        this.airGuardian = airGuardian;
        this.moveSpeed = moveSpeed;
    }

    @Override
    public boolean canUse() {
        if (cooldown-- > 0 || airGuardian.getAirGuardianAction() != AirGuardian.AirGuardianAction.IDLE) {
            return false;
        }
        if (airGuardian.getTarget() == null || airGuardian.getTarget().isDeadOrDying() || airGuardian.getTarget().distanceToSqr(airGuardian) > 25) {
            return false;
        }
        this.target = airGuardian.getTarget();
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
            if (!airGuardian.level.isClientSide()) {
                int y = (int) (airGuardian.getY() + 1);
                for (int x = -1; x <= 1; ++x) {
                    for (int z = -1; z <= 1; ++z) {
                        BlockPos pos = new BlockPos(airGuardian.getX() + x, y, airGuardian.getZ() + z);
                        while (!airGuardian.level.canSeeSky(pos) && airGuardian.level.getBlockState(pos).getBlock() != Blocks.BEDROCK) {
                            if (Math.abs(y - airGuardian.getY()) > 10) {
                                break;
                            }
                            airGuardian.level.destroyBlock(new BlockPos(airGuardian.getX() + x, y++, airGuardian.getZ() + z), true);
                        }
                        y = (int) (airGuardian.getY() + 2);
                    }
                }
            }

            List<LivingEntity> nearbyEntities = airGuardian.level.getEntitiesOfClass(LivingEntity.class, airGuardian.getBoundingBox().inflate(2, 2, 2));
            for (LivingEntity e : nearbyEntities) {
                if (e != airGuardian) {
                    //AMVector3 movement = MathUtilities.GetMovementVectorBetweenPoints(new AMVector3(host), new AMVector3(ent));
                    Vec3 movement = new Vec3(1, 1, 1); // wrong
                    float factor = 2.5f;

                    double x = -(movement.x() * factor);
                    double y = 2.5f;
                    double z = -(movement.z() * factor);

                    //e.hurt(DamageSource.causeWindDamage(airGuardian), 12);
                    e.push(x, y, z);

                    if (e instanceof Player) {
                        //AMNetHandler.INSTANCE.sendVelocityAddPacket(host.worldObj, ent, x, y, z);
                    }
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
        List<LivingEntity> nearbyEntities = airGuardian.level.getEntitiesOfClass(LivingEntity.class, airGuardian.getBoundingBox().inflate(6, 3, 6));
        for (LivingEntity e : nearbyEntities) {
            if (e != airGuardian) {
//                if (!airGuardian.level.isClientSide() && e instanceof Whirlwind) {
//                    e.remove(Entity.RemovalReason.KILLED);  // should be removeAfterChangingDimensions()
//                    continue;
//                }

                //MVector3 movement = MathUtilities.GetMovementVectorBetweenPoints(new AMVector3(host).add(new AMVector3(0, host.getEyeHeight(), 0)), new AMVector3(ent));
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
                    e.setDeltaMovement(x * (dX / dX), dY, dZ);
                }
                if (Math.abs(dY) > Math.abs(y * 2)) {
                    e.setDeltaMovement(dX, y * (dY / dY), dZ);
                }
                if (Math.abs(dZ) > Math.abs(z * 2)) {
                    e.setDeltaMovement(dX, dY, z * (dZ / dZ));
                }

                if (e instanceof Player) {
                    //AMNetHandler.INSTANCE.sendVelocityAddPacket(host.worldObj, ent, -(oX - ent.motionX), -(oY - ent.motionY), -(oZ - ent.motionZ));
                }
                e.fallDistance = 0f;
            }
        }
    }
}