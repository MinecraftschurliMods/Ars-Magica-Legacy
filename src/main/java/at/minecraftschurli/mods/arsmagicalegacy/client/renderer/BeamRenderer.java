package at.minecraftschurli.mods.arsmagicalegacy.client.renderer;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/// Adapted from [BeaconRenderer]
public final class BeamRenderer {
    private static final Identifier CORE_TEXTURE = ArsMagicaApi.id("textures/misc/beam_core.png");
    private static final Identifier GLOW_TEXTURE = ArsMagicaApi.id("textures/misc/beam_glow.png");
    private static final Identifier MAIN_TEXTURE = ArsMagicaApi.id("textures/misc/beam_main.png");

    private BeamRenderer() {}

    public static void submitThirdPerson(PoseStack stack, SubmitNodeCollector collector, Entity entity, Vec3 target, int color, float partialTick) {
        Level level = AMClientUtil.level();
        Vec3 origin = entity.getEyePosition(partialTick);
        double xd = target.x - origin.x;
        double zd = target.z - origin.z;
        float xRot = Mth.wrapDegrees((float) Math.toDegrees(-Math.atan2(target.y - origin.y, Math.sqrt(xd * xd + zd * zd))) + 90);
        float yRot = Mth.wrapDegrees((float) Math.toDegrees(Math.atan2(zd, xd)) - 90);
        stack.pushPose();
        stack.translate(origin);
        stack.mulPose(Axis.YP.rotationDegrees(-yRot));
        stack.mulPose(Axis.XP.rotationDegrees(xRot));
        submit(stack, collector, (float) target.distanceTo(origin), level != null ? -Math.floorMod(level.getGameTime(), 40) - partialTick : 0f, color);
        stack.popPose();
    }

    private static void submit(PoseStack stack, SubmitNodeCollector collector, float height, float time, int color) {
        float vOffset = 1 - Mth.frac((time * 0.2f - Mth.floor(time * 0.1f)) * 4);
        float glowVOffset = 1 - Mth.frac((time * 0.2f - Mth.floor(time * 0.1f)) * 3);
        submit(stack, collector, GLOW_TEXTURE, 0.07f * (0.9f + 0.1f * Mth.sin(time * 0.99f) * Mth.sin(time * 0.3f) * Mth.sin(time * 0.1f)), height, glowVOffset, height + glowVOffset, ARGB.color(32, color));
        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(time * 2.25f));
        submit(stack, collector, MAIN_TEXTURE, 0.02f, height, vOffset, height * 25f + vOffset, color);
        submit(stack, collector, CORE_TEXTURE, 0.01f, height, vOffset, height * 50f + vOffset, color);
        stack.popPose();
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    private static void submit(PoseStack stack, SubmitNodeCollector collector, Identifier texture, float radius, float height, float v0, float v1, int color) {
        float wnx = -radius;
        float wnz = -radius;
        float enx = -radius;
        float enz = radius;
        float wsx = radius;
        float wsz = -radius;
        float esx = radius;
        float esz = radius;
        collector.submitCustomGeometry(stack, RenderTypes.beaconBeam(texture, true), (pose, builder) -> {
            renderQuad(pose, builder, color, height, wnx, wnz, enx, enz, v0, v1);
            renderQuad(pose, builder, color, height, esx, esz, wsx, wsz, v0, v1);
            renderQuad(pose, builder, color, height, enx, enz, esx, esz, v0, v1);
            renderQuad(pose, builder, color, height, wsx, wsz, wnx, wnz, v0, v1);
        });
    }

    private static void renderQuad(PoseStack.Pose pose, VertexConsumer builder, int color, float height, float x0, float z0, float x1, float z1, float v0, float v1) {
        addVertex(pose, builder, color, x0, height, z0, 1, v1);
        addVertex(pose, builder, color, x1, height, z1, 0, v1);
        addVertex(pose, builder, color, x1, 0, z1, 0, v0);
        addVertex(pose, builder, color, x0, 0, z0, 1, v0);
        addVertex(pose, builder, color, x0, 0, z0, 1, v0);
        addVertex(pose, builder, color, x1, 0, z1, 0, v0);
        addVertex(pose, builder, color, x1, height, z1, 0, v1);
        addVertex(pose, builder, color, x0, height, z0, 1, v1);
    }

    private static void addVertex(PoseStack.Pose pose, VertexConsumer builder, int color, float x, float y, float z, float u, float v) {
        builder.addVertex(pose, x, y, z)
            .setColor(color)
            .setUv(u, v)
            .setOverlay(OverlayTexture.NO_OVERLAY)
            .setLight(LightCoordsUtil.FULL_BRIGHT)
            .setNormal(pose, 0f, 1f, 0f);
    }
}
