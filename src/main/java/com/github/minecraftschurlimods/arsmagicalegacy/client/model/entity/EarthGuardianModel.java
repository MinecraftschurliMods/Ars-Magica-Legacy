package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;

public class EarthGuardianModel extends AMGeckolibHeadModel<EarthGuardian> {
    public EarthGuardianModel() {
        super("earth_guardian");
    }

    @Override
    public void setCustomAnimations(EarthGuardian animatable, long instanceId, AnimationState<EarthGuardian> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        CoreGeoBone rock = getAnimationProcessor().getBone("rock");
        rock.setHidden(!animatable.shouldRenderRock);
    }
}
