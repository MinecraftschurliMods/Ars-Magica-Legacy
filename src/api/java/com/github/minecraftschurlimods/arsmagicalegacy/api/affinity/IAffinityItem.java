package com.github.minecraftschurlimods.arsmagicalegacy.api.affinity;

/**
 * Represents an item that has an affinity in the stack
 */
public interface IAffinityItem {
    /**
     * @return Whether this item should have a variant without an affinity or not.
     */
    default boolean hasNoneVariant() {
        return false;
    }
}
