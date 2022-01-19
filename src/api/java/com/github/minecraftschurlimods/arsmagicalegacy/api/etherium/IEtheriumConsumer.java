package com.github.minecraftschurlimods.arsmagicalegacy.api.etherium;

import net.minecraft.core.BlockPos;

import java.util.List;

/**
 * TODO doc
 */
public interface IEtheriumConsumer {
    List<IEtheriumProvider> getBoundProviders();

    void bindProvider(BlockPos pos);
}
