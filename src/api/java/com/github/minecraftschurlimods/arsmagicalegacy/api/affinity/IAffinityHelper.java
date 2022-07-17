package com.github.minecraftschurlimods.arsmagicalegacy.api.affinity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Interface for affinity related helper methods.
 */
public interface IAffinityHelper {
    /**
     * @param affinity The id of the affinity to get the essence stack for.
     * @return An item stack containing the affinity essence.
     */
    ItemStack getEssenceForAffinity(ResourceLocation affinity);

    /**
     * @param affinity The affinity to get the essence stack for.
     * @return An item stack containing the affinity essence.
     */
    ItemStack getEssenceForAffinity(Affinity affinity);

    /**
     * @param affinity The id of the affinity to get the tome stack for.
     * @return An item stack containing the affinity tome.
     */
    ItemStack getTomeForAffinity(ResourceLocation affinity);

    /**
     * @param affinity The affinity to get the tome stack for.
     * @return An item stack containing the affinity tome.
     */
    ItemStack getTomeForAffinity(Affinity affinity);

    /**
     * @param item     The item to make the item stack from.
     * @param affinity The id of the affinity to set on the item stack.
     * @param <T>      The item implementing AffinityItem.
     * @return An item stack of the given item with the given affinity stored in it.
     */
    <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, ResourceLocation affinity);

    /**
     * @param item     The item to make the item stack from.
     * @param affinity The affinity to set on the item stack.
     * @param <T>      The item implementing AffinityItem.
     * @return An item stack of the given item with the given affinity stored in it.
     */
    <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, Affinity affinity);

    /**
     * @param stack The stack to get the affinity from.
     * @return The affinity stored in the stack, or the NONE affinity if the stack does not contain one.
     */
    Affinity getAffinityForStack(ItemStack stack);

    /**
     * @param player   The player to get the affinity depth for.
     * @param affinity The id of the affinity to get the depth for.
     * @return The depth of the given player in the given affinity.
     */
    double getAffinityDepth(Player player, ResourceLocation affinity);

    /**
     * @param player   The player to get the affinity depth for.
     * @param affinity The affinity to get the depth for.
     * @return The depth of the given player in the given affinity.
     */
    double getAffinityDepth(Player player, Affinity affinity);

    /**
     * @param player   The player to set the affinity depth for.
     * @param affinity The id of the affinity to set the depth for.
     * @param amount   The amount the affinity should have.
     */
    void setAffinityDepth(Player player, ResourceLocation affinity, float amount);

    /**
     * @param player   The player to set the affinity depth for.
     * @param affinity The affinity to set the depth for.
     * @param amount   The amount the affinity should have.
     */
    void setAffinityDepth(Player player, Affinity affinity, float amount);

    /**
     * Applies the affinity shift for the given player and affinity.
     *
     * @param player   The player to shift the affinity for.
     * @param affinity The affinity to shift.
     * @param shift    The amount to shift.
     */
    void applyAffinityShift(Player player, ResourceLocation affinity, float shift);

    /**
     * Applies the affinity shift for the given player and affinity.
     *
     * @param player   The player to shift the affinity for.
     * @param affinity The affinity to shift.
     * @param shift    The amount to shift.
     */
    void applyAffinityShift(Player player, Affinity affinity, float shift);
}
