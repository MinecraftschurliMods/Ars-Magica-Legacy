package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.ColorUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

/**
 * Taken and adapted from <a href="https://github.com/Direwolf20-MC/MiningGadgets/blob/mc/1.18/src/main/java/com/direwolf20/mininggadgets/client/renderer/RenderMiningLaser.java">Direwolf20's Mining Gadgets mod</a>.
 */
public class BeamRenderer extends RenderType {
    private static final ResourceLocation CORE_TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/misc/beam_core.png");
    private static final RenderType CORE = create("beam_core", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, false,
            RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(CORE_TEXTURE, false, false))
                    .setShaderState(RenderStateShard.ShaderStateShard.POSITION_COLOR_TEX_SHADER)
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(NO_DEPTH_TEST)
                    .setCullState(NO_CULL)
                    .setLightmapState(NO_LIGHTMAP)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false));
    private static final ResourceLocation MAIN_TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/misc/beam_main.png");
    private static final RenderType MAIN = create("beam_main", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, false,
            RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(MAIN_TEXTURE, false, false))
                    .setShaderState(RenderStateShard.ShaderStateShard.POSITION_COLOR_TEX_SHADER)
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(NO_DEPTH_TEST)
                    .setCullState(NO_CULL)
                    .setLightmapState(NO_LIGHTMAP)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false));
    private static final ResourceLocation GLOW_TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/misc/beam_glow.png");
    private static final RenderType GLOW = create("beam_glow", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, false,
            RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(GLOW_TEXTURE, false, false))
                    .setShaderState(RenderStateShard.ShaderStateShard.POSITION_COLOR_TEX_SHADER)
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(NO_DEPTH_TEST)
                    .setCullState(NO_CULL)
                    .setLightmapState(NO_LIGHTMAP)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false));

    public BeamRenderer(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    public static void drawBeam(PoseStack stack, Entity entity, Vec3 target, InteractionHand hand, int color, float ticks) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.mainHand().get() != HumanoidArm.RIGHT) {
            hand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        }
        boolean firstPerson = entity == ClientHelper.getLocalPlayer() && mc.options.getCameraType().isFirstPerson();
        float height = entity.getBbHeight() / 2f;
        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
        Vec3 view = mc.gameRenderer.getMainCamera().getPosition();
        Vec3 origin = firstPerson ? entity.getEyePosition(ticks) : entity.getPosition(ticks).add(0, height, 0);
        long time = entity.getLevel().getGameTime();
        float v = -0.02f * time;
        float distance = (float) Math.max(1, origin.subtract(target).length());
        float r = ColorUtil.getRed(color), g = ColorUtil.getGreen(color), b = ColorUtil.getBlue(color);
        stack.pushPose();
        stack.translate(-view.x(), -view.y(), -view.z());
        stack.translate(origin.x, origin.y, origin.z);
        float x, y, z;
        if (firstPerson) {
            float fov = ((float) mc.options.fov().get() - 30) / 80f;
            x = hand == InteractionHand.MAIN_HAND ? -0.25f : 0.25f;
            y = -0.175f;
            // This calculation is responsible for correctly positioning the beam in-hand, based on the player's fov.
            // The constants are taken from an online function estimator, based on input values found by testing.
            z = -1.045f * fov * fov * fov + 2.3825f * fov * fov - 2.0785f * fov + 0.9175f;
            stack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(ticks, -entity.getYRot(), -entity.yRotO)));
            stack.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(ticks, entity.getXRot(), entity.xRotO)));
        } else {
            x = -height / 2f;
            y = 0;
            z = 0;
            if (entity instanceof Player) {
                stack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(ticks, -entity.getYRot(), -entity.yRotO)));
                stack.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(ticks, entity.getXRot(), entity.xRotO)));
            } else {
                Vec2 vec2 = AMUtil.getRotations(origin, target);
                stack.mulPose(Vector3f.YP.rotationDegrees(-vec2.y));
                stack.mulPose(Vector3f.XP.rotationDegrees(vec2.x));
            }
            stack.mulPose(Vector3f.ZP.rotationDegrees(90));
            stack.translate(height / 2f, 0, 0);
        }
        PoseStack.Pose pose = stack.last();
        Matrix3f normal = pose.normal();
        Matrix4f matrix = pose.pose();
        drawPart(buffer.getBuffer(GLOW), matrix, normal, 0.07f * (0.9f + 0.1f * Mth.sin(time * 0.99f) * Mth.sin(time * 0.3f) * Mth.sin(time * 0.1f)), distance, 0.5f, 1, x, y, z, hand, r, g, b, 0.7f);
        drawPart(buffer.getBuffer(MAIN), matrix, normal, 0.02f, distance, v, v + distance * 1.5f, x, y, z, hand, r, g, b, 1);
        drawPart(buffer.getBuffer(CORE), matrix, normal, 0.01f, distance, v, v + distance * 1.5f, x, y, z, hand, r, g, b, 1);
        stack.popPose();
        buffer.endBatch();
    }

    private static void drawPart(VertexConsumer vc, Matrix4f matrix, Matrix3f normal, float width, float distance, float v1, float v2, float x, float y, float z, InteractionHand hand, float r, float g, float b, float a) {
        Vector3f vec = new Vector3f(0, 1, 0);
        vec.transform(normal);
        Vector4f vec1 = new Vector4f(x, -width + y, z, 1);
        vec1.transform(matrix);
        Vector4f vec2 = new Vector4f(0, -width, distance, 1);
        vec2.transform(matrix);
        Vector4f vec3 = new Vector4f(0, width, distance, 1);
        vec3.transform(matrix);
        Vector4f vec4 = new Vector4f(x, width + y, z, 1);
        vec4.transform(matrix);
        if (hand == InteractionHand.MAIN_HAND) {
            vertex(vc, vec4, r, g, b, a, 0, v1, vec);
            vertex(vc, vec3, r, g, b, a, 0, v2, vec);
            vertex(vc, vec2, r, g, b, a, 1, v2, vec);
            vertex(vc, vec1, r, g, b, a, 1, v1, vec);
            //Rendering again to allow you to see both sides in multiplayer, shouldn't be necessary with culling disabled but here we are...
            vertex(vc, vec1, r, g, b, a, 1, v1, vec);
            vertex(vc, vec2, r, g, b, a, 1, v2, vec);
            vertex(vc, vec3, r, g, b, a, 0, v2, vec);
            vertex(vc, vec4, r, g, b, a, 0, v1, vec);
        } else {
            vertex(vc, vec1, r, g, b, a, 1, v1, vec);
            vertex(vc, vec2, r, g, b, a, 1, v2, vec);
            vertex(vc, vec3, r, g, b, a, 0, v2, vec);
            vertex(vc, vec4, r, g, b, a, 0, v1, vec);
            //Rendering again to allow you to see both sides in multiplayer, shouldn't be necessary with culling disabled but here we are...
            vertex(vc, vec4, r, g, b, a, 0, v1, vec);
            vertex(vc, vec3, r, g, b, a, 0, v2, vec);
            vertex(vc, vec2, r, g, b, a, 1, v2, vec);
            vertex(vc, vec1, r, g, b, a, 1, v1, vec);
        }
    }

    private static void vertex(VertexConsumer vc, Vector4f vec4, float r, float g, float b, float a, float u, float v, Vector3f vec3) {
        vc.vertex(vec4.x(), vec4.y(), vec4.z(), r, g, b, a, u, v, OverlayTexture.NO_OVERLAY, 0xf000f0, vec3.x(), vec3.y(), vec3.z());
    }
}