package com.github.minecraftschurli.arsmagicalegacy.client.gui;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.item.runebag.RuneBagContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class RuneBagScreen extends AbstractContainerScreen<RuneBagContainer> {
    private static final ResourceLocation GUI = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/rune_bag.png");

    public RuneBagScreen(RuneBagContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        inventoryLabelY = inventoryLabelY - 16;
    }

    @Override
    public void render(@NotNull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        renderTooltip(pMatrixStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        blit(pPoseStack, (width - imageWidth) / 2, (height - imageHeight) / 2, 0, 0, imageWidth, imageHeight);
    }
}
