package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop.DragTargetArea;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShapeGroupArea extends DragTargetArea<SpellPartDraggable> {
    private static final ResourceLocation GUI = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/inscription_table.png");
    private static final int ROWS = 2;
    private static final int COLUMNS = 2;
    private static final int X_PADDING = 2;
    private static final int Y_PADDING = 1;
    static final int WIDTH = 36;
    static final int HEIGHT = 34;
    private LockState lockState = LockState.NONE;

    public ShapeGroupArea(int x, int y) {
        super(x, y, WIDTH, HEIGHT, ROWS * COLUMNS);
    }

    public void setLockState(LockState lockState) {
        this.lockState = lockState;
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
        if (lockState == LockState.ALL) return false;
        if (lockState == LockState.FIRST && !contents.isEmpty() && contents.get(0).getPart() == draggable.getPart()) return false;
        List<SpellPartDraggable> list = new ArrayList<>(contents);
        list.remove(draggable);
        return isValid(list);
    }

    @Override
    public boolean canDrop(SpellPartDraggable draggable, int mouseX, int mouseY) {
        if (lockState == LockState.ALL) return false;
        if (!canStore() || draggable.getPart().getType() == ISpellPart.SpellPartType.COMPONENT) return false;
        List<SpellPartDraggable> list = new ArrayList<>(contents);
        list.add(draggable);
        return isValid(list);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShaderTexture(0, GUI);
        GuiComponent.blit(pPoseStack, x, y, 5, 220, 18, WIDTH, HEIGHT, 256, 256);
        if (lockState == LockState.ALL) {
            pPoseStack.pushPose();
            pPoseStack.translate(0, 0, 10);
            GuiComponent.fill(pPoseStack, x, y, x + WIDTH, y + HEIGHT, 0x7f000000);
            pPoseStack.popPose();
        }
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                int index = i * COLUMNS + j;
                if (index >= contents.size()) return;
                contents.get(index).render(pPoseStack, x + j * SpellPartDraggable.SIZE + X_PADDING, y + i * SpellPartDraggable.SIZE + Y_PADDING, pPartialTick);
            }
        }
    }

    public static boolean isValid(List<SpellPartDraggable> list) {
        if (list.isEmpty()) return true;
        SpellPartDraggable first = list.get(0);
        if (first.getPart().getType() != ISpellPart.SpellPartType.SHAPE) return false;
        if (((ISpellShape) first.getPart()).needsPrecedingShape()) return false;
        SpellPartDraggable last = Objects.requireNonNull(AMUtil.getLastMatching(list, e -> e.getPart().getType() == ISpellPart.SpellPartType.SHAPE));
        for (int i = 1; i < list.size(); i++) {
            SpellPartDraggable part = list.get(i);
            if (part.getPart().getType() == ISpellPart.SpellPartType.MODIFIER) continue;
            if (((ISpellShape) part.getPart()).needsToComeFirst()) return false;
            if (part != last && ((ISpellShape) part.getPart()).isEndShape()) return false;
        }
        return true;
    }

    public enum LockState {
        NONE, FIRST, ALL
    }
}
