package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.Identifier;

public class ManaCreeperRenderer extends CreeperRenderer {
    public static final Identifier TEXTURE = ArsMagicaApi.id("textures/entity/mana_creeper.png");

    public ManaCreeperRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Identifier getTextureLocation(CreeperRenderState state) {
        return TEXTURE;
    }
}
