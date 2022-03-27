package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public final class SpellHUD extends GuiComponent implements IIngameOverlay {
    private ItemStack mainHandItem = ItemStack.EMPTY;
    private ItemStack offHandItem = ItemStack.EMPTY;
    private float mainHandHeight;
    private float oMainHandHeight;
    private float offHandHeight;
    private float oOffHandHeight;

    @Override
    public void render(ForgeIngameGui gui, PoseStack stack, float partialTicks, int width, int height) {
        LocalPlayer player = ClientHelper.getLocalPlayer();
        if (!(Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) || player == null || !ArsMagicaAPI.get().getMagicHelper().knowsMagic(player) || player.hasEffect(MobEffects.INVISIBILITY))
            return;
        if (player.getMainHandItem().is(AMItems.SPELL.get())) {
            float equipProgress = -Mth.lerp(partialTicks, oMainHandHeight, mainHandHeight);;
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(ArsMagicaAPI.MOD_ID, "item/spell_" + SpellItem.getSpell(player.getMainHandItem()).primaryAffinity().getId().getPath()));
            startRender(stack, player, partialTicks);
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                renderRight(stack, sprite, width, height, equipProgress);
            } else {
                renderLeft(stack, sprite, width, height, equipProgress);
            }
            endRender(stack);
        }
        if (player.getOffhandItem().is(AMItems.SPELL.get())) {
            float equipProgress = -Mth.lerp(partialTicks, oOffHandHeight, offHandHeight);;
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(ArsMagicaAPI.MOD_ID, "item/spell_" + SpellItem.getSpell(player.getOffhandItem()).primaryAffinity().getId().getPath()));
            startRender(stack, player, partialTicks);
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                renderLeft(stack, sprite, width, height, equipProgress);
            } else {
                renderRight(stack, sprite, width, height, equipProgress);
            }
            endRender(stack);
        }
    }

    public void tick() {
        oMainHandHeight = mainHandHeight;
        oOffHandHeight = offHandHeight;
        LocalPlayer player = ClientHelper.getLocalPlayer();
        if (player == null) return;
        ItemStack mStack = player.getMainHandItem();
        ItemStack oStack = player.getOffhandItem();
        if (ItemStack.matches(mainHandItem, mStack)) {
            mainHandItem = mStack;
        }
        if (ItemStack.matches(offHandItem, oStack)) {
            offHandItem = oStack;
        }
        if (player.isHandsBusy()) {
            mainHandHeight = Mth.clamp(mainHandHeight - 0.4F, 0.0F, 1.0F);
            offHandHeight = Mth.clamp(offHandHeight - 0.4F, 0.0F, 1.0F);
        } else {
            float f = player.getAttackStrengthScale(1.0F);
            boolean mEquip = net.minecraftforge.client.ForgeHooksClient.shouldCauseReequipAnimation(mainHandItem, mStack, player.getInventory().selected);
            boolean oEquip = net.minecraftforge.client.ForgeHooksClient.shouldCauseReequipAnimation(offHandItem, oStack, -1);
            if (!mEquip && mainHandItem != mStack) {
                mainHandItem = mStack;
            }
            if (!oEquip && offHandItem != oStack) {
                offHandItem = oStack;
            }
            mainHandHeight += Mth.clamp((!mEquip ? f * f * f : 0f) - mainHandHeight, -0.4F, 0.4F);
            offHandHeight += Mth.clamp((!oEquip ? 1f : 0f) - offHandHeight, -0.4F, 0.4F);
        }
        if (mainHandHeight < 0.1F) {
            mainHandItem = mStack;
        }
        if (offHandHeight < 0.1F) {
            offHandItem = oStack;
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

    private void renderLeft(PoseStack poseStack, TextureAtlasSprite sprite, int width, int height, float equipProgress) {
        blit(poseStack, (int) (width * 0.3) - sprite.getWidth(), (int) (height * 0.5 - 16 + equipProgress * 20), getBlitOffset(), sprite.getWidth(), sprite.getHeight(), sprite);
    }

    private void renderRight(PoseStack poseStack, TextureAtlasSprite sprite, int width, int height, float equipProgress) {
        blit(poseStack, (int) (width * 0.7), (int) (height * 0.5 - 16 + equipProgress * 20), getBlitOffset(), sprite.getWidth(), sprite.getHeight(), sprite);
    }
}
