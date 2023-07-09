package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop;

import net.minecraft.client.gui.components.Widget;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class DragArea<T extends Draggable<?>> implements Widget {
    protected final int x;
    protected final int y;
    protected final int width;
    protected final int height;
    protected final List<T> contents = new ArrayList<>();
    protected boolean locked;

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

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }

    public void lock() {
        setLocked(true);
    }

    public void unlock() {
        setLocked(false);
    }

    public boolean canPick(T draggable) {
        return isLocked();
    }

    public boolean canDrop(T draggable) {
        return isLocked();
    }

    public void pick(T draggable) {
        contents.remove(draggable);
    }

    public void drop(T draggable) {
        contents.add(draggable);
    }
}
