package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.menu.RuneBagMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class RuneBagScreen extends AbstractContainerScreen<RuneBagMenu> {
    private static final Identifier BACKGROUND = ArsMagicaApi.id("textures/gui/rune_bag.png");

    public RuneBagScreen(RuneBagMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        inventoryLabelY -= 16;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        AMClientUtil.blitFull(graphics, BACKGROUND, (width - imageWidth) / 2, (height - imageHeight) / 2, imageWidth, imageHeight);
    }
}
