package com.github.minecraftschurli.arsmagicalegacy.api.skill;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

/**
 * Represents an item that has an skill point in the stack
 */
public interface ISkillPointItem {
    /**
     * Get the {@link ISkillPoint} stored in the {@link ItemStack}.
     *
     * @param stack the {@link ItemStack} that has the {@link ISkillPoint} stored in it
     * @return the stored {@link ISkillPoint}
     */
    default ISkillPoint getSkillPoint(ItemStack stack) {
        var skillPointRegistry = ArsMagicaAPI.get().getSkillPointRegistry();
        var key = ResourceLocation.tryParse(stack.getOrCreateTag().getString(skillPointRegistry.getRegistryName().toString()));
        return Objects.requireNonNull(skillPointRegistry.getValue(key));
    }

    /**
     * Set the {@link ISkillPoint} stored in the {@link ItemStack}
     *
     * @param stack    the {@link ItemStack} to set the {@link ISkillPoint} for
     * @param skillPoint the {@link ISkillPoint} to set
     * @return the {@link ItemStack} with the {@link ISkillPoint} stored in it
     */
    default ItemStack setSkillPoint(ItemStack stack, ISkillPoint skillPoint) {
        stack.getOrCreateTag().putString(ArsMagicaAPI.get().getSkillPointRegistry().getRegistryName().toString(), Objects.requireNonNull(skillPoint.getRegistryName()).toString());
        return stack;
    }
}