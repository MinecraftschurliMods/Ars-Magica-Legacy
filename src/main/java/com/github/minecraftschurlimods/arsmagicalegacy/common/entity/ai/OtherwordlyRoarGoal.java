package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class OtherwordlyRoarGoal extends Goal {
    private final EnderGuardian enderGuardian;
    private       int           cooldown = 0;

    public OtherwordlyRoarGoal(EnderGuardian enderGuardian) {
        this.enderGuardian = enderGuardian;
    }

    @Override
    public boolean canUse() {
        if (enderGuardian.getTarget() == null) {
            return false;
        }
        List<LivingEntity> nearbyEntities = enderGuardian.level.getEntitiesOfClass(LivingEntity.class, enderGuardian.getBoundingBox().inflate(9, 3, 9));
        if (nearbyEntities.size() < 2) {
            return false;
        }
        return cooldown-- <= 0;
    }

    @Override
    public void stop() {
        cooldown = 100;
    }

    @Override
    public void tick() {
        if (enderGuardian.getTarget() != null) {
            if (enderGuardian.getTicksInAction() == 33) {
                enderGuardian.lookAt(enderGuardian.getTarget(), 180, 180);
                // SpellUtils.applyStackStage(NPCSpells.instance.enderGuardian_otherworldlyRoar, guardian, guardian, guardian.posX, guardian.posY + 0.5f, guardian.posZ, null, guardian.worldObj, false, false, 0);
            } else {
                enderGuardian.lookAt(enderGuardian.getTarget(), 180, 180);
            }
        }
    }
}
