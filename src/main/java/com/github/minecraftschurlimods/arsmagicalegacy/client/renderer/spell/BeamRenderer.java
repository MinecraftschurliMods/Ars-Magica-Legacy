package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Taken and adapted from <a href="https://github.com/Direwolf20-MC/MiningGadgets/blob/mc/1.18/src/main/java/com/direwolf20/mininggadgets/client/renderer/RenderMiningLaser.java">Direwolf20's Mining Gadgets mod</a>.
 */
public class BeamRenderer extends RenderType {
    private static final ResourceLocation CORE_TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/misc/beam_core.png");
    public static final RenderType CORE = create("beam_core",
            DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, false,
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
    public static final RenderType MAIN = create("beam_main",
            DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, false,
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
    public static final RenderType GLOW = create("beam_glow",
            DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, false,
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

    public static void drawBeams(PoseStack stack, LivingEntity living, InteractionHand hand, Vec3 from, Vec3 to, float r, float g, float b, float ticks) {
        float distance = (float) Math.max(1, from.subtract(to).length());
        long time = living.getLevel().getGameTime();
        float v = -0.02f * time;
        Vec3 view = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        stack.pushPose();
        stack.translate(-view.x(), -view.y(), -view.z());
        stack.translate(from.x, from.y, from.z);
        stack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(ticks, -living.getYRot(), -living.yRotO)));
        stack.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(ticks, living.getXRot(), living.xRotO)));
        PoseStack.Pose pose = stack.last();
        Matrix3f normal = pose.normal();
        Matrix4f matrix = pose.pose();
        boolean firstPerson = living == Minecraft.getInstance().player && Minecraft.getInstance().options.getCameraType().isFirstPerson();
        VertexConsumer vc = buffer.getBuffer(GLOW);
        drawBeam(vc, matrix, normal, 0.07f * (0.9f + 0.1f * Mth.sin(time * 0.99f) * Mth.sin(time * 0.3f) * Mth.sin(time * 0.1f)), hand, distance, 0.5f, 1, ticks, r, g, b, 0.7f, firstPerson);
        vc = buffer.getBuffer(MAIN);
        drawBeam(vc, matrix, normal, 0.02f, hand, distance, v, v + distance * 1.5f, ticks, r, g, b, 1, firstPerson);
        vc = buffer.getBuffer(CORE);
        drawBeam(vc, matrix, normal, 0.01f, hand, distance, v, v + distance * 1.5f, ticks, r, g, b, 1, firstPerson);
        stack.popPose();
        buffer.endBatch();
    }

    private static void drawBeam(VertexConsumer vc, Matrix4f matrix, Matrix3f normal, float width, InteractionHand hand, float distance, float v1, float v2, float ticks, float r, float g, float b, float a, boolean firstPerson) {
        Player player = ClientHelper.getLocalPlayer();
        if (!(player instanceof LocalPlayer lp)) return;
        Vector3f vec = new Vector3f(0, 1, 0);
        vec.transform(normal);
        if (Minecraft.getInstance().options.mainHand != HumanoidArm.RIGHT) {
            hand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        }
        float x, y, z;
        // The various calculations in this section are responsible for correctly positioning the beam, based on the player's fov and rotations.
        // Some constants are taken from an online function estimator, based on input values found by testing.
        if (firstPerson) {
            x = hand == InteractionHand.MAIN_HAND ? -0.25f : 0.25f;
            y = -0.175f;
            float fov = ((float) Minecraft.getInstance().options.fov - 30) / 80f;
            z = -1.045f * fov * fov * fov + 2.3825f * fov * fov - 2.0785f * fov + 0.9175f;
        } else {
            x = (hand == InteractionHand.MAIN_HAND ? -1f : 1f) * (player.getXRot() / 900 + 0.3f);
            y = 0.0001f * player.getXRot() * player.getXRot() + 0.0024f * player.getXRot() - 0.8155f;
            z = 0.25f + (Mth.lerp(ticks, player.yRotO, player.getYRot()) - Mth.lerp(ticks, lp.yBobO, lp.yBob)) / 25;
        }
        x += (Mth.lerp(ticks, player.yRotO, player.getYRot()) - Mth.lerp(ticks, lp.yBobO, lp.yBob)) / 750;
        y += (Mth.lerp(ticks, player.xRotO, player.getXRot()) - Mth.lerp(ticks, lp.xBobO, lp.xBob)) / 750;
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
