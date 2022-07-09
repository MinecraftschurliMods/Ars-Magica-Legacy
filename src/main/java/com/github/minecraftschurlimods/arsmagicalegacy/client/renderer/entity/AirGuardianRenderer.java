package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.AirGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AirGuardianRenderer extends MobRenderer<AirGuardian, AirGuardianModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/air_guardian.png");

    public AirGuardianRenderer(EntityRendererProvider.Context context) {
        super(context, new AirGuardianModel(context.bakeLayer(AirGuardianModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(AirGuardian pEntity) {
        return TEXTURE;
    }
}
