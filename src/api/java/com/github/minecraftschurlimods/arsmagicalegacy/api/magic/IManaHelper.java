package com.github.minecraftschurlimods.arsmagicalegacy.api.magic;

import net.minecraft.world.entity.LivingEntity;

/**
 * Interface for mana related helper methods.
 */
public interface IManaHelper {
    /**
     * Decrease the mana value of the given entity.
     * If force is true and the entity has less mana than given sets the mana value to 0.
     *
     * @param entity the entity to decrease the mana value for
     * @return true if the entity had enough mana, false otherwise
     */
    boolean decreaseMana(LivingEntity entity, float mana, boolean force);

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
     * Sets the mana value of the given entity.
     *
     * @param livingEntity the entity to set the mana value for
     */
    void setMana(LivingEntity livingEntity, float amount);
}
