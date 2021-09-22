package com.github.minecraftschurli.arsmagicalegacy.common.skill;

import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public record Skill(ResourceLocation id, ResourceLocation occulusTab, ResourceLocation icon, Set<ResourceLocation> parents, int x, int y, Map<ResourceLocation, Integer> cost, boolean hidden) implements ISkill {

    @Override
    public ResourceLocation getId() {
        return id();
    }

    @Override
    public ResourceLocation getOcculusTab() {
        return occulusTab();
    }

    @Override
    public ResourceLocation getIcon() {
        return icon();
    }

    @Override
    public Set<ResourceLocation> getParents() {
        return Collections.unmodifiableSet(parents());
    }

    @Override
    public int getX() {
        return x();
    }

    @Override
    public int getY() {
        return y();
    }

    @Override
    public Map<ResourceLocation, Integer> getCost() {
        return Collections.unmodifiableMap(cost());
    }

    @Override
    public boolean isHidden() {
        return hidden();
    }
}
