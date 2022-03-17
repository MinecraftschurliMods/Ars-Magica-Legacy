package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import net.minecraft.world.entity.ai.goal.Goal;

public class ProtectGoal extends Goal {
    private final EnderGuardian enderGuardian;
    private int cooldown = 0;

    public ProtectGoal(EnderGuardian enderGuardian) {
        this.enderGuardian = enderGuardian;
    }

    @Override
    public boolean canUse() {
        if (enderGuardian.getTarget() == null || enderGuardian.getTicksSinceLastAttack() > 40) {
            return false;
        }
        return cooldown-- <= 0;
    }

    @Override
    public void stop() {
        cooldown = 20;
        enderGuardian.clearFire();
        //SpellUtils.applyStackStage(NPCSpells.instance.dispel, getEntity(), null, ent.posX, ent.posY, ent.posZ, null, ent.worldObj, false, false, 0);
    }

    @Override
    public void tick() {
        if (enderGuardian.getTarget() != null) {
            enderGuardian.getLookControl().setLookAt(enderGuardian.getTarget(), 30, 30);
        }
    }
}
