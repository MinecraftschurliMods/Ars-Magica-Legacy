package com.github.minecraftschurlimods.arsmagicalegacy.api.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

/**
 * Represents an item that has an skill point in the stack
 */
public interface ISkillPointItem {
    /**
     * Get the {@link SkillPoint} stored in the {@link ItemStack}.
     *
     * @param stack the {@link ItemStack} that has the {@link SkillPoint} stored in it
     * @return the stored {@link SkillPoint}
     */
    default SkillPoint getSkillPoint(ItemStack stack) {
        var registry = ArsMagicaAPI.get().getSkillPointRegistry();
        ResourceLocation key = ResourceLocation.tryParse(stack.getOrCreateTag().getString(registry.getRegistryName().toString()));
        return Objects.requireNonNull(registry.getValue(key));
    }

    /**
     * Set the {@link SkillPoint} stored in the {@link ItemStack}
     *
     * @param stack      the {@link ItemStack} to set the {@link SkillPoint} for
     * @param skillPoint the {@link SkillPoint} to set
     * @return the {@link ItemStack} with the {@link SkillPoint} stored in it
     */
    default ItemStack setSkillPoint(ItemStack stack, SkillPoint skillPoint) {
        stack.getOrCreateTag().putString(ArsMagicaAPI.get().getSkillPointRegistry().getRegistryName().toString(), skillPoint.getId().toString());
        return stack;
    }
}
