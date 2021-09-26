package com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.IOcculusTab;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkillPoint;
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
    public static final int TEXTURE_WIDTH = 1024;
    public static final int TEXTURE_HEIGHT = 1024;
    private final int renderSize = 32;
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private float offsetX = 0;
    private float offsetY = 0;
    private ISkill hoverItem = null;
    private float renderRatio;

    public OcculusSkillTreeTabRenderer(IOcculusTab occulusTab, Player player) {
        super(occulusTab, player);
        renderRatio = 1f;
    }

    @Override
    protected void init() {
        offsetX = TEXTURE_WIDTH/2f - width/2f;
    }

    @Override
    protected void renderBg(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        RenderSystem.setShaderTexture(0, occulusTab.getBackground());
        float scaledOffsetX = offsetX * renderRatio;
        float scaledOffsetY = offsetY * renderRatio;
        float scaledWidth = width * (1 / renderRatio);
        float scaledHeight = height * (1 / renderRatio);
        float minU = Mth.clamp(scaledOffsetX, 0, TEXTURE_WIDTH - scaledWidth) / TEXTURE_WIDTH;
        float minV = Mth.clamp(scaledOffsetY, 0, TEXTURE_HEIGHT - scaledHeight) / TEXTURE_HEIGHT;
        float maxU = Mth.clamp(scaledOffsetX + scaledWidth, scaledWidth, TEXTURE_WIDTH) / TEXTURE_WIDTH;
        float maxV = Mth.clamp(scaledOffsetY + scaledHeight, scaledHeight, TEXTURE_HEIGHT) / TEXTURE_HEIGHT;
        RenderUtil.drawBox(pMatrixStack, 0, 0, width, height, getBlitOffset(), minU, minV, maxU, maxV);

        if (isDragging()) {
            offsetX = Mth.clamp(offsetX - (pMouseX - lastMouseX), 0, TEXTURE_WIDTH - width);
            offsetY = Mth.clamp(offsetY - (pMouseY - lastMouseY), 0, TEXTURE_HEIGHT - height);
        }
        lastMouseX = pMouseX;
        lastMouseY = pMouseY;
    }

    @Override
    protected void renderFg(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        // TODO rewrite with scaling
        var skillManager = ArsMagicaAPI.get().getSkillManager();
        var knowledgeHelper = ArsMagicaAPI.get().getKnowledgeHelper();
        Set<ISkill> skills = skillManager.getSkillsForOcculusTab(occulusTab.getId());
        setBlitOffset(18);
        for (ISkill s : skills) {
            if (s.getCost().size() <= 0 || (s.isHidden() && !knowledgeHelper.knows(player, s))) continue;
            for (ResourceLocation resourceLocation : s.getParents()) {
                Optional<ISkill> iSkill = skillManager.getOptional(resourceLocation);
                if (iSkill.isEmpty()) continue;
                ISkill skill = iSkill.get();
                if (!skills.contains(skill) || skill.isHidden() && !knowledgeHelper.knows(player, skill)) continue;
                float offsetX = calcXOffset(s) + ((float)renderSize / 2) * renderRatio;
                float offsetY = calcYOffset(s) + ((float)renderSize / 2) * renderRatio;
                float offsetX2 = calcXOffset(skill) + ((float)renderSize / 2) * renderRatio;
                float offsetY2 = calcYOffset(skill) + ((float)renderSize / 2) * renderRatio;
                offsetX = Mth.clamp(offsetX, 0, width);
                offsetY = Mth.clamp(offsetY, 0, height);
                offsetX2 = Mth.clamp(offsetX2, 0, width);
                offsetY2 = Mth.clamp(offsetY2, 0, height);
                boolean hasPrereq = knowledgeHelper.canLearn(player, s) || knowledgeHelper.knows(player, s);
                int color = (!knowledgeHelper.knows(player, s) ? getColorForLine(skill, s) & 0x999999 : 0x00ff00);
                if (!hasPrereq) {
                    color = 0x000000;
                }
                if (!(offsetX == 0 || offsetX == width)) {
                    RenderUtil.lineThick2d(pMatrixStack, offsetX, offsetY, offsetX, offsetY2, getBlitOffset(), color);
                }
                if (!(offsetY2 == 0 || offsetY2 == height)) {
                    RenderUtil.lineThick2d(pMatrixStack, offsetX, offsetY2, offsetX2, offsetY2, getBlitOffset(), color);
                }
            }
        }
        for (ISkill s : skills) {
            if (s.getCost().size() <= 0 || (s.isHidden() && !knowledgeHelper.knows(player, s))) continue;
            RenderSystem.setShaderFogColor(1, 1, 1, 1);
            boolean hasPrereq = knowledgeHelper.canLearn(player, s) || knowledgeHelper.knows(player, s);
            float offsetX = calcXOffset(s);
            float offsetY = calcYOffset(s);
            int tick = (player.tickCount % 80) >= 40 ? (player.tickCount % 40) - 20 : -(player.tickCount % 40) + 20;
            float multiplier = 0.75F + tick / 80F;
            if (offsetX + renderSize < 0 || offsetX > width || offsetY + renderSize < 0 || offsetY > height) continue;
            RenderSystem.setShaderTexture(0, s.getIcon());
            float minU = 34.0f;
            float minV = 5.0f;
            float maxU = 35.0f;
            float maxV = 6.0f;
            float spriteXSize = 1f;
            float spriteYSize = 1f;
            float xStartMod = 0;
            float yStartMod = 0;
            float xEndMod = 0;
            float yEndMod = 0;
            if (offsetX < 0) {
                xStartMod = -offsetX;
            } else if (offsetX + renderSize > width) {
                xEndMod = renderSize - (width - offsetX);
            }
            if (offsetY < 0) {
                yStartMod = -offsetY;
            } else if (offsetY + renderSize > height) {
                yEndMod = renderSize - (height - offsetY);
            }
            if (!hasPrereq) {
                RenderSystem.setShaderFogColor(0.5F, 0.5F, 0.5F);
            } else if (!knowledgeHelper.knows(player, s)) {
                int c = getColorForSkill(s);
                RenderSystem.setShaderFogColor(Math.max(ColorUtil.getRed(c), 0.6F) * multiplier, Math.max(ColorUtil.getGreen(c), 0.6F) * multiplier, Math.max(ColorUtil.getBlue(c), 0.6F) * multiplier);
            }
            RenderSystem.enableBlend();
            RenderUtil.drawBox(pMatrixStack, offsetX + xStartMod, offsetY + yStartMod, renderSize - xStartMod - xEndMod, renderSize - yStartMod - yEndMod, 0, minU + (xStartMod / renderSize * spriteXSize), minV + (yStartMod / renderSize * spriteYSize), maxU - (xEndMod / renderSize * spriteXSize), maxV - (yEndMod / renderSize * spriteYSize));
            RenderSystem.disableBlend();
            RenderSystem.setShaderFogColor(1, 1, 1, 1);
        }
        if (pMouseX > -7 && pMouseX < width && pMouseY > -7 && pMouseY < height) {
            boolean flag = false;
            setBlitOffset(0);
            for (ISkill s : skills) {
                if (s.isHidden() && !knowledgeHelper.knows(player, s)) continue;
                float offsetX = calcXOffset(s);
                float offsetY = calcYOffset(s);
                if (offsetX > pMouseX || offsetX < pMouseX - renderSize || offsetY > pMouseY || offsetY < pMouseY - renderSize) continue;
                boolean hasPrereq = true;
                for (ResourceLocation subParent : s.getParents()) {
                    hasPrereq &= knowledgeHelper.knows(player, subParent);
                }
                List<Component> list = new ArrayList<>();
                list.add(s.getDisplayName().copy().withStyle(getChatColorForSkill(s)));
                if (hasPrereq) {
                    list.add(s.getDescription().copy().withStyle(ChatFormatting.DARK_GRAY));
                } else {
                    list.add(new TranslatableComponent("message.%s.occulus.missingRequirements".formatted(ArsMagicaAPI.MOD_ID)).withStyle(ChatFormatting.DARK_RED));
                }
                renderComponentTooltip(pMatrixStack, list, pMouseX, pMouseY);
                flag = true;
                hoverItem = s;
                //RenderHelper.disableStandardItemLighting();
                RenderSystem.setShaderFogColor(1, 1, 1);
            }
            if (!flag) {
                hoverItem = null;
            }
        }
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
        //this.renderRatio = (float) Mth.clamp(this.renderRatio+pDelta/100, 0.19, 1);
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    private float calcXOffset(ISkill s) {
        return s.getX() - offsetX;
    }

    private float calcYOffset(ISkill s) {
        return s.getY() - offsetY;
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
