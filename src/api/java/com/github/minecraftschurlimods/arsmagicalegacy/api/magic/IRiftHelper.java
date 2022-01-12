package com.github.minecraftschurlimods.arsmagicalegacy.api.magic;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * Interface for rift related helper methods.
 */
public interface IRiftHelper {
    /**
     * Get the rift item handler for the given player.
     *
     * @param player the player to get the rift item handler for
     * @return the rift item handler for the given player
     */
    IItemHandlerModifiable getRift(Player player);
}
