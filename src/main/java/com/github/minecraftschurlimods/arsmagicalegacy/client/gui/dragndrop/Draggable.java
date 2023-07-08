package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Draggable<T> extends AbstractContainerEventHandler implements Widget, NarratableEntry {
    protected final int width;
    protected final int height;
    protected final T content;
    private Integer x = null;
    private Integer y = null;
    private DragArea<Draggable<T>> source;

    protected Draggable(int width, int height, T content) {
        this.width = width;
        this.height = height;
        this.content = content;
    }

    public abstract void renderTooltip(PoseStack poseStack, int mouseX, int mouseY, float partialTicks);

    @Nullable
    public DragArea<Draggable<T>> getSource() {
        return source;
    }

    public void setSource(@Nullable DragArea<Draggable<T>> area) {
        source = area;
    }

    public Integer getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }

    @Override
    public boolean isMouseOver(double pMouseX, double pMouseY) {
        if (x == null || y == null || pMouseX < x || pMouseY < y) return false;
        int w = x + width - 1;
        int h = y + height - 1;
        return w < x && h < y || w > pMouseX && h < y || w < x && h > pMouseY || w > pMouseX && h > pMouseY;
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    public void returnToSource() {
        source.drop(this);
    }

    public void pick(DragArea<Draggable<T>> source) {
        if (source.canPick(this)) {
            source.pick(this);
        }
    }

    public void drop(DragArea<Draggable<T>> target) {
        if (target.canDrop(this)) {
            target.drop(this);
        } else {
            returnToSource();
        }
    }
}
