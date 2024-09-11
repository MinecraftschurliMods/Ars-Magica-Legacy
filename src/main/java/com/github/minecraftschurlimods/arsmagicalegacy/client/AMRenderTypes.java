package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.inscriptiontable.colorpicker.ColorPickerState;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public class AMRenderTypes {
    private static final RenderStateShard.ShaderStateShard COLOR_WHEEL_SHADER = new RenderStateShard.ShaderStateShard(AMShaders::getColorWheelShader);
    private static final RenderStateShard.ShaderStateShard POSITION_TEX_COLOR_SHADER = new RenderStateShard.ShaderStateShard(GameRenderer::getPositionTexColorShader);
    private static final RenderStateShard.TextureStateShard BEAM_CORE_TEXTURE = new RenderStateShard.TextureStateShard(ArsMagicaAPI.resource("textures/misc/beam_core.png"), false, false);
    private static final RenderStateShard.TextureStateShard BEAM_MAIN_TEXTURE = new RenderStateShard.TextureStateShard(ArsMagicaAPI.resource("textures/misc/beam_main.png"), false, false);
    private static final RenderStateShard.TextureStateShard BEAM_GLOW_TEXTURE = new RenderStateShard.TextureStateShard(ArsMagicaAPI.resource("textures/misc/beam_glow.png"), false, false);

    public static final RenderType COLOR_WHEEL = RenderType.create(
            "color_wheel",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
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
    public static final RenderType BEAM_CORE = RenderType.create(
            "beam_core",
            DefaultVertexFormat.POSITION_TEX_COLOR,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setTextureState(BEAM_CORE_TEXTURE)
                    .setShaderState(POSITION_TEX_COLOR_SHADER)
                    .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setLightmapState(RenderStateShard.NO_LIGHTMAP)
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                    .createCompositeState(false));
    public static final RenderType BEAM_MAIN = RenderType.create(
            "beam_main",
            DefaultVertexFormat.POSITION_TEX_COLOR,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setTextureState(BEAM_MAIN_TEXTURE)
                    .setShaderState(POSITION_TEX_COLOR_SHADER)
                    .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setLightmapState(RenderStateShard.NO_LIGHTMAP)
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                    .createCompositeState(false));
    public static final RenderType BEAM_GLOW = RenderType.create("beam_glow",
            DefaultVertexFormat.POSITION_TEX_COLOR,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setTextureState(BEAM_GLOW_TEXTURE)
                    .setShaderState(POSITION_TEX_COLOR_SHADER)
                    .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setLightmapState(RenderStateShard.NO_LIGHTMAP)
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                    .createCompositeState(false));
}
