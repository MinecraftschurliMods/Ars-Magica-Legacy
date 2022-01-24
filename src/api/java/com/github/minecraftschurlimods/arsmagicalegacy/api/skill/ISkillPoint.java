package com.github.minecraftschurlimods.arsmagicalegacy.api.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface ISkillPoint extends IForgeRegistryEntry<ISkillPoint>, ITranslatable.OfRegistryEntry<ISkillPoint> {
    String SKILL_POINT = "skill_point";

    /**
     * @return The color for this skill point.
     */
    int getColor();

    /**
     * @return The amount of levels needed to get the next skill point.
     */
    int getLevelsForPoint();

    /**
     * @return The minimum amount of levels needed to get this skill point.
     */
    int getMinEarnLevel();

    @Override
    default String getType() {
        return SKILL_POINT;
    }
}
