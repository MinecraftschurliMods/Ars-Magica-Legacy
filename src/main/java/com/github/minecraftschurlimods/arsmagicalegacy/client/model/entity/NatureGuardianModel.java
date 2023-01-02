package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class NatureGuardianModel extends AMGeckolibHeadModel<NatureGuardian> {
    public NatureGuardianModel() {
        super("nature_guardian");
    }

    @Override
    public void setCustomAnimations(NatureGuardian animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        IBone scythe = getAnimationProcessor().getBone("scythe");
        scythe.setHidden(!animatable.hasScythe());
    }
}
