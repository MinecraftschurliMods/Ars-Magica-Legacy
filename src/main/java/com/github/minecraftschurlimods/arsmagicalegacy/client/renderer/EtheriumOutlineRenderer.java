package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer;

import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumConsumer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.client.AMRenderTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.ColorUtil;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;
import org.lwjgl.opengl.GL32;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class EtheriumOutlineRenderer {
    private static final int CONSUMER_COLOR = 0xff0000;
    private static final Map<ChunkPos, Collection<BlockPos>> POSITIONS = new ConcurrentHashMap<>();

    public static void updatePositions(ChunkPos chunkPos, Collection<BlockPos> positions) {
        POSITIONS.put(chunkPos, positions);
    }

    public static void render(Player player, Level level, PoseStack poseStack) {
        if (POSITIONS.isEmpty()) return;
        if (!player.getAttributes().hasAttribute(AMAttributes.MAGIC_VISION.get()) || Objects.requireNonNull(player.getAttribute(AMAttributes.MAGIC_VISION.get())).getValue() <= 0)
            return;
        ChunkPos playerPos = player.chunkPosition();
        Minecraft minecraft = Minecraft.getInstance();
        int renderDistance = minecraft.options.getEffectiveRenderDistance();
        Vec3 camera = minecraft.gameRenderer.getMainCamera().getPosition();
        poseStack.pushPose();
        poseStack.translate(-camera.x(), -camera.y(), -camera.z());
        for (ChunkPos chunkPos : POSITIONS.keySet()) {
            if (playerPos.getChessboardDistance(chunkPos) >= renderDistance) continue;
            for (BlockPos pos : POSITIONS.get(chunkPos)) {
                BlockState state = level.getBlockState(pos);
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof IEtheriumProvider provider) {
                    renderOutline(state, pos, poseStack, provider.getType().getColor());
                } else if (blockEntity instanceof IEtheriumConsumer) {
                    renderOutline(state, pos, poseStack, CONSUMER_COLOR);
                }
            }
        }
        poseStack.popPose();
    }

    private static void renderOutline(BlockState state, BlockPos pos, PoseStack poseStack, int color) {
        poseStack.pushPose();
        poseStack.translate(pos.getX(), pos.getY(), pos.getZ());
        Minecraft minecraft = Minecraft.getInstance();

        //enable stencil
        MultiBufferSource bufferSource = minecraft.renderBuffers().bufferSource();
        GL32.glEnable(GL32.GL_STENCIL_TEST);
        GL32.glStencilOp(GL32.GL_KEEP, GL32.GL_KEEP, GL32.GL_REPLACE);
        //set stencil func and mask
        GL32.glStencilFunc(GL32.GL_ALWAYS, 1, 0xFF);
        GL32.glStencilMask(0xFF);
        //render block
        minecraft.getBlockRenderer().renderSingleBlock(state, poseStack, bufferSource, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, null);
        //set stencil func and mask
        GL32.glStencilFunc(GL32.GL_NOTEQUAL, 1, 0xFF);
        GL32.glStencilMask(0x00);
        //render scaled block
        poseStack.pushPose();
        poseStack.scale(1.1f, 1.1f, 1.1f);
        RenderSystem.setShaderColor(ColorUtil.getRed(color), ColorUtil.getGreen(color), ColorUtil.getBlue(color), 1f);
        minecraft.getBlockRenderer().renderSingleBlock(state, poseStack, bufferSource, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, AMRenderTypes.SINGLE_COLOR);
        poseStack.popPose();
        //disable stencil
        GL32.glDisable(GL32.GL_STENCIL_TEST);

        poseStack.popPose();
    }
}
