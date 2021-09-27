package com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.*;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.ColorUtil;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.RenderUtil;
import com.github.minecraftschurli.arsmagicalegacy.network.LearnSkillPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class OcculusSkillTreeTabRenderer extends OcculusTabRenderer {
    public static final int KNOWS_COLOR = 0x00ff00;
    private final int textureHeight;
    private final int textureWidth;
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private float offsetX = 0;
    private float offsetY = 0;
    private float renderRatio;
    private ISkill hoverItem = null;

    public OcculusSkillTreeTabRenderer(IOcculusTab occulusTab, Player player) {
        super(occulusTab, player);
        renderRatio = 1f;
        textureWidth = occulusTab.getWidth();
        textureHeight = occulusTab.getHeigth();
    }

    @Override
    protected void init() {
        if (offsetX == 0) offsetX = textureWidth / 2f - width / 2f;
    }

    @Override
    protected void renderBg(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        RenderSystem.setShaderTexture(0, occulusTab.getBackground());
        float scaledOffsetX = offsetX * renderRatio;
        float scaledOffsetY = offsetY * renderRatio;
        float scaledWidth = width * (1 / renderRatio);
        float scaledHeight = height * (1 / renderRatio);
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
    protected void renderFg(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        ISkillManager skillManager = ArsMagicaAPI.get().getSkillManager();
        IKnowledgeHelper knowledgeHelper = ArsMagicaAPI.get().getKnowledgeHelper();
        Set<ISkill> skills = skillManager.getSkillsForOcculusTab(occulusTab.getId());
        skills.removeIf(skill -> skill.isHidden() && !knowledgeHelper.knows(player, skill));
        float skillSize = 32f;
        pMatrixStack.pushPose();
        pMatrixStack.scale(renderRatio, renderRatio, 0);
        pMatrixStack.translate(-offsetX, -offsetY, 0);
        pMouseX += offsetX;
        pMouseY += offsetY;
        pMouseX *= renderRatio;
        pMouseY *= renderRatio;
        boolean isHoveringSkill = false;
        int tick = (player.tickCount % 80) >= 40 ? (player.tickCount % 40) - 20 : -(player.tickCount % 40) + 20;
        float multiplier = 0.75F + tick / 80F;
        double guiScale = getMinecraft().getWindow().getGuiScale();
        RenderSystem.enableScissor((int) (posX * guiScale), (int) Math.floor(posY * guiScale), (int) (width * guiScale), (int) (height * guiScale));
        for (ISkill skill : skills) {
            boolean knows = knowledgeHelper.knows(player, skill);
            float cX = skill.getX() + skillSize / 2 + 1;
            float cY = skill.getY() + skillSize / 2 + 1;
            setBlitOffset(1);
            boolean hasPrereq = knowledgeHelper.canLearn(player, skill) || knows;
            for (ResourceLocation parentId : skill.getParents()) {
                Optional<ISkill> parent = skillManager.getOptional(parentId);
                if (parent.isEmpty()) continue;
                ISkill parentSkill = parent.get();
                float parentCX = parentSkill.getX() + skillSize / 2 + 1;
                float parentCY = parentSkill.getY() + skillSize / 2 + 1;
                int color = (knows ? KNOWS_COLOR : getColorForLine(parentSkill, skill) & 0x999999);
                if (!hasPrereq) {
                    color = 0x000000;
                }
                if (cX != parentCX) {
                    RenderUtil.lineThick2d(pMatrixStack, parentCX, cY, cX, cY, getBlitOffset(), color);
                }
                if (cY != parentCY) {
                    RenderUtil.lineThick2d(pMatrixStack, parentCX, parentCY, parentCX, cY, getBlitOffset(), color);
                }
            }
        }
        for (ISkill skill : skills) {
            boolean knows = knowledgeHelper.knows(player, skill);
            boolean hasPrereq = knowledgeHelper.canLearn(player, skill) || knows;
            if (!hasPrereq) {
                RenderSystem.setShaderFogColor(0.5F, 0.5F, 0.5F);
            } else if (!knows) {
                int c = getColorForSkill(skill);
                RenderSystem.setShaderFogColor(Math.max(ColorUtil.getRed(c), 0.6F) * multiplier, Math.max(ColorUtil.getGreen(c), 0.6F) * multiplier, Math.max(ColorUtil.getBlue(c), 0.6F) * multiplier);
            }
            setBlitOffset(16);
            RenderSystem.setShaderTexture(0, skill.getIcon());
            RenderSystem.enableBlend();
            RenderUtil.drawBox(pMatrixStack, skill.getX(), skill.getY(), skillSize, skillSize, getBlitOffset(), 0, 0, 1, 1);
            RenderSystem.disableBlend();
            RenderSystem.setShaderFogColor(1, 1, 1);
        }
        RenderSystem.disableScissor();
        for (ISkill skill : skills) {
            boolean knows = knowledgeHelper.knows(player, skill);
            boolean hasPrereq = knowledgeHelper.canLearn(player, skill) || knows;
            if (pMouseX >= skill.getX() && pMouseX <= skill.getX()+skillSize && pMouseY >= skill.getY() && pMouseY <= skill.getY()+skillSize) {
                List<Component> list = new ArrayList<>();
                list.add(skill.getDisplayName().copy().withStyle(getChatColorForSkill(skill)));
                if (hasPrereq) {
                    list.add(skill.getDescription().copy().withStyle(ChatFormatting.DARK_GRAY));
                } else {
                    list.add(new TranslatableComponent("message.%s.occulus.missingRequirements".formatted(ArsMagicaAPI.MOD_ID)).withStyle(ChatFormatting.DARK_RED));
                }
                renderComponentTooltip(pMatrixStack, list, pMouseX, pMouseY);
                hoverItem = skill;
                isHoveringSkill = true;
            }
        }
        if (!isHoveringSkill) {
            hoverItem = null;
        }
        pMatrixStack.popPose();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0 && mouseX > 7 && mouseX < width && mouseY > 7 && mouseX < height) {
            var knowledgeHelper = ArsMagicaAPI.get().getKnowledgeHelper();
            if (hoverItem != null && !knowledgeHelper.knows(player, hoverItem)) {
                if (knowledgeHelper.canLearn(player, hoverItem) || player.isCreative()) {
                    ArsMagicaLegacy.NETWORK_HANDLER.sendToServer(new LearnSkillPacket(hoverItem.getId()));
                }
            } else {
                setDragging(true);
            }
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        this.renderRatio = (float) Mth.clamp(this.renderRatio+pDelta/100, 0.19, 1);
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    private ChatFormatting getChatColorForSkill(ISkill skill) {
        return skill.getCost()
                .keySet()
                .stream()
                .map(ArsMagicaAPI.get().getSkillPointRegistry()::getValue)
                .filter(Objects::nonNull)
                .findFirst()
                .map(ISkillPoint::getChatColor)
                .orElse(ChatFormatting.RESET);
    }

    private int getColorForSkill(ISkill skill) {
        return skill.getCost()
                .keySet()
                .stream()
                .map(ArsMagicaAPI.get().getSkillPointRegistry()::getValue)
                .filter(Objects::nonNull)
                .findFirst()
                .map(ISkillPoint::getColor)
                .orElse(0);
    }

    private int getColorForLine(ISkill parent, ISkill child) {
        return getColorForSkill(child);
    }
}
