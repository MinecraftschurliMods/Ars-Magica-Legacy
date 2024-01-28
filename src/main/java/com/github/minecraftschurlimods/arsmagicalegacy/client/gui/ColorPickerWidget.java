package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

import com.github.minecraftschurlimods.arsmagicalegacy.client.AMRenderTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.databinding.Listenable;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

public class ColorPickerWidget extends AbstractWidget {
    public static final double TAU = 2 * Math.PI;

    public final Listenable<Float> hue = Listenable.create(0f);
    public final Listenable<Float> saturation = Listenable.create(0f);
    public final Listenable<Float> brightness = Listenable.create(1f);
    public final Listenable<Float[]> hsb = Listenable.array(Float.class, hue, saturation, brightness);
    public final Listenable<Integer> color = hsb.xmap(ColorUtil::hsbToRgb, ColorUtil::rgbToHsbBoxed);

    private final float radius;
    private float _hue;
    private float _saturation;
    private float _brightness;
    private BrightnessSlider brightnessSlider = null;

    public ColorPickerWidget(int x, int y, int radius, Component message, @Nullable ColorPickerWidget previous) {
        this(x, y, radius, message, previous != null ? previous.getHoveredColorHSB() : new float[]{0, 0, 1});
    }

    public ColorPickerWidget(int x, int y, int radius, Component message, int initialColor) {
        this(x, y, radius, message, ColorUtil.rgbToHsb(initialColor));
    }

    public ColorPickerWidget(int x, int y, int radius, Component message, float[] initialColor) {
        super(x - radius, y - radius, radius * 2, radius * 2, message);
        this.radius = radius;
        this._hue = initialColor[0];
        this._saturation = initialColor[1];
        this._brightness = initialColor[2];
        this.hue.set(_hue);
        this.saturation.set(_saturation);
        this.brightness.set(_brightness);
    }

    public float[] getHoveredColorHSB() {
        return new float[]{_hue, _saturation, _brightness};
    }

    public int getHoveredColorRGB() {
        return ColorUtil.hsbToRgb(_hue, _saturation, _brightness);
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        Vec2 mouseRel = getMouseRel(mouseX, mouseY);
        float dist = mouseRel.length();
        double angle = (float) Math.atan2(mouseRel.y, mouseRel.x);
        if (angle < 0) {
            angle += TAU;
        }
        if (dist <= radius) {
            _hue = (float) (angle / TAU);
            _saturation = dist / radius;
        } else {
            _hue = this.hue.get();
            _saturation = this.saturation.get();
        }
        renderColorWheel(graphics);
        double v = hue.get() * TAU;
        int x = getX() + (int) radius + (int) (radius * saturation.get() * Math.cos(v));
        int y = getY() + (int) radius + (int) (radius * saturation.get() * Math.sin(v));
        graphics.fill(x - 1, y - 1, x + 1, y + 1, ColorUtil.chooseBW(ColorUtil.hsbToRgb(hue.get(), saturation.get(), _brightness)) | 0xFF000000);
    }

    private void renderColorWheel(GuiGraphics graphics) {
        float cX = getX() + radius;
        float cY = getY() + radius;
        graphics.pose().pushPose();
        AMRenderTypes.setColorWheel(cX, cY, radius, _brightness);
        graphics.fillGradient(AMRenderTypes.COLOR_WHEEL, getX(), getY(), getX() + width, getY() + height, 0xFFFFFFFF, 0xFFFFFFFF, 0);
        graphics.pose().popPose();
    }

    @Override
    protected boolean clicked(double mouseX, double mouseY) {
        return this.isActive() && this.visible && getMouseRel((float) mouseX, (float) mouseY).length() <= radius;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.hue.set(_hue);
        this.saturation.set(_saturation);
    }

    @Override
    protected void onDrag(double pMouseX, double pMouseY, double pDragX, double pDragY) {
        if (isHovered) {
            this.hue.set(_hue);
            this.saturation.set(_saturation);
        }
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        return switch (pKeyCode) {
            case GLFW.GLFW_KEY_LEFT -> {
                this.hue.set((this.hue.get() - 0.01f + 1) % 1);
                yield true;
            }
            case GLFW.GLFW_KEY_RIGHT -> {
                this.hue.set((this.hue.get() + 0.01f) % 1);
                yield true;
            }
            case GLFW.GLFW_KEY_UP -> {
                this.saturation.set((this.saturation.get() + 0.01f) % 1);
                yield true;
            }
            case GLFW.GLFW_KEY_DOWN -> {
                this.saturation.set((this.saturation.get() - 0.01f + 1) % 1);
                yield true;
            }
            default -> super.keyPressed(pKeyCode, pScanCode, pModifiers);
        };
    }

    @NotNull
    private Vec2 getMouseRel(float mouseX, float mouseY) {
        Vec2 mouse = new Vec2(mouseX, mouseY);
        float cX = getX() + radius;
        float cY = getY() + radius;
        Vec2 center = new Vec2(-cX, -cY);
        return mouse.add(center);
    }

    @Override
    public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }

    public BrightnessSlider brightnessSlider(int x, int y, int width, int height, Component message, BrightnessSlider.Orientation orientation) {
        if (brightnessSlider != null) {
            throw new IllegalStateException("Brightness slider already created");
        }
        return brightnessSlider = new BrightnessSlider(x, y, width, height, message, orientation);
    }

    public Renderable hoverPreview(int x, int y, int width, int height) {
        return (graphics, mouseX, mouseY, partialTick) -> graphics.fill(x, y, x + width, y + height, getHoveredColorRGB());
    }

    public Renderable selectionPreview(int x, int y, int width, int height) {
        return (graphics, mouseX, mouseY, partialTick) -> graphics.fill(x, y, x + width, y + height, color.get());
    }

    public PresetColor presetColor(int x, int y, int width, int height, int color, Component name) {
        return new PresetColor(x, y, width, height, color, name);
    }

    public class PresetColor extends AbstractWidget {
        private final int color;

        public PresetColor(int x, int y, int width, int height, int color, Component name) {
            super(x, y, width, height, name);
            this.color = color;
        }

        @Override
        public void renderWidget(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
            int x = getX();
            int y = getY();
            if (isHoveredOrFocused()) {
                graphics.fill(x - 1, y - 1, x + width + 1, y + height + 1, 0xFFFFFFFF);
                float[] hsb = ColorUtil.rgbToHsb(color);
                _hue = hsb[0];
                _saturation = hsb[1];
                _brightness = hsb[2];
            }
            graphics.fill(x, y, x + width, y + height, color | 0xFF000000);
        }

        @Override
        public void onClick(double pMouseX, double pMouseY) {
            brightness.set(_brightness);
            saturation.set(_saturation);
            hue.set(_hue);
        }

        @Override
        public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
            narrationElementOutput.add(NarratedElementType.TITLE, getMessage());
        }
    }

    public class BrightnessSlider extends AbstractWidget {
        private final Orientation orientation;

        public BrightnessSlider(int x, int y, int width, int height, Component message, Orientation orientation) {
            super(x, y, width, height, message);
            this.orientation = orientation;
        }

        @SuppressWarnings("DuplicatedCode")
        @Override
        public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
            graphics.pose().pushPose();
            int c1 = ColorUtil.hsbToRgb(_hue, _saturation, 0);
            int c2 = ColorUtil.hsbToRgb(_hue, _saturation, 1);
            int x = getX();
            int y = getY();
            renderBar(graphics, mouseX, mouseY, x, y, x + width, y + height, c1, c2);
            int color = ColorUtil.chooseBW(ColorUtil.hsbToRgb(_hue, _saturation, brightness.get())) | 0xFF000000;
            switch (orientation) {
                case BOTTOM_TO_TOP -> {
                    int yTemp = (int) (y + brightness.get() * height);
                    graphics.fill(x, yTemp, x + width, yTemp + 1, color);
                }
                case TOP_TO_BOTTOM -> {
                    int yTemp = (int) (y + (1 - brightness.get()) * height);
                    graphics.fill(x, yTemp, x + width, yTemp + 1, color);
                }
                case LEFT_TO_RIGHT -> {
                    int xTemp = (int) (x + brightness.get() * width);
                    graphics.fill(xTemp, y, xTemp + 1, y + height, color);
                }
                case RIGHT_TO_LEFT -> {
                    int xTemp = (int) (x + (1 - brightness.get()) * width);
                    graphics.fill(xTemp, y, xTemp + 1, y + height, color);
                }
            }
            graphics.pose().popPose();
        }

        private void renderBar(GuiGraphics graphics, int mouseX, int mouseY, int x1, int y1, int x2, int y2, int c1, int c2) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            switch (orientation) {
                case BOTTOM_TO_TOP -> {
                    _brightness = isHovered ? (mouseY - getY()) / (float) height : brightness.get();
                    fillGradient(graphics, bufferbuilder, x1, y1, x2, y2, c1, c1, c2, c2);
                }
                case TOP_TO_BOTTOM -> {
                    _brightness = isHovered ? 1 - (mouseY - getY()) / (float) height : brightness.get();
                    fillGradient(graphics, bufferbuilder, x1, y1, x2, y2, c2, c2, c1, c1);
                }
                case LEFT_TO_RIGHT -> {
                    _brightness = isHovered ? 1 - (mouseX - getX()) / (float) width : brightness.get();
                    fillGradient(graphics, bufferbuilder, x1, y1, x2, y2, c1, c2, c2, c1);
                }
                case RIGHT_TO_LEFT -> {
                    _brightness = isHovered ? (mouseX - getX()) / (float) width : brightness.get();
                    fillGradient(graphics, bufferbuilder, x1, y1, x2, y2, c2, c1, c1, c2);
                }
            }
            tesselator.end();
            RenderSystem.disableBlend();
        }

        private void fillGradient(GuiGraphics graphics, BufferBuilder buffer, int x1, int y1, int x2, int y2, int c1, int c2, int c3, int c4) {
            Matrix4f pose = graphics.pose().last().pose();
            float a1 = (c1 >> 24 & 255) / 255.0F;
            float r1 = (c1 >> 16 & 255) / 255.0F;
            float g1 = (c1 >> 8 & 255) / 255.0F;
            float b1 = (c1 & 255) / 255.0F;
            float a2 = (c2 >> 24 & 255) / 255.0F;
            float r2 = (c2 >> 16 & 255) / 255.0F;
            float g2 = (c2 >> 8 & 255) / 255.0F;
            float b2 = (c2 & 255) / 255.0F;
            float a3 = (c3 >> 24 & 255) / 255.0F;
            float r3 = (c3 >> 16 & 255) / 255.0F;
            float g3 = (c3 >> 8 & 255) / 255.0F;
            float b3 = (c3 & 255) / 255.0F;
            float a4 = (c4 >> 24 & 255) / 255.0F;
            float r4 = (c4 >> 16 & 255) / 255.0F;
            float g4 = (c4 >> 8 & 255) / 255.0F;
            float b4 = (c4 & 255) / 255.0F;
            buffer.vertex(pose, x2, y1, 0).color(r1, g1, b1, a1).endVertex();
            buffer.vertex(pose, x1, y1, 0).color(r2, g2, b2, a2).endVertex();
            buffer.vertex(pose, x1, y2, 0).color(r3, g3, b3, a3).endVertex();
            buffer.vertex(pose, x2, y2, 0).color(r4, g4, b4, a4).endVertex();
        }

        @Override
        public void onClick(double mouseX, double mouseY) {
            ColorPickerWidget.this.brightness.set(_brightness);
        }

        @Override
        protected void onDrag(double pMouseX, double pMouseY, double pDragX, double pDragY) {
            if (isHovered) {
                ColorPickerWidget.this.brightness.set(_brightness);
            }
        }

        @Override
        public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
            return switch (pKeyCode) {
                case GLFW.GLFW_KEY_UP -> {
                    if (orientation == Orientation.TOP_TO_BOTTOM) {
                        ColorPickerWidget.this.brightness.set(brightness.get() + 0.01F);
                        yield true;
                    } else if (orientation == Orientation.BOTTOM_TO_TOP) {
                        ColorPickerWidget.this.brightness.set(brightness.get() - 0.01F);
                        yield true;
                    }
                    yield false;
                }
                case GLFW.GLFW_KEY_DOWN -> {
                    if (orientation == Orientation.TOP_TO_BOTTOM) {
                        ColorPickerWidget.this.brightness.set(brightness.get() - 0.01F);
                        yield true;
                    } else if (orientation == Orientation.BOTTOM_TO_TOP) {
                        ColorPickerWidget.this.brightness.set(brightness.get() + 0.01F);
                        yield true;
                    }
                    yield false;
                }
                case GLFW.GLFW_KEY_LEFT -> {
                    if (orientation == Orientation.RIGHT_TO_LEFT) {
                        ColorPickerWidget.this.brightness.set(brightness.get() + 0.01F);
                        yield true;
                    } else if (orientation == Orientation.LEFT_TO_RIGHT) {
                        ColorPickerWidget.this.brightness.set(brightness.get() - 0.01F);
                        yield true;
                    }
                    yield false;
                }
                case GLFW.GLFW_KEY_RIGHT -> {
                    if (orientation == Orientation.RIGHT_TO_LEFT) {
                        ColorPickerWidget.this.brightness.set(brightness.get() - 0.01F);
                        yield true;
                    } else if (orientation == Orientation.LEFT_TO_RIGHT) {
                        ColorPickerWidget.this.brightness.set(brightness.get() + 0.01F);
                        yield true;
                    }
                    yield false;
                }
                default -> super.keyPressed(pKeyCode, pScanCode, pModifiers);
            };
        }

        @Override
        public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        }

        public enum Orientation {
            TOP_TO_BOTTOM,
            BOTTOM_TO_TOP,
            LEFT_TO_RIGHT,
            RIGHT_TO_LEFT
        }
    }
}
