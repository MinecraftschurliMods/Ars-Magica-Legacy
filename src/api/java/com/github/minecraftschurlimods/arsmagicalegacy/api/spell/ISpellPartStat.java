package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraft.resources.ResourceLocation;

/**
 *
 */
public interface ISpellPartStat {
    ResourceLocation getId();

    default boolean equals(ISpellPartStat other) {
        return getId().equals(other.getId());
    }
}
