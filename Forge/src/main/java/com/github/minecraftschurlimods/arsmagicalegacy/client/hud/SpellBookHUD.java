package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public final class SpellBookHUD extends GuiComponent implements IIngameOverlay {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/hud/spell_book.png");

    @Override
    public void render(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height) {
        Player player = ClientHelper.getLocalPlayer();
        if (player == null || Minecraft.getInstance().options.hideGui) return;
        ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);
        if (mainHand.getItem() instanceof SpellBookItem) {
            renderSpellBookHUD(gui, mStack, partialTicks, width, height, mainHand);
        } else if (offHand.getItem() instanceof SpellBookItem) {
            renderSpellBookHUD(gui, mStack, partialTicks, width, height, offHand);
        }
    }

    private void renderSpellBookHUD(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height, ItemStack spellBook) {
        SimpleContainer active = SpellBookItem.getContainer(spellBook).active();
        final int selected = SpellBookItem.getSelectedSlot(spellBook);
        if (selected != -1) {
            mStack.pushPose();
            mStack.translate(width / 2f + 100, height - 19, 100);
            mStack.scale(0.75f, 0.75f, 0.75f);
            mStack.pushPose();
            RenderSystem.setShaderTexture(0, TEXTURE);
            blit(mStack, 0, 0, gui.getBlitOffset(), 0, 0, 148, 22, 256, 256);
            mStack.popPose();
            for (int i = 0; i < active.getContainerSize(); i++) {
                ItemStack spell = active.getItem(i);
                mStack.pushPose();
                mStack.translate(i * 18f, 2f, 0);
                PoseStack modelViewStack = RenderSystem.getModelViewStack();
                modelViewStack.pushPose();
                modelViewStack.mulPoseMatrix(mStack.last().pose());
                RenderSystem.applyModelViewMatrix();
                Minecraft.getInstance().getItemRenderer().renderGuiItem(spell, 3, 1);
                modelViewStack.popPose();
                RenderSystem.applyModelViewMatrix();
                mStack.popPose();
            }
            mStack.pushPose();
            RenderSystem.setShaderTexture(0, TEXTURE);
            blit(mStack, selected * 18 + 1, 1, gui.getBlitOffset(), 148, 0, 20, 20, 256, 256);
            mStack.popPose();
            mStack.popPose();
        }
    }
}
