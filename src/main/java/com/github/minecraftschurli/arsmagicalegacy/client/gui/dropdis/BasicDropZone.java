package com.github.minecraftschurli.arsmagicalegacy.client.gui.dropdis;

import com.mojang.blaze3d.vertex.PoseStack;

import java.util.ArrayList;
import java.util.List;

public class BasicDropZone implements DropArea {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int elementWidth;
    private final int elementHeight;
    private final int elementPadding;
    private final int size;
    private final List<Draggable> items;
    private DragHandler dragHandler;

    public BasicDropZone(int x, int y, int width, int height, int elementWidth, int elementHeight, int elementPadding, int size) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.elementWidth = elementWidth;
        this.elementHeight = elementHeight;
        this.elementPadding = elementPadding;
        this.size = size;
        this.items = new ArrayList<>(this.size);
    }

    public BasicDropZone(int x, int y, int width, int height, int elementWidth, int elementHeight, int size) {
        this(x, y, width, height, elementWidth, elementHeight, 2, size);
    }

    @Override
    public List<Draggable> items() {
        return this.items;
    }

    @Override
    public boolean add(Draggable d) {
        if (this.items.size() + 1 > this.size) {
            return false;
        }
        boolean add = this.items.add(d);
        setPositionAndSize(d);
        return add;
    }

    @Override
    public void render(final PoseStack poseStack, final int mouseX, final int mouseY, final float partialTicks) {
        DropArea.super.render(poseStack, mouseX, mouseY, partialTicks);
        DropArea.vLine(poseStack, this.x, this.y, this.y + this.height, 0xffff0000);
        DropArea.vLine(poseStack, this.x + this.width, this.y, this.y + this.height, 0xffff0000);
        DropArea.hLine(poseStack, this.x, this.x + this.width, this.y, 0xffff0000);
        DropArea.hLine(poseStack, this.x, this.x + this.width, this.y + this.height, 0xffff0000);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        if (mouseX < x || mouseY < y) return false;
        int w = x + width;
        int h = y + height;
        return w < x && h < y || w > mouseX && h < y || w < x && h > mouseY || w > mouseX && h > mouseY;
    }

    @Override
    public DragHandler getDragHandler() {
        return dragHandler;
    }

    @Override
    public void setDragHandler(DragHandler dragHandler) {
        this.dragHandler = dragHandler;
    }

    private void setPositionAndSize(Draggable d) {
        int i = this.items.indexOf(d);
        int j = 0;
        int rows = (this.height - this.elementPadding) / (this.elementHeight + this.elementPadding);
        int cols = (this.width - this.elementPadding) / (this.elementWidth + this.elementPadding);
        while (i > cols) {
            i -= cols;
            j++;
        }
        if (j > rows) {
            throw new RuntimeException();
        }
        d.x = this.x + (i * (this.elementWidth + this.elementPadding)) + this.elementPadding;
        d.y = this.y + (j * (this.elementHeight + this.elementPadding)) + this.elementPadding;
        d.width = this.elementWidth;
        d.height = this.elementHeight;
    }

    @Override
    public void remove(Draggable d) {
        this.items.remove(d);
    }
}
