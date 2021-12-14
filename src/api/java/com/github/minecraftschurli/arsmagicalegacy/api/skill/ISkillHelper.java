package com.github.minecraftschurli.arsmagicalegacy.api.skill;

import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Interface representing a knowledge helper
 */
public interface ISkillHelper {
    /**
     * Check if the given player knows the given skill.
     *
     * @param player the player to check
     * @param skill  the skill to check for
     * @return true if the player knows the skill, otherwise false
     */
    boolean knows(Player player, ResourceLocation skill);

    /**
     * Check if the given player knows the given skill.
     *
     * @param player the player to check
     * @param skill  the skill to check for
     * @return true if the player knows the skill, otherwise false
     */
    boolean knows(Player player, ISkill skill);

    /**
     * Check if the given player can learn the given skill.
     *
     * @param player the player to check
     * @param skill  the skill to check for
     * @return true if the player can learn the skill, otherwise false
     */
    boolean canLearn(Player player, ResourceLocation skill);

    /**
     * Check if the given player can learn the given skill.
     *
     * @param player the player to check
     * @param skill  the skill to check for
     * @return true if the player can learn the skill, otherwise false
     */
    boolean canLearn(Player player, ISkill skill);

    /**
     * Let the given player learn the given skill.
     *
     * @param player the player that should learn the skill
     * @param skill  the skill to learn
     */
    void learn(Player player, ResourceLocation skill);

    /**
     * Let the given player learn the given skill.
     *
     * @param player the player that should learn the skill
     * @param skill  the skill to learn
     */
    void learn(Player player, ISkill skill);

    /**
     * Let the given player forget the given skill.
     *
     * @param player the player that should forget the skill
     * @param skill  the skill to forget
     */
    void forget(Player player, ResourceLocation skill);

    /**
     * Let the given player forget the given skill.
     *
     * @param player the player that should forget the skill
     * @param skill  the skill to forget
     */
    void forget(Player player, ISkill skill);

    /**
     * Let the given player forget all skills.
     *
     * @param player the player that should forget all skill
     */
    void forgetAll(Player player);

    /**
     * Get the number of skill points of the given type the given player has.
     *
     * @param player the player to get the skill points for
     * @param point  the skill point to get the count for
     * @return the number of skill points of the given type the given player has
     */
    int getSkillPoint(Player player, ResourceLocation point);

    /**
     * Get the number of skill points of the given type the given player has.
     *
     * @param player the player to get the skill points for
     * @param point  the skill point to get the count for
     * @return the number of skill points of the given type the given player has
     */
    int getSkillPoint(Player player, ISkillPoint point);

    /**
     * Add skill points of a given type for a given player.
     *
     * @param player     the player to add the skill points for
     * @param skillPoint the skill point to add
     * @param amount     the amount of skill points to add
     */
    void addSkillPoint(Player player, ResourceLocation skillPoint, int amount);

    /**
     * Add skill points of a given type for a given player.
     *
     * @param player     the player to add the skill points for
     * @param skillPoint the skill point to add
     * @param amount     the amount of skill points to add
     */
    void addSkillPoint(Player player, ISkillPoint skillPoint, int amount);

    /**
     * Add skill points of a given type for a given player.
     *
     * @param player     the player to add the skill points for
     * @param skillPoint the skill point to add
     */
    void addSkillPoint(Player player, ResourceLocation skillPoint);

    /**
     * Add skill points of a given type for a given player.
     *
     * @param player     the player to add the skill points for
     * @param skillPoint the skill point to add
     */
    void addSkillPoint(Player player, ISkillPoint skillPoint);

    /**
     * Consume skill points of a given type for a given player.
     *
     * @param player     the player to consume the skill points from
     * @param skillPoint the skill point to consume
     * @param amount     the amount of skill points to consume
     * @return whether the consuming was successful
     */
    boolean consumeSkillPoint(Player player, ResourceLocation skillPoint, int amount);

    /**
     * Consume skill points of a given type for a given player.
     *
     * @param player     the player to consume the skill points from
     * @param skillPoint the skill point to consume
     * @param amount     the amount of skill points to consume
     * @return whether the consuming was successful
     */
    boolean consumeSkillPoint(Player player, ISkillPoint skillPoint, int amount);

    /**
     * Consume skill points of a given type for a given player.
     *
     * @param player     the player to consume the skill points from
     * @param skillPoint the skill point to consume
     * @return whether the consuming was successful
     */
    boolean consumeSkillPoint(Player player, ResourceLocation skillPoint);

    /**
     * Consume skill points of a given type for a given player.
     *
     * @param player     the player to consume the skill points from
     * @param skillPoint the skill point to consume
     * @return whether the consuming was successful
     */
    boolean consumeSkillPoint(Player player, ISkillPoint skillPoint);

    /**
     * Get the skill point orb {@link ItemStack} for the {@link ISkillPoint skillPoint} under the given {@link ResourceLocation}.
     *
     * @param skillPoint the {@link ResourceLocation} of the {@link ISkillPoint skillPoint} to get the orb stack for
     * @return the {@link ItemStack} containing the orb with the skill point
     */
    ItemStack getOrbForSkillPoint(ResourceLocation skillPoint);

    /**
     * Get the skill point orb {@link ItemStack} for the given {@link ISkillPoint skillPoint}.
     *
     * @param skillPoint the {@link ISkillPoint skillPoint} to get the orb stack for
     * @return the {@link ItemStack} containing the orb with the skill point
     */
    ItemStack getOrbForSkillPoint(ISkillPoint skillPoint);

    /**
     * Get the {@link ItemStack} of the given {@link Item} for the {@link ISkillPoint skillPoint} under the given {@link ResourceLocation}.
     *
     * @param item     the item to make the {@link ItemStack} from on which the {@link ISkillPoint} will be set
     * @param skillPoint the {@link ResourceLocation} of the {@link ISkillPoint skillPoint} to set
     * @param <T>      the {@link Item} implementing {@link ISkillPointItem}
     * @return the {@link ItemStack} of the given item with the given {@link ISkillPoint} stored in it
     */
    <T extends Item & ISkillPointItem> ItemStack getStackForSkillPoint(T item, ResourceLocation skillPoint);

    /**
     * Get the {@link ItemStack} of the given {@link Item} for the given {@link ISkillPoint skillPoint}.
     *
     * @param item     the item to make the {@link ItemStack} from on which the {@link ISkillPoint} will be set
     * @param skillPoint the {@link ISkillPoint skillPoint} to set
     * @param <T>      the {@link Item} implementing {@link ISkillPointItem}
     * @return the {@link ItemStack} of the given item with the given {@link ISkillPoint} stored in it
     */
    <T extends Item & ISkillPointItem> ItemStack getStackForSkillPoint(T item, ISkillPoint skillPoint);

    /**
     * Get the {@link ISkillPoint} from a {@link ItemStack} returns null if the stack does not contain a skill point.
     *
     * @param stack the stack to get the skill point from
     * @return the {@link ISkillPoint} stored in the stack or null if the stack does not contain one
     */
    @Nullable
    ISkillPoint getSkillPointForStack(ItemStack stack);
    
    /**
     * Get all known skills for the given player.
     *
     * @param player the player to get the skills for
     * @return an unmodifiable collection of the players known skills
     */
    Collection<ResourceLocation> getKnownSkills(Player player);
}
