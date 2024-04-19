package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.runebag.RuneBagMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * Mostly taken from McJty's tutorials and the Botania mod.
 * {@see https://github.com/McJty/YouTubeModding14/blob/1.17/src/main/java/com/mcjty/mytutorial/blocks/FirstBlockScreen.java}
 * {@see https://github.com/VazkiiMods/Botania/blob/master/src/main/java/vazkii/botania/client/gui/bag/GuiFlowerBag.java}
 */
public class RuneBagScreen extends AbstractContainerScreen<RuneBagMenu> {
    private static final ResourceLocation GUI = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/rune_bag.png");

    public RuneBagScreen(RuneBagMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        inventoryLabelY = inventoryLabelY - 16;
    }

    @Override
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTicks) {
        renderTransparentBackground(graphics);
        super.render(graphics, pMouseX, pMouseY, pPartialTicks);
        renderTooltip(graphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float pPartialTicks, int pMouseX, int pMouseY) {
        graphics.blit(GUI, (width - imageWidth) / 2, (height - imageHeight) / 2, 0, 0, imageWidth, imageHeight);
    }
}
