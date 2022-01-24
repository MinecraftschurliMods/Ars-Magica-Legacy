package com.github.minecraftschurlimods.arsmagicalegacy.api.etherium;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * Interface for etherium providers to implement.
 */
public interface IEtheriumProvider {
    /**
     * Consumes etherium from this provider.
     *
     * @param level The level of this etherium provider.
     * @param consumerPos The position of the etherium consumer.
     * @param amount The etherium amount to consume.
     * @return The etherium amount that was actually consumed.
     */
    int consume(Level level, BlockPos consumerPos, int amount);

    /**
     * @return The etherium amount stored in this etherium provider.
     */
    int getAmount();

    /**
     * @return The etherium type stored in this etherium provider.
     */
    EtheriumType getType();

    /**
     * @return The max etherium amount that can be stored in this etherium provider.
     */
    int getMax();
}
