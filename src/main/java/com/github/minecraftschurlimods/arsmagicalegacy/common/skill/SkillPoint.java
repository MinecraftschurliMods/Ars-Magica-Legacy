package com.github.minecraftschurlimods.arsmagicalegacy.common.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;

public record SkillPoint(int color, int minEarnLevel, int levelsForPoint) implements ISkillPoint {}
