package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.SkillPoint;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface AMSkillPoints {
    RegistryObject<ISkillPoint> BLUE  = AMRegistries.SKILL_POINTS.register("blue",  () -> new SkillPoint(0x0000ff, 0,  1));
    RegistryObject<ISkillPoint> GREEN = AMRegistries.SKILL_POINTS.register("green", () -> new SkillPoint(0x00ff00, 10, 2));
    RegistryObject<ISkillPoint> RED   = AMRegistries.SKILL_POINTS.register("red",   () -> new SkillPoint(0xff0000, 20, 3));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
