package com.github.minecraftschurlimods.arsmagicalegacy.api.magic;

import net.minecraft.world.entity.player.Player;

/**
 * Interface for magic related helper methods.
 */
public interface IMagicHelper {
    /**
     * @param player The player to get the magic level for.
     * @return The magic level of the given player.
     */
    int getLevel(Player player);

    /**
     * @param player The player to get the magic xp for.
     * @return The magic xp of the given player.
     */
    float getXp(Player player);

    /**
     * Awards the given player the given amount of magic xp. Also handles leveling.
     *
     * @param player The player to award the xp to.
     * @param amount The amount of xp to award.
     */
    void awardXp(Player player, float amount);

    /**
     * @param player The player to check.
     * @return True if the player knows magic, false otherwise.
     */
    boolean knowsMagic(Player player);

    /**
     * @param player The player to check.
     * @return True if the player has magic vision, false otherwise.
     */
    //boolean hasMagicVision(Player player);

    /**
     * @param player The player to check.
     * @return The level of true sight the player has.
     */
    //int getTrueSightLevel(Player player);
}
