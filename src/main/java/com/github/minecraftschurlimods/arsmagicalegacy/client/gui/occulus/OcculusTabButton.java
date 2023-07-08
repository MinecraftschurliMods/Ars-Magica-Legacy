package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.OcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.RenderUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;

public class OcculusTabButton extends Button {
    private static final int SIZE = 22;
    private final int index;
    private final int xOffset;
    private final int yOffset;
    private final OcculusTab tab;

    public OcculusTabButton(int index, int x, int y, int xOffset, int yOffset, OcculusTab tab, OnPress pOnPress, OnTooltip pOnTooltip) {
        super(x, y, SIZE, SIZE, tab.getDisplayName(), pOnPress, pOnTooltip);
        this.index = index;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.tab = tab;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (!visible) return;
        float f = 1f / 0x100;
        RenderSystem.setShaderTexture(0, new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/occulus/overlay.png"));
        RenderUtil.drawBox(pPoseStack, x, y, SIZE, SIZE, getBlitOffset(), 0, 210 * f, SIZE * f, 210 * f + SIZE * f);
        RenderSystem.setShaderTexture(0, tab.icon(Minecraft.getInstance().getConnection().registryAccess()));
        RenderUtil.drawBox(pPoseStack, x + 2f, y + 2f, 18, 18, getBlitOffset(), 0, 0, 1, 1);
        pMouseX -= xOffset;
        pMouseY -= yOffset;
        isHovered = pMouseX >= x && pMouseY >= y && pMouseX < x + width && pMouseY < y + height;
        pPoseStack.pushPose();
        pPoseStack.translate(0, 0, 100);
        if (isHoveredOrFocused()) {
            renderToolTip(pPoseStack, pMouseX, pMouseY);
        }
        pPoseStack.popPose();
    }

    public int getIndex() {
        return index;
    }
}
