package com.github.minecraftschurlimods.arsmagicalegacy.api.affinity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

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

    /**
     * @param stack The stack to get the affinity from.
     * @return The affinity stored in the stack, or the NONE affinity if the stack does not contain one.
     */
    default Affinity getAffinity(ItemStack stack) {
        var registry = ArsMagicaAPI.get().getAffinityRegistry();
        ResourceLocation key = ResourceLocation.tryParse(stack.getOrCreateTag().getString(registry.key().location().toString()));
        if (key == null) {
            key = Affinity.NONE;
        }
        return Objects.requireNonNull(registry.get(key));
    }

    /**
     * @param stack    The item stack to set the affinity on.
     * @param affinity The affinity to set.
     * @return The stack, now with the given affinity set on it.
     */
    default ItemStack setAffinity(ItemStack stack, Affinity affinity) {
        stack.getOrCreateTag().putString(ArsMagicaAPI.get().getAffinityRegistry().key().location().toString(), affinity.getId().toString());
        return stack;
    }
}
