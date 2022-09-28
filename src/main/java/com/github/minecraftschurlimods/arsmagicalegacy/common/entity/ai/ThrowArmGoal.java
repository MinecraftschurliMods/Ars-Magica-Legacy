package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.IceGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.WintersGrasp;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;

public class ThrowArmGoal extends AbstractBossGoal<IceGuardian> {
    public ThrowArmGoal(IceGuardian boss) {
        super(boss, AbstractBoss.Action.THROW, 10, 10);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && boss.getTarget() != null && boss.distanceTo(boss.getTarget()) > 4 && boss.canLaunchArm();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.ICE_GUARDIAN_LAUNCH_ARM.get();
    }

    @Override
    public void perform() {
        if (!boss.getLevel().isClientSide()) {
            WintersGrasp entity = WintersGrasp.create(boss.getLevel());
            entity.moveTo(boss.position().add(0, 1, 0).add(boss.getLookAngle()));
            entity.setDeltaMovement(boss.getLookAngle());
            entity.setXRot(boss.getXRot());
            entity.setYRot(boss.getYRot());
            entity.setOwner(boss);
            boss.getLevel().addFreshEntity(entity);
            boss.launchArm();
        }
    }
}
