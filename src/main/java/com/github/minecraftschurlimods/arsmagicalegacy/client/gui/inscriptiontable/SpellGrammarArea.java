package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop.DragTargetArea;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.Nullable;

public class SpellGrammarArea extends DragTargetArea<SpellPartDraggable> {
    public SpellGrammarArea(int x, int y, int width, int height) {
        super(x, y, width, height, 8);
    }

    @Override
    @Nullable
    public SpellPartDraggable elementAt(int mouseX, int mouseY) {
        if (mouseX < x + 4 || mouseX >= x + maxElements * 16 + 4 || mouseY < y || mouseY >= y + 16) return null;
        int index = (mouseX - x - 4) / 16;
        return contents.size() > index ? contents.get(index) : null;
    }

    @Override
    public boolean canDrop(SpellPartDraggable draggable) {
        return draggable.getPart().getType() != ISpellPart.SpellPartType.SHAPE;
    }

    @Override
    public boolean canPick(SpellPartDraggable draggable) {
        return contents.size() < 2 || draggable.getPart().getType() == ISpellPart.SpellPartType.MODIFIER || contents.get(0).getPart() != draggable.getPart() || contents.stream().noneMatch(e -> e.getPart().getType() == ISpellPart.SpellPartType.MODIFIER);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (contents.isEmpty()) return;
        if (contents.stream().anyMatch(e -> e.getPart().getType() == ISpellPart.SpellPartType.MODIFIER)) {
            RenderSystem.setShaderFogColor(0.5f, 0.5f, 0.5f); //fixme
        }
        contents.get(0).render(pPoseStack, x + 4, y, pPartialTick);
        RenderSystem.setShaderFogColor(1f, 1f, 1f);
        for (int i = 1; i < contents.size(); i++) {
            contents.get(i).render(pPoseStack, x + i * 16 + 4, y, pPartialTick);
        }
    }
}
