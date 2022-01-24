package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dropdis;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class FilteredFilledDropArea<T> implements DropArea {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int elementWidth;
    private final int elementHeight;
    private final Predicate<T> filter;
    private final Iterable<T> elements;
    private final Function<T, Pair<TextureAtlasSprite, Component>> factory;
    private final List<DraggableWithData<T>> items = new ArrayList<>();
    private DragHandler dragHandler;

    public FilteredFilledDropArea(int x, int y, int width, int height, int elementWidth, int elementHeight, Predicate<T> filter, Iterable<T> elements, Function<T, Pair<TextureAtlasSprite, Component>> factory) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.elementWidth = elementWidth;
        this.elementHeight = elementHeight;
        this.filter = filter;
        this.elements = elements;
        this.factory = factory;
    }

    @Override
    public List<Draggable> items() {
        return items.stream().<Draggable>map(Function.identity()).toList();
    }

    @Override
    public boolean add(Draggable d) {
        return true;
    }

    @Override
    public void remove(Draggable d) {
        items.remove(d);
        update();
    }

    public void update() {
        this.items.clear();
        int x = this.x, y = this.y;
        for (T t : this.elements) {
            if (!this.filter.test(t)) {
                continue;
            }
            Pair<TextureAtlasSprite, Component> a = factory.apply(t);
            DraggableWithData<T> d = new DraggableWithData<>(x, y, elementWidth, elementHeight, a.getFirst(), a.getSecond(), t);
            this.items.add(d);
            x += this.elementWidth - 1;
            if (x > (this.x + this.width - this.elementWidth - 1)) {
                x = this.x;
                y += this.elementHeight - 1;
            }
            if (y > (this.y + this.height - this.elementHeight - 1)) break;
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        DropArea.super.render(poseStack, mouseX, mouseY, partialTicks);
//        DropArea.vLine(poseStack, this.x, this.y, this.y + this.height, 0xffff0000);
//        DropArea.vLine(poseStack, this.x + this.width, this.y, this.y + this.height, 0xffff0000);
//        DropArea.hLine(poseStack, this.x, this.x + this.width, this.y, 0xffff0000);
//        DropArea.hLine(poseStack, this.x, this.x + this.width, this.y + this.height, 0xffff0000);
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
}
