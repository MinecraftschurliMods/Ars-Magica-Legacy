package com.github.minecraftschurli.arsmagicalegacy.api.magic;

import net.minecraft.world.entity.player.Player;

/**
 * Interface for magic related helper methods.
 */
public interface IMagicHelper {
    /**
     * Get the current magic level for the given player.
     *
     * @param player the player to get the level for
     * @return the level of the player
     */
    int getLevel(Player player);

    /**
     * Get the current magic xp for the given player.
     *
     * @param player the player to get the xp for
     * @return the xp of the player
     */
    float getXp(Player player);

    /**
     * Award the given player the supplied amount of xp.
     * (this method handles leveling automatically)
     *
     * @param player the player to award the xp to
     * @param amount the amount of xp to award
     */
    void awardXp(Player player, float amount);

    /**
     * Check if the given player knows what magic is.
     *
     * @param player the player to check
     * @return true if the entity knows magic, false otherwise
     */
    boolean knowsMagic(Player player);

    /**
     * Check if the player has magic vision.
     * (this is mainly a check for the magitech goggles)
     *
     * @param player the player to check
     * @return true if the player has magic vision
     */
    //boolean hasMagicVision(Player player);

    /**
     * Get the level of truesight the player has.
     * (mainly through the potion effect)
     *
     * @param player the player to check
     * @return the level of truesight
     */
    //int getTrueSightLevel(Player player);
}
