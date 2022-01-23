package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.NatureGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NatureGuardianRenderer extends MobRenderer<NatureGuardian, NatureGuardianModel<NatureGuardian>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/nature_guardian.png");

    public NatureGuardianRenderer(EntityRendererProvider.Context rendererManagerIn) {
        super(rendererManagerIn, new NatureGuardianModel<>(rendererManagerIn.bakeLayer(NatureGuardianModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(final NatureGuardian pEntity) {
        return TEXTURE;
    }
}
