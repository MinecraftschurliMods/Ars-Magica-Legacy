package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable.colorpicker.ColorPickerState;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public final class AMRenderTypes {
    public static final RenderStateShard.ShaderStateShard COLOR_WHEEL_SHADER = new RenderStateShard.ShaderStateShard(AMShaders::getColorWheelShader);
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
                        ColorPickerState state = ColorPickerState.get();
                        AMShaders.setUniform("center", state.getCenterX(), state.getCenterY());
                        AMShaders.setUniform("radius", state.getRadius());
                        AMShaders.setUniform("brightness", state.getBrightness());
                    }, () -> {}))
                    .createCompositeState(false)
    );
}
