package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.NatureScytheModel;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.WintersGraspModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureScythe;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WintersGrasp;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WintersGraspRenderer extends NonLiving3DModelRenderer<WintersGrasp, WintersGraspModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/ice_guardian.png");

    public WintersGraspRenderer(EntityRendererProvider.Context context) {
        super(context, new WintersGraspModel(context.bakeLayer(WintersGraspModel.LAYER_LOCATION)));
    }

    @Override
    public ResourceLocation getTextureLocation(final @NotNull WintersGrasp pEntity) {
        return TEXTURE;
    }
}
