package com.github.minecraftschurlimods.arsmagicalegacy.api.magic;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * Interface for rift related helper methods.
 */
public interface IRiftHelper {
    /**
     * @param player The player to get the rift item handler for.
     * @return The rift item handler for the given player.
     */
    LazyOptional<? extends IItemHandlerModifiable> getRift(Player player);
}
