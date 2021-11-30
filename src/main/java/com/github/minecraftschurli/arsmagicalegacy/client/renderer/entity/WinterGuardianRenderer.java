package com.github.minecraftschurli.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.client.model.entity.WinterGuardianModel;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.WinterGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WinterGuardianRenderer extends MobRenderer<WinterGuardian, WinterGuardianModel<WinterGuardian>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/winter_guardian.png");

    public WinterGuardianRenderer(EntityRendererProvider.Context rendererManagerIn) {
        super(rendererManagerIn, new WinterGuardianModel<>(rendererManagerIn.bakeLayer(WinterGuardianModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(final @NotNull WinterGuardian pEntity) {
        return TEXTURE;
    }
}
