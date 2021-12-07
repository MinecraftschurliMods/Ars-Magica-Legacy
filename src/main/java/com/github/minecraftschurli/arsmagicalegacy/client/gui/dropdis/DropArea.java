package com.github.minecraftschurli.arsmagicalegacy.client.gui.dropdis;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;

import java.util.List;

public interface DropArea extends Widget, GuiEventListener {
    List<Draggable> items();

    boolean add(Draggable d);

    void remove(Draggable d);

    @Override
    default void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        for (Draggable draggable : items()) {
            draggable.render(poseStack, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    default boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Draggable draggable : items()) {
            if (draggable.isMouseOver(mouseX, mouseY)) {
                getDragHandler().setDragged(draggable, this);
                remove(draggable);
                return true;
            }
        }
        return GuiEventListener.super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    default boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY)) {
            if (getDragHandler().getDragged() != null) {
                if (!add(getDragHandler().getDragged())) {
                    getDragHandler().returnToSource();
                }
                return true;
            }
        } else {
            getDragHandler().returnToSource();
        }
        return GuiEventListener.super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    boolean isMouseOver(double mouseX, double mouseY);

    DragHandler getDragHandler();

    void setDragHandler(DragHandler dragHandler);

    static void hLine(PoseStack poseStack, int minX, int maxX, int y, int color) {
        if (maxX < minX) {
            int i = minX;
            minX = maxX;
            maxX = i;
        }

        GuiComponent.fill(poseStack, minX, y, maxX + 1, y + 1, color);
    }

    static void vLine(PoseStack poseStack, int x, int minY, int maxY, int color) {
        if (maxY < minY) {
            int i = minY;
            minY = maxY;
            maxY = i;
        }

        GuiComponent.fill(poseStack, x, minY + 1, x + 1, maxY, color);
    }
}
