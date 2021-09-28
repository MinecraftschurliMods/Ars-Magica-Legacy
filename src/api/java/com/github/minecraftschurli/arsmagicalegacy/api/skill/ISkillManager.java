package com.github.minecraftschurli.arsmagicalegacy.api.skill;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * Interface representing a skill manager
 */
public interface ISkillManager {

    /**
     * Get the skills for a given occulus tab.
     *
     * @param id the occulus tab to query for
     * @return the skills for the given occulus tab
     */
    Set<ISkill> getSkillsForOcculusTab(ResourceLocation id);

    /**
     * Get the {@link ISkill} for the id or empty.
     *
     * @param id the id of the requested {@link ISkill}
     * @return an {@link Optional} of the requested {@link ISkill} or {@link Optional#empty()} if it is not loaded
     */
    Optional<ISkill> getOptional(ResourceLocation id);

    /**
     * Get the {@link ISkill} for the id or throw.
     *
     * @param id the id of the requested {@link ISkill}
     * @return the requested {@link ISkill}
     */
    ISkill get(ResourceLocation id);

    /**
     * Get the {@link ISkill} for the id or null.
     *
     * @param id the id of the requested {@link ISkill}
     * @return the requested {@link ISkill} or null if it is not loaded
     */
    @Nullable
    ISkill getNullable(ResourceLocation id);

    /**
     * Get all loaded {@link ISkill}s.
     *
     * @return an unmodifiable collection of all loaded {@link ISkill}s
     */
    Collection<ISkill> getSkills();
}
