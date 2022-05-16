package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class SpellBookScreen extends AbstractContainerScreen<SpellBookMenu> {
    private static final ResourceLocation GUI = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/spell_book.png");

    public SpellBookScreen(SpellBookMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 256;
        imageHeight = 256;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        renderBackground(poseStack);
        RenderSystem.setShaderTexture(0, GUI);
        blit(poseStack, (width - imageWidth) >> 1, (height - imageHeight) >> 1, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(final PoseStack pPoseStack, final int pMouseX, final int pMouseY, final float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        for (int i = 0; i < 8; i++) {
            Slot slot = menu.slots.get(i);
            ItemStack item = slot.getItem();
            if (item.isEmpty()) continue;
            Component name = item.getHoverName();
            font.draw(pPoseStack, name, 37, 5 + i * 18, Optional.ofNullable(name.getStyle().getColor()).orElse(TextColor.fromRgb(0x000000)).getValue());
        }
    }
}
