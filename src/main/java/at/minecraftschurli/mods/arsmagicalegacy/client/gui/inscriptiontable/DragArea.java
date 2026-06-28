package at.minecraftschurli.mods.arsmagicalegacy.client.gui.inscriptiontable;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import org.jspecify.annotations.Nullable;

import java.util.List;

public abstract class DragArea implements Renderable {
    protected final int x;
    protected final int y;
    protected final int width;
    protected final int height;

    public DragArea(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Nullable
    public abstract Draggable elementAt(int mouseX, int mouseY);

    public abstract List<Draggable> getAll();

    public abstract void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick);

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    public List<Draggable> getVisible() {
        return getAll();
    }

    public boolean canPick(Draggable draggable, int mouseX, int mouseY) {
        return true;
    }

    public boolean canDrop(Draggable draggable, int mouseX, int mouseY) {
        return true;
    }

    public void pick(Draggable draggable, int mouseX, int mouseY) {
    }

    public void drop(Draggable draggable, int mouseX, int mouseY) {
    }
}
