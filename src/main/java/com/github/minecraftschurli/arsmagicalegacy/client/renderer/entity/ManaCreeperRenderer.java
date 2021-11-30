package com.github.minecraftschurli.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import org.jetbrains.annotations.NotNull;

public class ManaCreeperRenderer extends CreeperRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/mana_creeper.png");

    public ManaCreeperRenderer(final EntityRendererProvider.Context p_173958_) {
        super(p_173958_);
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull final Creeper pEntity) {
        return TEXTURE;
    }
}
