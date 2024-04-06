package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class SkillPointPanel extends Screen implements NarratableEntry {
    private static final ResourceLocation SKILL_POINT_BG = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/occulus/skill_points.png");

    protected SkillPointPanel() {
        super(Component.empty());
    }

    @Override
    public void render(PoseStack stack, int pMouseX, int pMouseY, float pPartialTicks) {
        LocalPlayer player = getMinecraft().player;
        if (player == null) return;
        stack.pushPose();
        stack.translate(width, 0, -1);
        var api = ArsMagicaAPI.get();
        List<Pair<MutableComponent, Integer>> skillPoints = api.getSkillPointRegistry().getValues().stream().filter(e -> e != AMSkillPoints.NONE.get()).map(point -> Pair.of(point.getDisplayName().copy().append(Component.literal(" : " + api.getSkillHelper().getSkillPoint(player, point))), point.color())).toList();
        int maxSize = skillPoints.stream().map(Pair::getFirst).mapToInt(font::width).max().orElse(0) + 6;
        RenderSystem.setShaderTexture(0, SKILL_POINT_BG);
        blit(stack, maxSize, 0, -1, 252, 0, 4, 4, 256, 256);
        blit(stack, maxSize, height - 4, -1, 252, 252, 4, 4, 256, 256);
        int w = maxSize;
        int h = height - 8;
        while (w > 0) {
            int x = Math.min(w, 252);
            while (h > 0) {
                int y = Math.min(h, 248);
                blit(stack, w - x, 4 + h - y, -1, 4, 4, x, y, 256, 256);
                h -= y;
            }
            w -= x;
        }
        w = maxSize;
        h = height - 8;
        while (w > 0) {
            int x = Math.min(w, 252);
            blit(stack, w - x, 0, -1, 4, 0, x, 4, 256, 256);
            blit(stack, w - x, height - 4, -1, 4, 252, x, 4, 256, 256);
            w -= x;
        }
        while (h > 0) {
            int y = Math.min(h, 248);
            blit(stack, maxSize, 4 + h - y, -1, 252, 4, 4, y, 256, 256);
            h -= y;
        }
        int pointOffsetY = 5;
        stack.pushPose();
        stack.translate(0, 0, 1);
        for (Pair<MutableComponent, Integer> point : skillPoints) {
            font.draw(stack, point.getFirst(), 4, pointOffsetY, point.getSecond() | 0xFF000000);
            pointOffsetY += 12;
        }
        stack.popPose();
        stack.popPose();
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
    }
}
