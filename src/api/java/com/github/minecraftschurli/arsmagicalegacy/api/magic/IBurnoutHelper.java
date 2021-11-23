package com.github.minecraftschurli.arsmagicalegacy.api.magic;

import net.minecraft.world.entity.LivingEntity;

/**
 * Interface for burnout related helper methods.
 */
public interface IBurnoutHelper {
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
}
