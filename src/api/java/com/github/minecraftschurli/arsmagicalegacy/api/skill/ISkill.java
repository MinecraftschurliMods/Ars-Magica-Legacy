package com.github.minecraftschurli.arsmagicalegacy.api.skill;

import com.github.minecraftschurli.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Set;

/**
 * Interface representing a skill
 */
public interface ISkill extends ITranslatable.WithDescription {
    String SKILL = "skill";

    /**
     * Get the id for this skill.
     *
     * @return the id for this skill
     */
    @Override
    ResourceLocation getId();

    /**
     * Get the id of the occulus tab this skill belongs to.
     *
     * @return the id of the occulus tab this skill belongs to
     */
    ResourceLocation getOcculusTab();

    /**
     * Get the ids of this' skills parents.
     *
     * @return the ids of this' skills parents
     */
    Set<ResourceLocation> getParents();

    /**
     * Get the x coordinate of the drawing position.
     *
     * @return the x coordinate of the drawing position
     */
    int getX();

    /**
     * Get the y coordinate of the drawing position.
     *
     * @return the y coordinate of the drawing position
     */
    int getY();

    /**
     * Get the cost for learning this skill.
     *
     * @return the cost for learning this skill
     */
    Map<ResourceLocation, Integer> getCost();

    /**
     * Get whether this skill is hidden or not.
     *
     * @return whether this skill is hidden
     */
    boolean isHidden();

    @Override
    default String getType() {
        return SKILL;
    }
}
