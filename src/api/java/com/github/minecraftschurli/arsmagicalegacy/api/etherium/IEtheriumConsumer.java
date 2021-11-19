package com.github.minecraftschurli.arsmagicalegacy.api.etherium;

import net.minecraft.core.BlockPos;

import java.util.List;

/**
 *
 */
public interface IEtheriumConsumer {
    List<IEtheriumProvider> getBoundProviders();

    void bindProvider(BlockPos pos);
}
