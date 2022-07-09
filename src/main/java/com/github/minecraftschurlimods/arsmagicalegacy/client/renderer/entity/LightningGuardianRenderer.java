package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.LightningGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LightningGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LightningGuardianRenderer extends MobRenderer<LightningGuardian, LightningGuardianModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/lightning_guardian.png");

    public LightningGuardianRenderer(EntityRendererProvider.Context context) {
        super(context, new LightningGuardianModel(context.bakeLayer(LightningGuardianModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(LightningGuardian pEntity) {
        return TEXTURE;
    }
}
