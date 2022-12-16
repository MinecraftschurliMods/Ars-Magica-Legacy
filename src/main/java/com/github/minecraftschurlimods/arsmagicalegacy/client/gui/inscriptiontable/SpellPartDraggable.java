package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SkillIconAtlas;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop.Draggable;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
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
        translationKey = Component.translatable("skill." + id.getNamespace() + "." + id.getPath() + ".name");
    }

    public ISpellPart getPart() {
        return content;
    }

    public Component getTranslationKey() {
        return translationKey;
    }

    @Override
    public void render(PoseStack poseStack, int x, int y, float partialTicks) {
        poseStack.pushPose();
        if (RenderSystem.getShaderTexture(0) != Minecraft.getInstance().getTextureManager().getTexture(sprite.atlasLocation()).getId()) {
            RenderSystem.setShaderTexture(0, sprite.atlasLocation());
        }
        GuiComponent.blit(poseStack, x, y, 10, width, height, sprite);
        poseStack.popPose();
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
        pNarrationElementOutput.add(NarratedElementType.TITLE, translationKey);
    }
}
