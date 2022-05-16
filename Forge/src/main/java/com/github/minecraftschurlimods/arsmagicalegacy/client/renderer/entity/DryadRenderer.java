package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.DryadModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.Dryad;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DryadRenderer extends HumanoidMobRenderer<Dryad, DryadModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/dryad.png");

    public DryadRenderer(final EntityRendererProvider.Context context) {
        super(context, new DryadModel(context.bakeLayer(DryadModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(final Dryad pEntity) {
        return TEXTURE;
    }
}
