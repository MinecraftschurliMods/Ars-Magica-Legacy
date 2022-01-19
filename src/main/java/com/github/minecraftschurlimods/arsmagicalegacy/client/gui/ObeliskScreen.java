package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ObeliskScreen extends AbstractContainerScreen<ObeliskMenu> {
    private static final ResourceLocation LOCATION = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/obelisk_gui.png");

    public ObeliskScreen(ObeliskMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, LOCATION);
        blit(poseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        if (this.menu.isLit()) {
            int k = this.menu.getLitProgress();
            this.blit(poseStack, leftPos + 80, topPos + 31 + 12 - k, 176, 12 - k, 14, k + 1);
        }
    }
}
