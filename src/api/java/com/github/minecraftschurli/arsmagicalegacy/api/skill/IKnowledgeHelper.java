package com.github.minecraftschurli.arsmagicalegacy.api.skill;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Collection;

/**
 * TODO
 */
public interface IKnowledgeHelper {

    boolean knows(Player player, ResourceLocation skill);

    boolean knows(Player player, ISkill skill);

    boolean canLearn(Player player, ResourceLocation skill);

    boolean canLearn(Player player, ISkill skill);

    void learn(Player player, ISkill id);

    void learn(Player player, ResourceLocation id);

    void forget(Player player, ResourceLocation skill);

    void forget(Player player, ISkill skill);

    void forgetAll(Player player);

    int getSkillPoint(Player player, ResourceLocation point);

    int getSkillPoint(Player player, ISkillPoint point);

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

    Collection<ResourceLocation> getKnownSkills(Player player);
}
