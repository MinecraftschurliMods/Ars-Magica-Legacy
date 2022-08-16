package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.ColorUtil;
import com.github.minecraftschurlimods.betterhudlib.HUDElement;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public abstract class AbstractHUD extends HUDElement {
    public static final ResourceLocation BAR_TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/hud/bar.png");
    private final EnumValue<AnchorX> anchorX;
    private final EnumValue<AnchorY> anchorY;
    private final IntValue x;
    private final IntValue y;

    protected AbstractHUD(EnumValue<AnchorX> anchorX, EnumValue<AnchorY> anchorY, IntValue x, IntValue y, int width, int height) {
        super(anchorX, anchorY, x::get, y::get, () -> width, () -> height);
        this.anchorX = anchorX;
        this.anchorY = anchorY;
        this.x = x;
        this.y = y;
    }

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

    @Override
    protected void onPositionUpdate(AnchorX anchorX, AnchorY anchorY, int x, int y) {
        this.anchorX.set(anchorX);
        this.anchorY.set(anchorY);
        this.x.set(x);
        this.y.set(y);
    }

    @Override
    protected void save() {
        Config.CLIENT.save();
    }
}
