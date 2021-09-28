package com.github.minecraftschurli.arsmagicalegacy.common.skill;

import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import net.minecraft.ChatFormatting;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * @author Minecraftschurli
 * @version 2021-09-23
 */
public class SkillPoint extends ForgeRegistryEntry<ISkillPoint> implements ISkillPoint {

    private final ChatFormatting formatting;
    private final int color;
    private final int minEarnLevel;
    private final int levelsForPoint;

    public SkillPoint(ChatFormatting formatting, int color, int minEarnLevel, int levelsForPoint) {
        this.formatting = formatting;
        this.color = color;
        this.minEarnLevel = minEarnLevel;
        this.levelsForPoint = levelsForPoint;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public ChatFormatting getChatColor() {
        return formatting;
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
