package com.github.minecraftschurlimods.arsmagicalegacy.api.magic;

import net.minecraft.world.entity.LivingEntity;

/**
 * Interface for burnout related helper methods.
 */
public interface IBurnoutHelper {
    /**
     * @param entity The entity to get the burnout value for.
     * @return The burnout value of the given entity.
     */
    float getBurnout(LivingEntity entity);

    /**
     * @param entity The entity to get the maximum burnout value for.
     * @return The maximum burnout value of the given entity.
     */
    float getMaxBurnout(LivingEntity entity);

    /**
     * Increases the burnout value of the given entity.
     *
     * @param entity The entity to increase the burnout value for.
     * @param amount The amount to increase the burnout value by.
     * @return True if the operation succeeded, false otherwise.
     */
    boolean increaseBurnout(LivingEntity entity, float amount);

    /**
     * Decreases the burnout value of the given entity.
     *
     * @param entity The entity to decrease the burnout value for.
     * @param amount The amount to decrease the burnout value by.
     * @return True if the operation succeeded, false otherwise.
     */
    boolean decreaseBurnout(LivingEntity entity, float amount);

    /**
     * Sets the burnout value of the given entity.
     *
     * @param entity The entity to set the burnout value for.
     * @param amount The value to set the burnout value to.
     * @return True if the operation succeeded, false otherwise.
     */
    boolean setBurnout(LivingEntity entity, float amount);
}
