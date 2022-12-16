package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AMGeckolibRenderer<T extends LivingEntity & GeoAnimatable> extends GeoEntityRenderer<T> {
    public AMGeckolibRenderer(EntityRendererProvider.Context context, GeoModel<T> model) {
        super(context, model);
    }
}
