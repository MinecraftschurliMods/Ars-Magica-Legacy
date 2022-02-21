package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class SpellRenderType extends RenderType {
    public static ShaderInstance spellShader;
    public static final Function<ResourceLocation, RenderType> SPELL = Util.memoize(SpellRenderType::spellShader);
    private static final RenderStateShard.ShaderStateShard SPELL_SHADER_STATE = new RenderStateShard.ShaderStateShard(() -> spellShader);

    private SpellRenderType(String s, VertexFormat v, VertexFormat.Mode m, int i, boolean b1, boolean b2, Runnable r1, Runnable r2) {
        super(s, v, m, i, b1, b2, r1, r2);
        throw new IllegalStateException("This class is not meant to be constructed!");
    }

    private static RenderType spellShader(ResourceLocation rl) {
        RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(SPELL_SHADER_STATE)
                .setTextureState(new RenderStateShard.TextureStateShard(rl, false, false))
                .setTransparencyState(NO_TRANSPARENCY)
                .setLightmapState(NO_LIGHTMAP)
                .setOverlayState(NO_OVERLAY)
                .createCompositeState(true);
        return create("spell_shader", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false, state);
    }
}
