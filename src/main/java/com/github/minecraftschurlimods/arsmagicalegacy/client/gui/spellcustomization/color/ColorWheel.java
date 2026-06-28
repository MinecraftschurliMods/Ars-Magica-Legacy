package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.spellcustomization.color;

import com.github.minecraftschurlimods.arsmagicalegacy.client.AMRenderPipelines;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.state.gui.BlitRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.Vec2;
import org.apache.commons.lang3.function.TriConsumer;
import org.joml.Matrix3x2f;
import org.jspecify.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

class ColorWheel extends ColorPickerWidget {
    private final int radius;

    protected ColorWheel(int centerX, int centerY, int radius, TriConsumer<Float, Float, Float> onChange) {
        super(centerX - radius, centerY - radius, radius * 2, radius * 2, onChange);
        this.radius = radius;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        if (isFocused()) {
            extractColorWheel(graphics, getX() - 1, getY() - 1, getX() + width + 1, getY() + height + 1, brightness, true);
        }
        extractColorWheel(graphics, getX(), getY(), getX() + width, getY() + height, brightness, false);
        extractIndicator(graphics, (int) (getX() + radius + radius * saturation * Math.cos(hue * Math.TAU)), (int) (getY() + radius + radius * saturation * Math.sin(hue * Math.TAU)));
    }

    @Override
    protected float @Nullable [] getHovered(MouseButtonEvent event) {
        Vec2 mouseRelative = getMouseRelative(event.x(), event.y());
        double length = mouseRelative.length();
        double angle = Math.atan2(mouseRelative.y, mouseRelative.x);
        if (angle < 0) {
            angle += Math.TAU;
        }
        return length <= radius ? new float[]{(float) (angle / Math.TAU), (float) (length / radius), brightness} : null;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        return active && visible && getMouseRelative(event.x(), event.y()).length() <= radius;
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        return switch (event.key()) {
            case GLFW.GLFW_KEY_LEFT -> {
                hue = (hue - 1 / 256f + 1) % 1;
                onChange();
                yield true;
            }
            case GLFW.GLFW_KEY_RIGHT -> {
                hue = (hue + 1 / 256f) % 1;
                onChange();
                yield true;
            }
            case GLFW.GLFW_KEY_UP -> {
                saturation = (saturation + 1 / 256f) % 1;
                onChange();
                yield true;
            }
            case GLFW.GLFW_KEY_DOWN -> {
                saturation = (saturation - 1 / 256f + 1) % 1;
                onChange();
                yield true;
            }
            default -> super.keyPressed(event);
        };
    }

    private Vec2 getMouseRelative(double mouseX, double mouseY) {
        return new Vec2((float) mouseX - getX() - radius, (float) mouseY - getY() - radius);
    }

    private void extractColorWheel(GuiGraphicsExtractor graphics, int x0, int y0, int x1, int y1, float brightness, boolean outline) {
        graphics.submitGuiElementRenderState(new BlitRenderState(AMRenderPipelines.COLOR_WHEEL, TextureSetup.noTexture(), new Matrix3x2f(graphics.pose()), x0, y0, x1, y1, -1, 1, -1, 1, ARGB.colorFromFloat(brightness, outline ? 1 : 0, 0, 0), graphics.peekScissorStack()));
    }
}
