package com.github.minecraftschurli.arsmagicalegacy.client.render;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.client.model.NatureGuardianModel;
import com.github.minecraftschurli.arsmagicalegacy.common.entity.NatureGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class NatureGuardianRenderer extends MobRenderer<NatureGuardian, NatureGuardianModel<NatureGuardian>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/nature_guardian.png");

    public NatureGuardianRenderer(EntityRendererProvider.Context rendererManagerIn) {
        super(rendererManagerIn, new NatureGuardianModel<>(rendererManagerIn.bakeLayer(NatureGuardianModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(final @NotNull NatureGuardian pEntity) {
        return TEXTURE;
    }
}
