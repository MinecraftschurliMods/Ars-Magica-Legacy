package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.stream.Collectors;

public class SkillPointPanel extends AbstractWidget {
    private static final ResourceLocation SKILL_POINT_BG = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/occulus/skill_points.png");
    private final Font font;
    private final List<Component> skillPointText;

    protected SkillPointPanel(Player player, Font font, int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
        this.font = font;
        ArsMagicaAPI api = ArsMagicaAPI.get();
        this.skillPointText = api.getSkillPointRegistry()
                                 .getValues()
                                 .stream()
                                 .filter(e -> e != AMSkillPoints.NONE.get())
                                 .map(point -> {
                                     int skillPoint = api.getSkillHelper().getSkillPoint(player, point);
                                     return point.getDisplayName().copy().append(" : " + skillPoint).withStyle(Style.EMPTY.withColor(point.color()));
                                 }).collect(Collectors.toList());
        this.setWidth(skillPointText.stream().mapToInt(font::width).max().orElse(0) + 6);
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        graphics.pose().pushPose();
        graphics.pose().translate(getX(), getY(), 0);
        graphics.blit(SKILL_POINT_BG, width, 0, 0, 252, 0, 4, 4, 256, 256);
        graphics.blit(SKILL_POINT_BG, width, height - 4, 0, 252, 252, 4, 4, 256, 256);
        int w = width;
        int h = height - 8;
        while (w > 0) {
            int x = Math.min(w, 252);
            while (h > 0) {
                int y = Math.min(h, 248);
                graphics.blit(SKILL_POINT_BG, w - x, 4 + h - y, 0, 4, 4, x, y, 256, 256);
                h -= y;
            }
            w -= x;
        }
        w = width;
        h = height - 8;
        while (w > 0) {
            int x = Math.min(w, 252);
            graphics.blit(SKILL_POINT_BG, w - x, 0, 0, 4, 0, x, 4, 256, 256);
            graphics.blit(SKILL_POINT_BG, w - x, height - 4, 0, 4, 252, x, 4, 256, 256);
            w -= x;
        }
        while (h > 0) {
            int y = Math.min(h, 248);
            graphics.blit(SKILL_POINT_BG, width, 4 + h - y, 0, 252, 4, 4, y, 256, 256);
            h -= y;
        }
        int pointOffsetY = 5;
        graphics.pose().pushPose();
        graphics.pose().translate(0, 0, 1);
        for (Component point : skillPointText) {
            graphics.drawString(font, point, 4, pointOffsetY, point.getStyle().getColor().getValue() | 0xFF000000);
            pointOffsetY += 12;
        }
        graphics.pose().popPose();
        graphics.pose().popPose();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
    }
}
