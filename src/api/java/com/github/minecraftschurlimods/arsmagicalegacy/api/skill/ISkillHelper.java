package com.github.minecraftschurlimods.arsmagicalegacy.api.skill;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Interface for skill related helper methods.
 */
public interface ISkillHelper {
    /**
     * @param player The player to check the skill knowledge for.
     * @param skill  The id of the skill to check for.
     * @return True if the given player knows the given skill, false otherwise.
     */
    boolean knows(Player player, ResourceLocation skill);

    /**
     * @param player The player to check the skill knowledge for.
     * @param skill  The skill to check for.
     * @return True if the given player knows the given skill, false otherwise.
     */
    boolean knows(Player player, Skill skill, RegistryAccess registryAccess);

    /**
     * @param player The player to check the skill requirements for.
     * @param skill  The id of the skill to check for.
     * @return True if the given player can learn the given skill, false otherwise.
     */
    boolean canLearn(Player player, ResourceLocation skill);

    /**
     * @param player The player to check the skill requirements for.
     * @param skill  The skill to check for.
     * @return True if the given player can learn the given skill, false otherwise.
     */
    boolean canLearn(Player player, Skill skill, RegistryAccess registryAccess);

    /**
     * Unlocks the given skill for the given player.
     *
     * @param player The player to unlock the skill for.
     * @param skill  The id of the skill to unlock.
     */
    void learn(Player player, ResourceLocation skill);

    /**
     * Unlocks the given skill for the given player.
     *
     * @param player The player to unlock the skill for.
     * @param skill  The skill to unlock.
     */
    void learn(Player player, Skill skill, RegistryAccess registryAccess);

    /**
     * Locks the given skill for the given player.
     *
     * @param player The player to lock the skill for.
     * @param skill  The id of the skill to lock.
     */
    void forget(Player player, ResourceLocation skill);

    /**
     * Locks the given skill for the given player.
     *
     * @param player The player to lock the skill for.
     * @param skill  The skill to lock.
     */
    void forget(Player player, Skill skill, RegistryAccess registryAccess);

    /**
     * Unlocks all skills for the given player.
     *
     * @param player The player to unlock the skills for.
     */
    void learnAll(Player player, RegistryAccess registryAccess);

    /**
     * Locks all skills for the given player.
     *
     * @param player The player to lock the skills for.
     */
    void forgetAll(Player player);

    /**
     * @param player The player to get the skill points for.
     * @param point  The id of the skill point to get the amount for.
     * @return The amount of skill points of the given skill point type the given player has.
     */
    int getSkillPoint(Player player, ResourceLocation point);

    /**
     * @param player The player to get the skill points for.
     * @param point  The skill point to get the amount for.
     * @return The amount of skill points of the given skill point type the given player has.
     */
    int getSkillPoint(Player player, SkillPoint point);

    /**
     * Adds skill points of a given type to a given player.
     *
     * @param player     The player to add the skill points to.
     * @param skillPoint The id of the skill point type to add.
     * @param amount     The amount of skill points to add.
     */
    void addSkillPoint(Player player, ResourceLocation skillPoint, int amount);

    /**
     * Adds skill points of a given type to a given player.
     *
     * @param player     The player to add the skill points to.
     * @param skillPoint The skill point type to add.
     * @param amount     The amount of skill points to add.
     */
    void addSkillPoint(Player player, SkillPoint skillPoint, int amount);

    /**
     * Adds a skill point of a given type to a given player.
     *
     * @param player     The player to add the skill point to.
     * @param skillPoint The id of the skill point type to add.
     */
    void addSkillPoint(Player player, ResourceLocation skillPoint);

    /**
     * Adds a skill point of a given type to a given player.
     *
     * @param player     The player to add the skill point to.
     * @param skillPoint The skill point type to add.
     */
    void addSkillPoint(Player player, SkillPoint skillPoint);

    /**
     * Consumes skill points of a given type for a given player.
     *
     * @param player     The player to consume the skill points of.
     * @param skillPoint The id of the skill point type to consume.
     * @param amount     The amount of skill points to consume.
     * @return Whether the consumption was successful or not.
     */
    boolean consumeSkillPoint(Player player, ResourceLocation skillPoint, int amount);

    /**
     * Consumes skill points of a given type for a given player.
     *
     * @param player     The player to consume the skill points of.
     * @param skillPoint The skill point type to consume.
     * @param amount     The amount of skill points to consume.
     * @return Whether the consumption was successful or not.
     */
    boolean consumeSkillPoint(Player player, SkillPoint skillPoint, int amount);

    /**
     * Consumes one skill point of a given type for a given player.
     *
     * @param player     The player to consume the skill point of.
     * @param skillPoint The id of the skill point type to consume.
     * @return Whether the consumption was successful or not.
     */
    boolean consumeSkillPoint(Player player, ResourceLocation skillPoint);

    /**
     * Consumes one skill point of a given type for a given player.
     *
     * @param player     The player to consume the skill point of.
     * @param skillPoint The skill point type to consume.
     * @return Whether the consumption was successful or not.
     */
    boolean consumeSkillPoint(Player player, SkillPoint skillPoint);

    /**
     * @param skillPoint The id of the skill point type to get the orb stack for.
     * @return An item stack containing the orb.
     */
    ItemStack getOrbForSkillPoint(ResourceLocation skillPoint);

    /**
     * @param skillPoint The skill point type to get the orb stack for.
     * @return An item stack containing the orb.
     */
    ItemStack getOrbForSkillPoint(SkillPoint skillPoint);

    /**
     * @param item       The item to make the item stack from.
     * @param skillPoint The id of the skill point type to set on the item stack.
     * @param <T>        The item implementing ISkillPointItem.
     * @return An item stack of the given item with the given skill point type stored in it.
     */
    <T extends Item & ISkillPointItem> ItemStack getStackForSkillPoint(T item, ResourceLocation skillPoint);

    /**
     * @param item       The item to make the item stack from.
     * @param skillPoint The skill point type to set on the item stack.
     * @param <T>        The item implementing ISkillPointItem.
     * @return An item stack of the given item with the given skill point type stored in it.
     */
    <T extends Item & ISkillPointItem> ItemStack getStackForSkillPoint(T item, SkillPoint skillPoint);

    /**
     * @param stack The stack to get the skill point type from.
     * @return The skill point type stored in the stack, or null if the stack does not contain one.
     */
    @Nullable
    SkillPoint getSkillPointForStack(ItemStack stack);

    /**
     * @param player The player to get the skills for.
     * @return An unmodifiable collection of the given player's known skills.
     */
    Collection<ResourceLocation> getKnownSkills(Player player);
}
