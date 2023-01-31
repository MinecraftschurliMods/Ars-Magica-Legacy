package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Taken and adapted from <a href="https://github.com/Direwolf20-MC/MiningGadgets/blob/1.18/src/main/java/com/direwolf20/mininggadgets/client/renderer/RenderMiningLaser.java">Direwolf20's Mining Gadgets mod</a>.
 */
public class BeamRenderer {
    public static void drawBeams(LivingEntity living, InteractionHand hand, Vec3 from, Vec3 to, float r, float g, float b, float ticks) {
        double distance = Math.max(1, from.subtract(to).length());
        long time = living.level.getGameTime();
        double v = -0.02f * time;
        Vec3 view = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        PoseStack stack = new PoseStack();//event.getPoseStack();
        stack.translate(-view.x(), -view.y(), -view.z());
        stack.translate(from.x, from.y, from.z);
        stack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(ticks, -living.getYRot(), -living.yRotO)));
        stack.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(ticks, living.getXRot(), living.xRotO)));
        PoseStack.Pose pose = stack.last();
        Matrix3f normal = pose.normal();
        Matrix4f matrix = pose.pose();
        VertexConsumer vc = buffer.getBuffer(BeamRenderType.GLOW);
        drawBeam(vc, matrix, normal, 0.07f * (0.9f + 0.1f * Mth.sin(time * 0.99f) * Mth.sin(time * 0.3f) * Mth.sin(time * 0.1f)), hand, distance, 0.5, 1, ticks, r, g, b, 0.7f);
        vc = buffer.getBuffer(BeamRenderType.MAIN);
        drawBeam(vc, matrix, normal, 0.02f, hand, distance, v, v + distance * 1.5, ticks, r, g, b, 1f);
        vc = buffer.getBuffer(BeamRenderType.CORE);
        drawBeam(vc, matrix, normal, 0.01f, hand, distance, v, v + distance * 1.5, ticks, r, g, b, 1f);
        buffer.endBatch();
    }

    private static void drawBeam(VertexConsumer vc, Matrix4f matrix, Matrix3f normal, float width, InteractionHand hand, double distance, double v1, double v2, float ticks, float r, float g, float b, float a) {
        Player player = ClientHelper.getLocalPlayer();
        if (!(player instanceof LocalPlayer lp)) return;
        Vector3f vector3f = new Vector3f(0f, 1f, 0f);
        vector3f.transform(normal);
        if (Minecraft.getInstance().options.mainHand != HumanoidArm.RIGHT) {
            hand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        }
        float startXOffset = -0.20f;
        float startYOffset = -0.108f;
        float startZOffset = 1.60f - lp.getFieldOfViewModifier();
        if (hand == InteractionHand.OFF_HAND) {
            startXOffset = 0.25f;
            startYOffset = -0.120f;
        }
        startXOffset += ((Mth.lerp(ticks, player.yRotO, player.getYRot()) - Mth.lerp(ticks, lp.yBobO, lp.yBob)) / 750);
        startYOffset += ((Mth.lerp(ticks, player.xRotO, player.getXRot()) - Mth.lerp(ticks, lp.xBobO, lp.xBob)) / 750);
        Vector4f vec1 = new Vector4f(startXOffset, -width + startYOffset, startZOffset, 1);
        vec1.transform(matrix);
        Vector4f vec2 = new Vector4f(0, -width, (float) distance, 1);
        vec2.transform(matrix);
        Vector4f vec3 = new Vector4f(0, width, (float) distance, 1);
        vec3.transform(matrix);
        Vector4f vec4 = new Vector4f(startXOffset, width + startYOffset, startZOffset, 1);
        vec4.transform(matrix);
        if (hand == InteractionHand.MAIN_HAND) {
            vertex(vc, vec4, r, g, b, a, 0, (float) v1, vector3f);
            vertex(vc, vec3, r, g, b, a, 0, (float) v2, vector3f);
            vertex(vc, vec2, r, g, b, a, 1, (float) v2, vector3f);
            vertex(vc, vec1, r, g, b, a, 1, (float) v1, vector3f);
            //Rendering again to allow you to see both sides in multiplayer, shouldn't be necessary with culling disabled but here we are...
            vertex(vc, vec1, r, g, b, a, 1, (float) v1, vector3f);
            vertex(vc, vec2, r, g, b, a, 1, (float) v2, vector3f);
            vertex(vc, vec3, r, g, b, a, 0, (float) v2, vector3f);
            vertex(vc, vec4, r, g, b, a, 0, (float) v1, vector3f);
        } else {
            vertex(vc, vec1, r, g, b, a, 1, (float) v1, vector3f);
            vertex(vc, vec2, r, g, b, a, 1, (float) v2, vector3f);
            vertex(vc, vec3, r, g, b, a, 0, (float) v2, vector3f);
            vertex(vc, vec4, r, g, b, a, 0, (float) v1, vector3f);
            //Rendering again to allow you to see both sides in multiplayer, shouldn't be necessary with culling disabled but here we are...
            vertex(vc, vec4, r, g, b, a, 0, (float) v1, vector3f);
            vertex(vc, vec3, r, g, b, a, 0, (float) v2, vector3f);
            vertex(vc, vec2, r, g, b, a, 1, (float) v2, vector3f);
            vertex(vc, vec1, r, g, b, a, 1, (float) v1, vector3f);
        }
    }
    
    private static void vertex(VertexConsumer vc, Vector4f vec4, float r, float g, float b, float a, float u, float v, Vector3f vec3) {
        vc.vertex(vec4.x(), vec4.y(), vec4.z(), r, g, b, a, u, v, OverlayTexture.NO_OVERLAY, 0xf000f0, vec3.x(), vec3.y(), vec3.z());
    }
}
