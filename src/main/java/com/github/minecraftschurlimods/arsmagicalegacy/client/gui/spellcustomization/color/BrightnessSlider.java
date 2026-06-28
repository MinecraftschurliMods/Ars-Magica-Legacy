package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.spellcustomization.color;

import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import org.apache.commons.lang3.function.TriConsumer;
import org.jspecify.annotations.Nullable;

class BrightnessSlider extends ColorPickerWidget {
    protected BrightnessSlider(int x, int y, int width, int height, TriConsumer<Float, Float, Float> onChange) {
        super(x, y, width, height, onChange);
    }

    @Override
    protected float @Nullable [] getHovered(MouseButtonEvent event) {
        return new float[]{hue, saturation, Math.clamp((float) (1 - (event.y() - getY()) / height), 0, 1)};
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        if (isFocused()) {
            graphics.fill(getX() - 1, getY() - 1, getX() + width + 1, getY() + height + 1, 0xffffffff);
        }
        int[] rgb = AMClientUtil.hsbToRgb(hue, saturation, 1);
        graphics.fillGradient(getX(), getY(), getX() + width, getY() + height, 0xff << 24 | rgb[0] << 16 | rgb[1] << 8 | rgb[2], 0xff000000);
        extractIndicator(graphics, getX() + width / 2, (int) (getY() + Math.clamp(1 - brightness, 0, 1) * height));
    }
}
