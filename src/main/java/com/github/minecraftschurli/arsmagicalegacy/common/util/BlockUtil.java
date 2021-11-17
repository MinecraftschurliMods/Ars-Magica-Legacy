package com.github.minecraftschurli.arsmagicalegacy.common.util;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class BlockUtil {
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
