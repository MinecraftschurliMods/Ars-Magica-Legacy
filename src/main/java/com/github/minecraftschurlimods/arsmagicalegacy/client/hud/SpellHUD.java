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
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public final class SpellHUD extends GuiComponent implements IIngameOverlay {
    @Override
    public void render(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !ArsMagicaAPI.get().getMagicHelper().knowsMagic(player)) return;
        if (player.getMainHandItem().is(AMItems.SPELL.get())) {
            ResourceLocation texture = new ResourceLocation(ArsMagicaAPI.MOD_ID, "item/spell_" + SpellItem.getSpell(player.getMainHandItem()).primaryAffinity().getId().getPath());
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                renderRight(player, mStack, texture, width, height);
            } else {
                renderLeft(player, mStack, texture, width, height);
            }
        }
        if (player.getOffhandItem().is(AMItems.SPELL.get())) {
            ResourceLocation texture = new ResourceLocation(ArsMagicaAPI.MOD_ID, "item/spell_" + SpellItem.getSpell(player.getOffhandItem()).primaryAffinity().getId().getPath());
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                renderLeft(player, mStack, texture, width, height);
            } else {
                renderRight(player, mStack, texture, width, height);
            }
        }
    }

    private void renderLeft(LocalPlayer player, PoseStack poseStack, ResourceLocation texture, int width, int height) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture);
        poseStack.pushPose();
        RenderSystem.setShaderTexture(0, new ResourceLocation(texture.getNamespace(), "textures/" + texture.getPath() + ".png"));
        blit(poseStack, (int) (width * 0.3) - sprite.getFrameCount() * 2, (int) (height * 0.5), 0, sprite.getHeight() * (player.tickCount % sprite.getFrameCount()), sprite.getWidth(), sprite.getWidth());
        poseStack.popPose();
    }

    private void renderRight(LocalPlayer player, PoseStack poseStack, ResourceLocation texture, int width, int height) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture);
        poseStack.pushPose();
        RenderSystem.setShaderTexture(0, new ResourceLocation(texture.getNamespace(), "textures/" + texture.getPath() + ".png"));
        blit(poseStack, (int) (width * 0.7), (int) (height * 0.5), sprite.getWidth() / 2, sprite.getHeight() * (player.tickCount % sprite.getFrameCount()), sprite.getWidth() * 2, sprite.getHeight() * 4);
        poseStack.popPose();
    }
}
