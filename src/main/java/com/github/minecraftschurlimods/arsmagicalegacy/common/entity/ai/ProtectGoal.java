package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;

public class ProtectGoal extends DispelGoal<EnderGuardian> {
    public ProtectGoal(EnderGuardian caster) {
        super(caster);
    }

    @Override
    public void stop() {
        super.stop();
        caster.clearFire();
    }
}
