package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;

public class SpellRecipeScreen extends Screen {
    private ItemStack stack;

    public SpellRecipeScreen(ItemStack stack) {
        super(TextComponent.EMPTY);
        this.stack = stack;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BookViewScreen.BOOK_LOCATION);
        blit(pPoseStack, (width - 192) / 2, 2, 0, 0, 192, 192);
    }
}
