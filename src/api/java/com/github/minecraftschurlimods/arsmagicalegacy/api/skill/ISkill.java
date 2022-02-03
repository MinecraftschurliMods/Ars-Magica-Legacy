package com.github.minecraftschurlimods.arsmagicalegacy.api.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.Set;

/**
 * Interface representing a skill.
 */
public interface ISkill extends ITranslatable.WithDescription {
    String SKILL = "skill";

    /**
     * @return The id of the occulus tab this skill belongs to.
     */
    ResourceLocation getOcculusTab();

    /**
     * @return The ids of this skill's parents.
     */
    Set<ResourceLocation> getParents();

    /**
     * @return The x coordinate of this skill.
     */
    int getX();

    /**
     * @return The y coordinate of this skill.
     */
    int getY();

    /**
     * @return The cost for learning this skill.
     */
    Map<ResourceLocation, Integer> getCost();

    /**
     * @return Whether this skill is hidden or not.
     */
    boolean isHidden();

    @Override
    default String getType() {
        return SKILL;
    }
}
