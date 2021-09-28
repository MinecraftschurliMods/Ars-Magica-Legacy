package com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SkillPointPanel extends Screen implements NarratableEntry {
    private static final ResourceLocation SKILL_POINT_BG = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/occulus/skill_points.png");

    protected SkillPointPanel() {
        super(TextComponent.EMPTY);
    }

    @Override
    public void render(@NotNull PoseStack stack, int pMouseX, int pMouseY, float pPartialTicks) {
        stack.pushPose();
        stack.translate(width, 0, 0);
        var api = ArsMagicaAPI.get();
        var knowledgeHelper = api.getKnowledgeHelper();
        int maxSize = api.getSkillPointRegistry()
                .getValues()
                .stream()
                .map(point -> point.getDisplayName().copy().append(new TextComponent(" : " + knowledgeHelper.getSkillPoint(getMinecraft().player, point))))
                .mapToInt(font::width)
                .max()
                .orElse(0) + 6;
        setBlitOffset(-1);
        RenderSystem.setShaderTexture(0, SKILL_POINT_BG);
        blit(stack, maxSize, 0, 252, 0, 4, 4);
        blit(stack, maxSize, height - 4, 252, 252, 4, 4);
        int w = maxSize;
        int h = height - 8;
        while (w > 0) {
            int x = Math.min(w, 252);
            while (h > 0) {
                int y = Math.min(h, 248);
                blit(stack, w - x, 4 + h - y, 4, 4, x, y);
                h -= y;
            }
            w -= x;
        }
        w = maxSize;
        h = height - 8;
        while (w > 0) {
            int x = Math.min(w, 252);
            blit(stack, w - x, 0, 4, 0, x, 4);
            blit(stack, w - x, height - 4, 4, 252, x, 4);
            w -= x;
        }
        while (h > 0) {
            int y = Math.min(h, 248);
            blit(stack, maxSize, 4 + h - y, 252, 4, 4, y);
            h -= y;
        }
        stack.popPose();
    }

    @NotNull
    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {}
}
