package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop;

import net.minecraft.client.gui.components.Renderable;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class DragArea<T extends Draggable<?>> implements Renderable {
    protected final int x;
    protected final int y;
    protected final int width;
    protected final int height;
    protected final List<T> contents = new ArrayList<>();

    public DragArea(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Nullable
    public abstract T elementAt(int mouseX, int mouseY);

    public boolean isAbove(int mouseX, int mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    public List<T> getAll() {
        return contents;
    }

    public List<T> getVisible() {
        return getAll();
    }

    public boolean canPick(T draggable, int mouseX, int mouseY) {
        return true;
    }

    public boolean canDrop(T draggable, int mouseX, int mouseY) {
        return true;
    }

    public void pick(T draggable, int mouseX, int mouseY) {
        contents.remove(draggable);
    }

    public void drop(T draggable, int mouseX, int mouseY) {
        contents.add(draggable);
    }
}
