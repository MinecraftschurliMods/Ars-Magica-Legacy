package com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar;

import net.minecraft.world.level.block.state.BlockState;

public interface IStructureMatcher {
    boolean testBlock(BlockState state);

    boolean testStair(BlockState state);
}
