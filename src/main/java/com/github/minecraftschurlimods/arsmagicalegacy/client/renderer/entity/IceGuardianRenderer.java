package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.IceGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class IceGuardianRenderer extends MobRenderer<IceGuardian, IceGuardianModel<IceGuardian>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/ice_guardian.png");

    public IceGuardianRenderer(EntityRendererProvider.Context rendererManagerIn) {
        super(rendererManagerIn, new IceGuardianModel<>(rendererManagerIn.bakeLayer(IceGuardianModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(final IceGuardian pEntity) {
        return TEXTURE;
    }
}
