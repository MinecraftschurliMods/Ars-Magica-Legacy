package com.github.minecraftschurlimods.arsmagicalegacy.common.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.OcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Map;

public final class SkillManager {

    private static void validateSkills(Map<ResourceLocation, Skill> data, Logger logger) {
        data.values().removeIf(skill -> {
            boolean err = false;
            if (!data.keySet().containsAll(skill.parents())) {
                logger.warn("Skill {} is missing parents {}. It will be removed!", skill.getId(), new HashSet<>(skill.parents()).removeAll(data.keySet()));
                err = true;
            }
            OcculusTab tab = RegistryAccess.BUILTIN.get().registryOrThrow(OcculusTab.REGISTRY_KEY).get(skill.occulusTab());
            if (tab == null) {
                logger.warn("The occulus tab {} for skill {} is not available. The skill will be removed!", skill.occulusTab(), skill.getId());
                return true;
            }
            if (skill.y() < 0 || skill.y() > (tab.height() - 32) || skill.x() < 0 || skill.x() > (tab.width() - 32)) {
                logger.warn("Skill {} is outside the bounds of the skill tree. It will be removed!", skill.getId());
                err = true;
            }
            return err;
        });
    }
}
