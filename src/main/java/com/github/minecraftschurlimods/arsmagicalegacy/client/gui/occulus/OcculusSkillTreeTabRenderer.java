package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.occulus.OcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SkillIconAtlas;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.ColorUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.RenderUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.github.minecraftschurlimods.arsmagicalegacy.network.LearnSkillPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class OcculusSkillTreeTabRenderer extends OcculusTabRenderer {
    public static final Component MISSING_REQUIREMENTS = Component.translatable(TranslationConstants.OCCULUS_MISSING_REQUIREMENTS).withStyle(ChatFormatting.DARK_RED);
    private static final float SKILL_SIZE = 32f;
    private static final float SCALE = 1f;
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private float offsetX = 0;
    private float offsetY = 0;
    private Skill hoverItem = null;

    public OcculusSkillTreeTabRenderer(OcculusTab occulusTab, Screen parent) {
        super(occulusTab, parent);
    }

    private static int getColorForSkill(Skill skill) {
        return skill.cost().keySet().stream().map(ArsMagicaAPI.get().getSkillPointRegistry()::getValue).filter(Objects::nonNull).findFirst().map(SkillPoint::color).orElse(ColorUtil.GRAY);
    }

    private static int getColorForLine(Skill parent, Skill child) {
        return ColorUtil.calculateAverage(getColorForSkill(parent), getColorForSkill(child));
    }

    @Override
    protected void init() {
        offsetX = occulusTab.startX() - width / 2f;
        if (offsetX < 0) offsetX = 0;
        if (offsetX > textureWidth - width) offsetX = textureWidth - width;
        offsetY = occulusTab.startY() - width / 2f;
        if (offsetY < 0) offsetY = 0;
        if (offsetY > textureHeight - height) offsetY = textureHeight - height;
    }

    @Override
    protected void renderBg(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        RenderSystem.setShaderTexture(0, occulusTab.background());
        float scaledOffsetX = offsetX * SCALE;
        float scaledOffsetY = offsetY * SCALE;
        float scaledWidth = width * (1 / SCALE);
        float scaledHeight = height * (1 / SCALE);
        float minU = Mth.clamp(scaledOffsetX, 0, textureWidth - scaledWidth) / textureWidth;
        float minV = Mth.clamp(scaledOffsetY, 0, textureHeight - scaledHeight) / textureHeight;
        float maxU = Mth.clamp(scaledOffsetX + scaledWidth, scaledWidth, textureWidth) / textureWidth;
        float maxV = Mth.clamp(scaledOffsetY + scaledHeight, scaledHeight, textureHeight) / textureHeight;
        RenderUtil.drawBox(pMatrixStack, 0, 0, width, height, getBlitOffset(), minU, minV, maxU, maxV);
        if (isDragging()) {
            offsetX = Mth.clamp(offsetX - (pMouseX - lastMouseX), 0, textureWidth - scaledWidth);
            offsetY = Mth.clamp(offsetY - (pMouseY - lastMouseY), 0, textureHeight - scaledHeight);
        }
        lastMouseX = pMouseX;
        lastMouseY = pMouseY;
    }

    @Override
    protected void renderFg(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        Player player = getMinecraft().player;
        if (player == null) return;
        var api = ArsMagicaAPI.get();
        var skillRegistry = api.getSkillRegistry();
        var helper = api.getSkillHelper();
        Set<Skill> skills = skillRegistry.getValues().stream().filter(skill -> occulusTab.getId().equals(skill.occulusTab())).collect(Collectors.toSet());
        skills.removeIf(skill -> skill.hidden() && !helper.knows(player, skill));
        stack.pushPose();
        stack.translate(-offsetX, -offsetY, 0);
        stack.scale(SCALE, SCALE, 0);
        mouseX += offsetX;
        mouseY += offsetY;
        mouseX *= SCALE;
        mouseY *= SCALE;
        boolean isHoveringSkill = false;
        int tick = (player.tickCount % 80) >= 40 ? (player.tickCount % 40) - 20 : -(player.tickCount % 40) + 20;
        float multiplier = 0.75F + tick / 80F;
        double guiScale = getMinecraft().getWindow().getGuiScale();
        RenderSystem.enableScissor((int) (posX * guiScale), (int) Math.floor(posY * guiScale), (int) (width * guiScale), (int) Math.floor(height * guiScale));
        for (Skill skill : skills) {
            boolean knows = helper.knows(player, skill);
            float cX = skill.x() + SKILL_SIZE / 2 + 1;
            float cY = skill.y() + SKILL_SIZE / 2 + 1;
            setBlitOffset(1);
            boolean hasPrereq = helper.canLearn(player, skill) || knows;
            for (ResourceLocation parentId : skill.parents()) {
                Optional<Skill> parent = Optional.ofNullable(skillRegistry.getValue(parentId));
                if (parent.isEmpty()) continue;
                Skill parentSkill = parent.get();
                float parentCX = parentSkill.x() + SKILL_SIZE / 2 + 1;
                float parentCY = parentSkill.y() + SKILL_SIZE / 2 + 1;
                int color = (knows ? ColorUtil.KNOWS_COLOR : getColorForLine(parentSkill, skill) & ColorUtil.UNKNOWN_SKILL_LINE_COLOR_MASK);
                if (!hasPrereq) {
                    color = ColorUtil.BLACK;
                }
                if (cX != parentCX) {
                    RenderUtil.lineThick2d(stack, parentCX, cY, cX, cY, getBlitOffset(), color);
                }
                if (cY != parentCY) {
                    RenderUtil.lineThick2d(stack, parentCX, parentCY, parentCX, cY, getBlitOffset(), color);
                }
            }
        }
        RenderSystem.setShaderTexture(0, SkillIconAtlas.SKILL_ICON_ATLAS);
        for (Skill skill : skills) {
            boolean knows = helper.knows(player, skill);
            boolean hasPrereq = helper.canLearn(player, skill) || knows;
            if (!hasPrereq) {
                RenderSystem.setShaderColor(0.5F, 0.5F, 0.5F, 1);
            } else if (!knows) {
                int c = getColorForSkill(skill);
                float red = Math.max(ColorUtil.getRed(c), 0.6F) * multiplier;
                float green = Math.max(ColorUtil.getGreen(c), 0.6F) * multiplier;
                float blue = Math.max(ColorUtil.getBlue(c), 0.6F) * multiplier;
                RenderSystem.setShaderColor(red, green, blue, 1);
            }
            setBlitOffset(16);
            RenderSystem.enableBlend();
            blit(stack, skill.x(), skill.y(), getBlitOffset(), (int) SKILL_SIZE, (int) SKILL_SIZE, SkillIconAtlas.instance().getSprite(skill.getId()));
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }
        RenderSystem.disableScissor();
        stack.popPose();
        if (!(mouseX > offsetX && mouseX < offsetX+width && mouseY > offsetY && mouseY < offsetY+height)) return;
        for (Skill skill : skills) {
            if (mouseX >= skill.x() && mouseX <= skill.x() + SKILL_SIZE && mouseY >= skill.y() && mouseY <= skill.y() + SKILL_SIZE) {
                List<Component> list = new ArrayList<>();
                list.add(skill.getDisplayName().copy().withStyle(style -> style.withColor(getColorForSkill(skill))));
                if (helper.canLearn(player, skill) || helper.knows(player, skill)) {
                    list.add(skill.getDescription().copy().withStyle(ChatFormatting.DARK_GRAY));
                } else {
                    list.add(MISSING_REQUIREMENTS);
                }
                stack.pushPose();
                stack.translate(0, -1, 0);
                parent.renderTooltip(stack, list, Optional.empty(), (int) (mouseX / SCALE - offsetX), (int) (mouseY / SCALE - offsetY), getFont());
                stack.popPose();
                hoverItem = skill;
                isHoveringSkill = true;
            }
        }
        if (!isHoveringSkill) {
            hoverItem = null;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0 && mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height) {
            var helper = ArsMagicaAPI.get().getSkillHelper();
            Player player = getMinecraft().player;
            if (player != null && hoverItem != null && !helper.knows(player, hoverItem)) {
                if (helper.canLearn(player, hoverItem) || player.isCreative()) {
                    ArsMagicaLegacy.NETWORK_HANDLER.sendToServer(new LearnSkillPacket(hoverItem.getId()));
                }
            } else {
                setDragging(true);
            }
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
