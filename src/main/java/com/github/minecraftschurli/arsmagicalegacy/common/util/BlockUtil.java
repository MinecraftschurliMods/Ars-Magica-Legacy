package com.github.minecraftschurli.arsmagicalegacy.common.util;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class BlockUtil {
    public static VoxelShape joinShapes(VoxelShape first, VoxelShape second, VoxelShape... others) {
        VoxelShape result = Shapes.join(first, second, BooleanOp.OR);
        for (VoxelShape shape : others) {
            result = Shapes.join(result, shape, BooleanOp.OR);
        }
        return result;
    }
}
