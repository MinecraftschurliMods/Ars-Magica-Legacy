package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.spellcustomization.color;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

import java.util.function.IntConsumer;

class ColorButton extends Button {
    private static final int SIZE = 10;
    private final int color;

    protected ColorButton(int x, int y, int color, IntConsumer onPress, Component tooltip) {
        super(x, y, SIZE, SIZE, Component.empty(), _ -> onPress.accept(color), DEFAULT_NARRATION);
        this.color = color;
        setTooltip(Tooltip.create(tooltip));
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        if (isHoveredOrFocused()) {
            graphics.fill(getX() - 1, getY() - 1, getX() + width + 1, getY() + height + 1, 0xffffffff);
        }
        graphics.fill(getX(), getY(), getX() + width, getY() + height, color | 0xff000000);
    }
}
