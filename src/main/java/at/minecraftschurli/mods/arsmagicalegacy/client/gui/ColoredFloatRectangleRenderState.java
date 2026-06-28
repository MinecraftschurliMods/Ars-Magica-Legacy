package at.minecraftschurli.mods.arsmagicalegacy.client.gui;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import org.joml.Matrix3x2fc;
import org.jspecify.annotations.Nullable;

public record ColoredFloatRectangleRenderState(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2fc pose, float x0, float y0, float x1, float y1, int col1, int col2, @Nullable ScreenRectangle scissorArea, @Nullable ScreenRectangle bounds) implements GuiElementRenderState {
    public ColoredFloatRectangleRenderState(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2fc pose, float x0, float y0, float x1, float y1, int col1, int col2, @Nullable ScreenRectangle scissorArea) {
        this(pipeline, textureSetup, pose, x0, y0, x1, y1, col1, col2, scissorArea, getBounds((int) x0, (int) y0, (int) x1, (int) y1, pose, scissorArea));
    }

    @Override
    public void buildVertices(VertexConsumer vertexConsumer) {
        vertexConsumer.addVertexWith2DPose(pose(), x0(), y0()).setColor(col1());
        vertexConsumer.addVertexWith2DPose(pose(), x0(), y1()).setColor(col2());
        vertexConsumer.addVertexWith2DPose(pose(), x1(), y1()).setColor(col2());
        vertexConsumer.addVertexWith2DPose(pose(), x1(), y0()).setColor(col1());
    }

    @Nullable
    private static ScreenRectangle getBounds(int x0, int y0, int x1, int y1, Matrix3x2fc pose, @Nullable ScreenRectangle scissorArea) {
        ScreenRectangle bounds = new ScreenRectangle(x0, y0, x1 - x0, y1 - y0).transformMaxBounds(pose);
        return scissorArea != null ? scissorArea.intersection(bounds) : bounds;
    }
}
