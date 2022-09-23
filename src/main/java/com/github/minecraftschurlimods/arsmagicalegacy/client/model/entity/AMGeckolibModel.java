package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AMGeckolibModel<T extends IAnimatable> extends AnimatedGeoModel<T> {
    private final ResourceLocation MODEL_LOCATION;
    private final ResourceLocation TEXTURE_LOCATION;
    private final ResourceLocation ANIMATION_LOCATION;

    public AMGeckolibModel(String name) {
        MODEL_LOCATION = new ResourceLocation(ArsMagicaAPI.MOD_ID, "geo/" + name + ".geo.json");
        TEXTURE_LOCATION = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/entity/" + name + ".png");
        ANIMATION_LOCATION = new ResourceLocation(ArsMagicaAPI.MOD_ID, "animations/" + name + ".animation.json");
    }

    @Override
    public ResourceLocation getModelLocation(T t) {
        return MODEL_LOCATION;
    }

    @Override
    public ResourceLocation getTextureLocation(T t) {
        return TEXTURE_LOCATION;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(T t) {
        return ANIMATION_LOCATION;
    }
}
