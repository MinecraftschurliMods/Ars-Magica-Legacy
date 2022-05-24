package com.github.minecraftschurlimods.arsmagicalegacy.api.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

/**
 * Interface for contingency helpers.
 */
public interface IContingencyHelper {
    /**
     * Set the contingency type and spell for a given target.
     *
     * @param target The target to set the contingency for.
     * @param type   The contingency type.
     * @param spell  The spell to set.
     */
    void setContingency(LivingEntity target, ResourceLocation type, ISpell spell);

    /**
     * Get the contingency type for a given target.
     *
     * @param target The target to get the contingency type for.
     * @return The contingency type.
     */
    ResourceLocation getContingencyType(LivingEntity target);

    /**
     * Trigger a contingency for a given target.
     *
     * @param target The target to trigger the contingency for.
     * @param type   The contingency type.
     */
    void triggerContingency(LivingEntity target, ResourceLocation type);

    /**
     * Clear the contingency for a given target.
     *
     * @param target The target to clear the contingency for.
     */
    void clearContingency(LivingEntity target);
}
