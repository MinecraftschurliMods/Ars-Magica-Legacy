package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop;

import java.util.List;

public abstract class DragSourceArea<T extends Draggable<?>> extends DragArea<T> {
    protected final int maxDisplay;

    public DragSourceArea(int x, int y, int width, int height, int maxDisplay) {
        super(x, y, width, height);
        this.maxDisplay = maxDisplay;
    }

    @Override
    public boolean canPick(T draggable, int mouseX, int mouseY) {
        return true;
    }

    @Override
    public boolean canDrop(T draggable, int mouseX, int mouseY) {
        return false;
    }

    @Override
    public List<T> getVisible() {
        return getAll().stream().limit(maxDisplay).toList();
    }
}
