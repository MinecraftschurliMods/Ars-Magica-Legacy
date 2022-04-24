package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity.EnderGuardianModel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class EnderGuardianRenderer extends MobRenderer<EnderGuardian, EnderGuardianModel<EnderGuardian>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/ender_guardian.png");

    public EnderGuardianRenderer(EntityRendererProvider.Context rendererManagerIn) {
        super(rendererManagerIn, new EnderGuardianModel<>(rendererManagerIn.bakeLayer(EnderGuardianModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(final @NotNull EnderGuardian pEntity) {
        return TEXTURE;
    }
}
