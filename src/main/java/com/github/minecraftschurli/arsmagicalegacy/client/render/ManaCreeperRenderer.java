package com.github.minecraftschurli.arsmagicalegacy.client.render;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.ManaCreeper;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;

public class ManaCreeperRenderer extends CreeperRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/mana_creeper.png");

    public ManaCreeperRenderer(final EntityRendererProvider.Context p_173958_) {
        super(p_173958_);
    }

    public ResourceLocation getTextureLocation(final ManaCreeper pEntity) {
        return TEXTURE;
    }
}
