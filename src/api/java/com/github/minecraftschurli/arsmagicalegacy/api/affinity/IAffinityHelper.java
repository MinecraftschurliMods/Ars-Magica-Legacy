package com.github.minecraftschurli.arsmagicalegacy.api.affinity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * TODO
 */
public interface IAffinityHelper {
    ItemStack getItemStackForAffinity(ResourceLocation aff);
    ItemStack getItemStackForAffinity(IAffinity aff);
    double getAffinityDepth(Player player, ResourceLocation affinity);
    double getAffinityDepth(Player player, IAffinity affinity);
}
