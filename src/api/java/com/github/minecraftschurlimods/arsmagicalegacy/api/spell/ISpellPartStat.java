package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraft.resources.ResourceLocation;

/**
 * Interface representing a spell part stat.
 */
public interface ISpellPartStat {
    /**
     * @return The id of this spell part stat.
     */
    ResourceLocation getId();

    default boolean equals(ISpellPartStat other) {
        return getId().equals(other.getId());
    }
}
