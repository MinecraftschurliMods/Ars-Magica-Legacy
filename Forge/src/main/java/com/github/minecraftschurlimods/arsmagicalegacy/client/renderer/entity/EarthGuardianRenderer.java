package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.EarthGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class EarthGuardianRenderer extends MobRenderer<EarthGuardian, EarthGuardianModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/earth_guardian.png");

    public EarthGuardianRenderer(EntityRendererProvider.Context context) {
        super(context, new EarthGuardianModel(context.bakeLayer(EarthGuardianModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(final @NotNull EarthGuardian pEntity) {
        return TEXTURE;
    }
}
