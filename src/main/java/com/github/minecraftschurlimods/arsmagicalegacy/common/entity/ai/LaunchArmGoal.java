package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardianArm;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;

public class LaunchArmGoal extends AbstractBossGoal<IceGuardian> {
    public LaunchArmGoal(IceGuardian boss) {
        super(boss, IceGuardian.IceGuardianAction.LAUNCHING);
    }

    @Override
    public boolean canUse() {
        return (boss.hasRightArm() || boss.hasLeftArm()) && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return (boss.hasRightArm() || boss.hasLeftArm()) && super.canContinueToUse();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.ICE_GUARDIAN_LAUNCH_ARM.get();
    }

    @Override
    public void perform() {
        if (!boss.getLevel().isClientSide()) {
            IceGuardianArm entity = IceGuardianArm.create(boss.getLevel());
            entity.moveTo(boss.getX(), boss.getY() + boss.getEyeHeight(), boss.getZ());
            entity.setDeltaMovement(boss.getLookAngle());
            boss.getLevel().addFreshEntity(entity);
        }
    }
}
