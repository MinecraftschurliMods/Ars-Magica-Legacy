package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.github.minecraftschurlimods.betterhudlib.HUDElement;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public final class SpellBookHUD extends HUDElement {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/hud/spell_book.png");

    public SpellBookHUD() {
        super(Config.CLIENT.SPELL_BOOK_ANCHOR_X, Config.CLIENT.SPELL_BOOK_ANCHOR_Y, Config.CLIENT.SPELL_BOOK_X::get, Config.CLIENT.SPELL_BOOK_Y::get, () -> 148, () -> 22);
    }

    @Override
    public void draw(ForgeGui forgeGui, PoseStack poseStack, float partialTicks) {
        Player player = ClientHelper.getLocalPlayer();
        if (player == null || Minecraft.getInstance().options.hideGui) return;
        ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);
        if (mainHand.getItem() instanceof SpellBookItem) {
            renderSpellBookHUD(forgeGui, poseStack, mainHand);
        } else if (offHand.getItem() instanceof SpellBookItem) {
            renderSpellBookHUD(forgeGui, poseStack, offHand);
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

    private void renderSpellBookHUD(ForgeGui gui, PoseStack mStack, ItemStack spellBook) {
        SimpleContainer active = SpellBookItem.getContainer(spellBook).active();
        final int selected = SpellBookItem.getSelectedSlot(spellBook);
        if (selected != -1) {
            mStack.pushPose();
            mStack.scale(0.75f, 0.75f, 0.75f);
            mStack.pushPose();
            RenderSystem.setShaderTexture(0, TEXTURE);
            blit(mStack, 0, 0, 0, 0, 0, 148, 22, 256, 256);
            mStack.popPose();
            for (int i = 0; i < active.getContainerSize(); i++) {
                ItemStack spell = active.getItem(i);
                mStack.pushPose();
                mStack.translate(i * 18f, 2f, 0);
                Minecraft.getInstance().getItemRenderer().renderGuiItem(mStack, spell, 3, 1);
                mStack.popPose();
            }
            mStack.pushPose();
            RenderSystem.setShaderTexture(0, TEXTURE);
            blit(mStack, selected * 18 + 1, 1, 0, 148, 0, 20, 20, 256, 256);
            mStack.popPose();
            mStack.popPose();
        }
    }
}
