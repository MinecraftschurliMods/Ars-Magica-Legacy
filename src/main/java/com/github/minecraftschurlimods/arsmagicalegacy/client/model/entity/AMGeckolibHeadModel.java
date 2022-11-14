package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class AMGeckolibHeadModel<T extends IAnimatable> extends AMGeckolibModel<T> {
    public AMGeckolibHeadModel(String name) {
        super(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        if (customPredicate != null) {
            IBone head = getAnimationProcessor().getBone("head");
            EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationX((float) (extraData.headPitch * Math.PI / 180D));
            head.setRotationY((float) (extraData.netHeadYaw * Math.PI / 180D));
        }
    }
}
