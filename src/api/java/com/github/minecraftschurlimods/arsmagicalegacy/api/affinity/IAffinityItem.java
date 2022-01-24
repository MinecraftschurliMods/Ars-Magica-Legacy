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
     * @param stack The stack to get the affinity from.
     * @return The affinity stored in the stack, or the NONE affinity if the stack does not contain one.
     */
    default IAffinity getAffinity(ItemStack stack) {
        var registry = ArsMagicaAPI.get().getAffinityRegistry();
        ResourceLocation key = ResourceLocation.tryParse(stack.getOrCreateTag().getString(registry.getRegistryName().toString()));
        if (key == null) {
            key = IAffinity.NONE;
        }
        return Objects.requireNonNull(registry.getValue(key));
    }

    /**
     * @param stack    The item stack to set the affinity on.
     * @param affinity The affinity to set.
     * @return The stack, now with the given affinity set on it.
     */
    default ItemStack setAffinity(ItemStack stack, IAffinity affinity) {
        stack.getOrCreateTag().putString(ArsMagicaAPI.get().getAffinityRegistry().getRegistryName().toString(), Objects.requireNonNull(affinity.getRegistryName()).toString());
        return stack;
    }
}
