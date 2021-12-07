package com.github.minecraftschurli.arsmagicalegacy.common.block.altar;

import net.minecraft.world.level.block.state.BlockState;

public interface ICapMatcher {
    boolean testCap(BlockState state);
}
