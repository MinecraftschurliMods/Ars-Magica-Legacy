package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public final class AMRenderPipelines {
    private static final Identifier MAGITECH_GOGGLES_ID = ArsMagicaApi.id("magitech_goggles");
    public static final RenderPipeline MAGITECH_GOGGLES = RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
        .withLocation(MAGITECH_GOGGLES_ID)
        .withDepthStencilState(Optional.empty())
        .build();
    public static final RenderType MAGITECH_GOGGLES_TYPE = RenderType.create(MAGITECH_GOGGLES_ID.toString(), RenderSetup.builder(MAGITECH_GOGGLES)
        .sortOnUpload()
        .createRenderSetup());
    private static final Identifier COLOR_WHEEL_ID = ArsMagicaApi.id("color_wheel");
    public static final RenderPipeline COLOR_WHEEL = RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
        .withLocation(COLOR_WHEEL_ID)
        .withVertexShader(ArsMagicaApi.id("color_wheel"))
        .withFragmentShader(ArsMagicaApi.id("color_wheel"))
        .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
        .build();

    private AMRenderPipelines() {}
}
