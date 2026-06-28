package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.block;

import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.BlackAuremBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class BlackAuremRenderer extends AbstractEtheriumBlockEntityRenderer<BlackAuremBlockEntity, BlackAuremRenderer.State> {
    private static final float RAD = (float) (Math.PI / 180);
    private static final Vector3f FORWARDS = new Vector3f(0, 0, -1);
    private static final Vector3f UP = new Vector3f(0, 1, 0);
    private static final Vector3f LEFT = new Vector3f(-1, 0, 0);

    @SuppressWarnings("unused")
    public BlackAuremRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(BlackAuremBlockEntity blockEntity, State state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        Minecraft mc = AMClientUtil.mc();
        // Still need to go through the main camera because CameraRenderState doesn't give us what we need
        Camera gameCamera = mc.gameRenderer.getMainCamera();
        state.quaternion.rotationYXZ(-gameCamera.yRot() * RAD, gameCamera.xRot() * RAD, -gameCamera.getRoll() * RAD);
        FORWARDS.rotate(state.quaternion, new Vector3f(gameCamera.forwardVector()));
        UP.rotate(state.quaternion, new Vector3f(gameCamera.upVector()));
        LEFT.rotate(state.quaternion, new Vector3f(gameCamera.leftVector()));
        state.rotation = Axis.ZP.rotation(Objects.requireNonNull(AMClientUtil.player()).tickCount / 10f % 360);
        BlockStateModel model = mc.getModelManager().getBlockStateModelSet().get(blockEntity.getBlockState());
        state.sprite = model.particleMaterial(Objects.requireNonNull(AMClientUtil.level()), blockEntity.getBlockPos(), blockEntity.getBlockState()).sprite();
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(state.quaternion);
        poseStack.mulPose(state.rotation);
        submitNodeCollector.submitCustomGeometry(poseStack, RenderTypes.translucentMovingBlock(), new Renderer(state.sprite, state.lightCoords));
        poseStack.popPose();
    }

    @NullUnmarked
    public static class State extends AbstractEtheriumBlockEntityRenderer.RenderState {
        public Quaternionf quaternion = new Quaternionf();
        public Quaternionf rotation;
        public TextureAtlasSprite sprite;
    }

    private record Renderer(TextureAtlasSprite sprite, int light) implements SubmitNodeCollector.CustomGeometryRenderer {
        @Override
        public void render(PoseStack.Pose pose, VertexConsumer buffer) {
            Matrix4f m = pose.pose();
            buffer.addVertex(m, -1, -1, 0).setColor(1f, 1f, 1f, 1f).setUv(sprite.getU1(), sprite.getV1()).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
            buffer.addVertex(m, -1, 1, 0).setColor(1f, 1f, 1f, 1f).setUv(sprite.getU1(), sprite.getV0()).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
            buffer.addVertex(m, 1, 1, 0).setColor(1f, 1f, 1f, 1f).setUv(sprite.getU0(), sprite.getV0()).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
            buffer.addVertex(m, 1, -1, 0).setColor(1f, 1f, 1f, 1f).setUv(sprite.getU0(), sprite.getV1()).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
        }
    }
}
