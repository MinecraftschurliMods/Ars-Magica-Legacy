package com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.IOcculusTab;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.RenderUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OcculusAffinityTabRenderer extends OcculusTabRenderer {
    public OcculusAffinityTabRenderer(final IOcculusTab occulusTab, final Player player) {
        super(occulusTab, player);
    }

    @Override
    protected void renderBg(PoseStack pMatrixStack, int posX, int posY, int pMouseX, int pMouseY, float pPartialTicks) {
        RenderSystem.setShaderTexture(0, occulusTab.getBackground());
        RenderUtil.drawBox(pMatrixStack, posX + 7, posY + 7, 196, 196, getBlitOffset(), 0, 0, 1, 1);
    }

    @Override
    protected void renderFg(PoseStack pMatrixStack, int posX, int posY, int pMouseX, int pMouseY, float pPartialTicks) {
        boolean isShiftDown = player.isShiftKeyDown();
        var affinityRegistry = ArsMagicaAPI.get().getAffinityRegistry();
        int affNum = affinityRegistry.getValues().size() - 1;
        int portion = 360 / affNum;
        int currentID = 0;
        int cX = posX + xSize / 2;
        int cY = posY + ySize / 2;
        List<Component> drawString = new ArrayList<>();
        for (IAffinity aff : affinityRegistry.getValues()) {
            //if (Objects.equals(aff.getRegistryName(), Affinity.NONE)) continue;
            double depth = ArsMagicaAPI.get().getAffinityHelper().getAffinityDepth(player, aff) / 100;
            double var1 = Math.cos(Math.toRadians(portion * currentID));
            double var2 = Math.sin(Math.toRadians(portion * currentID));
            double var3 = Math.toRadians(portion * currentID - portion / 2.);
            double var4 = Math.toRadians(portion * currentID + portion / 2.);
            double affEndX = var1 * 10F + var1 * depth * 60F;
            double affEndY = var2 * 10F + var2 * depth * 60F;
            double affStartX1 = Math.cos(var3) * 10F;
            double affStartY1 = Math.sin(var3) * 10F;
            double affStartX2 = Math.cos(var4) * 10F;
            double affStartY2 = Math.sin(var4) * 10F;
            double affDrawTextX = var1 * 80F - 7;
            double affDrawTextY = var2 * 80F - 7;
            currentID++;
            int displace = (int) ((Math.max(affStartX1, affStartX2) - Math.min(affStartX1, affStartX2) + Math.max(affStartY1, affStartY2) - Math.min(affStartY1, affStartY2)) / 2);
            if (depth > 0.01F) {
                RenderUtil.fractalLine2dd(pMatrixStack, affStartX1 + cX, affStartY1 + cY, affEndX + cX, affEndY + cY, getBlitOffset(), aff.getColor(), displace, 0.8F);
                RenderUtil.fractalLine2dd(pMatrixStack, affStartX2 + cX, affStartY2 + cY, affEndX + cX, affEndY + cY, getBlitOffset(), aff.getColor(), displace, 0.8F);
                RenderUtil.fractalLine2dd(pMatrixStack, affStartX1 + cX, affStartY1 + cY, affEndX + cX, affEndY + cY, getBlitOffset(), aff.getColor(), displace, 1.1F);
                RenderUtil.fractalLine2dd(pMatrixStack, affStartX2 + cX, affStartY2 + cY, affEndX + cX, affEndY + cY, getBlitOffset(), aff.getColor(), displace, 1.1F);
            } else {
                RenderUtil.line2d(pMatrixStack, (float) affStartX1 + cX, (float) affStartY1 + cY, (float) affEndX + cX, (float) affEndY + cY, getBlitOffset(), aff.getColor());
                RenderUtil.line2d(pMatrixStack, (float) affStartX2 + cX, (float) affStartY2 + cY, (float) affEndX + cX, (float) affEndY + cY, getBlitOffset(), aff.getColor());
            }
            drawString(pMatrixStack, font, "" + (float) Math.round(depth * 10000) / 100F, (int) ((affDrawTextX * 0.9) + cX), (int) ((affDrawTextY * 0.9) + cY), aff.getColor());
            int xMovement = affDrawTextX > 0 ? 5 : -5;
            xMovement = affDrawTextX == 0 ? 0 : xMovement;
            int yMovement = affDrawTextY > 0 ? 5 : -5;
            yMovement = affDrawTextY == 0 ? 0 : yMovement;
            int drawX = (int) ((affDrawTextX * 1.1) + cX + xMovement);
            int drawY = (int) ((affDrawTextY * 1.1) + cY + yMovement);
            itemRenderer.renderAndDecorateFakeItem(ArsMagicaAPI.get().getAffinityHelper().getItemStackForAffinity(aff), drawX, drawY);
            if (pMouseX > drawX && pMouseX < drawX + 16 && pMouseY > drawY && pMouseY < drawY + 16) {
                drawString.add(aff.getDisplayName());
//                    List<AbstractAffinityAbility> abilites = Lists.newArrayList(ArsMagicaAPI.getAffinityAbilityRegistry().getValues());
//                    abilites.sort(new Comparator<AbstractAffinityAbility>() {
//                        @Override
//                        public int compare(AbstractAffinityAbility o1, AbstractAffinityAbility o2) {
//                            return (int) ((o1.getMinimumDepth() * 100) - (o2.getMinimumDepth() * 100));
//                        }
//                    });
//                    for (AbstractAffinityAbility ability : abilites) {
//                        if (ability.getAffinity() == aff) {
//                            String advancedTooltip = "";
//                            if (isShiftDown) advancedTooltip = " (Min. : " + Math.round(ability.getMinimumDepth() * 100) + "%" + (ability.hasMax() ?(", Max. : " + Math.round(ability.getMaximumDepth() * 100) + "%")  : "") + ")";
//                            drawString.add(TextFormatting.RESET.toString() + (ability.isEligible(player) ? TextFormatting.GREEN.toString() : TextFormatting.DARK_RED.toString()) + I18n.translateToLocal("affinityability." + ability.getRegistryName().toString().replaceAll("arsmagica2:", "") + ".name") + advancedTooltip);
//                        }
//                    }
            }
        }
        if (!drawString.isEmpty()) {
            if (!isShiftDown)
                drawString.add(new TranslatableComponent("tooltip.%s.shiftForDetails".formatted(ArsMagicaAPI.MOD_ID)).withStyle(ChatFormatting.GRAY));
            renderTooltip(pMatrixStack, drawString, Optional.empty(), pMouseX, pMouseY);
        }
        RenderSystem.setShaderFogColor(1, 1, 1);
        //RenderHelper.disableStandardItemLighting();
    }
}
