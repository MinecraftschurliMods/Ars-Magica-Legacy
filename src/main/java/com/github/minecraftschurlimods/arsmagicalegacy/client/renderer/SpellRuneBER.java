package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer;

import com.github.minecraftschurlimods.arsmagicalegacy.common.block.spellrune.SpellRuneBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SpellRuneBER implements BlockEntityRenderer<SpellRuneBlockEntity> {

    private final BlockRenderDispatcher dispatcher;

    public SpellRuneBER(BlockEntityRendererProvider.Context pContext) {
        dispatcher = pContext.getBlockRenderDispatcher();
    }

    @Override
    public void render(SpellRuneBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        Level level = pBlockEntity.getLevel();
        if (level == null) return;
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        if (!player.isCreative() && !player.hasEffect(AMMobEffects.TRUE_SIGHT.get())) return;
        BlockState blockState = pBlockEntity.getBlockState();
        BlockPos blockPos = pBlockEntity.getBlockPos();
        dispatcher.renderBatched(blockState, blockPos, level, pPoseStack, pBufferSource.getBuffer(ItemBlockRenderTypes.getRenderType(blockState, false)), false, level.random, pBlockEntity.getModelData());
    }
}
