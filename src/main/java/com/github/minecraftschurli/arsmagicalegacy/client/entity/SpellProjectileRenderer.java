package com.github.minecraftschurli.arsmagicalegacy.client.entity;

import com.github.minecraftschurli.arsmagicalegacy.common.entity.SpellProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class SpellProjectileRenderer extends ThrownItemRenderer<SpellProjectile> {
    public SpellProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }
}
