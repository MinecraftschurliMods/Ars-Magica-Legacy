package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class EarthGuardianModel extends AMGeckolibHeadModel<EarthGuardian> {
    public EarthGuardianModel() {
        super("earth_guardian");
    }

    @Override
    public void setCustomAnimations(EarthGuardian animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        IBone rock = getAnimationProcessor().getBone("rock");
        rock.setHidden(!animatable.shouldRenderRock);
    }
}
