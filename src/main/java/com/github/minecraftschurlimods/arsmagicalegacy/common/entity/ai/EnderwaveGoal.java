package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class EnderwaveGoal extends Goal {
    private final EnderGuardian enderGuardian;
    private int cooldown = 0;

    public EnderwaveGoal(EnderGuardian enderGuardian) {
        this.enderGuardian = enderGuardian;
    }

    @Override
    public boolean canUse() {
        if (enderGuardian.getTarget() == null || new Vec3(enderGuardian.getX(), enderGuardian.getY(), enderGuardian.getZ()).distanceToSqr(new Vec3(enderGuardian.getTarget().getX(), enderGuardian.getTarget().getY(), enderGuardian.getTarget().getZ())) > 100) {  // maybe wrong
            return false;
        }
        return cooldown-- <= 0;
    }

    @Override
    public void stop() {
        cooldown = 20;
    }

    @Override
    public void tick() {
        if (enderGuardian.getTarget() != null) {
            enderGuardian.getLookControl().setLookAt(enderGuardian.getTarget(), 30, 30);
            if (enderGuardian.getTicksInAction() == 7) {
                enderGuardian.lookAt(enderGuardian.getTarget(), 180, 180);
                enderGuardian.level.playSound(null, enderGuardian, AMSounds.ENDER_GUARDIAN_ATTACK.get(), SoundSource.HOSTILE, 1.0f, (float) (0.5 + enderGuardian.getRandom().nextDouble() * 0.5f));
                //SpellUtils.applyStackStage(NPCSpells.instance.enderGuardian_enderWave, guardian, guardian, guardian.posX, guardian.posY + 0.5f, guardian.posZ, null, guardian.worldObj, false, false, 0);
            } else {
                enderGuardian.lookAt(enderGuardian.getTarget(), 180, 180);
            }
        }
    }
}
