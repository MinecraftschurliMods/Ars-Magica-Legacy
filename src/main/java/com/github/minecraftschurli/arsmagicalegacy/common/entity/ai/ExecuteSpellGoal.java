package com.github.minecraftschurli.arsmagicalegacy.common.entity.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class ExecuteSpellGoal extends Goal {
    private final Mob guardian;

    public ExecuteSpellGoal(Mob guardian) {
        this.guardian = guardian;
    }

    @Override
    public boolean canUse() {
        return false;
    }
}
