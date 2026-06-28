package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.menu.RiftMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class RiftScreen extends AbstractContainerScreen<RiftMenu> {
    private static final Identifier BACKGROUND = ArsMagicaApi.id("textures/gui/rift/background.png");
    private static final Identifier SLOT = ArsMagicaApi.id("textures/gui/rift/slot.png");
    private final int rows;

    public RiftScreen(RiftMenu menu, Inventory playerInventory, Component title) {
        int rows = Math.ceilDiv(menu.getSlotCount(), 9);
        this.rows = rows;
        super(menu, playerInventory, title, 176, 114 + rows * 18);
        inventoryLabelY = imageHeight - 94;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        AMClientUtil.blit(graphics, BACKGROUND, x, y, 0, 0, imageWidth, 17, imageWidth, imageHeight);
        int size = menu.getSlotCount();
        for (int i = 0; i < rows; i++) {
            AMClientUtil.blit(graphics, BACKGROUND, x, y + 17 + i * 18, 0, 17, imageWidth, 18, imageWidth, imageHeight);
            for (int j = 0; j < 9; j++) {
                if (i * 9 + j < size) {
                    AMClientUtil.blit(graphics, SLOT, x + 7 + j * 18, y + 17 + i * 18, 18, 18);
                }
            }
        }
        AMClientUtil.blit(graphics, BACKGROUND, x, y + 17 + rows * 18, 0, 35, imageWidth, imageHeight - 35 - (rows - 1) * 18, imageWidth, imageHeight - (rows - 1) * 18);
    }
}
