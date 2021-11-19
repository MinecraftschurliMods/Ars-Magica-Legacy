package com.github.minecraftschurli.arsmagicalegacy.api.etherium;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Set;

public interface IEtheriumProvider {
    boolean provides(Set<EtheriumType> types);

    int consume(Level level, BlockPos consumerPos, int amount);
}
