package com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBossGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.FireGuardian;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;

public class FlamethrowerGoal extends AbstractBossGoal<FireGuardian> {
    public FlamethrowerGoal(FireGuardian boss) {
        super(boss, AbstractBoss.Action.LONG_CAST, 20);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.FIRE_GUARDIAN_FLAMETHROWER.get();
    }

    @Override
    public void perform() {
        boss.flamethrower();
    }
}
