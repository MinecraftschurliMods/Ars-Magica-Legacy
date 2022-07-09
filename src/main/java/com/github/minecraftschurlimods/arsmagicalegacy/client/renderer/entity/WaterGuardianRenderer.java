package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.WaterGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WaterGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WaterGuardianRenderer extends MobRenderer<WaterGuardian, WaterGuardianModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/water_guardian.png");

    public WaterGuardianRenderer(EntityRendererProvider.Context context) {
        super(context, new WaterGuardianModel(context.bakeLayer(WaterGuardianModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(WaterGuardian pEntity) {
        return TEXTURE;
    }
}
