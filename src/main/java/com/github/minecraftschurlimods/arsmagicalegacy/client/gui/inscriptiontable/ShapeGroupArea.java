package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class ShapeGroupArea extends DragTargetArea {
    private static final Identifier BACKGROUND = ArsMagicaApi.id("textures/gui/inscription_table/shape_group.png");
    public static final int ROWS = 2;
    public static final int COLUMNS = 2;
    public static final int X_PADDING = 2;
    public static final int Y_PADDING = 1;
    public static final int WIDTH = 36;
    public static final int HEIGHT = 34;
    public boolean locked;
    public boolean darken = false;

    public ShapeGroupArea(int x, int y, Runnable onDrop) {
        super(x, y, WIDTH, HEIGHT, ROWS * COLUMNS, onDrop);
    }

    @Override
    @Nullable
    public Draggable elementAt(int mouseX, int mouseY) {
        mouseX -= x;
        mouseY -= y;
        mouseX -= X_PADDING;
        mouseY -= Y_PADDING;
        if (mouseX < 0 || mouseX >= ROWS * Draggable.SIZE || mouseY < 0 || mouseY >= COLUMNS * Draggable.SIZE) return null;
        int index = 0;
        index += mouseX / Draggable.SIZE;
        index += mouseY / Draggable.SIZE * COLUMNS;
        return contents.size() > index ? contents.get(index) : null;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        AMClientUtil.blit(graphics, BACKGROUND, x, y, width, height);
        if (locked || darken) {
            graphics.fill(x, y, x + width, y + height, 0x7f000000);
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                int index = i * COLUMNS + j;
                if (index >= contents.size()) return;
                contents.get(index).extractRenderState(graphics, x + j * Draggable.SIZE + X_PADDING, y + i * Draggable.SIZE + Y_PADDING, partialTick);
            }
        }
    }

    @Override
    public boolean canPick(Draggable draggable, int mouseX, int mouseY) {
        return !locked && (contents.size() < 2 || contents.getFirst().getSkill().getKey() != draggable.getSkill().getKey());
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public boolean canDrop(Draggable draggable, int mouseX, int mouseY) {
        if (locked || !super.canDrop(draggable, mouseX, mouseY)) return false;
        SpellPart part = AMUtil.spellPart(draggable.getSkill()).value();
        if (part.isPrimaryShape()) return contents.isEmpty();
        if (part.isSecondaryShape()) return !contents.isEmpty() && contents.stream().noneMatch(e -> AMUtil.spellPart(e.getSkill()).value().isSecondaryShape());
        return !contents.isEmpty() && part.isModifier();
    }

    public void setFromData(List<Holder<Skill>> skills) {
        contents.clear();
        skills.stream()
            .map(Draggable::new)
            .forEach(contents::add);
    }
}
