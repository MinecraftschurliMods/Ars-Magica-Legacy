package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardian;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class IceGuardianModel extends AMGeckolibHeadModel<IceGuardian> {
    public IceGuardianModel() {
        super("ice_guardian");
    }

    @Override
    public void setLivingAnimations(IceGuardian entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone rightArm = getAnimationProcessor().getBone("right_arm");
        rightArm.setHidden(entity.getArmCount() <= 1);
        IBone leftArm = getAnimationProcessor().getBone("left_arm");
        leftArm.setHidden(!entity.canLaunchArm());
    }
}
