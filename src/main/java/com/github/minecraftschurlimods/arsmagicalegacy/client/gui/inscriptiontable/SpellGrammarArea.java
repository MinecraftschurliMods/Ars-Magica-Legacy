package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop.DragTargetArea;
import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.Nullable;

public class SpellGrammarArea extends DragTargetArea<SpellPartDraggable> {
    private static final int X_OFFSET = 4;

    public SpellGrammarArea(int x, int y, int width, int height) {
        super(x, y, width, height, 8);
    }

    @Override
    @Nullable
    public SpellPartDraggable elementAt(int mouseX, int mouseY) {
        if (mouseX < x + X_OFFSET || mouseX >= x + maxElements * SpellPartDraggable.SIZE + X_OFFSET || mouseY < y || mouseY >= y + SpellPartDraggable.SIZE) return null;
        int index = (mouseX - x - X_OFFSET) / SpellPartDraggable.SIZE;
        return contents.size() > index ? contents.get(index) : null;
    }

    @Override
    public boolean canDrop(SpellPartDraggable draggable) {
        return draggable.getPart().getType() != ISpellPart.SpellPartType.SHAPE;
    }

    @Override
    public boolean canPick(SpellPartDraggable draggable) {
        return contents.size() < 2 || draggable.getPart().getType() == ISpellPart.SpellPartType.MODIFIER || contents.get(0).getPart() != draggable.getPart() || contents.get(1).getPart().getType() != ISpellPart.SpellPartType.MODIFIER;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (contents.isEmpty()) return;
        contents.get(0).render(pPoseStack, x + X_OFFSET, y, pPartialTick);
        for (int i = 1; i < contents.size(); i++) {
            contents.get(i).render(pPoseStack, x + i * SpellPartDraggable.SIZE + X_OFFSET, y, pPartialTick);
        }
    }
}
