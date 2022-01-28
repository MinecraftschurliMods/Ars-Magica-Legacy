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
     * @param level The current magic level.
     * @return The magic xp required to level from the given level to the next magic level.
     */
    float getXpForNextLevel(int level);

    /**
     * Awards the given amount of magic xp to the given player. Also handles leveling.
     *
     * @param player The player to award the magic xp to.
     * @param amount The amount of magic xp to award.
     */
    void awardXp(Player player, float amount);

    /**
     * Sets the given amount of magic xp for the given player. Also handles leveling.
     *
     * @param player The player to set the magic xp for.
     * @param amount The amount of magic xp to set.
     */
    void setXp(Player player, float amount);

    /**
     * Awards the given amount of magic levels to the given player. Also handles leveling.
     *
     * @param player The player to award the magic levels to.
     * @param amount The amount of magic levels to award.
     */
    void awardLevel(Player player, int levels);

    /**
     * Sets the given magic level for the given player. Also handles leveling.
     *
     * @param player The player to set the magic level for.
     * @param level  The magic level to set.
     */
    void setLevel(Player player, int level);

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
