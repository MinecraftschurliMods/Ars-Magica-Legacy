package com.github.minecraftschurlimods.arsmagicalegacy.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import org.joml.Matrix4f;

import java.util.Optional;

public final class RenderUtil {
    private RenderUtil() {}

    /**
     * Draws a gradient line between the given coordinates.
     *
     * @param stack  The pose stack to use.
     * @param startX The start x coordinate.
     * @param startY The start y coordinate.
     * @param endX   The end x coordinate.
     * @param endY   The end y coordinate.
     * @param zLevel The z level to draw on.
     * @param color1 The first color, used near the start of the line.
     * @param color2 The second color, used near the end of the line.
     * @param width  The width of the line.
     */
    public static void gradientLine2d(PoseStack stack, float startX, float startY, float endX, float endY, int zLevel, int color1, int color2, float width) {
        if (ColorUtil.getAlpha(color1) == 0) {
            color1 = 0xFF000000 | color1;
        }
        if (ColorUtil.getAlpha(color2) == 0) {
            color2 = 0xFF000000 | color2;
        }
        stack.pushPose();
        Matrix4f pose = stack.last().pose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        RenderSystem.lineWidth(width);
        buf.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
        buf.vertex(pose, startX, startY, zLevel).color(ColorUtil.getRed(color1), ColorUtil.getGreen(color1), ColorUtil.getBlue(color1), ColorUtil.getAlpha(color1)).normal(1, 1, 0).endVertex();
        buf.vertex(pose, endX, endY, zLevel).color(ColorUtil.getRed(color2), ColorUtil.getGreen(color2), ColorUtil.getBlue(color2), ColorUtil.getAlpha(color2)).normal(1, 1, 0).endVertex();
        BufferUploader.drawWithShader(buf.end());
        RenderSystem.lineWidth(1);
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        stack.popPose();
    }

    /**
     * Draws a gradient line between the given coordinates, with a width of 1px.
     *
     * @param stack  The pose stack to use.
     * @param startX The start x coordinate.
     * @param startY The start y coordinate.
     * @param endX   The end x coordinate.
     * @param endY   The end y coordinate.
     * @param zLevel The z level to draw on.
     * @param color1 The first color, used near the start of the line.
     * @param color2 The second color, used near the end of the line.
     */
    public static void gradientLine2d(PoseStack stack, float startX, float startY, float endX, float endY, int zLevel, int color1, int color2) {
        gradientLine2d(stack, startX, startY, endX, endY, zLevel, color1, color2, 1f);
    }

    /**
     * Draws a line between the given coordinates.
     *
     * @param stack  The pose stack to use.
     * @param startX The start x coordinate.
     * @param startY The start y coordinate.
     * @param endX   The end x coordinate.
     * @param endY   The end y coordinate.
     * @param zLevel The z level to draw on.
     * @param color  The color of the line.
     * @param width  The width of the line.
     */
    public static void line2d(PoseStack stack, float startX, float startY, float endX, float endY, int zLevel, int color, float width) {
        gradientLine2d(stack, startX, startY, endX, endY, zLevel, color, color, width);
    }

    /**
     * Draws a line between the given coordinates, with a width of 1px.
     *
     * @param stack  The pose stack to use.
     * @param startX The start x coordinate.
     * @param startY The start y coordinate.
     * @param endX   The end x coordinate.
     * @param endY   The end y coordinate.
     * @param zLevel The z level to draw on.
     * @param color  The color of the line.
     */
    public static void line2d(PoseStack stack, float startX, float startY, float endX, float endY, int zLevel, int color) {
        line2d(stack, startX, startY, endX, endY, zLevel, color, 1f);
    }

    /**
     * Draws a line between the given coordinates, with a width of 4px.
     *
     * @param stack  The pose stack to use.
     * @param startX The start x coordinate.
     * @param startY The start y coordinate.
     * @param endX   The end x coordinate.
     * @param endY   The end y coordinate.
     * @param zLevel The z level to draw on.
     * @param color  The color of the line.
     */
    public static void lineThick2d(PoseStack stack, float startX, float startY, float endX, float endY, int zLevel, int color) {
        line2d(stack, startX, startY, endX, endY, zLevel, color, 4f);
    }

    /**
     * Draws a fractal line.
     *
     * @param stack         The pose stack to use.
     * @param startX        The start x coordinate.
     * @param startY        The start y coordinate.
     * @param endX          The end x coordinate.
     * @param endY          The end y coordinate.
     * @param zLevel        The z level to draw on.
     * @param color         The color of the line.
     * @param displace      The displace value to use.
     * @param fractalDetail The fractal detail to use.
     */
    public static void fractalLine2dd(PoseStack stack, double startX, double startY, double endX, double endY, int zLevel, int color, float displace, float fractalDetail) {
        fractalLine2df(stack, (float) startX, (float) startY, (float) endX, (float) endY, zLevel, color, displace, fractalDetail);
    }

    /**
     * Draws a fractal line.
     *
     * @param stack         The pose stack to use.
     * @param startX        The start x coordinate.
     * @param startY        The start y coordinate.
     * @param endX          The end x coordinate.
     * @param endY          The end y coordinate.
     * @param zLevel        The z level to draw on.
     * @param color         The color of the line.
     * @param displace      The displace value to use.
     * @param fractalDetail The fractal detail to use.
     */
    public static void fractalLine2df(PoseStack stack, float startX, float startY, float endX, float endY, int zLevel, int color, float displace, float fractalDetail) {
        if (displace < fractalDetail) {
            line2d(stack, startX, startY, endX, endY, zLevel, color);
        } else {
            int mx = (int) ((endX + startX) / 2);
            int my = (int) ((endY + startY) / 2);
            RandomSource random = Optional.ofNullable(Minecraft.getInstance().level).map(Level::getRandom).orElseGet(RandomSource::create);
            mx += (random.nextFloat() - 0.5) * displace;
            my += (random.nextFloat() - 0.5) * displace;
            fractalLine2df(stack, startX, startY, mx, my, zLevel, color, displace / 2f, fractalDetail);
            fractalLine2df(stack, endX, endY, mx, my, zLevel, color, displace / 2f, fractalDetail);
        }
    }

    /**
     * Draws a box at the given position.
     *
     * @param stack  The pose stack to use.
     * @param startX The start x coordinate.
     * @param startY The start y coordinate.
     * @param endX   The end x coordinate.
     * @param endY   The end y coordinate.
     * @param zLevel The z level to draw on.
     * @param minU   The min u texture coordinate.
     * @param minV   The min v texture coordinate.
     * @param maxU   The max u texture coordinate.
     * @param maxV   The max v texture coordinate.
     */
    public static void drawBox(PoseStack stack, float startX, float startY, float endX, float endY, float zLevel, float minU, float minV, float maxU, float maxV) {
        Matrix4f pMatrix = stack.last().pose();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(pMatrix, startX, startY + endY, zLevel).uv(minU, maxV).endVertex();
        bufferbuilder.vertex(pMatrix, startX + endX, startY + endY, zLevel).uv(maxU, maxV).endVertex();
        bufferbuilder.vertex(pMatrix, startX + endX, startY, zLevel).uv(maxU, minV).endVertex();
        bufferbuilder.vertex(pMatrix, startX, startY, zLevel).uv(minU, minV).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
    }
}
