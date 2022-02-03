package com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar;

import net.minecraft.world.level.block.state.BlockState;

public interface IStructureMatcher {
    /**
     * @param state The block state to test.
     * @return Whether the given block state matches this material.
     */
    boolean testBlock(BlockState state);

    /**
     * @param state The block state to test.
     * @return Whether the given block state matches this material's stair.
     */
    boolean testStair(BlockState state);
}
