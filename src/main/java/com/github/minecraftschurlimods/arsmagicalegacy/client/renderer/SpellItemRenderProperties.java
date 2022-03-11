package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;

public class SpellItemRenderProperties extends BlockEntityWithoutLevelRenderer implements IItemRenderProperties {
    private float rot;
    public SpellItemRenderProperties() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
        return this;
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (transformType.firstPerson()) {
            rot += 0.25f;
            rot %= 360;
            LocalPlayer player = ClientHelper.getLocalPlayer();
            if (player == null) return;
            boolean isMainHand = player.getUsedItemHand() == InteractionHand.MAIN_HAND;
            HumanoidArm arm = isMainHand ? player.getMainArm() : player.getMainArm().getOpposite();
            float armMultiplier = arm == HumanoidArm.RIGHT ? 1 : -1;
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(ArsMagicaAPI.MOD_ID, "item/spell_" + SpellItem.getSpell(stack).primaryAffinity().getId().getPath()));
            if (sprite.getAnimationTicker() == null) return;
            float u0 = sprite.getU0();
            float u1 = sprite.getU1();
            float v0 = sprite.getV0();
            float v1 = sprite.getV1();
            float m0 = 0;
            float m1 = 64;
            float swingProgress = /*player.isUsingItem() ? player.getAttackAnim(1) : */0;
            float swingSqrt = Mth.sqrt(swingProgress);
//            poseStack.translate(-sprite.getWidth() / 2f, 0, -sprite.getWidth() / 2f);
//            poseStack.translate(armMultiplier * 0.64f, -0.6f, -0.72f);
//            poseStack.mulPose(Vector3f.YP.rotationDegrees(armMultiplier * 45));
//            poseStack.mulPose(Vector3f.YP.rotationDegrees(armMultiplier * Mth.sin(Mth.sqrt(swingProgress) * (float) Math.PI) * 70));
//            poseStack.mulPose(Vector3f.ZP.rotationDegrees(armMultiplier * Mth.sin(swingProgress * swingProgress * (float) Math.PI) * -20));
//            poseStack.translate(-armMultiplier, 3.6f, 3.5f);
//            poseStack.mulPose(Vector3f.ZP.rotationDegrees(armMultiplier * 120));
//            poseStack.mulPose(Vector3f.XP.rotationDegrees(200));
//            poseStack.mulPose(Vector3f.YP.rotationDegrees(armMultiplier * -135));
//            poseStack.translate(armMultiplier * 5.6f, 0, 0);
//            poseStack.mulPose(Vector3f.XP.rotationDegrees(armMultiplier * -60));
//            poseStack.mulPose(Vector3f.YP.rotationDegrees(armMultiplier * 30));
//            poseStack.mulPose(Vector3f.ZP.rotationDegrees(armMultiplier * 75));
//            poseStack.mulPose(Vector3f.XP.rotationDegrees(armMultiplier * -30));
//            poseStack.scale(0.25f, 0.25f, 0.25f);
//            poseStack.mulPose(Vector3f.ZP.rotationDegrees(armMultiplier * 105));
//            poseStack.translate(sprite.getWidth() / 2f, 0, sprite.getWidth() / 2f);
            VertexConsumer vc = buffer.getBuffer(SpellRenderType.SPELL.apply(InventoryMenu.BLOCK_ATLAS));
            PoseStack.Pose pose = poseStack.last();
            Matrix4f matrix = pose.pose();
            Matrix3f normal = pose.normal();
            vc.vertex(matrix, m0, m1, 0).color(1f, 1f, 1f, 1f).uv(u0, v1).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
            vc.vertex(matrix, m1, m1, 0).color(1f, 1f, 1f, 1f).uv(u1, v1).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
            vc.vertex(matrix, m1, m0, 0).color(1f, 1f, 1f, 1f).uv(u1, v0).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
            vc.vertex(matrix, m0, m0, 0).color(1f, 1f, 1f, 1f).uv(u0, v0).overlayCoords(packedOverlay).uv2(packedLight).normal(normal, 0, 1, 0).endVertex();
        }
    }
}
