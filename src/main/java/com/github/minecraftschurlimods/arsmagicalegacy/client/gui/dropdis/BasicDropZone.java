package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dropdis;

import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
    private DropValidator validator;
    private Consumer<Draggable> dropListener;
    private Consumer<Draggable> dragListener;

    public BasicDropZone(int x, int y, int width, int height, int elementWidth, int elementHeight, int elementPadding, int size, @Nullable BasicDropZone prev) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.elementWidth = elementWidth;
        this.elementHeight = elementHeight;
        this.elementPadding = elementPadding;
        this.size = size;
        items = new ArrayList<>(size);
        if (prev != null) {
            for (Draggable element : prev.items) {
                add(element);
            }
        }
    }

    public BasicDropZone(int x, int y, int width, int height, int elementWidth, int elementHeight, int size, @Nullable BasicDropZone prev) {
        this(x, y, width, height, elementWidth, elementHeight, 2, size, prev);
    }

    @Override
    public List<Draggable> items() {
        return items;
    }

    @Override
    public boolean add(Draggable d) {
        if (validator != null && !validator.validate(items, d)) return false;
        if (items.size() + 1 > size) return false;
        boolean add = items.add(d);
        setPositionAndSize(d);
        if (dropListener != null) {
            dropListener.accept(d);
        }
        return add;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        DropArea.super.render(poseStack, mouseX, mouseY, partialTicks);
//        DropArea.vLine(poseStack, x, y, y + height, 0xffff0000);
//        DropArea.vLine(poseStack, x + width, y, y + height, 0xffff0000);
//        DropArea.hLine(poseStack, x, x + width, y, 0xffff0000);
//        DropArea.hLine(poseStack, x, x + width, y + height, 0xffff0000);
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

    public void setOnDropListener(Consumer<Draggable> listener) {
        this.dropListener = listener;
    }

    public void setOnDragListener(Consumer<Draggable> listener) {
        this.dragListener = listener;
    }

    public void clear() {
        if (items == null) return;
        new ArrayList<>(items).forEach(this::remove);
    }

    private void setPositionAndSize(Draggable d) {
        int i = items.indexOf(d);
        int j = 0;
        int rows = (height - elementPadding) / (elementHeight + elementPadding);
        int cols = (width - elementPadding) / (elementWidth + elementPadding);
        while (i > cols) {
            i -= cols;
            j++;
        }
        if (j > rows) {
            throw new RuntimeException("Encountered an issue with drag & drop!");
        }
        d.x = x + (i * (elementWidth + elementPadding)) + elementPadding;
        d.y = y + (j * (elementHeight + elementPadding)) + elementPadding;
        d.width = elementWidth;
        d.height = elementHeight;
    }

    @Override
    public void remove(Draggable d) {
        items.remove(d);
        if (dragListener != null) {
            dragListener.accept(d);
        }
    }

    public void setDropValidator(DropValidator validator) {
        this.validator = validator;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
