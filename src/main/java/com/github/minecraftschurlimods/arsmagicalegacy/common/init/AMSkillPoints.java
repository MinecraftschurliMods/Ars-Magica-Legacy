package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface AMSkillPoints {
    Holder<SkillPoint> NONE  = AMRegistries.SKILL_POINTS.register("none",  () -> new SkillPoint(0x7f7f7f, -1, -1));
    Holder<SkillPoint> BLUE  = AMRegistries.SKILL_POINTS.register("blue",  () -> new SkillPoint(0x0000ff, 0,  1));
    Holder<SkillPoint> GREEN = AMRegistries.SKILL_POINTS.register("green", () -> new SkillPoint(0x00ff00, 10, 2));
    Holder<SkillPoint> RED   = AMRegistries.SKILL_POINTS.register("red",   () -> new SkillPoint(0xff0000, 20, 3));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
