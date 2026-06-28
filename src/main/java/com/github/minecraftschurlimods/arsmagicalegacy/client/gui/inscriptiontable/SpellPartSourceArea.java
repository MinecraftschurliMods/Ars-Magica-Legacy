package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStat;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class SpellPartSourceArea extends DragArea {
    private static final Identifier BACKGROUND = ArsMagicaApi.id("textures/gui/inscription_table/source.png");
    private static final int X_PADDING = 4;
    private static final int ROWS = 3;
    private static final int COLUMNS = 8;
    private final InscriptionTableScreen screen;
    private final List<Pair<Draggable, Pair<Integer, Integer>>> cache = new ArrayList<>();
    @Nullable
    private String nameFilter;
    private boolean primaryShapes = true;
    private boolean secondaryShapes = true;
    private boolean components = true;
    private boolean modifiers = true;

    public SpellPartSourceArea(int x, int y, int width, int height, InscriptionTableScreen screen) {
        super(x, y, width, height);
        this.screen = screen;
        updateCache();
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter.toLowerCase(Locale.ROOT);
        updateCache();
    }

    public void setTypeFilter(boolean primaryShapes, boolean secondaryShapes, boolean components, boolean modifiers) {
        this.primaryShapes = primaryShapes;
        this.secondaryShapes = secondaryShapes;
        this.components = components;
        this.modifiers = modifiers;
        updateCache();
    }

    @Override
    @Nullable
    public Draggable elementAt(int mouseX, int mouseY) {
        return cache.stream()
            .filter(e -> mouseX >= e.getSecond().getFirst() && mouseX < e.getSecond().getFirst() + Draggable.SIZE && mouseY >= e.getSecond().getSecond() && mouseY < e.getSecond().getSecond() + Draggable.SIZE)
            .findAny()
            .map(Pair::getFirst)
            .orElse(null);
    }

    @Override
    public List<Draggable> getAll() {
        LocalPlayer player = AMClientUtil.player();
        return player == null ? List.of() : AMRegistries.skills(true)
            .listElements()
            .filter(e -> ArsMagicaApi.magicHelper().knows(player, e))
            .map(Draggable::new)
            .toList();
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        AMClientUtil.blit(graphics, BACKGROUND, x - 3, y - 3, width + 6, height + 6);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        for (Pair<Draggable, Pair<Integer, Integer>> pair : cache) {
            Pair<Integer, Integer> xy = pair.getSecond();
            pair.getFirst().extractRenderState(graphics, xy.getFirst(), xy.getSecond(), partialTick);
        }
    }

    @Override
    public List<Draggable> getVisible() {
        updateCache();
        return cache.stream().map(Pair::getFirst).toList();
    }

    @Override
    public boolean canDrop(Draggable draggable, int mouseX, int mouseY) {
        return false;
    }

    private void updateCache() {
        cache.clear();
        List<Draggable> list = getAll()
            .stream()
            .map(Draggable::getSkill)
            .filter(e -> nameFilter == null || Skill.getName(e).getString().toLowerCase(Locale.ROOT).contains(nameFilter))
            .filter(this::isSkillVisible)
            .limit(ROWS * COLUMNS)
            .map(Draggable::new)
            .toList();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                int index = i * COLUMNS + j;
                if (index >= list.size()) return;
                cache.add(Pair.of(list.get(index), Pair.of(x + j * Draggable.SIZE + X_PADDING, y + i * Draggable.SIZE)));
            }
        }
    }

    private boolean isSkillVisible(Holder<Skill> skill) {
        Holder<SpellPart> holder = AMUtil.spellPart(skill);
        if (holder == null) return false;
        SpellPart spellPart = holder.value();
        if (spellPart.isPrimaryShape() && primaryShapes) return true;
        if (spellPart.isSecondaryShape() && secondaryShapes) return true;
        if (spellPart.isComponent() && components && screen.getGrammarArea().contents
            .stream()
            .map(Draggable::getSkill)
            .map(AMUtil::spellPart)
            .filter(Objects::nonNull)
            .map(Holder::value)
            .noneMatch(part -> part == spellPart)) return true;
        if (spellPart.isModifier()) {
            if (!modifiers || !primaryShapes && !secondaryShapes && !components) return false;
            Set<SpellStat> stats = spellPart.getStats();
            if (stats.contains(SpellStat.COLOR)) return true;
            return Stream.concat(screen.getGrammarArea().contents.stream(), screen.getShapeGroupAreas().stream().map(area -> area.contents).flatMap(List::stream))
                .map(Draggable::getSkill)
                .map(AMUtil::spellPart)
                .filter(Objects::nonNull)
                .map(Holder::value)
                .filter(part -> !part.isModifier())
                .map(SpellPart::getStats)
                .flatMap(Set::stream)
                .anyMatch(stats::contains);
        }
        return false;
    }
}
