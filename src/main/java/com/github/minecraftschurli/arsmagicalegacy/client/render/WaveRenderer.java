package com.github.minecraftschurli.arsmagicalegacy.client.render;

import com.github.minecraftschurli.arsmagicalegacy.common.entity.WaveEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class WaveRenderer extends ThrownItemRenderer<WaveEntity> {
    public WaveRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }
}
