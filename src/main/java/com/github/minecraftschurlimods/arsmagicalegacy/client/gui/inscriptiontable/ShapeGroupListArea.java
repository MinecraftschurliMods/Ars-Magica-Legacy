package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop.DragTargetArea;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ShapeGroupListArea extends DragTargetArea<SpellPartDraggable> {
    private final List<ShapeGroupArea> shapeGroups;
    private final InscriptionTableScreen screen;

    public ShapeGroupListArea(int x, int y, InscriptionTableScreen screen) {
        super(x, y, ShapeGroupArea.WIDTH * 5, ShapeGroupArea.HEIGHT, 20);
        this.screen = screen;
        shapeGroups = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            shapeGroups.add(new ShapeGroupArea(x + i * ShapeGroupArea.WIDTH, y));
        }
        setLocks();
    }

    @Override
    @Nullable
    public SpellPartDraggable elementAt(int mouseX, int mouseY) {
        ShapeGroupArea area = getHoveredArea(mouseX, mouseY);
        return area == null ? null : area.elementAt(mouseX, mouseY);
    }

    @Override
    public List<SpellPartDraggable> getAll() {
        List<SpellPartDraggable> list = new ArrayList<>();
        shapeGroups.forEach(e -> list.addAll(e.getAll()));
        return list;
    }

    @Override
    public boolean canPick(SpellPartDraggable draggable, int mouseX, int mouseY) {
        ShapeGroupArea area = getHoveredArea(mouseX, mouseY);
        return area != null && area.canPick(draggable, mouseX, mouseY);
    }

    @Override
    public boolean canDrop(SpellPartDraggable draggable, int mouseX, int mouseY) {
        ShapeGroupArea area = getHoveredArea(mouseX, mouseY);
        return area != null && area.canDrop(draggable, mouseX, mouseY);
    }

    @Override
    public void pick(SpellPartDraggable draggable, int mouseX, int mouseY) {
        ShapeGroupArea area = getHoveredArea(mouseX, mouseY);
        if (area != null && area.canPick(draggable, mouseX, mouseY)) {
            area.pick(draggable, mouseX, mouseY);
        }
        setLocks();
    }

    @Override
    public void drop(SpellPartDraggable draggable, int mouseX, int mouseY) {
        ShapeGroupArea area = getHoveredArea(mouseX, mouseY);
        if (area != null && area.canDrop(draggable, mouseX, mouseY)) {
            area.drop(draggable, mouseX, mouseY);
        }
        setLocks();
    }

    @Override
    public boolean canStore() {
        return shapeGroups.stream().anyMatch(DragTargetArea::canStore);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        for (ShapeGroupArea area : shapeGroups) {
            area.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        }
    }

    public boolean isValid() {
        return shapeGroups.stream().anyMatch(e -> ShapeGroupArea.isValid(e.getAll()));
    }

    public List<List<ResourceLocation>> getShapeGroupData() {
        return shapeGroups.stream()
                .map(e -> e.getAll().stream()
                        .map(f -> f.getPart().getId())
                        .toList())
                .toList();
    }

    public ShapeGroupArea get(int index) {
        return shapeGroups.get(index);
    }

    @Nullable
    private ShapeGroupArea getHoveredArea(int mouseX, int mouseY) {
        for (ShapeGroupArea area : shapeGroups) {
            if (area.isAbove(mouseX, mouseY)) return area;
        }
        return null;
    }

    private void setLocks() {
        shapeGroups.forEach(e -> e.setLocked(true));
        for (int i = 0; i < screen.allowedShapeGroups(); i++) {
            shapeGroups.get(i).setLocked(false);
        }
        for (int i = 0; i < shapeGroups.size(); i++) {
            ShapeGroupArea area = shapeGroups.get(i);
            if (area.getAll().size() == 1 && shapeGroups.size() > i + 1 && !shapeGroups.get(i + 1).getAll().isEmpty()) {
                area.setLocked(true);
            }
            if (i > 0 && shapeGroups.get(i - 1).getAll().isEmpty()) {
                area.setLocked(true);
            }
        }
    }
}
