package at.minecraftschurli.mods.arsmagicalegacy.client.gui;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.menu.SpellBookMenu;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class SpellBookScreen extends AbstractContainerScreen<SpellBookMenu> {
    private static final Identifier BACKGROUND = ArsMagicaApi.id("textures/gui/spell_book/background.png");

    public SpellBookScreen(SpellBookMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 256, 256);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);
        AMClientUtil.blitFull(graphics, BACKGROUND, (width - imageWidth) / 2, (height - imageHeight) / 2, imageWidth, imageHeight);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        for (int i = 0; i < 8; i++) {
            ItemStack item = menu.slots.get(i).getItem();
            if (item.isEmpty()) continue;
            Component name = item.getHoverName();
            graphics.text(font, name, 37, 9 + i * 18, 0xff000000 | Optional.ofNullable(name.getStyle().getColor()).orElse(TextColor.fromRgb(0x000000)).getValue(), false);
        }
    }
}
