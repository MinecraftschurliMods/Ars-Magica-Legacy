package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;

public class NatureGuardianModel extends AMGeckolibHeadModel<NatureGuardian> {
    public NatureGuardianModel() {
        super("nature_guardian");
    }

    @Override
    public void setCustomAnimations(NatureGuardian animatable, long instanceId, AnimationState<NatureGuardian> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        CoreGeoBone scythe = getAnimationProcessor().getBone("scythe");
        scythe.setHidden(!animatable.hasScythe());
    }
}
