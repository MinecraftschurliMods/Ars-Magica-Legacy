package at.minecraftschurli.mods.arsmagicalegacy.client.renderer.block;

import at.minecraftschurli.mods.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.client.MagitechGogglesOverlayRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public abstract class AbstractEtheriumBlockEntityRenderer<T extends BlockEntity, S extends AbstractEtheriumBlockEntityRenderer.RenderState> implements BlockEntityRenderer<T, S> {
    @Override
    public void extractRenderState(T blockEntity, S state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.gogglesOverlay.clear();
        if (ArsMagicaClientApi.shouldRenderMagitechGogglesOutline()) {
            state.gogglesOverlay.extract(blockEntity);
        }
    }

    @Override
    public void submit(S state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        state.gogglesOverlay.submit(poseStack, submitNodeCollector);
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

    public static class RenderState extends BlockEntityRenderState {
        public MagitechGogglesOverlayRenderState gogglesOverlay = ArsMagicaClientApi.createMagitechGogglesOutlineRenderState();
    }
}
