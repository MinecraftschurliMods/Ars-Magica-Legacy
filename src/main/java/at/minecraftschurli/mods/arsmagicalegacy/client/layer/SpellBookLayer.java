package at.minecraftschurli.mods.arsmagicalegacy.client.layer;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.client.AMClientConfig;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.item.SpellBookItem;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class SpellBookLayer extends AMGuiLayer {
    private static final Identifier TEXTURE = ArsMagicaApi.id("textures/gui/spell_book/overlay.png");
    private static final Identifier HIGHLIGHT_TEXTURE = ArsMagicaApi.id("textures/gui/spell_book/highlight.png");

    public SpellBookLayer() {
        super(AMClientConfig.SPELL_BOOK_X_ANCHOR, AMClientConfig.SPELL_BOOK_Y_ANCHOR, AMClientConfig.SPELL_BOOK_X, AMClientConfig.SPELL_BOOK_Y);
    }

    @Override
    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, LocalPlayer player) {
        ItemStack item = player.getMainHandItem();
        if (!SpellBookItem.isSpellBook(item)) {
            item = player.getOffhandItem();
            if (!SpellBookItem.isSpellBook(item)) return;
        }
        int index = item.getOrDefault(AMDataComponents.SELECTED_INDEX, -1);
        if (index < 0 || index >= SpellBookItem.HOTBAR_SLOTS) return;
        ItemContainerContents container = item.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        graphics.pose().scale(0.75f, 0.75f);
        AMClientUtil.blit(graphics, TEXTURE, 0, 0, 148, 22);
        for (int i = 0; i < Math.min(container.getSlots(), SpellBookItem.HOTBAR_SLOTS); i++) {
            AMClientUtil.renderItem(graphics, container.getStackInSlot(i), i * 18 + 3, 3);
        }
        AMClientUtil.blit(graphics, HIGHLIGHT_TEXTURE, index * 18 + 1, 1, 20, 20);
    }
}
