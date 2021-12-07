package com.github.minecraftschurli.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurli.arsmagicalegacy.common.entity.Projectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ProjectileRenderer extends ThrownItemRenderer<Projectile> {
    public ProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }
}
