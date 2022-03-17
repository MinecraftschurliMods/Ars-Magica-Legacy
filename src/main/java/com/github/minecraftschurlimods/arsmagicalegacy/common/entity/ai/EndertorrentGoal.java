package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.EnderGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;

public class EndertorrentGoal extends Goal {
    private final EnderGuardian enderGuardian;
    private       int           cooldown = 0;

    public EndertorrentGoal(EnderGuardian enderGuardian) {
        this.enderGuardian = enderGuardian;
    }

    @Override
    public boolean canUse() {
        if (enderGuardian.getTarget() == null) {
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
            enderGuardian.getLookControl().setLookAt(enderGuardian.getTarget(), 30, 30);
            if (enderGuardian.getTicksInAction() > 15) {
                if ((enderGuardian.getTicksInAction() - 15) % 10 == 0) {
                    //SpellUtils.applyStackStage(NPCSpells.instance.enderGuardian_enderTorrent, guardian, null, guardian.posX, guardian.posY, guardian.posZ, null, guardian.worldObj, false, false, guardian.getTicksInCurrentAction());
                }
                enderGuardian.lookAt(enderGuardian.getTarget(), 15, 180);
            } else if (enderGuardian.getTicksInAction() == 15) {
                enderGuardian.level.playSound(null, enderGuardian, AMSounds.ENDER_GUARDIAN_ATTACK.get(), SoundSource.HOSTILE, 1.0f, (float) (0.5 + enderGuardian.getRandom().nextDouble() * 0.5f));
            } else {
                enderGuardian.lookAt(enderGuardian.getTarget(), 180, 180);
            }
        }
    }
}
