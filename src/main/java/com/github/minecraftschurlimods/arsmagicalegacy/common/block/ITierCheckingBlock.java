package com.github.minecraftschurlimods.arsmagicalegacy.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * Marks a block that has some kind of tier system.
 */
public interface ITierCheckingBlock {
    /**
     * @param level The level to use.
     * @param pos   The position of the block to get the tier for.
     * @return The tier of the block in the given level at the given position.
     */
    int getTier(Level level, BlockPos pos);
}
