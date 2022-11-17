package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.ThrownRockModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ThrownRock;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ThrownRockRenderer extends NonLiving3DModelRenderer<ThrownRock, ThrownRockModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/earth_guardian.png");

    public ThrownRockRenderer(EntityRendererProvider.Context context) {
        super(context, new ThrownRockModel(context.bakeLayer(ThrownRockModel.LAYER_LOCATION)));
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownRock pEntity) {
        return TEXTURE;
    }
}
