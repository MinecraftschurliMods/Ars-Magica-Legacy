package com.github.minecraftschurli.arsmagicalegacy.api.affinity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Interface representing an affinity helper
 */
public interface IAffinityHelper {

    /**
     * Get the {@link ItemStack} of the given {@link Item} for the {@link IAffinity affinity} under the given {@link ResourceLocation}.
     *
     * @param item the item to make the {@link ItemStack} from on which the {@link IAffinity} will be set
     * @param affinity the {@link ResourceLocation} of the {@link IAffinity affinity} to set
     * @param <T> the {@link Item} implementing {@link IAffinityItem}
     * @return the {@link ItemStack} of the given item with the given {@link IAffinity} stored in it
     */
    <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, ResourceLocation affinity);

    /**
     * Get the {@link ItemStack} of the given {@link Item} for the given {@link IAffinity affinity}.
     *
     * @param item the item to make the {@link ItemStack} from on which the {@link IAffinity} will be set
     * @param affinity the {@link IAffinity affinity} to set
     * @param <T> the {@link Item} implementing {@link IAffinityItem}
     * @return the {@link ItemStack} of the given item with the given {@link IAffinity} stored in it
     */
    <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, IAffinity affinity);

    /**
     * Get the {@link IAffinity} from a {@link ItemStack} returns the NONE affinity if the stack does not contain an affinity.
     *
     * @param stack the stack to get the affinity from
     * @return the {@link IAffinity} stored in the stack or the NONE affinity if the stack does not contain one
     */
    IAffinity getAffinityForStack(ItemStack stack);

    /**
     * Get the affinity essence {@link ItemStack} for the {@link IAffinity affinity} under the given {@link ResourceLocation}.
     *
     * @param affinity the {@link ResourceLocation} of the {@link IAffinity affinity} to get the essence stack for
     * @return the {@link ItemStack} containing the affinity essence
     */
    ItemStack getEssenceForAffinity(ResourceLocation affinity);

    /**
     * Get the affinity essence {@link ItemStack} for the given {@link IAffinity affinity}.
     *
     * @param affinity the {@link IAffinity affinity} to get the essence stack for
     * @return the {@link ItemStack} containing the affinity essence
     */
    ItemStack getEssenceForAffinity(IAffinity affinity);

    /**
     * Get the affinity tome {@link ItemStack} for the {@link IAffinity affinity} under the given {@link ResourceLocation}.
     *
     * @param affinity the {@link ResourceLocation} of the {@link IAffinity affinity} to get the tome stack for
     * @return the {@link ItemStack} containing the affinity tome
     */
    ItemStack getTomeForAffinity(ResourceLocation affinity);

    /**
     * Get the affinity tome {@link ItemStack} for the given {@link IAffinity affinity}.
     *
     * @param affinity the {@link IAffinity affinity} to get the tome stack for
     * @return the {@link ItemStack} containing the affinity tome
     */
    ItemStack getTomeForAffinity(IAffinity affinity);

    /**
     * Get the affinity depth for a given player and affinity.
     *
     * @param player the player to get the affinity depth for
     * @param affinity the affinity to get the depth for
     * @return the depth of the given player in the given affinity
     */
    double getAffinityDepth(Player player, ResourceLocation affinity);

    /**
     * Get the affinity depth for a given player and affinity.
     *
     * @param player the player to get the affinity depth for
     * @param affinity the affinity to get the depth for
     * @return the depth of the given player in the given affinity
     */
    double getAffinityDepth(Player player, IAffinity affinity);
}
