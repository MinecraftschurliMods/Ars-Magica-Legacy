package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop.DragTargetArea;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShapeGroupArea extends DragTargetArea<SpellPartDraggable> {
    private static final ResourceLocation GUI = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/inscription_table.png");
    private static final int ROWS = 2;
    private static final int COLUMNS = 2;
    private static final int X_PADDING = 2;
    private static final int Y_PADDING = 1;
    static final int WIDTH = 36;
    static final int HEIGHT = 34;
    private boolean locked = false;

    public ShapeGroupArea(int x, int y) {
        super(x, y, WIDTH, HEIGHT, ROWS * COLUMNS);
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    @Nullable
    public SpellPartDraggable elementAt(int mouseX, int mouseY) {
        mouseX -= x;
        mouseY -= y;
        mouseX -= X_PADDING;
        mouseY -= Y_PADDING;
        if (mouseX < 0 || mouseX >= ROWS * SpellPartDraggable.SIZE || mouseY < 0 || mouseY >= COLUMNS * SpellPartDraggable.SIZE) return null;
        int index = 0;
        index += mouseX / SpellPartDraggable.SIZE;
        index += mouseY / SpellPartDraggable.SIZE * COLUMNS;
        return contents.size() > index ? contents.get(index) : null;
    }

    @Override
    public boolean canPick(SpellPartDraggable draggable, int mouseX, int mouseY) {
        return !locked && (contents.size() < 2 || draggable.getPart().getType() == ISpellPart.SpellPartType.MODIFIER || contents.get(0).getPart() != draggable.getPart() || contents.get(1).getPart().getType() != ISpellPart.SpellPartType.MODIFIER);
    }

    @Override
    public boolean canDrop(SpellPartDraggable draggable, int mouseX, int mouseY) {
        return canStore() && !locked && (draggable.getPart().getType() == ISpellPart.SpellPartType.SHAPE || draggable.getPart().getType() == ISpellPart.SpellPartType.MODIFIER && !contents.isEmpty() && contents.get(0).getPart().getType() == ISpellPart.SpellPartType.SHAPE);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShaderTexture(0, GUI);
        if (locked) {
            RenderSystem.setShaderFogColor(0.5f, 0.5f, 0.5f); //fixme
        }
        GuiComponent.blit(pPoseStack, x, y, 5, 220, 18, WIDTH, HEIGHT, 256, 256);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                int index = i * COLUMNS + j;
                if (index >= contents.size()) return;
                contents.get(index).render(pPoseStack, x + j * SpellPartDraggable.SIZE + X_PADDING, y + i * SpellPartDraggable.SIZE + Y_PADDING, pPartialTick);
            }
        }
    }
}
