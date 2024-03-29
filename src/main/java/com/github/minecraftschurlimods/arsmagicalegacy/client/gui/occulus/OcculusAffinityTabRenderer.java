package com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.OcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.RenderUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OcculusAffinityTabRenderer extends OcculusTabRenderer {
    private static final NumberFormat RANGE_FORMAT = NumberFormat.getPercentInstance();

    public OcculusAffinityTabRenderer(OcculusTab occulusTab, Screen parent) {
        super(occulusTab, parent);
    }

    @Override
    protected void renderBg(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        RenderSystem.setShaderTexture(0, occulusTab.background(getMinecraft().getConnection().registryAccess()));
        RenderUtil.drawBox(pMatrixStack, 0, 0, width, height, 0, 0, 0, 1, 1);
    }

    @Override
    protected void renderFg(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        var api = ArsMagicaAPI.get();
        var helper = api.getAffinityHelper();
        var registry = api.getAffinityRegistry();
        RegistryAccess registryAccess = getMinecraft().getConnection().registryAccess();
        var abilityRegistry = registryAccess.registryOrThrow(Ability.REGISTRY_KEY);
        int affNum = registry.getValues().size() - 1;
        int portion = 360 / affNum;
        int currentID = 0;
        int cX = width / 2;
        int cY = height / 2;
        List<Component> drawString = new ArrayList<>();
        List<Affinity> affinities = new ArrayList<>(registry.getValues());
        affinities.sort(null);
        Player player = getPlayer();
        assert player != null;
        for (Affinity aff : affinities) {
            if (Objects.equals(aff.getId(), Affinity.NONE)) continue;
            double depth = helper.getAffinityDepth(player, aff);
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
                RenderUtil.fractalLine2dd(pMatrixStack, affStartX1 + cX, affStartY1 + cY, affEndX + cX, affEndY + cY, 0, aff.color(), displace, 0.8F);
                RenderUtil.fractalLine2dd(pMatrixStack, affStartX2 + cX, affStartY2 + cY, affEndX + cX, affEndY + cY, 0, aff.color(), displace, 0.8F);
                RenderUtil.fractalLine2dd(pMatrixStack, affStartX1 + cX, affStartY1 + cY, affEndX + cX, affEndY + cY, 0, aff.color(), displace, 1.1F);
                RenderUtil.fractalLine2dd(pMatrixStack, affStartX2 + cX, affStartY2 + cY, affEndX + cX, affEndY + cY, 0, aff.color(), displace, 1.1F);
            } else {
                RenderUtil.line2d(pMatrixStack, (float) affStartX1 + cX, (float) affStartY1 + cY, (float) affEndX + cX, (float) affEndY + cY, 0, aff.color());
                RenderUtil.line2d(pMatrixStack, (float) affStartX2 + cX, (float) affStartY2 + cY, (float) affEndX + cX, (float) affEndY + cY, 0, aff.color());
            }
            drawString(pMatrixStack, getFont(), "%.2f".formatted(depth), (int) ((affDrawTextX * 0.9) + cX), (int) ((affDrawTextY * 0.9) + cY), aff.color());
            int xMovement = affDrawTextX > 0 ? 5 : -5;
            xMovement = affDrawTextX == 0 ? 0 : xMovement;
            int yMovement = affDrawTextY > 0 ? 5 : -5;
            yMovement = affDrawTextY == 0 ? 0 : yMovement;
            int drawX = (int) ((affDrawTextX * 1.1) + cX + xMovement);
            int drawY = (int) ((affDrawTextY * 1.1) + cY + yMovement);
            getItemRenderer().renderAndDecorateFakeItem(pMatrixStack, helper.getEssenceForAffinity(aff), drawX, drawY);
            if (pMouseX > drawX && pMouseX < drawX + 16 && pMouseY > drawY && pMouseY < drawY + 16) {
                drawString.add(aff.getDisplayName().copy().withStyle(style -> style.withColor(aff.color())));
                abilityRegistry.stream()
                               .filter(ability -> aff.getId().equals(ability.affinity().getId()))
                               .sorted((o1, o2) -> (int) ((Objects.requireNonNullElse(o1.bounds().getMin(), 0D) * 100) - (Objects.requireNonNullElse(o2.bounds().getMin(), 0D) * 100)))
                               .forEach(ability -> {
                    boolean test = ability.test(player);
                    MutableComponent component = ability.getDisplayName(registryAccess).copy().withStyle(test ? ChatFormatting.GREEN : ChatFormatting.DARK_RED);
                    if (Screen.hasShiftDown()) {
                        MinMaxBounds.Doubles range = ability.bounds();
                        Double lower = range.getMin();
                        Double upper = range.getMax();
                        if (lower != null || upper != null) {
                            MutableComponent cmp = Component.literal(" (");
                            if (lower != null) {
                                cmp.append(Component.translatable(TranslationConstants.RANGE_LOWER, RANGE_FORMAT.format(lower).replace("\u00A0", "")));
                                if (upper != null) {
                                    cmp.append(", ");
                                }
                            }
                            if (upper != null) {
                                cmp.append(Component.translatable(TranslationConstants.RANGE_UPPER, RANGE_FORMAT.format(upper).replace("\u00A0", "")));
                            }
                            cmp.append(")");
                            component.append(cmp);
                        }
                    }
                    drawString.add(component);
                });
            }
        }
        if (!drawString.isEmpty()) {
            if (!Screen.hasShiftDown()) {
                drawString.add(Component.translatable(TranslationConstants.HOLD_SHIFT_FOR_DETAILS).withStyle(ChatFormatting.GRAY));
            }
            pMatrixStack.pushPose();
            pMatrixStack.translate(-posX, -posY, 0);
            parent.renderTooltip(pMatrixStack, drawString, Optional.empty(), pMouseX + posX, pMouseY + posY, getFont());
            pMatrixStack.popPose();
        }
        RenderSystem.setShaderFogColor(1, 1, 1);
    }
}
