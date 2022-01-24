package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dropdis;

public interface DragHandler {
    Draggable getDragged();

    void setDragged(Draggable draggable, final DropArea source);

    void returnToSource();
}
