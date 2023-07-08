package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop.DragSourceArea;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpellPartSourceArea extends DragSourceArea<SpellPartDraggable> {
    private static final int ROWS = 3;
    private static final int COLUMNS = 8;
    private final List<SpellPartDraggable> cachedContents = new ArrayList<>();

    public SpellPartSourceArea(int x, int y, int width, int height) {
        super(x, y, width, height, ROWS * COLUMNS);
        updateVisibility();
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        for (SpellPartDraggable part : cachedContents) {
            part.render(poseStack, part.getX(), part.getY(), partialTicks);
        }
        poseStack.pushPose();
        poseStack.translate(0, 0, 200);
        for (SpellPartDraggable part : cachedContents) {
            part.renderTooltip(poseStack, mouseX, mouseY, partialTicks);
        }
        poseStack.popPose();
    }

    @Override
    public List<SpellPartDraggable> getAll() {
        return ArsMagicaAPI.get().getSpellPartRegistry().getValues().stream()
                .filter(e -> ArsMagicaAPI.get().getSkillHelper().knows(Objects.requireNonNull(Minecraft.getInstance().player), e.getId()))
                .map(e -> new SpellPartDraggable(16, 16, e))
                .toList();
    }

    @Override
    public List<SpellPartDraggable> getVisible() {
        updateVisibility();
        return cachedContents;
    }

    private void updateVisibility() {
        cachedContents.clear();
        List<SpellPartDraggable> list = getAll();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (list.size() <= i * COLUMNS + j) return;
                SpellPartDraggable part = list.get(i * COLUMNS + j);
                part.setX(x + j * 16 + 4);
                part.setY(y + i * 16 + 1);
                cachedContents.add(part);
            }
        }
    }
}
