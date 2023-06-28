package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop.DragTargetArea;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.Nullable;

public class SpellGrammarArea extends DragTargetArea<SpellPartDraggable> {
    private static final int X_PADDING = 4;

    public SpellGrammarArea(int x, int y, int width, int height) {
        super(x, y, width, height, 8);
    }

    @Override
    @Nullable
    public SpellPartDraggable elementAt(int mouseX, int mouseY) {
        if (mouseX < x + X_PADDING || mouseX >= x + maxElements * SpellPartDraggable.SIZE + X_PADDING || mouseY < y || mouseY >= y + SpellPartDraggable.SIZE) return null;
        int index = (mouseX - x - X_PADDING) / SpellPartDraggable.SIZE;
        return contents.size() > index ? contents.get(index) : null;
    }

    @Override
    public boolean canPick(SpellPartDraggable draggable, int mouseX, int mouseY) {
        return contents.size() < 2 || draggable.getPart().getType() == ISpellPart.SpellPartType.MODIFIER || contents.get(0).getPart() != draggable.getPart() || contents.get(1).getPart().getType() != ISpellPart.SpellPartType.MODIFIER;
    }

    @Override
    public boolean canDrop(SpellPartDraggable draggable, int mouseX, int mouseY) {
        return canStore() && (draggable.getPart().getType() == ISpellPart.SpellPartType.COMPONENT || draggable.getPart().getType() == ISpellPart.SpellPartType.MODIFIER && !contents.isEmpty() && contents.get(0).getPart().getType() == ISpellPart.SpellPartType.COMPONENT);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        for (int i = 0; i < contents.size(); i++) {
            contents.get(i).render(guiGraphics, x + i * SpellPartDraggable.SIZE + X_PADDING, y, pPartialTick);
        }
    }
}
