package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import org.jetbrains.annotations.Nullable;

/**
 * Interface representing a spell data manager.
 */
public interface ISpellDataManager {
    /**
     * @param part The spell part to get the data for.
     * @return The spell part data for the given part, or null if it is not available.
     */
    @Nullable
    ISpellPartData getDataForPart(ISpellPart part);
}
