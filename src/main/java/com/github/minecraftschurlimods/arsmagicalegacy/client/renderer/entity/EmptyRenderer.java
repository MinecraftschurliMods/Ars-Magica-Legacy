package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EmptyRenderer extends EntityRenderer<Entity> {
    public EmptyRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(final @NotNull Entity pEntity) {
        return new ResourceLocation("missingno");
    }
}
