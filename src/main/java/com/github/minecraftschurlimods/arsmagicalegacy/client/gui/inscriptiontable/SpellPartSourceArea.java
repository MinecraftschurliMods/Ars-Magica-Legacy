package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop.DragSourceArea;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component.AbstractComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier.AbstractModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape.AbstractShape;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public class SpellPartSourceArea extends DragSourceArea<SpellPartDraggable> {
    private static final int ROWS = 3;
    private static final int COLUMNS = 8;
    private final List<Pair<SpellPartDraggable, Pair<Integer, Integer>>> cachedContents = new ArrayList<>();
    private String nameFilter;
    private boolean showShapes;
    private boolean showComponents;
    private boolean showModifiers;

    public SpellPartSourceArea(int x, int y, int width, int height) {
        super(x, y, width, height, ROWS * COLUMNS);
        nameFilter = "";
        showShapes = false;
        showComponents = true;
        showModifiers = false;
        updateVisibility();
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter.toLowerCase();
        updateVisibility();
    }

    public void setTypeFilter(boolean shapes, boolean components, boolean modifiers) {
        showShapes = shapes;
        showComponents = components;
        showModifiers = modifiers;
        updateVisibility();
    }

    @Override
    public SpellPartDraggable elementAt(int mouseX, int mouseY) {
        return cachedContents.stream()
                .filter(e -> mouseX >= e.getSecond().getFirst() && mouseX < e.getSecond().getFirst() + 16 && mouseY >= e.getSecond().getSecond() && mouseY < e.getSecond().getSecond() + 16)
                .findAny()
                .map(Pair::getFirst)
                .orElse(null);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        for (Pair<SpellPartDraggable, Pair<Integer, Integer>> pair : cachedContents) {
            Pair<Integer, Integer> xy = pair.getSecond();
            pair.getFirst().render(poseStack, xy.getFirst(), xy.getSecond(), partialTicks);
        }
    }

    @Override
    public List<SpellPartDraggable> getAll() {
        return getParts().stream()
                .map(SpellPartDraggable::new)
                .toList();
    }

    @Override
    public List<SpellPartDraggable> getVisible() {
        updateVisibility();
        return cachedContents.stream()
                .map(Pair::getFirst)
                .toList();
    }

    private void updateVisibility() {
        cachedContents.clear();
        List<SpellPartDraggable> list = getAll().stream()
                .filter(part -> switch (part.getPart().getType()) {
                    case SHAPE -> showShapes;
                    case COMPONENT -> showComponents;
                    case MODIFIER -> (showShapes || showComponents) && showModifiers;
                })
                .filter(part -> part.getTranslationKey().getString().toLowerCase().contains(nameFilter))
                .limit(maxDisplay)
                .toList();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                cachedContents.add(Pair.of(list.get(i * COLUMNS + j), Pair.of(x + j * 16 + 4, y + i * 16)));
            }
        }
    }

    private List<ISpellPart> getParts() {
        return ArsMagicaAPI.get().getSpellPartRegistry().getValues().stream()
                .filter(e -> ArsMagicaAPI.get().getSkillHelper().knows(Objects.requireNonNull(Minecraft.getInstance().player), e.getId()))
                .toList();
    }
}
