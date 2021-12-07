package com.github.minecraftschurli.arsmagicalegacy.client.gui.dropdis;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;

import java.util.List;

public class Draggable extends AbstractContainerEventHandler implements Widget, NarratableEntry {
    private final Component name;
    private final TextureAtlasSprite sprite;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean visible = true;

    public Draggable(int x, int y, int width, int height, TextureAtlasSprite sprite, Component name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.name = name;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        this.x = this.x + (int) dragX; // fixme
        this.y = this.y + (int) dragY;
        return true;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        if (!isVisible()) return;
        poseStack.pushPose();
        blit(poseStack, x, y, 10, width, height, sprite);
        poseStack.popPose();
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        if (mouseX < x || mouseY < y) return false;
        int w = x + width;
        int h = y + height;
        return w < x && h < y || w > mouseX && h < y || w < x && h > mouseY || w > mouseX && h > mouseY;
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, this.name);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }
}
