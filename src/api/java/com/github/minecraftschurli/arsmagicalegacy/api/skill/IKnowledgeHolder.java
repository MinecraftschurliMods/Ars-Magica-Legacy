package com.github.minecraftschurli.arsmagicalegacy.api.skill;

import net.minecraft.resources.ResourceLocation;

/**
 * Interface representing a knowledge-holder
 */
public interface IKnowledgeHolder {
    boolean knows(ResourceLocation skill);
    boolean canLearn(ResourceLocation skill);
    void learn(ResourceLocation skill);
    void forget(ResourceLocation skill);
    int level();
    float xp();
    int getSkillPoint(ResourceLocation skillPoint);
    void addSkillPoint(ResourceLocation skillPoint, int amount);
    boolean consumeSkillPoint(ResourceLocation skillPoint, int amount);

    default boolean knows(ISkill skill) {
        return knows(skill.getId());
    }
    default boolean canLearn(ISkill skill) {
        return canLearn(skill.getId());
    }
    default void learn(ISkill skill) {
        learn(skill.getId());
    }
    default void forget(ISkill skill) {
        forget(skill.getId());
    }
    default int getSkillPoint(ISkillPoint skillPoint) {
        return getSkillPoint(skillPoint.getId());
    }
    default void addSkillPoint(ISkillPoint skillPoint, int amount) {
        addSkillPoint(skillPoint.getId(), amount);
    }
    default boolean consumeSkillPoint(ISkillPoint skillPoint, int amount) {
        return consumeSkillPoint(skillPoint.getId(), amount);
    }
    default void addSkillPoint(ISkillPoint skillPoint) {
        addSkillPoint(skillPoint, 1);
    }
    default boolean consumeSkillPoint(ISkillPoint skillPoint) {
        return consumeSkillPoint(skillPoint, 1);
    }
    default void addSkillPoint(ResourceLocation skillPoint) {
        addSkillPoint(skillPoint, 1);
    }
    default boolean consumeSkillPoint(ResourceLocation skillPoint) {
        return consumeSkillPoint(skillPoint, 1);
    }
}
