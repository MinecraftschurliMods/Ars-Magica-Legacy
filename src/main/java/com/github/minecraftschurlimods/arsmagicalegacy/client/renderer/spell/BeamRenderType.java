package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

/**
 * Taken and adapted from <a href="https://github.com/Direwolf20-MC/MiningGadgets/blob/1.18/src/main/java/com/direwolf20/mininggadgets/client/renderer/RenderMiningLaser.java">Direwolf20's Mining Gadgets mod</a>.
 */
public class BeamRenderType extends RenderType {
    private static final ResourceLocation CORE_TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/misc/beam_core.png");
    public static final RenderType CORE = create("beam_core",
            DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, false,
            RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(CORE_TEXTURE, false, false))
                    .setShaderState(RenderStateShard.ShaderStateShard.POSITION_COLOR_TEX_SHADER)
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(NO_DEPTH_TEST)
                    .setCullState(NO_CULL)
                    .setLightmapState(NO_LIGHTMAP)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false));
    private static final ResourceLocation MAIN_TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/misc/beam_main.png");
    public static final RenderType MAIN = create("beam_main",
            DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, false,
            RenderType.CompositeState.builder().setTextureState(new TextureStateShard(MAIN_TEXTURE, false, false))
                    .setShaderState(ShaderStateShard.POSITION_COLOR_TEX_SHADER)
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(NO_DEPTH_TEST)
                    .setCullState(NO_CULL)
                    .setLightmapState(NO_LIGHTMAP)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false));
    private static final ResourceLocation GLOW_TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/misc/beam_glow.png");
    public static final RenderType GLOW = create("beam_glow",
            DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, false,
            RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(GLOW_TEXTURE, false, false))
                    .setShaderState(RenderStateShard.ShaderStateShard.POSITION_COLOR_TEX_SHADER)
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(NO_DEPTH_TEST)
                    .setCullState(NO_CULL)
                    .setLightmapState(NO_LIGHTMAP)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false));

    public BeamRenderType(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }
}
