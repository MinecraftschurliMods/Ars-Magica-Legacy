package com.github.minecraftschurlimods.arsmagicalegacy.api.magic;

import net.minecraft.world.entity.player.Player;

/**
 * Interface for shrink related helper methods.
 */
public interface IShrinkHelper {
    /**
     * Whether the player is currently shrunk or not.
     *
     * @param player the player to get the shrink status for
     * @return whether the player is currently shrunk or not
     */
    boolean isShrunk(Player player);

    /**
     * Sets the given player's shrink status.
     *
     * @param player the player to set the shrink status for
     * @param shrunk true if the player should be shrunk, false otherwise
     */
    void setShrunk(Player player, boolean shrunk);
}
