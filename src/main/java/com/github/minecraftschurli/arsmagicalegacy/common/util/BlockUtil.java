package com.github.minecraftschurli.arsmagicalegacy.common.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Predicate;

public final class BlockUtil {
    /**
     * Performs a ray trace from "from" to "to". Modified version of {@link ProjectileUtil#getHitResult(Entity, Predicate)}
     *
     * @param entity       the entity that causes this ray trace
     * @param blockContext the block clipping context to use
     * @param fluidContext the fluid clipping context to use
     * @return A hit result, representing the ray trace.
     */
    public static HitResult getHitResult(Vec3 from, Vec3 to, Entity entity, ClipContext.Block blockContext, ClipContext.Fluid fluidContext) {
        HitResult hitResult = entity.level.clip(new ClipContext(from, to, blockContext, fluidContext, entity));
        if (hitResult.getType() != HitResult.Type.MISS) {
            to = hitResult.getLocation();
        }
        HitResult entityHitResult = ProjectileUtil.getEntityHitResult(entity.level, entity, from, to, entity.getBoundingBox().expandTowards(entity.getDeltaMovement()).inflate(1), e -> true);
        if (entityHitResult != null) {
            hitResult = entityHitResult;
        }
        return hitResult;
    }

    /**
     * Joins multiple VoxelShapes into one. If only two VoxelShapes need to be joined, use {@link Shapes#join} instead.
     *
     * @param first  VoxelShape #1
     * @param second VoxelShape #2
     * @param others other VoxelShapes
     * @return A new VoxelShape, consisting of all parameter VoxelShapes.
     */
    public static VoxelShape joinShapes(VoxelShape first, VoxelShape second, VoxelShape... others) {
        VoxelShape result = Shapes.join(first, second, BooleanOp.OR);
        for (VoxelShape shape : others) {
            result = Shapes.join(result, shape, BooleanOp.OR);
        }
        return result;
    }
}
