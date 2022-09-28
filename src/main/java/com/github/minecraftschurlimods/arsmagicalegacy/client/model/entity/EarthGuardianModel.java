package com.github.minecraftschurlimods.arsmagicalegacy.client.model.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EarthGuardian;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;

public class EarthGuardianModel extends AMGeckolibHeadModel<EarthGuardian> {
    public EarthGuardianModel() {
        super("earth_guardian");
    }

    @Override
    public void setLivingAnimations(EarthGuardian entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone rock = getAnimationProcessor().getBone("rock");
        rock.setHidden(!(entity.getAction() == AbstractBoss.Action.THROW && entity.shouldRenderRock));
    }
}
