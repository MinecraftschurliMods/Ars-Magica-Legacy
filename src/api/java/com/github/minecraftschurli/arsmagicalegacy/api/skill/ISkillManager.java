package com.github.minecraftschurli.arsmagicalegacy.api.skill;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * TODO
 */
public interface ISkillManager {

    /**
     * Get the skills for a given occulus tab.
     *
     * @param id the occulus tab to query for
     * @return the skills for the given occulus tab
     */
    Set<ISkill> getSkillsForOcculusTab(ResourceLocation id);

    Optional<ISkill> getOptional(ResourceLocation id);

    default ISkill get(ResourceLocation id) {
        return getOptional(id).orElseThrow();
    }

    Collection<ISkill> getSkills();
}
