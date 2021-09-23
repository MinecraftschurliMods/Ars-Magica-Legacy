package com.github.minecraftschurli.arsmagicalegacy.api.affinity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * TODO
 */
public interface IAffinityHelper {
    IAffinityHolder getAffinityHolder(Player player);

    ItemStack getEssenceForAffinity(ResourceLocation aff);
    ItemStack getTomeForAffinity(ResourceLocation aff);
    <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, ResourceLocation aff);
    double getAffinityDepth(Player player, ResourceLocation affinity);
    IAffinity getAffinityForStack(ItemStack stack);

    default ItemStack getEssenceForAffinity(IAffinity aff) {
        return getEssenceForAffinity(aff.getRegistryName());
    }
    default ItemStack getTomeForAffinity(IAffinity aff) {
        return getTomeForAffinity(aff.getRegistryName());
    }
    default double getAffinityDepth(Player player, IAffinity affinity) {
        return getAffinityDepth(player, affinity.getRegistryName());
    }
}
