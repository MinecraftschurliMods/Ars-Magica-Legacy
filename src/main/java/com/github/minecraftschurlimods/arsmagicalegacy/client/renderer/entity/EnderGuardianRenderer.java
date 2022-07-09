package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.EnderGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EnderGuardianRenderer extends MobRenderer<EnderGuardian, EnderGuardianModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/ender_guardian.png");

    public EnderGuardianRenderer(EntityRendererProvider.Context context) {
        super(context, new EnderGuardianModel(context.bakeLayer(EnderGuardianModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(EnderGuardian pEntity) {
        return TEXTURE;
    }
}
