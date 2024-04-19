package com.github.minecraftschurlimods.arsmagicalegacy.api.magic;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

/**
 * Interface for rift related helper methods.
 */
public interface IRiftHelper {
    /**
     * @param player The player to get the rift item handler for.
     * @return The rift item handler for the given player.
     */
    IItemHandlerModifiable getRift(Player player);
}
