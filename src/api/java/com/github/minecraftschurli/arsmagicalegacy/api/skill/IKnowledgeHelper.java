package com.github.minecraftschurli.arsmagicalegacy.api.skill;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/**
 * TODO
 */
public interface IKnowledgeHelper {
    IKnowledgeHolder getKnowledgeHolder(Player player);

    boolean knows(Player player, ResourceLocation skill);

    boolean knows(Player player, ISkill skill);

    boolean canLearn(Player player, ResourceLocation skill);

    boolean canLearn(Player player, ISkill skill);

    void learn(Player player, ISkill id);

    void forget(Player player, ResourceLocation skill);

    void forget(Player player, ISkill skill);

    int getSkillPoint(Player player, ResourceLocation point);

    int getSkillPoint(Player player, ISkillPoint point);

    void learn(Player player, ResourceLocation id);

    int getCurrentLevel(Player player);

    void addSkillPoint(Player player, ResourceLocation skillPoint, int amount);

    void addSkillPoint(Player player, ISkillPoint skillPoint, int amount);

    void addSkillPoint(Player player, ResourceLocation skillPoint);

    void addSkillPoint(Player player, ISkillPoint skillPoint);

    boolean consumeSkillPoint(Player player, ResourceLocation skillPoint, int amount);

    boolean consumeSkillPoint(Player player, ISkillPoint skillPoint, int amount);

    boolean consumeSkillPoint(Player player, ResourceLocation skillPoint);

    boolean consumeSkillPoint(Player player, ISkillPoint skillPoint);

    float getXp(Player player);
}
