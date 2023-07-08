package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SkillIconAtlas;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.dragndrop.Draggable;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class SpellPartDraggable extends Draggable<ISpellPart> {
    private final TextureAtlasSprite sprite;
    private final Component translationKey;

    protected SpellPartDraggable(int width, int height, ISpellPart content) {
        super(width, height, content);
        ResourceLocation id = content.getId();
        sprite = SkillIconAtlas.instance().getSprite(id);
        translationKey = Component.translatable("skill." + id.getNamespace() + "." + id.getPath() + ".name");
    }

    @Override
    public void render(PoseStack poseStack, int x, int y, float partialTicks) {
        poseStack.pushPose();
        if (RenderSystem.getShaderTexture(0) != Minecraft.getInstance().getTextureManager().getTexture(sprite.atlas().location()).getId()) {
            RenderSystem.setShaderTexture(0, sprite.atlas().location());
        }
        blit(poseStack, x, y, 10, width, height, sprite);
        poseStack.popPose();
    }

    @Override
    public void renderTooltip(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        if (isMouseOver(mouseX, mouseY)) {
            Objects.requireNonNull(Minecraft.getInstance().screen).renderTooltip(poseStack, translationKey, mouseX, mouseY);
        }
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
        pNarrationElementOutput.add(NarratedElementType.TITLE, translationKey);
    }
}
