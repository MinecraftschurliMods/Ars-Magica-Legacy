package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dropdis;

public interface DragHandler {
    /**
     * @return The currently dragged element.
     */
    Draggable getDragged();

    /**
     * Sets the dragged element.
     * @param draggable The draggable element to set.
     * @param source    The drop area the draggable is being dragged from.
     */
    void setDragged(Draggable draggable, final DropArea source);

    /**
     * Returns the currently dragged element to its source.
     */
    void returnToSource();
}
