package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;

public class EarthGuardianModel extends AMGeckolibHeadModel<EarthGuardian> {
    public EarthGuardianModel() {
        super("earth_guardian");
    }

    @Override
    public void setCustomAnimations(EarthGuardian animatable, long instanceId, AnimationState<EarthGuardian> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        GeoBone rock = getAnimationProcessor().getBone("rock");
        rock.setHidden(!animatable.shouldRenderRock);
    }
}
