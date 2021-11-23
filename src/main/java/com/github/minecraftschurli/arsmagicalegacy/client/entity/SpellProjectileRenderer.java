package com.github.minecraftschurli.arsmagicalegacy.client.entity;

import com.github.minecraftschurli.arsmagicalegacy.common.entity.SpellProjectile;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SpellProjectileRenderer extends EntityRenderer<SpellProjectile> {
    public SpellProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(SpellProjectile pEntity) {
        return new ResourceLocation(pEntity.getIcon());
    }
}
