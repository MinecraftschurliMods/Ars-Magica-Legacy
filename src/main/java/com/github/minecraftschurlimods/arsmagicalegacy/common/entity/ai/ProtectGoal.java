package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;

public class ProtectGoal extends DispelGoal<EnderGuardian> {
    public ProtectGoal(EnderGuardian caster) {
        super(caster);
    }

    @Override
    public boolean canUse() {
        return !caster.isCastingSpell() && caster.getTarget() != null && !caster.getTarget().isDeadOrDying() && caster.getRandom().nextBoolean() && (caster.getActiveEffects().size() > 0 || caster.isOnFire());
    }

    @Override
    public void stop() {
        super.stop();
        caster.clearFire();
    }
}
