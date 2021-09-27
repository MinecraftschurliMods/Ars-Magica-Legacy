package com.github.minecraftschurli.arsmagicalegacy.client.render;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.client.model.WaterGuardianModel;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.WaterGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WaterGuardianRenderer extends MobRenderer<WaterGuardian, WaterGuardianModel<WaterGuardian>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/water_guardian.png");
    
    public WaterGuardianRenderer(EntityRendererProvider.Context rendererManagerIn) {
        super(rendererManagerIn, new WaterGuardianModel<>(rendererManagerIn.bakeLayer(WaterGuardianModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(final WaterGuardian pEntity) {
        return TEXTURE;
    }
}
