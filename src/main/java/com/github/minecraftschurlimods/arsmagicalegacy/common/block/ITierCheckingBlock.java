package com.github.minecraftschurlimods.arsmagicalegacy.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface ITierCheckingBlock {
    int getTier(Level world, BlockPos pos);
}
