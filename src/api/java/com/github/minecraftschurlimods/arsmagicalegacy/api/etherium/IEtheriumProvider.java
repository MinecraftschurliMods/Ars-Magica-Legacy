package com.github.minecraftschurlimods.arsmagicalegacy.api.etherium;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * TODO doc
 */
public interface IEtheriumProvider {
    int consume(Level level, BlockPos consumerPos, int amount);

    int getAmount();

    EtheriumType getType();

    int getMax();
}
