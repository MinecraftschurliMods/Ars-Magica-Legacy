package com.github.minecraftschurli.arsmagicalegacy.api.affinity;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

/**
 * Represents an item that has an affinity in the stack
 */
public interface IAffinityItem {
    /**
     * Get the {@link IAffinity} stored in the {@link ItemStack}.
     *
     * @param stack the {@link ItemStack} that has the {@link IAffinity} stored in it
     * @return the stored {@link IAffinity}
     */
    default IAffinity getAffinity(ItemStack stack) {
        var affinityRegistry = ArsMagicaAPI.get().getAffinityRegistry();
        var key = ResourceLocation.tryParse(stack.getOrCreateTag().getString(affinityRegistry.getRegistryName().toString()));
        if (key == null) key = IAffinity.NONE;
        return Objects.requireNonNull(affinityRegistry.getValue(key));
    }

    /**
     * Set the {@link IAffinity} stored in the {@link ItemStack}
     *
     * @param stack the {@link ItemStack} to set the {@link IAffinity} for
     * @param affinity the {@link IAffinity} to set
     * @return the {@link ItemStack} with the {@link IAffinity} stored in it
     */
    default ItemStack setAffinity(ItemStack stack, IAffinity affinity) {
        stack.getOrCreateTag().putString(
                ArsMagicaAPI.get().getAffinityRegistry().getRegistryName().toString(),
                Objects.requireNonNull(affinity.getRegistryName()).toString()
        );
        return stack;
    }
}
