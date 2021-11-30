package com.github.minecraftschurli.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurli.arsmagicalegacy.common.entity.WallEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class WallRenderer extends ThrownItemRenderer<WallEntity> {
    public WallRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }
}
