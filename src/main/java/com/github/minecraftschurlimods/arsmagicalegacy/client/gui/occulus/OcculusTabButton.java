package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.OcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.RenderUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class OcculusTabButton extends Button {
    private static final int SIZE = 22;
    private final int index;
    private final int xOffset;
    private final int yOffset;
    private final OcculusTab tab;

    public OcculusTabButton(int index, int x, int y, int xOffset, int yOffset, OcculusTab tab, OnPress pOnPress) {
        super(x, y, SIZE, SIZE, tab.getDisplayName(ClientHelper.getRegistryAccess()), pOnPress, DEFAULT_NARRATION);
        this.index = index;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.tab = tab;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (!visible) return;
        float f = 1f / 0x100;
        RenderSystem.setShaderTexture(0, new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/occulus/overlay.png"));
        RenderUtil.drawBox(graphics, getX(), getY(), SIZE, SIZE, 0, 0, 210 * f, SIZE * f, 210 * f + SIZE * f);
        RenderSystem.setShaderTexture(0, tab.icon(ClientHelper.getRegistryAccess()));
        RenderUtil.drawBox(graphics, getX() + 2f, getY() + 2f, 18, 18, 0, 0, 0, 1, 1);
        pMouseX -= xOffset;
        pMouseY -= yOffset;
        isHovered = pMouseX >= getX() && pMouseY >= getY() && pMouseX < getX() + width && pMouseY < getY() + height;
    }

    public int getIndex() {
        return index;
    }

    public boolean isHovered() {
        return isHovered;
    }

    public Component getDisplayName(RegistryAccess registryAccess) {
        return tab.getDisplayName(registryAccess);
    }
}
