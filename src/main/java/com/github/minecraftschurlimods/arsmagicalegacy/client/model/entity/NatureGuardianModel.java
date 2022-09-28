package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.NatureGuardian;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class NatureGuardianModel extends AMGeckolibHeadModel<NatureGuardian> {
    public NatureGuardianModel() {
        super("nature_guardian");
    }

    @Override
    public void setLivingAnimations(NatureGuardian entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone scythe = getAnimationProcessor().getBone("scythe");
        scythe.setHidden(!entity.hasScythe());
    }
}
