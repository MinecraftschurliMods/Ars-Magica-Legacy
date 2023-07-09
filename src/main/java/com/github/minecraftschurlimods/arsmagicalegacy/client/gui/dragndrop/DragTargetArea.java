package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop;

public abstract class DragTargetArea<T extends Draggable<?>> extends DragArea<T> {
    protected final int maxElements;

    public DragTargetArea(int x, int y, int width, int height, int maxElements) {
        super(x, y, width, height);
        this.maxElements = maxElements;
    }

    @Override
    public boolean canDrop(T draggable) {
        return canStore();
    }

    public boolean canStore() {
        return maxElements > contents.size();
    }
}
