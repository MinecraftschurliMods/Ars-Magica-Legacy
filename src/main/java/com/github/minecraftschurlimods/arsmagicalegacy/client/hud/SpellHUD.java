package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public final class SpellHUD extends GuiComponent implements IIngameOverlay {
    @Override
    public void render(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !ArsMagicaAPI.get().getMagicHelper().knowsMagic(player) || player.hasEffect(MobEffects.INVISIBILITY)) return;
        if (player.getMainHandItem().is(AMItems.SPELL.get())) {
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(ArsMagicaAPI.MOD_ID, "item/spell_" + SpellItem.getSpell(player.getMainHandItem()).primaryAffinity().getId().getPath()));
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                renderRight(mStack, sprite, width, height);
            } else {
                renderLeft(mStack, sprite, width, height);
            }
        }
        if (player.getOffhandItem().is(AMItems.SPELL.get())) {
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(ArsMagicaAPI.MOD_ID, "item/spell_" + SpellItem.getSpell(player.getMainHandItem()).primaryAffinity().getId().getPath()));
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                renderLeft(mStack, sprite, width, height);
            } else {
                renderRight(mStack, sprite, width, height);
            }
        }
    }

    private void renderLeft(PoseStack poseStack, TextureAtlasSprite sprite, int width, int height) {
        poseStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        blit(poseStack, (int) (width * 0.3), (int) (height * 0.5) - sprite.getHeight() / 2, getBlitOffset(), sprite.getWidth(), sprite.getHeight(), sprite);
        RenderSystem.disableBlend();
        poseStack.popPose();
    }

    private void renderRight(PoseStack poseStack, TextureAtlasSprite sprite, int width, int height) {
        poseStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        blit(poseStack, (int) (width * 0.7), (int) (height * 0.5) - sprite.getHeight() / 2, getBlitOffset(), sprite.getWidth(), sprite.getHeight(), sprite);
        RenderSystem.disableBlend();
        poseStack.popPose();
    }
}
