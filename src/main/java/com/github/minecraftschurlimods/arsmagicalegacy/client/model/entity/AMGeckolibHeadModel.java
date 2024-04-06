package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.data.EntityModelData;

public class AMGeckolibHeadModel<T extends GeoEntity> extends AMGeckolibModel<T> {
    public AMGeckolibHeadModel(String name) {
        super(name);
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        EntityModelData extraData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        head.setRotX((float) (extraData.headPitch() * Math.PI / 180D));
        head.setRotY((float) (extraData.netHeadYaw() * Math.PI / 180D));
    }
}
