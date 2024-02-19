package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop.DragTargetArea;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShapeGroupListArea extends DragTargetArea<SpellPartDraggable> {
    private final List<ShapeGroupArea> shapeGroups;
    private final InscriptionTableScreen screen;

    public ShapeGroupListArea(int x, int y, InscriptionTableScreen screen, TriConsumer<SpellPartDraggable, Integer, Integer> onDrop) {
        super(x, y, ShapeGroupArea.WIDTH * 5, ShapeGroupArea.HEIGHT, 20);
        this.screen = screen;
        shapeGroups = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            shapeGroups.add(new ShapeGroupArea(x + i * ShapeGroupArea.WIDTH, y, (part, index) -> onDrop.accept(part, finalI, index)));
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
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        for (ShapeGroupArea area : shapeGroups) {
            area.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
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

    public void setLocks() {
        shapeGroups.forEach(e -> e.setLockState(ShapeGroupArea.LockState.ALL));
        for (int i = 0; i < screen.allowedShapeGroups(); i++) {
            shapeGroups.get(i).setLockState(ShapeGroupArea.LockState.NONE);
        }
        for (int i = 0; i < shapeGroups.size(); i++) {
            ShapeGroupArea area = shapeGroups.get(i);
            if (area.getAll().size() == 1 && shapeGroups.size() > i + 1 && !shapeGroups.get(i + 1).getAll().isEmpty()) {
                area.setLockState(ShapeGroupArea.LockState.FIRST);
            }
            if (i > 0 && shapeGroups.get(i - 1).getAll().isEmpty()) {
                area.setLockState(ShapeGroupArea.LockState.ALL);
            }
        }
    }

    public List<ShapeGroupArea> getShapeGroups() {
        return Collections.unmodifiableList(shapeGroups);
    }
}
