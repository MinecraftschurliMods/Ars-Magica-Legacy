package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;

public class ManaCreeperRenderer extends CreeperRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/mana_creeper.png");

    public ManaCreeperRenderer(EntityRendererProvider.Context rendererManagerIn) {
        super(rendererManagerIn);
    }

    @Override
    public ResourceLocation getTextureLocation(final Creeper pEntity) {
        return TEXTURE;
    }
}
