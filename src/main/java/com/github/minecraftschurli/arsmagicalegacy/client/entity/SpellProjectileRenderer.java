package com.github.minecraftschurli.arsmagicalegacy.client.entity;

import com.github.minecraftschurli.arsmagicalegacy.common.entity.ProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class SpellProjectileRenderer extends ThrownItemRenderer<ProjectileEntity> {
    public SpellProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }
}
