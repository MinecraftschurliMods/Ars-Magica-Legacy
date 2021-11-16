package com.github.minecraftschurli.arsmagicalegacy.api.magic;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * A magic helper handles the level/mana/burnout data
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
     * Get the mana value of the given player.
     *
     * @param entity the player to get the mana value for
     * @return the mana value of the player
     */
    float getMana(LivingEntity entity);

    /**
     * Get the maximum mana value of the given player.
     *
     * @param entity the player to get the maximum mana value for
     * @return the maximum mana value of the player
     */
    float getMaxMana(LivingEntity entity);

    /**
     * Increase the mana value of the given player.
     *
     * @param entity the player to increase the mana value for
     * @return true if it succeeded, false otherwise
     */
    boolean increaseMana(LivingEntity entity, float amount);

    /**
     * Decrease the mana value of the given player.
     *
     * @param entity the player to decrease the mana value for
     * @return true if it succeeded, false otherwise
     */
    boolean decreaseMana(LivingEntity entity, float amount);

    /**
     * Sets the mana value of the given player.
     *
     * @param entity the player to set the mana value for
     * @return true if it succeeded, false otherwise
     */
    boolean setMana(LivingEntity entity, float amount);

    /**
     * Get the burnout value of the given player.
     *
     * @param entity the player to get the burnout value for
     * @return the burnout value of the player
     */
    float getBurnout(LivingEntity entity);

    /**
     * Get the maximum burnout value of the given player.
     *
     * @param entity the player to get the maximum burnout value for
     * @return the maximum burnout value of the player
     */
    float getMaxBurnout(LivingEntity entity);

    /**
     * Increase the burnout value of the given player.
     *
     * @param entity the player to increase the burnout value for
     * @return true if it succeeded, false otherwise
     */
    boolean increaseBurnout(LivingEntity entity, float amount);

    /**
     * Decrease the burnout value of the given player.
     *
     * @param entity the player to decrease the burnout value for
     * @return true if it succeeded, false otherwise
     */
    boolean decreaseBurnout(LivingEntity entity, float amount);

    /**
     * Sets the burnout value of the given player.
     *
     * @param entity the player to set the burnout value for
     * @return true if it succeeded, false otherwise
     */
    boolean setBurnout(LivingEntity entity, float amount);

    /**
     * Check if the given player knows what magic is.
     *
     * @param player the player to check
     * @return true if the player knows magic, false otherwise
     */
    boolean knowsMagic(Player player);
}
