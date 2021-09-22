package com.github.minecraftschurli.arsmagicalegacy.common.skill;

import com.github.minecraftschurli.arsmagicalegacy.api.skill.IOcculusTab;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class OcculusTab extends ForgeRegistryEntry<IOcculusTab> implements IOcculusTab {
    private final int occulusIndex;

    public OcculusTab(int occulusIndex) {
        this.occulusIndex = occulusIndex;
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(getId().getNamespace(), "textures/gui/occulus/" + getId().getPath() + ".png");
    }

    @Override
    public ResourceLocation getIcon() {
        return new ResourceLocation(getId().getNamespace(), "textures/icon/" + getId().getPath() + ".png");
    }

    protected int calcXOffset(int posX, int offsetX, ISkill s) {
        return posX - offsetX + s.getX();
    }

    protected int calcYOffset(int posY, int offsetY, ISkill s) {
        return posY - offsetY + s.getY();
    }

    @Override
    public int getOcculusIndex() {
        return occulusIndex;
    }
}
