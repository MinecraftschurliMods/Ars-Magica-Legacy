package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SkillIconAtlas;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop.Draggable;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SpellPartDraggable extends Draggable<ISpellPart> {
    public static final int SIZE = 16;
    private final TextureAtlasSprite sprite;
    private final Component translationKey;

    protected SpellPartDraggable(ISpellPart content) {
        super(SIZE, SIZE, content);
        ResourceLocation id = content.getId();
        sprite = SkillIconAtlas.instance().getSprite(id);
        translationKey = Component.translatable(Util.makeDescriptionId(Skill.SKILL, id) + ".name");
    }

    public ISpellPart getPart() {
        return content;
    }

    public Component getTranslationKey() {
        return translationKey;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.blit(0, 0, 10, width, height, sprite);
        pGuiGraphics.pose().popPose();
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
        pNarrationElementOutput.add(NarratedElementType.TITLE, translationKey);
    }
}
