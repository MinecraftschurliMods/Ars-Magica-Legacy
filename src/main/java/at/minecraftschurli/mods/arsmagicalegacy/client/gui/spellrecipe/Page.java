package at.minecraftschurli.mods.arsmagicalegacy.client.gui.spellrecipe;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

import java.util.List;

abstract class Page<T> {
    protected final int xOffset;
    protected final int yOffset;
    protected final int size;
    protected final int spacing;
    protected final int maxPerLine;
    protected final List<T> elements;

    Page(int xOffset, int yOffset, int size, int spacing, int maxPerLine, List<T> elements) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.size = size;
        this.spacing = spacing;
        this.maxPerLine = maxPerLine;
        this.elements = elements;
    }

    public void extractRenderState(GuiGraphicsExtractor graphics, int x, int y) {
        for (int i = 0; i < elements.size(); i++) {
            extractElement(elements.get(i), i, graphics, x + xOffset, y + yOffset);
        }
    }

    public List<Component> getTooltip(int mouseX, int mouseY) {
        int x = mouseX - xOffset;
        int y = mouseY - yOffset;
        int resultX = -1;
        int resultY = -1;
        for (int i = 0; i < maxPerLine; i++) {
            int min = i * (size + spacing);
            int max = min + size;
            if (x >= min && x < max) {
                resultX = i;
            }
        }
        for (int i = 0; i < Math.ceilDiv(elements.size(), maxPerLine); i++) {
            int min = i * (size + spacing);
            int max = min + size;
            if (y >= min && y < max) {
                resultY = i;
            }
        }
        if (resultX == -1 || resultY == -1) return List.of();
        int result = resultX + resultY * maxPerLine;
        return result >= elements.size() ? List.of() : getElementTooltip(elements.get(result));
    }

    public abstract Component getTitle();

    public abstract void extractElement(T element, int index, GuiGraphicsExtractor graphics, int x, int y);

    public abstract List<Component> getElementTooltip(T element);
}
