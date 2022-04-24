package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.AirGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AirGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AirGuardianRenderer extends MobRenderer<AirGuardian, AirGuardianModel<AirGuardian>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/air_guardian.png");

    public AirGuardianRenderer(EntityRendererProvider.Context rendererManagerIn) {
        super(rendererManagerIn, new AirGuardianModel<>(rendererManagerIn.bakeLayer(AirGuardianModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(final @NotNull AirGuardian pEntity) {
        return TEXTURE;
    }
}
