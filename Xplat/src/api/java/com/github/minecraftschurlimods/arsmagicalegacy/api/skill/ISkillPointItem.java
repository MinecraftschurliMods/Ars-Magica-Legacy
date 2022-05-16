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
     * Get the {@link ISkillPoint} stored in the {@link ItemStack}.
     *
     * @param stack the {@link ItemStack} that has the {@link ISkillPoint} stored in it
     * @return the stored {@link ISkillPoint}
     */
    default ISkillPoint getSkillPoint(ItemStack stack) {
        var registry = ArsMagicaAPI.get().getSkillPointRegistry();
        ResourceLocation key = ResourceLocation.tryParse(stack.getOrCreateTag().getString(ISkillPoint.REGISTRY_KEY.location().toString()));
        return Objects.requireNonNull(registry.get(key));
    }

    /**
     * Set the {@link ISkillPoint} stored in the {@link ItemStack}
     *
     * @param stack      the {@link ItemStack} to set the {@link ISkillPoint} for
     * @param skillPoint the {@link ISkillPoint} to set
     * @return the {@link ItemStack} with the {@link ISkillPoint} stored in it
     */
    default ItemStack setSkillPoint(ItemStack stack, ISkillPoint skillPoint) {
        stack.getOrCreateTag().putString(ISkillPoint.REGISTRY_KEY.location().toString(), Objects.requireNonNull(skillPoint.getRegistryName()).toString());
        return stack;
    }
}
