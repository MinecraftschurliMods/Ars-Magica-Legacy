package com.github.minecraftschurli.arsmagicalegacy.client.hud;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.ColorUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public abstract class AbstractHUD extends GuiComponent implements IIngameOverlay {
    public static final ResourceLocation BAR_TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/bar.png");

    @Override
    public void render(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height) {
        if (!Minecraft.getInstance().options.hideGui && gui.shouldDrawSurvivalElements()) {
            this.render(mStack, width, height, partialTicks);
        }
    }

    protected abstract void render(PoseStack mStack, int width, int height, float partialTicks);

    protected void renderBar(PoseStack mStack, int x, int y, int width, int height, double value, double maxValue, int color) {
        mStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, BAR_TEXTURE);
        blit(mStack, x, y, 0, 0, width + 1, height -1);
        float r = ColorUtil.getRed(color);
        float g = ColorUtil.getGreen(color);
        float b = ColorUtil.getBlue(color);
        RenderSystem.setShaderFogColor(r, g, b);
        blit(mStack, x + 2, y + 2, 2, height + 1, (int)Math.ceil(Math.max(width * (value / maxValue), 0)) - 1, height - 3);
        RenderSystem.setShaderFogColor(1, 1, 1, 1);
        RenderSystem.disableBlend();
        mStack.popPose();
    }
}
