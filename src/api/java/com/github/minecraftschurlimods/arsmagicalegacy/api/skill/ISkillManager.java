package com.github.minecraftschurlimods.arsmagicalegacy.api.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.api.occulus.IOcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.IDataManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * Interface representing a skill manager.
 */
public interface ISkillManager extends IDataManager<ISkill> {
    /**
     * @param id The id of the occulus tab to get the skills for.
     * @return The skills in the given occulus tab.
     */
    Set<ISkill> getSkillsForOcculusTab(ResourceLocation id);

    /**
     * @param tab The occulus tab to get the skills for.
     * @return The skills in the given occulus tab.
     */
    Set<ISkill> getSkillsForOcculusTab(IOcculusTab tab);

    /**
     * @param id The id of the skill to get.
     * @return The skill, or null if the skill is not loaded.
     */
    Optional<ISkill> getOptional(ResourceLocation id);

    /**
     * @param id The id of the skill to get.
     * @return The skill, or null if the skill is not loaded.
     */
    @Nullable
    ISkill get(ResourceLocation id);

    /**
     * @param id The id of the skill to get.
     * @return The skill. Throws an exception if the skill is not loaded.
     */
    ISkill getOrThrow(ResourceLocation id);

    /**
     * @return An unmodifiable collection of all skills.
     */
    Collection<ISkill> getSkills();
}
