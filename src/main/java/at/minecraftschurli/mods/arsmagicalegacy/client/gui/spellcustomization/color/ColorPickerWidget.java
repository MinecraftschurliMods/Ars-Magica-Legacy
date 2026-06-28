package at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellcustomization.color;

import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.function.TriConsumer;
import org.jspecify.annotations.Nullable;

abstract class ColorPickerWidget extends AbstractWidget {
    private final TriConsumer<Float, Float, Float> onChange;
    protected float hue;
    protected float saturation;
    protected float brightness;

    protected ColorPickerWidget(int x, int y, int width, int height, TriConsumer<Float, Float, Float> onChange) {
        super(x, y, width, height, Component.empty());
        this.onChange = onChange;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        setHovered(event);
    }

    @Override
    protected void onDrag(MouseButtonEvent event, double dx, double dy) {
        if (isHovered) {
            setHovered(event);
        }
    }

    protected void setValue(float hue, float saturation, float brightness) {
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }

    protected void setHovered(MouseButtonEvent event) {
        float @Nullable [] hovered = getHovered(event);
        if (hovered != null) {
            setValue(hovered[0], hovered[1], hovered[2]);
            onChange();
        }
    }

    protected void onChange() {
        onChange.accept(hue, saturation, brightness);
    }

    protected void extractIndicator(GuiGraphicsExtractor graphics, int x, int y) {
        int[] rgb = AMClientUtil.hsbToRgb(hue, saturation, brightness);
        int color = rgb[0] * 0.299 + rgb[1] * 0.587 + rgb[2] * 0.114 > 186 ? 0xff000000 : 0xffffffff;
        graphics.fill(x - 1, y - 1, x + 1, y + 1, color);
    }

    protected abstract float @Nullable [] getHovered(MouseButtonEvent event);
}
