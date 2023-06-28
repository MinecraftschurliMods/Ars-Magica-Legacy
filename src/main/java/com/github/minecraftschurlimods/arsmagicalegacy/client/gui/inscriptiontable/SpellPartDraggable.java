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
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SpellPartDraggable extends Draggable<ISpellPart> {
    public static final int SIZE = 16;
    private final TextureAtlasSprite sprite;
    private final Component translationKey;
    private final Map<Key<?>, Object> data = new HashMap<>();

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

    public <T> void setData(Key<T> key, T data) {
        this.data.put(key, data);
    }

    @SuppressWarnings("unchecked")
    public <T> T getData(Key<T> key, T defaultValue) {
        return (T) this.data.getOrDefault(key, defaultValue);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T getData(Key<T> key) {
        return (T) this.data.get(key);
    }

    public record Key<T>(String name) {
        private static final Map<String, Key<?>> LOOKUP = new HashMap<>();

        @SuppressWarnings("unchecked")
        public static synchronized <T> Key<T> get(String name) {
            return (Key<T>) LOOKUP.computeIfAbsent(name, Key::new);
        }
    }
}
