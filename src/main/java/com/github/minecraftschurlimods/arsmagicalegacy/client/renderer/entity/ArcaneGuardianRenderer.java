package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.ArcaneGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ArcaneGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ArcaneGuardianRenderer extends MobRenderer<ArcaneGuardian, ArcaneGuardianModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/arcane_guardian.png");

    public ArcaneGuardianRenderer(EntityRendererProvider.Context context) {
        super(context, new ArcaneGuardianModel(context.bakeLayer(ArcaneGuardianModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(final @NotNull ArcaneGuardian pEntity) {
        return TEXTURE;
    }
}
