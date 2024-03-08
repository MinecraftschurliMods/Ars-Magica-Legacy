package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.github.minecraftschurlimods.betterhudlib.HUDElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;

public final class SpellBookHUD extends HUDElement {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/hud/spell_book.png");

    public SpellBookHUD() {
        super(Config.CLIENT.SPELL_BOOK_ANCHOR_X, Config.CLIENT.SPELL_BOOK_ANCHOR_Y, Config.CLIENT.SPELL_BOOK_X::get, Config.CLIENT.SPELL_BOOK_Y::get, () -> 148, () -> 22);
    }

    @Override
    public void draw(ExtendedGui forgeGui, GuiGraphics graphics, float partialTicks) {
        Player player = ClientHelper.getLocalPlayer();
        if (player == null || Minecraft.getInstance().options.hideGui) return;
        ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);
        if (mainHand.getItem() instanceof SpellBookItem) {
            renderSpellBookHUD(forgeGui, graphics, mainHand);
        } else if (offHand.getItem() instanceof SpellBookItem) {
            renderSpellBookHUD(forgeGui, graphics, offHand);
        }
    }

    @Override
    protected void onPositionUpdate(AnchorX anchorX, AnchorY anchorY, int x, int y) {
        Config.CLIENT.SPELL_BOOK_ANCHOR_X.set(anchorX);
        Config.CLIENT.SPELL_BOOK_ANCHOR_Y.set(anchorY);
        Config.CLIENT.SPELL_BOOK_X.set(x);
        Config.CLIENT.SPELL_BOOK_Y.set(y);
    }

    @Override
    protected void save() {
        Config.CLIENT.save();
    }

    private void renderSpellBookHUD(ExtendedGui gui, GuiGraphics graphics, ItemStack spellBook) {
        SimpleContainer active = SpellBookItem.getContainer(spellBook).active();
        final int selected = SpellBookItem.getSelectedSlot(spellBook);
        if (selected != -1) {
            graphics.pose().pushPose();
            graphics.pose().scale(0.75f, 0.75f, 0.75f);
            graphics.pose().pushPose();
            graphics.blit(TEXTURE, 0, 0, 0, 0, 0, 148, 22, 256, 256);
            graphics.pose().popPose();
            for (int i = 0; i < active.getContainerSize(); i++) {
                ItemStack spell = active.getItem(i);
                graphics.pose().pushPose();
                graphics.pose().translate(i * 18f, 2f, 0);
                graphics.renderItem(spell, 3, 1);
                graphics.renderItemDecorations(gui.getFont(), spell, 3, 1);
                graphics.pose().popPose();
            }
            graphics.pose().pushPose();
            graphics.blit(TEXTURE, selected * 18 + 1, 1, 0, 148, 0, 20, 20, 256, 256);
            graphics.pose().popPose();
            graphics.pose().popPose();
        }
    }
}
