package com.github.minecraftschurli.arsmagicalegacy.client.render;

import com.github.minecraftschurli.arsmagicalegacy.common.entity.ProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ProjectileRenderer extends ThrownItemRenderer<ProjectileEntity> {
    public ProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }
}
