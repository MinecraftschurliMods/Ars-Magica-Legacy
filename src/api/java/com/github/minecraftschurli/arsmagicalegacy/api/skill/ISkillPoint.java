package com.github.minecraftschurli.arsmagicalegacy.api.skill;

import com.github.minecraftschurli.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Optional;

public interface ISkillPoint extends IForgeRegistryEntry<ISkillPoint>, ITranslatable {
    String SKILL_POINT = "skill_point";

    boolean canRenderInGui();

    @Override
    default ResourceLocation getId() {
        return Optional.ofNullable(getRegistryName()).orElseThrow();
    }

    @Override
    default String getType() {
        return SKILL_POINT;
    }
}
