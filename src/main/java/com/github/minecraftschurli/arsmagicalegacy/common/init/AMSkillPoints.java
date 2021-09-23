package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurli.arsmagicalegacy.common.skill.SkillPoint;
import net.minecraft.ChatFormatting;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.SKILL_POINTS;

@NonExtendable
public interface AMSkillPoints {
    RegistryObject<ISkillPoint> BLUE = SKILL_POINTS.register("blue", () -> new SkillPoint(ChatFormatting.BLUE, 0x0000ff, 0, 1));

    @Internal
    static void register() {}
}
