package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.SkillPoint;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.SKILL_POINTS;

@NonExtendable
public interface AMSkillPoints {
    RegistryObject<ISkillPoint> BLUE  = SKILL_POINTS.register("blue",  () -> new SkillPoint(0x0000ff,  0, 1));
    RegistryObject<ISkillPoint> GREEN = SKILL_POINTS.register("green", () -> new SkillPoint(0x00ff00, 20, 2));
    RegistryObject<ISkillPoint> RED   = SKILL_POINTS.register("red",   () -> new SkillPoint(0xff0000, 30, 2));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }
}
