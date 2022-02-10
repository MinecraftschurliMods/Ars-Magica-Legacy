package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.common.skill.SkillPoint;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface AMSkillPoints {
    RegistryObject<ISkillPoint> BLUE  = AMRegistries.SKILL_POINTS.register("blue",  () -> new SkillPoint(0x0000ff, Config.SERVER.BLUE_POINTS_MIN_LEVEL.get(),  Config.SERVER.BLUE_POINTS_INTERVAL.get()));
    RegistryObject<ISkillPoint> GREEN = AMRegistries.SKILL_POINTS.register("green", () -> new SkillPoint(0x00ff00, Config.SERVER.GREEN_POINTS_MIN_LEVEL.get(), Config.SERVER.GREEN_POINTS_INTERVAL.get()));
    RegistryObject<ISkillPoint> RED   = AMRegistries.SKILL_POINTS.register("red",   () -> new SkillPoint(0xff0000, Config.SERVER.RED_POINTS_MIN_LEVEL.get(),   Config.SERVER.RED_POINTS_INTERVAL.get()));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
