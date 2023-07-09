package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop;

import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.narration.NarratableEntry;

public abstract class Draggable<T> implements Widget, NarratableEntry {
    protected final int width;
    protected final int height;
    protected final T content;

    protected Draggable(int width, int height, T content) {
        this.width = width;
        this.height = height;
        this.content = content;
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }
}
