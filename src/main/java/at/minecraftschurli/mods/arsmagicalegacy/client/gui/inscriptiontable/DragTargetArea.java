package at.minecraftschurli.mods.arsmagicalegacy.client.gui.inscriptiontable;

import java.util.ArrayList;
import java.util.List;

public abstract class DragTargetArea extends DragArea {
    protected final List<Draggable> contents = new ArrayList<>();
    protected final int maxSize;
    private final Runnable onChange;

    public DragTargetArea(int x, int y, int width, int height, int maxSize, Runnable onChange) {
        super(x, y, width, height);
        this.maxSize = maxSize;
        this.onChange = onChange;
    }

    @Override
    public List<Draggable> getAll() {
        return contents;
    }

    @Override
    public boolean canDrop(Draggable draggable, int mouseX, int mouseY) {
        return maxSize > contents.size();
    }

    @Override
    public void pick(Draggable draggable, int mouseX, int mouseY) {
        contents.remove(draggable);
        onChange.run();
    }

    @Override
    public void drop(Draggable draggable, int mouseX, int mouseY) {
        contents.add(draggable);
        onChange.run();
    }

    public boolean isEmpty() {
        return getAll().isEmpty();
    }

    public boolean isNotFull() {
        return getAll().size() < maxSize;
    }
}
