package com.github.minecraftschurlimods.arsmagicalegacy.api.magic;

import net.minecraft.world.entity.LivingEntity;

/**
 * Interface for mana related helper methods.
 */
public interface IManaHelper {
    /**
     * @param entity The entity to get the mana value for.
     * @return The mana value of the entity.
     */
    float getMana(LivingEntity entity);

    /**
     * @param entity The entity to get the maximum mana value for.
     * @return The maximum mana value of the entity.
     */
    float getMaxMana(LivingEntity entity);

    /**
     * Increases the mana value of the given entity.
     *
     * @param entity The entity to increase the mana value for.
     * @param amount The amount to increase the mana value by.
     * @return True if the operation succeeded, false otherwise.
     */
    boolean increaseMana(LivingEntity entity, float amount);

    /**
     * Decreases the mana value of the given entity. If the entity has less mana than given, this method does nothing.
     *
     * @param entity The entity to decrease the mana value for.
     * @return True if the entity had enough mana, false otherwise.
     */
    boolean decreaseMana(LivingEntity entity, float amount);

    /**
     * Decreases the mana value of the given entity.
     * If force is true and the entity has less mana than given, sets the mana value to 0.
     * If force is false and the entity has less mana than given, this method does nothing.
     *
     * @param entity The entity to decrease the mana value for.
     * @return True if the entity had enough mana, false otherwise.
     */
    boolean decreaseMana(LivingEntity entity, float mana, boolean force);

    /**
     * Sets the mana value of the given entity.
     *
     * @param entity The entity to set the mana value for.
     * @param amount The amount to set the mana value to.
     */
    void setMana(LivingEntity entity, float amount);
}
