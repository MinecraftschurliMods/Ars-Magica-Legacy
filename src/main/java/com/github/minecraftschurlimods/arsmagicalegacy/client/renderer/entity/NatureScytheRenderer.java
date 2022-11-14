package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.NatureScytheModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureScythe;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class NatureScytheRenderer extends NonLiving3DModelRenderer<NatureScythe, NatureScytheModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/nature_guardian.png");

    public NatureScytheRenderer(EntityRendererProvider.Context context) {
        super(context, new NatureScytheModel(context.bakeLayer(NatureScytheModel.LAYER_LOCATION)));
    }

    @Override
    public ResourceLocation getTextureLocation(NatureScythe pEntity) {
        return TEXTURE;
    }
}
