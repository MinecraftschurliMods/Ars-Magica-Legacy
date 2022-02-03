package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dropdis;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;

import java.util.List;

public interface DropArea extends Widget, GuiEventListener {
    /**
     * @return A list of all elements in this drop area.
     */
    List<Draggable> items();

    /**
     * Adds a new element to this drop area.
     *
     * @param d The element to add.
     */
    boolean add(Draggable d);

    /**
     * Removes a new element from this drop area.
     *
     * @param d The element to remove.
     */
    void remove(Draggable d);

    @Override
    default void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        for (Draggable draggable : items()) {
            draggable.render(poseStack, mouseX, mouseY, partialTicks);
        }
        for (Draggable draggable : items()) {
            if (draggable.isMouseOver(mouseX, mouseY)) {
                draggable.renderTooltip(poseStack, mouseX, mouseY);
            }
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

    /**
     * @return The drag handler of this drop area.
     */
    DragHandler getDragHandler();

    /**
     * Sets the drag handler of this drop area.
     *
     * @param dragHandler The drag handler to set.
     */
    void setDragHandler(DragHandler dragHandler);
}
