package com.github.minecraftschurli.arsmagicalegacy.api.skill;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

/**
 * Interface representing a knowledge helper
 */
public interface IKnowledgeHelper {

    /**
     * Check if the given player knows the given skill.
     *
     * @param player the player to check
     * @param skill the skill to check for
     * @return true if the player knows the skill, otherwise false
     */
    boolean knows(Player player, ResourceLocation skill);

    /**
     * Check if the given player knows the given skill.
     *
     * @param player the player to check
     * @param skill the skill to check for
     * @return true if the player knows the skill, otherwise false
     */
    boolean knows(Player player, ISkill skill);

    /**
     * Check if the given player can learn the given skill.
     *
     * @param player the player to check
     * @param skill the skill to check for
     * @return true if the player can learn the skill, otherwise false
     */
    boolean canLearn(Player player, ResourceLocation skill);

    /**
     * Check if the given player can learn the given skill.
     *
     * @param player the player to check
     * @param skill the skill to check for
     * @return true if the player can learn the skill, otherwise false
     */
    boolean canLearn(Player player, ISkill skill);

    /**
     * Let the given player learn the given skill.
     *
     * @param player the player that should learn the skill
     * @param skill the skill to learn
     */
    void learn(Player player, ResourceLocation skill);

    /**
     * Let the given player learn the given skill.
     *
     * @param player the player that should learn the skill
     * @param skill the skill to learn
     */
    void learn(Player player, ISkill skill);

    /**
     * Let the given player forget the given skill.
     *
     * @param player the player that should forget the skill
     * @param skill the skill to forget
     */
    void forget(Player player, ResourceLocation skill);

    /**
     * Let the given player forget the given skill.
     *
     * @param player the player that should forget the skill
     * @param skill the skill to forget
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
     * @param point the skill point to get the count for
     * @return the number of skill points of the given type the given player has
     */
    int getSkillPoint(Player player, ResourceLocation point);

    /**
     * Get the number of skill points of the given type the given player has.
     *
     * @param player the player to get the skill points for
     * @param point the skill point to get the count for
     * @return the number of skill points of the given type the given player has
     */
    int getSkillPoint(Player player, ISkillPoint point);

    /**
     * Add skill points of a given type for a given player.
     *
     * @param player the player to add the skill points for
     * @param skillPoint the skill point to add
     * @param amount the amount of skill points to add
     */
    void addSkillPoint(Player player, ResourceLocation skillPoint, int amount);

    /**
     * Add skill points of a given type for a given player.
     *
     * @param player the player to add the skill points for
     * @param skillPoint the skill point to add
     * @param amount the amount of skill points to add
     */
    void addSkillPoint(Player player, ISkillPoint skillPoint, int amount);

    /**
     * Add skill points of a given type for a given player.
     *
     * @param player the player to add the skill points for
     * @param skillPoint the skill point to add
     */
    void addSkillPoint(Player player, ResourceLocation skillPoint);

    /**
     * Add skill points of a given type for a given player.
     *
     * @param player the player to add the skill points for
     * @param skillPoint the skill point to add
     */
    void addSkillPoint(Player player, ISkillPoint skillPoint);

    /**
     * Consume skill points of a given type for a given player.
     *
     * @param player the player to consume the skill points from
     * @param skillPoint the skill point to consume
     * @param amount the amount of skill points to consume
     * @return whether the consuming was successful
     */
    boolean consumeSkillPoint(Player player, ResourceLocation skillPoint, int amount);

    /**
     * Consume skill points of a given type for a given player.
     *
     * @param player the player to consume the skill points from
     * @param skillPoint the skill point to consume
     * @param amount the amount of skill points to consume
     * @return whether the consuming was successful
     */
    boolean consumeSkillPoint(Player player, ISkillPoint skillPoint, int amount);

    /**
     * Consume skill points of a given type for a given player.
     *
     * @param player the player to consume the skill points from
     * @param skillPoint the skill point to consume
     * @return whether the consuming was successful
     */
    boolean consumeSkillPoint(Player player, ResourceLocation skillPoint);

    /**
     * Consume skill points of a given type for a given player.
     *
     * @param player the player to consume the skill points from
     * @param skillPoint the skill point to consume
     * @return whether the consuming was successful
     */
    boolean consumeSkillPoint(Player player, ISkillPoint skillPoint);

    /**
     * Get the current magic level for the given player.
     *
     * @param player the player to get the level for
     * @return the level of the player
     */
    int getCurrentLevel(Player player);

    /**
     * Get the current magic xp for the given player.
     *
     * @param player the player to get the xp for
     * @return the xp of the player
     */
    float getXp(Player player);

    /**
     * Get all known skills for the given player.
     *
     * @param player the player to get the skills for
     * @return an unmodifiable collection of the players known skills
     */
    Collection<ResourceLocation> getKnownSkills(Player player);
}
