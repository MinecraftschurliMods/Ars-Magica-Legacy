package com.github.minecraftschurli.arsmagicalegacy.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.Random;

public final class RenderUtil {
    private RenderUtil() {}

    public static void gradientLine2d(PoseStack stack, float startX, float startY, float endX, float endY, int zLevel, int color1, int color2, float width) {
        stack.pushPose();
        var pose = stack.last().pose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        RenderSystem.lineWidth(width);
        buf.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
        buf.vertex(pose, startX, startY, zLevel).color(ColorUtil.getRed(color1), ColorUtil.getGreen(color1), ColorUtil.getBlue(color1), 1.0f).normal(0, 1, 0).endVertex();
        buf.vertex(pose, endX, endY, zLevel).color(ColorUtil.getRed(color2), ColorUtil.getGreen(color2), ColorUtil.getBlue(color2), 1.0f).normal(0, 1, 0).endVertex();
        buf.end();
        BufferUploader.end(buf);
        RenderSystem.lineWidth(1);
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        stack.popPose();
    }

    public static void gradientLine2d(PoseStack stack, float startX, float startY, float endX, float endY, int zLevel, int color1, int color2) {
        gradientLine2d(stack, startX, startY, endX, endY, zLevel, color1, color2, 1f);
    }

    public static void line2d(PoseStack stack, float startX, float startY, float endX, float endY, int zLevel, int color, float width) {
        gradientLine2d(stack, startX, startY, endX, endY, zLevel, color, color, width);
    }

    public static void line2d(PoseStack stack, float startX, float startY, float endX, float endY, int zLevel, int color) {
        line2d(stack, startX, startY, endX, endY, zLevel, color, 1f);
    }

    public static void lineThick2d(PoseStack stack, float startX, float startY, float endX, float endY, int zLevel, int color) {
        line2d(stack, startX, startY, endX, endY, zLevel, color, 4f);
    }

    public static void fractalLine2dd(PoseStack stack, double xStart, double yStart, double xEnd, double yEnd, int zLevel, int color, float displace, float fractalDetail) {
        fractalLine2df(stack, (float) xStart, (float) yStart, (float) xEnd, (float) yEnd, zLevel, color, displace, fractalDetail);
    }

    public static void fractalLine2df(PoseStack stack, float xStart, float yStart, float xEnd, float yEnd, int zLevel, int color, float displace, float fractalDetail) {
        if (displace < fractalDetail) {
            line2d(stack, xStart, yStart, xEnd, yEnd, zLevel, color);
        } else {
            int mx = (int) ((xEnd + xStart) / 2);
            int my = (int) ((yEnd + yStart) / 2);
            var random = Optional.ofNullable(Minecraft.getInstance().level).map(Level::getRandom).orElseGet(Random::new);
            mx += (random.nextFloat() - 0.5) * displace;
            my += (random.nextFloat() - 0.5) * displace;
            fractalLine2df(stack, xStart, yStart, mx, my, zLevel, color, displace / 2f, fractalDetail);
            fractalLine2df(stack, xEnd, yEnd, mx, my, zLevel, color, displace / 2f, fractalDetail);
        }
    }

    public static void drawBox(PoseStack stack, float minX, float minY, float maxX, float maxY, float zLevel, float minU, float minV, float maxU, float maxV) {
        final Matrix4f pMatrix = stack.last().pose();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(pMatrix, minX, minY + maxY, zLevel).uv(minU, maxV).endVertex();
        bufferbuilder.vertex(pMatrix, minX + maxX, minY + maxY, zLevel).uv(maxU, maxV).endVertex();
        bufferbuilder.vertex(pMatrix, minX + maxX, minY, zLevel).uv(maxU, minV).endVertex();
        bufferbuilder.vertex(pMatrix, minX, minY, zLevel).uv(minU, minV).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
    }
}
