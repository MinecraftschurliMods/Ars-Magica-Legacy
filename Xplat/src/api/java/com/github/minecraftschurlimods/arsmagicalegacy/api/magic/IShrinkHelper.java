package com.github.minecraftschurlimods.arsmagicalegacy.api.magic;

import net.minecraft.world.entity.player.Player;

/**
 * Interface for shrink related helper methods.
 */
public interface IShrinkHelper {
    /**
     * @param player The player to get the shrink status for.
     * @return Whether the player is currently shrunk or not.
     */
    boolean isShrunk(Player player);

    /**
     * Sets the given player's shrink status.
     *
     * @param player The player to set the shrink status for.
     * @param shrunk True if the player should be shrunk, false otherwise.
     */
    void setShrunk(Player player, boolean shrunk);
}
