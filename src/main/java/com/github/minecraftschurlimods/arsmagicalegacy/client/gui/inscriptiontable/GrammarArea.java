package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.InscriptionTableBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

public class GrammarArea extends DragTargetArea {
    private static final Identifier BACKGROUND = ArsMagicaApi.id("textures/gui/inscription_table/grammar.png");
    private static final int X_PADDING = 4;
    public boolean darken = false;

    public GrammarArea(int x, int y, int width, int height, Runnable onDrop) {
        super(x, y, width, height, 8, onDrop);
    }

    @Override
    @Nullable
    public Draggable elementAt(int mouseX, int mouseY) {
        if (mouseX < x + X_PADDING || mouseX >= x + maxSize * Draggable.SIZE + X_PADDING || mouseY < y || mouseY >= y + Draggable.SIZE) return null;
        int index = (mouseX - x - X_PADDING) / Draggable.SIZE;
        return contents.size() > index ? contents.get(index) : null;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        AMClientUtil.blit(graphics, BACKGROUND, x - 3, y - 3, width + 6, height + 6);
        if (darken) {
            graphics.fill(x - 3, y - 3, x + width + 6, y + height + 6, 0x7f000000);
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        for (int i = 0; i < contents.size(); i++) {
            contents.get(i).extractRenderState(graphics, x + i * Draggable.SIZE + X_PADDING, y, partialTick);
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public boolean canPick(Draggable draggable, int mouseX, int mouseY) {
        if (contents.size() < 2) return true;
        SpellPart part = AMUtil.spellPart(draggable.getSkill()).value();
        return part.isModifier() || AMUtil.spellPart(contents.getFirst().getSkill()).value() != part || !AMUtil.spellPart(contents.get(1).getSkill()).value().isModifier();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public boolean canDrop(Draggable draggable, int mouseX, int mouseY) {
        if (!super.canDrop(draggable, mouseX, mouseY)) return false;
        Holder<Skill> skill = draggable.getSkill();
        SpellPart part = AMUtil.spellPart(skill).value();
        return part.isComponent() && contents.stream().noneMatch(e -> e.getSkill().getKey() == skill.getKey()) || part.isModifier() && !contents.isEmpty() && AMUtil.spellPart(contents.getFirst().getSkill()).value().isComponent();
    }

    public void setFromData(InscriptionTableBlockEntity.MenuData menuData) {
        contents.clear();
        menuData.grammar()
            .stream()
            .map(Draggable::new)
            .forEach(contents::add);
    }
}
