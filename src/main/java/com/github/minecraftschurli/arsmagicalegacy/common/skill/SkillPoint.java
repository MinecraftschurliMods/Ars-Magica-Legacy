package com.github.minecraftschurli.arsmagicalegacy.common.skill;

import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SkillPoint extends ForgeRegistryEntry<ISkillPoint> implements ISkillPoint {
    private final int color;
    private final int minEarnLevel;
    private final int levelsForPoint;

    /**
     * Creates a new SkillPoint.
     * @param color          The color for this skill point.
     * @param minEarnLevel   The minimum amount of levels needed to get this skill point.
     * @param levelsForPoint The amount of levels needed to get the next skill point.
     */
    public SkillPoint(int color, int minEarnLevel, int levelsForPoint) {
        this.color = color;
        this.minEarnLevel = minEarnLevel;
        this.levelsForPoint = levelsForPoint;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public int getLevelsForPoint() {
        return levelsForPoint;
    }

    @Override
    public int getMinEarnLevel() {
        return minEarnLevel;
    }
}
