package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardian;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;

public class IceGuardianModel extends AMGeckolibHeadModel<IceGuardian> {
    public IceGuardianModel() {
        super("ice_guardian");
    }

    @Override
    public void setCustomAnimations(IceGuardian animatable, long instanceId, AnimationState<IceGuardian> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        CoreGeoBone rightArm = getAnimationProcessor().getBone("right_arm");
        rightArm.setHidden(animatable.getArmCount() <= 1);
        CoreGeoBone leftArm = getAnimationProcessor().getBone("left_arm");
        leftArm.setHidden(!animatable.canLaunchArm());
    }
}
