package com.github.minecraftschurli.arsmagicalegacy.api.skill;

import com.github.minecraftschurli.arsmagicalegacy.api.util.ITranslatable;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface ISkillPoint extends IForgeRegistryEntry<ISkillPoint>, ITranslatable.OfRegistryEntry<ISkillPoint> {
    String SKILL_POINT = "skill_point";

    /**
     * Get the color for this skill point.
     *
     * @return the color for this skill point
     */
    int getColor();

    /**
     * Get the amount of levels needed to get the next of this skill point on level up.
     *
     * @return the amount of levels needed to get the next skill point
     */
    int getLevelsForPoint();

    /**
     * Get the minimum amount of levels needed to get this skill point on level up.
     *
     * @return the minimum amount of levels needed to get this skill point
     */
    int getMinEarnLevel();

    @Override
    default String getType() {
        return SKILL_POINT;
    }
}
