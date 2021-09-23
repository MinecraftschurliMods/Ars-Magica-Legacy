package com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.IOcculusTab;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class OcculusSkillTreeTabRenderer extends OcculusTabRenderer {
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private int offsetX = ((568 / 2) - 82) + 8;
    private int offsetY = 0;
    private ISkill hoverItem = null;
    private final int textureWidth;
    private final int textureHeight;
    private final int renderSize;

    public OcculusSkillTreeTabRenderer(IOcculusTab occulusTab, Player player) {
        super(occulusTab, player);
        textureWidth = 196;
        textureHeight = 196;
        renderSize = 32;
    }

    @Override
    protected void renderBg(PoseStack pMatrixStack, int posX, int posY, int pMouseX, int pMouseY, float pPartialTicks) {
        RenderSystem.setShaderTexture(0, occulusTab.getBackground());
        float renderRatio = 0.29F;
        float calcYOffest = ((float) offsetY / 568) * (1 - renderRatio);
        float calcXOffest = ((float) offsetX / 568) * (1 - renderRatio);
        RenderUtil.drawBox(pMatrixStack, posX + 7, posY + 7, textureWidth, textureHeight, getBlitOffset(), calcXOffest, calcYOffest, renderRatio + calcXOffest, renderRatio + calcYOffest);
    }

    @Override
    protected void renderFg(PoseStack pMatrixStack, int posX, int posY, int pMouseX, int pMouseY, float pPartialTicks) {
        if (isDragging()) {
            int dx = lastMouseX - pMouseX;
            int dy = lastMouseY - pMouseY;
            offsetX += dx;
            offsetY += dy;
            if (offsetX < 0) offsetX = 0;
            if (offsetX > 568) offsetX = 568;
            if (offsetY < 0) offsetY = 0;
            if (offsetY > 568) offsetY = 568;
        }
        lastMouseX = pMouseX;
        lastMouseY = pMouseY;
        var skillManager = ArsMagicaAPI.get().getSkillManager();
        var knowledgeHelper = ArsMagicaAPI.get().getKnowledgeHelper();
        Set<ISkill> skills = skillManager.getSkillsForOcculusTab(occulusTab.getId());
        setBlitOffset(1);
        for (ISkill s : skills) {
            if (s.getCost().size() <= 0 || (s.isHidden() && !knowledgeHelper.knows(player, s))) continue;
            for (ResourceLocation resourceLocation : s.getParents()) {
                Optional<ISkill> iSkill = skillManager.getOptional(resourceLocation);
                if (iSkill.isEmpty()) continue;
                ISkill skill = iSkill.get();
                if (!skills.contains(skill) || skill.isHidden() && !knowledgeHelper.knows(player, skill)) continue;
                int offsetX = calcXOffset(posX, s) + 16;
                int offsetY = calcYOffset(posY, s) + 16;
                int offsetX2 = calcXOffset(posX, skill) + 16;
                int offsetY2 = calcYOffset(posY, skill) + 16;
                offsetX = Mth.clamp(offsetX, posX + 7, posX + 203);
                offsetY = Mth.clamp(offsetY, posY + 7, posY + 203);
                offsetX2 = Mth.clamp(offsetX2, posX + 7, posX + 203);
                offsetY2 = Mth.clamp(offsetY2, posY + 7, posY + 203);
                boolean hasPrereq = knowledgeHelper.canLearn(player, s) || knowledgeHelper.knows(player, s);
                int color = (!knowledgeHelper.knows(player, s) ? getColorForLine(skill, s) & 0x999999 : 0x00ff00);
                if (!hasPrereq) {
                    color = 0x000000;
                }
                if (!(offsetX == posX + 7 || offsetX == posX + 203)) {
                    RenderUtil.lineThick2d(pMatrixStack, offsetX, offsetY, offsetX, offsetY2, hasPrereq ? 0 : -1, color);
                }
                if (!(offsetY2 == posY + 7 || offsetY2 == posY + 203)) {
                    RenderUtil.lineThick2d(pMatrixStack, offsetX, offsetY2, offsetX2, offsetY2, hasPrereq ? 0 : -1, color);
                }
            }
        }
        for (ISkill s : skills) {
            if (s.getCost().size() <= 0 || (s.isHidden() && !knowledgeHelper.knows(player, s))) continue;
            RenderSystem.setShaderFogColor(1, 1, 1, 1);
            boolean hasPrereq = knowledgeHelper.canLearn(player, s) || knowledgeHelper.knows(player, s);
            int offsetX = calcXOffset(posX, s);
            int offsetY = calcYOffset(posY, s);
            int tick = (player.tickCount % 80) >= 40 ? (player.tickCount % 40) - 20 : -(player.tickCount % 40) + 20;
            float multiplier = 0.75F + tick / 80F;
            if (offsetX + renderSize < posX + 7 || offsetX > posX + 203 || offsetY + renderSize < posY + 7 || offsetY > posY + 203 /*|| sprite == null*/) continue;
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
            if (offsetX < posX + 7) {
                xStartMod = (float) (posX + 7 - offsetX);
            } else if (offsetX + renderSize > posX + 203) {
                xEndMod = renderSize - (posX + 203 - offsetX);
            }
            if (offsetY < posY + 7) {
                yStartMod = (float) (posY + 7 - offsetY);
            } else if (offsetY + renderSize > posY + 203) {
                yEndMod = renderSize - (posY + 203 - offsetY);
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
        if (pMouseX > posX && pMouseX < posX + xSize && pMouseY > posY && pMouseY < posY + ySize) {
            boolean flag = false;
            setBlitOffset(0);
            for (ISkill s : skills) {
                if (s.isHidden() && !knowledgeHelper.knows(player, s)) continue;
                int offsetX = calcXOffset(posX, s);
                int offsetY = calcYOffset(posY, s);
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
        if (mouseButton == 0) {
            var knowledgeHelper = ArsMagicaAPI.get().getKnowledgeHelper();
            if (hoverItem != null && !knowledgeHelper.knows(player, hoverItem)) {
                if (knowledgeHelper.canLearn(player, hoverItem) || player.isCreative()) {
                    ArsMagicaLegacy.NETWORK_HANDLER.sendToServer(new LearnSkillPacket(hoverItem.getId()));
                }
            } else {
                setDragging(true);
            }
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private int calcXOffset(int posX, ISkill s) {
        return posX - offsetX + s.getX();
    }

    private int calcYOffset(int posY, ISkill s) {
        return posY - offsetY + s.getY();
    }

    private ChatFormatting getChatColorForSkill(ISkill s) {
        return ChatFormatting.DARK_RED;
    }

    private int getColorForSkill(ISkill skill) {
        return 0;
    }

    private int getColorForLine(ISkill parent, ISkill child) {
        return 0;
    }
}
