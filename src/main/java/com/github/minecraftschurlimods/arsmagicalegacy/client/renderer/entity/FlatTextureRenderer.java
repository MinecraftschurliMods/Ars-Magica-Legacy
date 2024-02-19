package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public abstract class FlatTextureRenderer<T extends Entity> extends EntityRenderer<T> {
    protected FlatTextureRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.tickCount < 2 && entityRenderDispatcher.camera.getEntity().distanceToSqr(pEntity) < 12.25D) return;
        ResourceLocation texture = getTextureLocation(pEntity);
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Quaternionf quaternion = camera.rotation();
        Vec3 vec3 = camera.getPosition();
        Vector3f[] array = new Vector3f[]{new Vector3f(-1F, -1F, 0F), new Vector3f(-1F, 1F, 0F), new Vector3f(1F, 1F, 0F), new Vector3f(1F, -1F, 0F)};
        for (Vector3f vector : array) {
            vector.rotate(quaternion);
            Vector4f toTransform = new Vector4f(vector, 0f);
            toTransform.mul(pMatrixStack.last().pose());
            vector.set(toTransform.x(), toTransform.y() + 1, toTransform.z());
        }
        pMatrixStack.pushPose();
        pMatrixStack.scale(3F, 3F, 3F);
        pMatrixStack.translate(pEntity.position().x - vec3.x(), pEntity.position().y - vec3.y(), pEntity.position().z - vec3.z());
        VertexConsumer consumer = pBuffer.getBuffer(RenderType.entityTranslucent(texture));
        float minU = 0;
        float maxU = 1;
        float minV = (pEntity.tickCount % getTextureHeight()) / (float) getTextureHeight();
        float maxV = minV + 1 / (float) getTextureHeight();
        Vec3 entityPos = pEntity.getPosition(pPartialTicks);
        float x = (float) entityPos.x;
        float y = (float) entityPos.y;
        float z = (float) entityPos.z;
        consumer.vertex(array[0].x(), array[0].y(), array[0].z(), 1f, 1f, 1f, 1f, maxU, maxV, OverlayTexture.NO_OVERLAY, pPackedLight, x, y, z);
        consumer.vertex(array[1].x(), array[1].y(), array[1].z(), 1f, 1f, 1f, 1f, maxU, minV, OverlayTexture.NO_OVERLAY, pPackedLight, x, y, z);
        consumer.vertex(array[2].x(), array[2].y(), array[2].z(), 1f, 1f, 1f, 1f, minU, minV, OverlayTexture.NO_OVERLAY, pPackedLight, x, y, z);
        consumer.vertex(array[3].x(), array[3].y(), array[3].z(), 1f, 1f, 1f, 1f, minU, maxV, OverlayTexture.NO_OVERLAY, pPackedLight, x, y, z);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    /**
     * @return The amount of single images the texture has. For example, a 16x64 texture should return 4, while a 64x768 texture should return 12.
     */
    protected abstract int getTextureHeight();
}
