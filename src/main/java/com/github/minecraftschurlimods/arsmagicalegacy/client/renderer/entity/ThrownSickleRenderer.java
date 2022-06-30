package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.ThrownSickleModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ThrownSickle;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ThrownSickleRenderer extends NonLiving3DModelRenderer<ThrownSickle, ThrownSickleModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/nature_guardian.png");

    public ThrownSickleRenderer(EntityRendererProvider.Context context) {
        super(context, new ThrownSickleModel(context.bakeLayer(ThrownSickleModel.LAYER_LOCATION)));
    }

    @Override
    public ResourceLocation getTextureLocation(final @NotNull ThrownSickle pEntity) {
        return TEXTURE;
    }
}
