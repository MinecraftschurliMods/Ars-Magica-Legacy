package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public class AMRenderTypes {
    public static final RenderStateShard.ShaderStateShard COLOR_WHEEL_SHADER = new RenderStateShard.ShaderStateShard(AMShaders::getColorWheelShader);
    public static float colorWheelCenterX, colorWheelCenterY, colorWheelRadius, colorWheelBrightness; // TODO: This is incredibly hacky, find a better way

    public static void setColorWheel(float cX, float cY, float radius, float brightness) {
        colorWheelCenterX = cX;
        colorWheelCenterY = cY;
        colorWheelRadius = radius;
        colorWheelBrightness = brightness;
    }

    public static final RenderType COLOR_WHEEL = RenderType.create(
            "color_wheel",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            RenderType.CompositeState
                    .builder()
                    .setShaderState(COLOR_WHEEL_SHADER)
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                    .setLayeringState(new RenderStateShard.LayeringStateShard("set_uniforms", () -> {
                        AMShaders.setUniform("center", colorWheelCenterX, colorWheelCenterY);
                        AMShaders.setUniform("radius", colorWheelRadius);
                        AMShaders.setUniform("brightness", colorWheelBrightness);
                    }, () -> {}))
                    .createCompositeState(false)
    );
}
