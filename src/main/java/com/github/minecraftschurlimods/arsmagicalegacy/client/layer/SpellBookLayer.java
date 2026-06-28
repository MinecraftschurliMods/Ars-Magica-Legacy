package com.github.minecraftschurlimods.arsmagicalegacy.client.layer;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.client.AMClientConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellBookItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.client.gui.GuiLayer;
import org.joml.Matrix3x2fStack;

public class SpellBookLayer implements GuiLayer {
    private static final Identifier TEXTURE = ArsMagicaApi.id("textures/gui/spell_book/overlay.png");
    private static final Identifier HIGHLIGHT_TEXTURE = ArsMagicaApi.id("textures/gui/spell_book/highlight.png");

    @Override
    public void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (AMClientUtil.mc().options.hideGui) return;
        Player player = AMClientUtil.player();
        if (player == null || player.isSpectator()) return;
        ItemStack item = player.getMainHandItem();
        if (!item.is(AMItems.SPELL_BOOK)) {
            item = player.getOffhandItem();
            if (!item.is(AMItems.SPELL_BOOK)) return;
        }
        int index = item.getOrDefault(AMDataComponents.SELECTED_INDEX, -1);
        if (index < 0 || index >= SpellBookItem.HOTBAR_SLOTS) return;
        ItemContainerContents container = item.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        int x = AMClientConfig.SPELL_BOOK_X_ANCHOR.get().getLocation(AMClientConfig.SPELL_BOOK_X);
        int y = AMClientConfig.SPELL_BOOK_Y_ANCHOR.get().getLocation(AMClientConfig.SPELL_BOOK_Y);
        Matrix3x2fStack stack = graphics.pose();
        stack.pushMatrix();
        stack.translate(x, y);
        stack.scale(0.75f, 0.75f);
        AMClientUtil.blit(graphics, TEXTURE, 0, 0, 148, 22);
        for (int i = 0; i < Math.min(container.getSlots(), SpellBookItem.HOTBAR_SLOTS); i++) {
            AMClientUtil.renderItem(graphics, container.getStackInSlot(i), i * 18 + 3, 3);
        }
        AMClientUtil.blit(graphics, HIGHLIGHT_TEXTURE, index * 18 + 1, 1, 20, 20);
        stack.popMatrix();
    }
}
