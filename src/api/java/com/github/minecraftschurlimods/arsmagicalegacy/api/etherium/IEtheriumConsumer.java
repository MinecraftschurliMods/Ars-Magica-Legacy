package com.github.minecraftschurlimods.arsmagicalegacy.api.etherium;

import net.minecraft.core.BlockPos;

import java.util.List;

/**
 * Interface for etherium consumers to implement.
 */
public interface IEtheriumConsumer {
    /**
     * @return A list of etherium providers that are bound to this consumer.
     */
    List<IEtheriumProvider> getBoundProviders();

    /**
     * Binds a new provider to this consumer.
     * @param pos The position of the provider to bind to this consumer.
     */
    void bindProvider(BlockPos pos);
}
