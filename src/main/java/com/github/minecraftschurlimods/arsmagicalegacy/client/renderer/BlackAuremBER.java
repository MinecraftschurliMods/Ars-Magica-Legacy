package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer;

import com.github.minecraftschurlimods.arsmagicalegacy.common.block.blackaurem.BlackAuremBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class BlackAuremBER implements BlockEntityRenderer<BlackAuremBlockEntity> {
    public BlackAuremBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(BlackAuremBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Minecraft instance = Minecraft.getInstance();
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.scale(1.25f, 1.25f, 1.25f);
        poseStack.mulPose(instance.gameRenderer.getMainCamera().rotation());
        poseStack.mulPose(Vector3f.ZP.rotation(instance.player.tickCount / 10f % 360));
        TextureAtlasSprite sprite = instance.getBlockRenderer().getBlockModelShaper().getParticleIcon(blockEntity.getBlockState());
        VertexConsumer buffer = bufferSource.getBuffer(RenderType.translucent());

        buffer.vertex(poseStack.last().pose(), -1F, -1F, 0F).color(1f, 1f, 1f, 1f).uv(sprite.getU1(), sprite.getV1()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(poseStack.last().normal(), 0, 1, 0).endVertex();
        buffer.vertex(poseStack.last().pose(), -1F, 1F, 0F).color(1f, 1f, 1f, 1f).uv(sprite.getU1(), sprite.getV0()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(poseStack.last().normal(), 0, 1, 0).endVertex();
        buffer.vertex(poseStack.last().pose(), 1F, 1F, 0F).color(1f, 1f, 1f, 1f).uv(sprite.getU0(), sprite.getV0()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(poseStack.last().normal(), 0, 1, 0).endVertex();
        buffer.vertex(poseStack.last().pose(), 1F, -1F, 0F).color(1f, 1f, 1f, 1f).uv(sprite.getU0(), sprite.getV1()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(poseStack.last().normal(), 0, 1, 0).endVertex();
        poseStack.popPose();
    }
}
