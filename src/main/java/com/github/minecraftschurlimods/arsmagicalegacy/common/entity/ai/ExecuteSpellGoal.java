package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class ExecuteSpellGoal extends Goal {
    private final Mob mob;

    public ExecuteSpellGoal(Mob mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        return false;
    }
}
