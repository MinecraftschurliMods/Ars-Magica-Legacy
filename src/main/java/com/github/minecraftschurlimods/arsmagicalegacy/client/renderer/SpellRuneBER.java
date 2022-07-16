package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer;

import com.github.minecraftschurlimods.arsmagicalegacy.common.block.spellrune.SpellRuneBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public class SpellRuneBER implements BlockEntityRenderer<SpellRuneBlockEntity> {

    private final BlockRenderDispatcher dispatcher;

    public SpellRuneBER(BlockEntityRendererProvider.Context pContext) {
        dispatcher = pContext.getBlockRenderDispatcher();
    }

    @Override
    public void render(SpellRuneBlockEntity blockEntity, float pPartialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        if (level == null) return;
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        if (!player.isCreative() && !player.hasEffect(AMMobEffects.TRUE_SIGHT.get())) return;
        BlockState blockState = blockEntity.getBlockState();
        BlockPos blockPos = blockEntity.getBlockPos();
        ModelData modelData = blockEntity.getModelData();
        long seed = blockState.getSeed(blockPos);
        BakedModel blockModel = dispatcher.getBlockModel(blockState);
        RandomSource random = level.random;
        RenderType renderType = ItemBlockRenderTypes.getRenderType(blockState, false);
        VertexConsumer buffer = bufferSource.getBuffer(renderType);
        dispatcher.getModelRenderer().tesselateBlock(level, blockModel, blockState, blockPos, poseStack, buffer, false, random, seed, packedOverlay, modelData, renderType);
    }
}
