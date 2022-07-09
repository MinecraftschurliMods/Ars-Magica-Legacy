package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;

public abstract class NonLiving3DModelRenderer<T extends Entity, M extends EntityModel<T>> extends EntityRenderer<T> {
    private final M model;

    public NonLiving3DModelRenderer(EntityRendererProvider.Context context, M model) {
        super(context);
        this.model = model;
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        model.setupAnim(pEntity, pPartialTicks, 0, pEntity.tickCount + pPartialTicks, pEntityYaw, pEntity.getXRot());
        model.renderToBuffer(pMatrixStack, pBuffer.getBuffer(model.renderType(getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
