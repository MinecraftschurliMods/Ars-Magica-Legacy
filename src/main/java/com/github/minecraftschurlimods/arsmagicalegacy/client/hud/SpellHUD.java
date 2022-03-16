package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public final class SpellHUD extends GuiComponent implements IIngameOverlay {
    @Override
    public void render(ForgeIngameGui gui, PoseStack stack, float partialTicks, int width, int height) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !ArsMagicaAPI.get().getMagicHelper().knowsMagic(player) || player.hasEffect(MobEffects.INVISIBILITY)) return;
        if (player.getMainHandItem().is(AMItems.SPELL.get())) {
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(ArsMagicaAPI.MOD_ID, "item/spell_" + SpellItem.getSpell(player.getMainHandItem()).primaryAffinity().getId().getPath()));
            startRender(stack, player, partialTicks);
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                renderRight(stack, sprite, width, height);
            } else {
                renderLeft(stack, sprite, width, height);
            }
            endRender(stack);
        }
        if (player.getOffhandItem().is(AMItems.SPELL.get())) {
            startRender(stack, player, partialTicks);
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(ArsMagicaAPI.MOD_ID, "item/spell_" + SpellItem.getSpell(player.getOffhandItem()).primaryAffinity().getId().getPath()));
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                renderLeft(stack, sprite, width, height);
            } else {
                renderRight(stack, sprite, width, height);
            }
            endRender(stack);
        }
    }

    private void startRender(PoseStack stack, LocalPlayer player, float partialTicks) {
        stack.pushPose();
        stack.mulPose(Vector3f.XP.rotationDegrees((player.getViewXRot(partialTicks) - Mth.lerp(partialTicks, player.xBobO, player.xBob)) * 0.1f));
        stack.mulPose(Vector3f.YP.rotationDegrees((player.getViewYRot(partialTicks) - Mth.lerp(partialTicks, player.yBobO, player.yBob)) * 0.1f));
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
    }

    private void endRender(PoseStack stack) {
        RenderSystem.disableBlend();
        stack.popPose();
    }

    private void renderLeft(PoseStack poseStack, TextureAtlasSprite sprite, int width, int height) {
        blit(poseStack, (int) (width * 0.3) - sprite.getWidth(), (int) (height * 0.5) - 16, getBlitOffset(), sprite.getWidth(), sprite.getHeight(), sprite);
    }

    private void renderRight(PoseStack poseStack, TextureAtlasSprite sprite, int width, int height) {
        blit(poseStack, (int) (width * 0.7), (int) (height * 0.5) - 16, getBlitOffset(), sprite.getWidth(), sprite.getHeight(), sprite);
    }
}
