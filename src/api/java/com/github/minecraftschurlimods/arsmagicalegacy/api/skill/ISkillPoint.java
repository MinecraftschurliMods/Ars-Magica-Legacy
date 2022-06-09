package com.github.minecraftschurlimods.arsmagicalegacy.api.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public interface ISkillPoint extends ITranslatable {
    String SKILL_POINT = "skill_point";

    /**
     * @return The color for this skill point.
     */
    int color();

    /**
     * @return The amount of levels needed to get the next skill point.
     */
    int levelsForPoint();

    /**
     * @return The minimum amount of levels needed to get this skill point.
     */
    int minEarnLevel();

    @Override
    default String getType() {
        return SKILL_POINT;
    }

    @Override
    default ResourceLocation getId() {
        return Objects.requireNonNull(ArsMagicaAPI.get().getSkillPointRegistry().getKey(this));
    }
}
