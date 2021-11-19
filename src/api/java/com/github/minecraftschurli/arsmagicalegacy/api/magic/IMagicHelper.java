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
     * Get the mana value of the given entity.
     *
     * @param entity the entity to get the mana value for
     * @return the mana value of the entity
     */
    float getMana(LivingEntity entity);

    /**
     * Get the maximum mana value of the given entity.
     *
     * @param entity the entity to get the maximum mana value for
     * @return the maximum mana value of the entity
     */
    float getMaxMana(LivingEntity entity);

    /**
     * Increase the mana value of the given entity.
     *
     * @param entity the entity to increase the mana value for
     * @return true if it succeeded, false otherwise
     */
    boolean increaseMana(LivingEntity entity, float amount);

    /**
     * Decrease the mana value of the given entity.
     *
     * @param entity the entity to decrease the mana value for
     * @return true if it succeeded, false otherwise
     */
    boolean decreaseMana(LivingEntity entity, float amount);

    /**
     * Decrease the mana value of the given entity.
     * If force is true and the entity has less mana than given sets the mana value to 0.
     *
     * @param entity the entity to decrease the mana value for
     * @return true if the entity had enough mana, false otherwise
     */
    boolean decreaseMana(LivingEntity entity, float mana, boolean force);

    /**
     * Sets the mana value of the given entity.
     *
     * @param entity the entity to set the mana value for
     * @return true if it succeeded, false otherwise
     */
    boolean setMana(LivingEntity entity, float amount);

    /**
     * Get the burnout value of the given entity.
     *
     * @param entity the entity to get the burnout value for
     * @return the burnout value of the entity
     */
    float getBurnout(LivingEntity entity);

    /**
     * Get the maximum burnout value of the given entity.
     *
     * @param entity the entity to get the maximum burnout value for
     * @return the maximum burnout value of the entity
     */
    float getMaxBurnout(LivingEntity entity);

    /**
     * Increase the burnout value of the given entity.
     *
     * @param entity the entity to increase the burnout value for
     * @return true if it succeeded, false otherwise
     */
    boolean increaseBurnout(LivingEntity entity, float amount);

    /**
     * Decrease the burnout value of the given entity.
     *
     * @param entity the entity to decrease the burnout value for
     * @return true if it succeeded, false otherwise
     */
    boolean decreaseBurnout(LivingEntity entity, float amount);

    /**
     * Sets the burnout value of the given entity.
     *
     * @param entity the entity to set the burnout value for
     * @return true if it succeeded, false otherwise
     */
    boolean setBurnout(LivingEntity entity, float amount);

    /**
     * Check if the given player knows what magic is.
     *
     * @param player the player to check
     * @return true if the entity knows magic, false otherwise
     */
    boolean knowsMagic(Player player);
}
