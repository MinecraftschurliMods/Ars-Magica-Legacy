package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.ColorUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public abstract class AbstractHUD extends GuiComponent implements IIngameOverlay {
    public static final ResourceLocation BAR_TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/hud/bar.png");

    @Override
    public void render(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height) {
        if (!Minecraft.getInstance().options.hideGui && gui.shouldDrawSurvivalElements()) {
            render(mStack, width, height, partialTicks);
        }
    }

    /**
     * Renders the HUD.
     *
     * @param mStack       The pose stack to use.
     * @param width        The width to use.
     * @param height       The height to use.
     * @param partialTicks The partial ticks value to use.
     */
    protected abstract void render(PoseStack mStack, int width, int height, float partialTicks);

    /**
     * Renders a bar.
     *
     * @param mStack   The pose stack to use.
     * @param x        The x coordinate to draw the bar at.
     * @param y        The y coordinate to draw the bar at.
     * @param width    The width to use.
     * @param height   The height to use.
     * @param value    The current value of the bar.
     * @param maxValue The max value of the bar.
     * @param color    The bar color.
     */
    protected void renderBar(PoseStack mStack, int x, int y, int width, int height, double value, double maxValue, int color) {
        int relWidth = 0;
        if (maxValue != 0) {
            relWidth = (int) Math.ceil(Math.max(width * (value / maxValue), 0));
        }
        mStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, BAR_TEXTURE);
        blit(mStack, x, y, 0, 0, width + 1, height - 1);
        float r = ColorUtil.getRed(color);
        float g = ColorUtil.getGreen(color);
        float b = ColorUtil.getBlue(color);
        RenderSystem.setShaderColor(r, g, b, 1);
        RenderSystem.setShaderFogColor(r, g, b);
        blit(mStack, x + 2, y + 2, 2, height + 1, relWidth - 1, height - 3);
        RenderSystem.setShaderFogColor(1, 1, 1, 1);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.disableBlend();
        mStack.popPose();
    }
}
