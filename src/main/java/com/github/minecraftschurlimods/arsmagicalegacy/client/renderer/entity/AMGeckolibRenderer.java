package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AMGeckolibRenderer<T extends LivingEntity & IAnimatable> extends GeoEntityRenderer<T> {
    public AMGeckolibRenderer(EntityRendererProvider.Context context, AnimatedGeoModel<T> model) {
        super(context, model);
    }
}
