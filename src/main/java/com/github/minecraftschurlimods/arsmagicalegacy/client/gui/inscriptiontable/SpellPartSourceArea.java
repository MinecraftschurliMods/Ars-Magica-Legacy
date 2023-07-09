package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop.DragSourceArea;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component.AbstractComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier.AbstractModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape.AbstractShape;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class SpellPartSourceArea extends DragSourceArea<SpellPartDraggable> {
    private static final int ROWS = 3;
    private static final int COLUMNS = 8;
    private final List<Pair<SpellPartDraggable, Pair<Integer, Integer>>> cachedContents = new ArrayList<>();
    private String nameFilter;
    private boolean showShapes;
    private boolean showComponents;
    private boolean showShapeModifiers;
    private boolean showComponentModifiers;

    public SpellPartSourceArea(int x, int y, int width, int height) {
        super(x, y, width, height, ROWS * COLUMNS);
        nameFilter = "";
        showShapes = true;
        showComponents = true;
        showShapeModifiers = false;
        showComponentModifiers = false;
        updateVisibility();
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter.toLowerCase();
        updateVisibility();
    }

    public void setTypeFilter(boolean shapes, boolean components, boolean shapeModifiers, boolean componentModifiers) {
        showShapes = shapes;
        showComponents = components;
        showShapeModifiers = shapeModifiers;
        showComponentModifiers = componentModifiers;
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
        return cachedContents.stream().map(Pair::getFirst).toList();
    }

    private void updateVisibility() {
        cachedContents.clear();
        List<SpellPartDraggable> list = getAll().stream()
                .filter(part -> switch (part.getPart().getType()) {
                    case SHAPE -> showShapes;
                    case COMPONENT -> showComponents;
                    case MODIFIER -> {
                        if (showShapeModifiers && showComponentModifiers) yield true; //early yield if all modifier types should be shown
                        AbstractModifier modifier = (AbstractModifier) part.getPart();
                        boolean isShapeModifier = getParts().stream()
                                .filter(e -> e.getType() == ISpellPart.SpellPartType.SHAPE)
                                .map(e -> (AbstractShape) e)
                                .anyMatch(e -> !Sets.intersection(Sets.newHashSet(e.getStatsUsed()), Sets.newHashSet(modifier.getStatsModified())).isEmpty());
                        boolean isComponentModifier = getParts().stream()
                                .filter(e -> e.getType() == ISpellPart.SpellPartType.COMPONENT)
                                .map(e -> (AbstractComponent) e)
                                .anyMatch(e -> !Sets.intersection(Sets.newHashSet(e.getStatsUsed()), Sets.newHashSet(modifier.getStatsModified())).isEmpty());
                        yield showShapeModifiers && isShapeModifier || showComponentModifiers && isComponentModifier;
                    }
                })
                .filter(part -> part.getTranslationKey().getString().toLowerCase().contains(nameFilter))
                .limit(maxDisplay)
                .toList();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                cachedContents.add(Pair.of(list.get(i * COLUMNS + j), Pair.of(x + j * 16 + 4, y + i * 16 + 1)));
            }
        }
    }

    private List<ISpellPart> getParts() {
        return ArsMagicaAPI.get().getSpellPartRegistry().getValues().stream()
                .filter(e -> ArsMagicaAPI.get().getSkillHelper().knows(Objects.requireNonNull(Minecraft.getInstance().player), e.getId()))
                .toList();
    }
}
