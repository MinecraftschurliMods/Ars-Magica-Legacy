package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.block;

import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.SpellRuneBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class SpellRuneRenderer implements BlockEntityRenderer<SpellRuneBlockEntity, SpellRuneRenderer.State> {
    private final BlockModelResolver blockModelResolver;

    public SpellRuneRenderer(BlockEntityRendererProvider.Context context) {
        blockModelResolver = context.blockModelResolver();
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(SpellRuneBlockEntity blockEntity, State state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.disabled = false;
        Level level = blockEntity.getLevel();
        Player player = Objects.requireNonNull(AMClientUtil.player());
        if (level != null && (player.isCreative() || player.hasEffect(AMMobEffects.TRUE_SIGHT))) {
            blockModelResolver.update(state.blockModel, blockEntity.getBlockState(), BlockDisplayContext.create());
        } else {
            state.disabled = true;
        }
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (!state.disabled) {
            state.blockModel.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        }
    }

    public static class State extends BlockEntityRenderState {
        public boolean disabled = false;
        public BlockModelRenderState blockModel = new BlockModelRenderState();
    }
}
