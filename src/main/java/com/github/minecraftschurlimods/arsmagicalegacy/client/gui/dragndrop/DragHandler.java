package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop;

import org.jetbrains.annotations.Nullable;

public interface DragHandler<T extends Draggable<?>> {
    @Nullable
    T getDragged();

    void setDragged(@Nullable T draggable);
}
