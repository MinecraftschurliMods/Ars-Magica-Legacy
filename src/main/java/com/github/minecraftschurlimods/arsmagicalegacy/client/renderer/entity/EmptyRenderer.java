package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class EmptyRenderer extends EntityRenderer<Entity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("missingno");

    public EmptyRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity pEntity) {
        return TEXTURE;
    }
}
