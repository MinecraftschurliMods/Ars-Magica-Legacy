package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardian;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class IceGuardianModel extends AMGeckolibHeadModel<IceGuardian> {
    public IceGuardianModel() {
        super("ice_guardian");
    }

    @Override
    public void setCustomAnimations(IceGuardian animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        IBone rightArm = getAnimationProcessor().getBone("right_arm");
        rightArm.setHidden(animatable.getArmCount() <= 1);
        IBone leftArm = getAnimationProcessor().getBone("left_arm");
        leftArm.setHidden(!animatable.canLaunchArm());
    }
}
