package com.github.minecraftschurli.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurli.arsmagicalegacy.common.entity.ZoneEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ZoneRenderer extends ThrownItemRenderer<ZoneEntity> {
    public ZoneRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }
}
