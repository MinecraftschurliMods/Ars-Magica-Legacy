package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.LifeGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.LifeGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LifeGuardianRenderer extends MobRenderer<LifeGuardian, LifeGuardianModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/life_guardian.png");

    public LifeGuardianRenderer(EntityRendererProvider.Context context) {
        super(context, new LifeGuardianModel(context.bakeLayer(LifeGuardianModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(final @NotNull LifeGuardian pEntity) {
        return TEXTURE;
    }
}
